const submitButton = document.getElementById("submitButton")
let x
let y
let r
const form = document.getElementById("form")
loadTableFromLocalStorage()
function radioClick(e) {
    x = +e.target.value
}

for (let i = 0; i < form.x.length; i++) {
    form.x[i].addEventListener("click", radioClick)
}

function errorMessage(elementId, inputField, errorMessage) {
    var error = document.getElementById(elementId)
    error.innerHTML = "<span style='color: red; animation: 3s fadeOut ease-in forwards'>" +
        `${errorMessage}</span>`
    if (errorMessage !== undefined) {
        document.getElementById(inputField).value = ""
    }
}

submitButton.onclick = function (e) {
    e.preventDefault()
    if (x === undefined) {
        errorMessage("xError", "Р’С‹Р±РµСЂРёС‚Рµ X!")
    }

    y = document.getElementById("inputY").value;
    r = document.getElementById("inputR").value;

    const regexp = /^[-+]?[0-9]*[.,][0-9]+$|^[-+]?[0-9]+$/
    if (!regexp.test(y) || y === undefined) {
        errorMessage("yError", "inputY","Р’РІРµРґРёС‚Рµ Y РІ РїСЂР°РІРёР»СЊРЅРѕРј С„РѕСЂРјР°С‚Рµ!")
    }
    if (!regexp.test(r) || r === undefined) {
        errorMessage("rError", "inputR", "Р’РІРµРґРёС‚Рµ R РІ РїСЂР°РІРёР»СЊРЅРѕРј С„РѕСЂРјР°С‚Рµ!")
    }
    if (!(-3 <= y && y <= 3)) {
        errorMessage("yError",  "inputY", "Р’РІРµРґРёС‚Рµ Р·РЅР°С‡РµРЅРёРµ Y РІ РїСЂРµРґРµР»Р°С… [-3;3]!")
    }
    if (!(2 <= r && r <= 5)) {
        errorMessage("rError", "inputR", "Р’РІРµРґРёС‚Рµ Р·РЅР°С‡РµРЅРёРµ R РІ РїСЂРµРґРµР»Р°С… [2;5]!")
    }
    else {
        hit(x, y, r)
        fetch('/fcgi-bin/server.jar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `x=${x}&y=${y}&r=${r}`
        }).then(response => response.json()).then(json => {
            console.log(json)
            if (json.error == null) {
                updateTable(jsonToDict(json))
            } else {
                // console.log(data.error)
            }
        }).catch(error => {
            alert("РћС€РёР±РєР° СЃРµСЂРІРµСЂР°! " + error.toString())
        })

    }
}
function hit(x, y, r) {
    const svg = document.getElementById("svg")
    const rPxSize = svg.clientWidth / 3
    const svgCenterX = 150
    const svgCenterY = 150
    const scale = rPxSize / r
    const dot = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    dot.setAttributeNS(null, "r", "1%")
    dot.setAttributeNS(null, "cx", (svgCenterX + x * scale).toString())
    dot.setAttributeNS(null, "cy", (svgCenterY - y * scale).toString())
    dot.setAttributeNS(null, "fill", "red")
    dot.setAttributeNS(null, "visibility", "visible")
    svg.appendChild(dot)
}


function updateTable(dict = {}) {
    const table = document.getElementById("statsTable")
    const row = table.insertRow()
    const xCell = row.insertCell(0)
    const yCell = row.insertCell(1)
    const rCell = row.insertCell(2)
    const statusCell = row.insertCell(3)
    const dateCell = row.insertCell(4)
    const executionTimeCell = row.insertCell(5)
    xCell.textContent = dict["x"]
    yCell.textContent = dict["y"]
    rCell.textContent = dict["r"]
    statusCell.textContent = dict["status"]
    dateCell.textContent = dict["currentTime"]
    executionTimeCell.textContent = dict["executionTime"]
    updateLocalStorage(localStorage.length + 1, dictToString(dict))
    console.log(`localStorage.length + 1: ${localStorage.length + 1}, stringItem ${dictToString(dict)}`)
}
function updateLocalStorage(index, dict){
    localStorage.setItem(index.toString(), dictToString(dict))
}
function dictToString(dict) {
    return `x: ${dict["x"]},
            y: ${dict["y"]}
            r: ${dict["r"]}
            status: ${dict["status"]}
            currentTime: ${dict["currentTime"]}
            executionTime: ${dict["executionTime"]}
            `
}
function stringToDict(string) {
    let statsDict = string.split(", ")
    let dictItem = []
    for (let pair in statsDict) {
        let keyVal = pair.split(": ")
        dictItem[keyVal[0]] = keyVal[1]

    }
    console.log(`parsedDictItem: ${dictItem}`)
    return dictItem
}

function jsonToDict(json) {
    let dict = {}
    dict["x"] = json.x
    dict["y"] = json.y
    dict["r"] = json.r
    dict["status"] = json.status
    dict["currentTime"] = json.currentTime
    dict["executionTime"] = json.executionTime
    return dict
}

function loadTableFromLocalStorage(){
    let localStorageTableLinesCount = window.localStorage.length
    for (let i = 0; i < localStorageTableLinesCount; i++) {
        let item = window.localStorage.getItem(i.toString())
        let dictItem = stringToDict(item)
        updateTable(dictItem)
        console.log(`localStorageLineIndex: ${i} dictItem: ${dictItem}`)
    }
}