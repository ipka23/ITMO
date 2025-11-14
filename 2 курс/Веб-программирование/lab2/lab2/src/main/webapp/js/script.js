import { EasyIntl } from './libs/easy-intl/index.js'
import dictionary from './intl/dictionary.js'

const intl = new EasyIntl({
    autorun: false,
    dictionary
})
let lang = localStorage.getItem("lang")
if (lang === null) lang = "ru-RU"

let x
let yValues
let r
let form
let submitButton
let rInput
let xInput

const svg = document.getElementById("svg")
const rPxSize = svg.clientWidth / 3
const svgCenterX = 150
const svgCenterY = 150
let scale
let dot
let previousR
document.addEventListener("DOMContentLoaded", function () {

    document.getElementById(lang).setAttribute("selected", "selected")
    intl.locale = lang
    intl.localize()

    form = document.getElementById("form")
    submitButton = document.getElementById("submitButton")
    if (submitButton) submitButton.addEventListener("click", sendPointToServer)
    rInput = document.getElementById("inputR")
    if (rInput) rInput.addEventListener("input", handleInputR)
    xInput = document.getElementById("inputX")
    if (xInput) xInput.addEventListener("input", handleInputX)
    if (svg) svg.addEventListener("click", drawPointByClick)

    const localeSelector = document.getElementById("localeSelector")
    localeSelector.onchange = (e) => {
        let lang = e.target.value
        intl.locale = lang
        localStorage.setItem("lang", lang)
        intl.localize()
        updateHitMiss('statsTable')
    }

    intl.localize()
    updateHitMiss('statsTable')
})


// убрать
export function updateHitMiss(tableID) {
    let currentLang = localStorage.getItem("lang")
    if (lang === null) currentLang = lang
    const table = document.getElementById(tableID)

    for (let r = 0, n = table.rows.length; r < n; r++) {
        for (let c = 0, m = table.rows[r].cells.length; c < m; c++) {
            let value = table.rows[r].cells[c].innerHTML.trim()
            if (value === dictionary["ru-RU"].hit || value === dictionary["eo-EO"].hit || value === dictionary["en-US"].hit) {
                table.rows[r].cells[c].innerHTML = dictionary[currentLang].hit
            } else if (value === dictionary["ru-RU"].miss || value === dictionary["eo-EO"].miss || value === dictionary["en-US"].miss){
                table.rows[r].cells[c].innerHTML = dictionary[currentLang].miss
            }
        }
    }
}

function handleInputX() {
    if (xInput.value < -3 || xInput.value > 3) {
        if (xInput.value < 10) {
            xInput.value = ""
        } else {
            xInput.value = xInput.value[0]
        }
    }
}

function handleInputR() {
    const newR = rInput.value
    if (newR < 1 || newR > 4) {
        if (newR < 10) {
            rInput.value = ""
            return
        } else {
            rInput.value = rInput.value[0]
            return
        }
    }
    if (newR === "" || isNaN(newR) || newR === undefined) return
    if (rInput.value === "" || isNaN(rInput.value) || rInput.value === undefined) return;
    changeRadiusOnAxis(previousR, newR)
    r = newR
    previousR = newR
}

function makeFetch(method, body, contentType) {
    let currentLang = localStorage.getItem("lang")
    if (lang === null) currentLang = lang
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

    if (message !== undefined && inputField !== undefined && inputField !== "inputY") {
        const inputEl = document.getElementById(inputField)
        if (inputEl) inputEl.value = ""
    }
}

function jsonToDict(json) {
    let dict = {}
    dict["x"] = json.result.x
    dict["y"] = json.result.y
    dict["r"] = json.result.r
    let serverStatus = json.result.status
    const current = localStorage.getItem("lang") || lang
    if (serverStatus === dictionary["ru-RU"].hit || serverStatus === dictionary["eo-EO"].hit || serverStatus === dictionary["en-US"].hit) {
        dict["status"] = dictionary[current].hit
    } else if (serverStatus === dictionary["ru-RU"].miss || serverStatus === dictionary["eo-EO"].miss || serverStatus === dictionary["en-US"].miss) {
        dict["status"] = dictionary[current].miss
    } else {
        dict["status"] = serverStatus
    }
    dict["currentTime"] = json.result.currentTime
    dict["executionTime"] = json.result.executionTime
    return dict
}

function dictToJson(dict) {
    let json = JSON.stringify(dict)
    return json
}

function svgToMathCoords(svgX, svgY, r) {
    const scale = rPxSize / r
    return {
        x: ((svgX - svgCenterX) / scale).toFixed(2),
        y: ((svgCenterY - svgY) / scale).toFixed(2)
    }
}

function initYvalues() {
    let yItems = form.y
    let checkedValues = []
    for (let i = 0; i < yItems.length; i++) {
        let y = yItems[i]
        if (y.checked) {
            checkedValues.push(y.value)
        }
    }
    return checkedValues
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
    const table = document.getElementById("statsTable")
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
function drawPoint(x, y, r) {
    dot = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    scale = rPxSize / r
    dot.setAttributeNS(null, "r", "1%")
    setPointXY(x, y, dot, scale)
    changePointColor(x, y, r, dot)
    dot.setAttributeNS(null, "visibility", "visible")
    svg.appendChild(dot)
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

function drawPointByClick(e) {
    const r = rInput ? rInput.value : undefined
    const absoluteX = e.clientX
    const absoluteY = e.clientY
    const absolutePoint = svg.createSVGPoint()
    absolutePoint.x = absoluteX
    absolutePoint.y = absoluteY
    const svgPoint = absolutePoint.matrixTransform(svg.getScreenCTM().inverse())
    const svgX = svgPoint.x
    const svgY = svgPoint.y

    const coords = svgToMathCoords(svgX, svgY, r)

    if (r === undefined || r === "") {
        const cur = localStorage.getItem("lang") || lang
        errorMessage("svgError", undefined, dictionary[cur].rInputError)
    } else {
        makeFetch("GET", {x: coords.x, y: coords.y, r: r, drawByClick: true})
    }

}

function sendPointToServer(e) {
    e.preventDefault()
    const regexp = /^[-+]?[0-9]*[.,][0-9]+$|^[-+]?[0-9]+$/
    x = document.getElementById("inputX").value;
    yValues = initYvalues()
    r = rInput ? rInput.value : undefined;
    let successX = true
    let successY = true
    let successR = true
    let cur = localStorage.getItem("lang")
    if (cur == null) cur = lang

    if (x === undefined || x === "") {
        errorMessage("xError", "inputX", dictionary[cur].xError)
        successX = false
    }

    if (yValues.length === 0) {
        errorMessage("yError", "inputY", dictionary[cur].yInputError)
        successY = false
    }
    if (r === undefined || r === "") {
        errorMessage("rError", "inputR", dictionary[cur].rInputError)
        successR = false
    }

    if (!regexp.test(x) && successX) {
        errorMessage("xError", "inputX", dictionary[cur].xIncorrectError)
        successX = false
    }
    if (!regexp.test(r) && successR) {
        errorMessage("rError", "inputR", dictionary[cur].rIncorrectError)
        successR = false
    }

    if (!(-3 <= x && x <= 3) && successX) {
        errorMessage("xError", "inputX", dictionary[cur].xBoundsError)
        successX = false
    }
    if (!(1 <= r && r <= 4) && successR) {
        errorMessage("rError", "inputR", dictionary[cur].rBoundsError)
        successR = false
    } else if (successX && successY && successR) {

        for (let i = 0; i < yValues.length; i++) {
            let y = yValues[i]
            makeFetch("GET", {x: x, y: y, r: r, drawByClick: false}, 'application/x-www-form-urlencoded')
        }

    }
}

function checkPointHit(x, y, r) {
    return  checkTriangle(+x, +y, +r) || checkRectangle(+x, +y, +r) || checkCircle(+x, +y, +r);
}

function checkCircle(x, y, r) {
    if (x >= 0 && y <= 0) {
        return x ** 2 + y ** 2 <= r ** 2;
    }
    return false
}

function checkTriangle(x, y, r) {
    if (x <= 0 && y >= 0) {
        return y < x + r
    }
    return false
}

function checkRectangle(x, y, r) {
    if (x <= 0 && y <= 0) {
        return x > -r / 2 && y > -r;
    }
    return false
}