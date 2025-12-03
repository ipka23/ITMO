let x
let y
let yInput
let r
let form
let submitButton
let rInput
let xInput

// const svg = document.getElementById("svg")
const rPxSize = 100
const svgCenterX = 150
const svgCenterY = 150
let mainSvg

let scale
let dot
let previousR
document.addEventListener("DOMContentLoaded", function () {
    form = document.getElementById("mainForm")

    submitButton = document.getElementById("mainForm:submitButton")
    submitButton.addEventListener("click", sendPointToServer)

    rInput = document.getElementById("mainForm:inputR")
    rInput.addEventListener("change", handleChangeR)

    xInput = document.getElementById("mainForm:inputX")
    // xInput.addEventListener("change", handleChangeX)

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
})


// function getSelectedR() {
//     const radioButtons = document.getElementsByName('mainForm:inputR');
//
//     for (const radio of radioButtons) {
//         if (radio.checked) {
//             return radio.value;
//         }
//     }
//
//     return undefined;
// }

// function handleChangeX() {
//     if (xInput.value < -3 || xInput.value > 3) {
//         if (xInput.value < 10) {
//             xInput.value = ""
//         } else {
//             xInput.value = xInput.value[0]
//         }
//     }
// }

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

function makeFetch(method, body, contentType) {
    let currentLang = localStorage.getItem("lang")
    if (method === "POST") {
        alert("Ошибка: Введите GET запрос!")
    } else if (method === "GET") {
        if (body.drawByClick === false) {
            fetch(`http://localhost:25230/lab2/controller?x=${body.x}&y=${body.y}&r=${body.r}&lang=${currentLang}`, {
                method: "GET",
            }).then(response => {
                if (!response.ok) {
                    throw new Error(`${response.status}`)
                } else {
                    return response.text()
                }
            }).then(resultHtml => {
                document.open()
                document.write(resultHtml)
                document.close()
            }).catch(error => {
                alert("Ошибка: " + error.toString())
            })

        } else {
            fetch(`http://localhost:25230/lab2/controller?x=${body.x}&y=${body.y}&r=${body.r}&drawByClick=${body.drawByClick}&lang=${currentLang}`, {
                method: "GET",
            }).then(response => response.json())
                .then(json => {
                    if (json.status !== "error") {
                        let point = jsonToDict(json)
                        drawPoint(point.x, point.y, point.r)
                        updateTable(point, true)
                    } else {
                        console.log(json)
                    }
                }).catch(error => {
                alert("Ошибка: " + error.toString())
            })
        }
    }

}

function errorMessage(elementId, inputField, message) {
    let error = document.getElementById(elementId)
    error.innerHTML = "<span style='color: red; animation: 3s fadeOut ease-in forwards'>" +
        `${message}</span>`

    if (message !== undefined && inputField !== undefined && (inputField !== "mainForm:inputX" || inputField !== "mainForm:inputR")) {
        const inputEl = document.getElementById(inputField)
        if (inputEl) inputEl.value = ""
    }
}

function jsonToDict(json) {
    let dict = {}
    dict["x"] = json.result.x
    dict["y"] = json.result.y
    dict["r"] = json.result.r
    dict["status"] = json.result.status
    dict["currentTime"] = json.result.currentTime
    dict["executionTime"] = json.result.executionTime
    return dict
}

function dictToJson(dict) {
    return JSON.stringify(dict)
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

function updateTable(dict, firstAdd) {
    const table = document.getElementById("mainForm:statsTable")
    const row = table.insertRow()
    const xCell = row.insertCell(0)
    const yCell = row.insertCell(1)
    const rCell = row.insertCell(2)
    const statusCell = row.insertCell(3)
    const dateCell = row.insertCell(4)
    const executionTimeCell = row.insertCell(5)
    xCell.style.maxWidth = '15px'
    yCell.style.maxWidth = '15px'
    rCell.style.maxWidth = '15px'

    if (dict["x"].length > 5) {
        yCell.title = dict["y"]
        yCell.style.overflow = "hidden"
        dict["x"] = dict["x"].substring(0, 5) + "..."
    }
    xCell.textContent = dict["x"]

    if (dict["y"].length > 5) {
        yCell.title = dict["y"]
        yCell.style.overflow = "hidden"
        dict["y"] = dict["y"].substring(0, 5) + "..."
    }
    yCell.textContent = dict["y"]

    if (dict["r"].length > 5) {
        rCell.title = dict["r"]
        rCell.style.overflow = "hidden"
        dict["r"] = dict["r"].substring(0, 5) + "..."
    }
    rCell.textContent = dict["r"]

    statusCell.textContent = dict["status"]
    dateCell.textContent = dict["currentTime"]
    executionTimeCell.textContent = dict["executionTime"]
}

// Actions with points ===============================================

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

function loadPoints() {
    let points = document.querySelectorAll("circle")
    for (let i = 0; i < points.length; i++) {
        let p = points[i]
        if (r === undefined) {
            resetPointColor(p)
        }
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

function resetPointColor(point) {
    point.setAttributeNS(null, "fill", "black")
}
function drawPoint(x, y, r) {
    dot = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    scale = rPxSize / r
    dot.setAttributeNS(null, "r", "1%")
    setPointXY(x, y, dot, scale)
    changePointColor(x, y, r, dot)
    dot.setAttributeNS(null, "visibility", "visible")
    mainSvg.appendChild(dot)

}


function drawPointByClick(e) {
    // const svg = document.getElementById("svg")
    // const r = rInput ? rInput.value : undefined
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
        errorMessage("svgError", undefined, "Выберите радиус R!")
    }
    else {
        remotePointCommand([
            {name: 'x', value: coords.x},
            {name: 'y', value: coords.y},
            {name: 'r', value: r}
        ])
        drawPoint(coords.x, coords.y, r)
        // updateTable(point, true)

    }

}


function sendPointToServer(e) {
    e.preventDefault()
    const regexp = /^[-+]?[0-9]*[.,][0-9]+$|^[-+]?[0-9]+$/
    // x = xInput.value;
    // yValues = initYvalues()
    y = yInput.value
    // r = rInput ? rInput.value : undefined;
    let successX = true
    let successY = true
    let successR = true

    if (x === undefined || x === "") {
        console.log(`x: ${x}`)
        errorMessage("mainForm:xError", "inputX", "Выберите X!")
        successX = false
    }

    if (y === undefined || y === "") {
        errorMessage("mainForm:yError", "inputY", 'Введите Y!')
        successY = false
    }
    if (r === undefined || r === "") {
        console.log(`r: ${r}`)
        errorMessage("mainForm:rError", "inputR", 'Выберите R!')
        successR = false
    }

    if (!regexp.test(y) && successY) {
        errorMessage("mainForm:xError", "inputX", 'Введите Y в правильном формате!')
        successX = false
    }

    if (!(-3 <= y && y <= 3) && successY) {
        errorMessage("mainForm:xError", "inputX", 'Введите значение Y в пределах [-3;3]!')
        successX = false
    }
     else if (successX && successY && successR) {
        drawPoint(x, y, r)


        // ...
        // makeFetch("GET", {x: x, y: y, r: r, drawByClick: false}, 'application/x-www-form-urlencoded')
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
        const halfRNegative = r.div(2).negated();  // -r/2
        const xCondition = x.greaterThanOrEqualTo(halfRNegative);
        const yCondition = y.lessThanOrEqualTo(r);

        return xCondition && yCondition;
    }
    return false;
}