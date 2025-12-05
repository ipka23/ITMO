let x
let y
let yInput
let r
let form
let submitButton
let rInput
let xInput

const rPxSize = 100
const svgCenterX = 150
const svgCenterY = 150
let mainSvg

let scale
let dot
let previousR
document.addEventListener("DOMContentLoaded", function () {
    form = document.getElementById("mainForm")



    rInput = document.getElementById("mainForm:inputR")
    rInput.addEventListener("change", handleChangeR)

    xInput = document.getElementById("mainForm:inputX")

    yInput = document.getElementById("mainForm:inputY")
    yInput.value = ""
    yInput.addEventListener("input", handleInputY)

    mainSvg = document.getElementById("mainSvg")
    mainSvg.addEventListener("click", drawPointByClick)

    let xRadioValues = document.querySelectorAll('input[name="mainForm:inputX"]')
    for (let i = 0; i < xRadioValues.length; i++) {
        xRadioValues[i].checked = false
        xRadioValues[i].addEventListener("click", function (e){
            x = +e.target.value
        })
    }

    let rRadioValues = document.querySelectorAll('input[name="mainForm:inputR"]')
    for (let i = 0; i < rRadioValues.length; i++) {
        rRadioValues[i].checked = false
        rRadioValues[i].addEventListener("click", function (e){
            r = +e.target.value
        })
    }
    submitButton = document.getElementById("mainForm:submitButton")
    submitButton.addEventListener("click", drawPoint)
})


function handleInputY() {
    if (yInput.value < -3 || yInput.value > 3) {
        if (yInput.value < 10) {
            yInput.value = ""
        } else {
            yInput.value = yInput.value[0]
        }
    }
}

function handleChangeR() {
    const newR = r
    console.log(newR)
    if (newR === "" || isNaN(newR) || newR === undefined) return
    if (r === "" || isNaN(r) || r === undefined) return;
    changeRadiusOnAxis(previousR, newR)
    r = newR
    previousR = newR
}


function svgToMathCoords(svgX, svgY, r) {
    const scale = rPxSize / r
    return {
        x: ((svgX - svgCenterX) / scale).toFixed(2),
        y: ((svgCenterY - svgY) / scale).toFixed(2)
    }
}


function changeRadiusOnAxis(oldR, newR) {
    let r = newR
    document.getElementById("-rx").textContent = `-${r}`
    document.getElementById("-ry").textContent = `-${r}`
    document.getElementById("-rx/2").textContent = `-${r / 2}`
    document.getElementById("-ry/2").textContent = `-${r / 2}`
    document.getElementById("rx").textContent = `${r}`
    document.getElementById("ry").textContent = `${r}`
    document.getElementById("rx/2").textContent = `${r / 2}`
    document.getElementById("ry/2").textContent = `${r / 2}`
    changePointR(oldR, newR)
}


function setPointXY(x, y, point, scale) {
    point.setAttributeNS(null, "cx", (svgCenterX + x * scale).toString())
    point.setAttributeNS(null, "cy", (svgCenterY - y * scale).toString())
}

function changePointR(oldR, newR) {
    let points = document.querySelectorAll("circle")
    for (let i = 0; i < points.length; i++) {
        let p = points[i]
        let svgX = +p.getAttributeNS(null, "cx")
        let svgY = +p.getAttributeNS(null, "cy")
        if (oldR === undefined) oldR = newR
        let mathCoords = svgToMathCoords(svgX, svgY, oldR)
        let x = +mathCoords.x
        let y = +mathCoords.y

        scale = rPxSize / newR
        setPointXY(x, y, p, scale)

        changePointColor(x, y, newR, p)
    }
}


function changePointColor(x, y, r, point) {
    x = new Decimal(x);
    y = new Decimal(y);
    r = new Decimal(r);
    let hitFlag = checkPointHit(x, y, r)
    if (hitFlag) {
        point.setAttributeNS(null, "fill", "green")
    } else {
        point.setAttributeNS(null, "fill", "red")
    }
}

function drawPoint() {
    dot = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    scale = rPxSize / r
    dot.setAttributeNS(null, "r", "1%")
    y = yInput.value
    setPointXY(x, y, dot, scale)
    changePointColor(x, y, r, dot)
    dot.setAttributeNS(null, "visibility", "visible")
    mainSvg.appendChild(dot)

}


function drawPointByClick(e) {
    const absoluteX = e.clientX
    const absoluteY = e.clientY
    const absolutePoint = mainSvg.createSVGPoint()
    absolutePoint.x = absoluteX
    absolutePoint.y = absoluteY
    const svgPoint = absolutePoint.matrixTransform(mainSvg.getScreenCTM().inverse())
    const svgX = svgPoint.x
    const svgY = svgPoint.y

    const coords = svgToMathCoords(svgX, svgY, r)

    if (r === undefined || r === "") {
        document.getElementById("svgError").innerHTML = '<span style=\'color: red; animation: 3s fadeOut ease-in forwards\'>Выберите R!</span>'
    }
    else {
        remotePointCommand([
            {name: 'x', value: coords.x},
            {name: 'y', value: coords.y},
            {name: 'r', value: r}
        ])
        x = coords.x
        y = coords.y
        drawPoint()
    }

}


function checkPointHit(x, y, r) {
    x = new Decimal(x);
    y = new Decimal(y);
    r = new Decimal(r);

    return checkTriangle(x, y, r) || checkRectangle(x, y, r) || checkCircle(x, y, r);
}

function checkCircle(x, y, r) {
    if (x.lessThanOrEqualTo(0) && y.lessThanOrEqualTo(0)) {
        const halfR = r.div(2);
        const radiusSquared = halfR.pow(2);
        const distanceSquared = x.pow(2).plus(y.pow(2));

        return distanceSquared.lessThanOrEqualTo(radiusSquared);
    }
    return false;
}

function checkTriangle(x, y, r) {
    if (x.greaterThanOrEqualTo(0) && y.greaterThanOrEqualTo(0)) {
        const twoX = x.times(2);
        const rightSide = r.minus(twoX);

        return y.lessThanOrEqualTo(rightSide);
    }
    return false;
}

function checkRectangle(x, y, r) {
    if (x.lessThanOrEqualTo(0) && y.greaterThanOrEqualTo(0)) {
        const halfRNegative = r.div(2).negated();
        const xCondition = x.greaterThanOrEqualTo(halfRNegative);
        const yCondition = y.lessThanOrEqualTo(r);

        return xCondition && yCondition;
    }
    return false;
}