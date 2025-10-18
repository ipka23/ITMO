let x
let yValues
let r
let form
let submitButton


if (localStorage.length !== 0) {
    loadTableFromLocalStorage()
}


// function sendStorageLength() {
//     makeFetch("GET", `storageLength=${localStorage.length}`)
// }

function sendStorageItem(jsonItem) {
    makeFetch('POST', jsonItem, 'application/json')
}

function makeFetch(method, body, contentType) {
    if (method === "POST") {
        alert("Ошибка: Введите GET запрос!")
    } else if (method === "GET") {
        fetch(`http://localhost:25230/lab2/controller?${body}`, {
            method: "GET"
        }).then(response => response.json())
            .then(json => {
                console.log(json)
                let point = jsonToDict(json)
                hit(point.x, point.y, point.r, point.status)
                updateTable(point, true)
                window.location.href = "http://localhost:25230/lab2/result"
            }).catch(error => {
            alert("Ошибка сервера!: " + error.toString())
        })
    }

}


function errorMessage(elementId, inputField, errorMessage) {
    let error = document.getElementById(elementId)
    error.innerHTML = "<span style='color: red; animation: 3s fadeOut ease-in forwards'>" +
        `${errorMessage}</span>`
    if (errorMessage !== undefined && inputField !== "inputY") {
        document.getElementById(inputField).value = ""
    }
}

function hit(x, y, r, status) {
    const svg = document.getElementById("svg")
    const rPxSize = svg.clientWidth / 3
    const svgCenterX = 150
    const svgCenterY = 150
    const scale = rPxSize / r
    const dot = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    dot.setAttributeNS(null, "r", "1%")
    dot.setAttributeNS(null, "cx", (svgCenterX + x * scale).toString())
    dot.setAttributeNS(null, "cy", (svgCenterY - y * scale).toString())
    if (status === "Попадание") {
        dot.setAttributeNS(null, "fill", "green")
    } else {
        dot.setAttributeNS(null, "fill", "red")
    }
    dot.setAttributeNS(null, "visibility", "visible")
    svg.appendChild(dot)
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
    yCell.style.maxWidth = '15px'
    rCell.style.maxWidth = '15px'
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
    if (firstAdd) {
        updateLocalStorage(localStorage.length, dict)
    }
}

function updateLocalStorage(index, dict) {
    let item = dictToString(dict)
    console.log(`index: ${localStorage.length}\nitem: ${item}`)
    localStorage.setItem(index.toString(), item)
}

function dictToString(dict) {
    return `x: ${dict["x"]},
            y: ${dict["y"]},
            r: ${dict["r"]},
            status: ${dict["status"]},
            currentTime: ${dict["currentTime"]},
            executionTime: ${dict["executionTime"]}`
}

function stringToDict(string) {
    let statsList = string.split(",\n")
    let dictItem = {}
    for (let i = 0; i < statsList.length; i++) {
        let keyVal = statsList[i].split(": ")
        let key = keyVal[0].trim()
        let value = keyVal[1].trim()
        dictItem[key] = value

    }
    // console.log(`parsedDictItem: ${dictItem}`)
    return dictItem
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
    let json = JSON.stringify(dict)
    console.log(json)
    return json
}

function loadTableFromLocalStorage() {
    let storageLength = window.localStorage.length
    for (let i = 0; i < storageLength; i++) {
        let index = i.toString()
        let item = window.localStorage.getItem(index)

        console.log(item)
        if (item === null) continue
        let dictItem = stringToDict(item)
        dictItem["index"] = index
        // sendStorageItem(dictToJson(dictItem))

        updateTable(dictItem, false)
        hit(dictItem["x"], dictItem["y"], dictItem["r"])
        console.log(`localStorageLineIndex: ${i} dictItem: ${dictItem}`)
    }
}

function initYvalues() {
    let yItems = form.y
    let checkedValues = []
    for (let i = 0; i < yItems.length; i++){
        let y = yItems[i]
        if (y.checked) {
            checkedValues.push(y.value)
        }
        //
        console.log(checkedValues)
    }
    return checkedValues
}

function sendPoint() {
    const regexp = /^[-+]?[0-9]*[.,][0-9]+$|^[-+]?[0-9]+$/
    x = document.getElementById("inputX").value;
    yValues = initYvalues()
    r = document.getElementById("inputR").value;
    let successX = true
    let successY = true
    let successR = true
    if (x === undefined) {
        errorMessage("xError", "inputX", "Введите X!")
        successX = false
    }

    if (yValues.length === 0) {
        errorMessage("yError", "inputY", "Выберите Y!")
        successY = false
    }
    if (r === undefined) {
        errorMessage("rError", "inputR", "Введите R!")
        successR = false
    }

    if (!regexp.test(x) && successY) {
        errorMessage("xError", "inputX", "Введите X в правильном формате!")
        successY = false
    }
    if (!regexp.test(r) && successR) {
        errorMessage("rError", "inputR", "Введите R в правильном формате!")
        successR = false
    }

    if (!(-3 <= x && x <= 3) && successY) {
        errorMessage("xError", "inputX", "Введите значение X в пределах [-3;3]!")
        successY = false
    }
    if (!(1 <= r && r <= 4) && successR) {
        errorMessage("rError", "inputR", "Введите значение R в пределах [1;4]!")
        successR = false
    } else if (successX && successY && successR) {

        for (let i = 0; i < yValues.length; i++) {
            let y = yValues[i]
            makeFetch("GET", `x=${x}&y=${y}&r=${r}`, 'application/x-www-form-urlencoded')
        }

    }
}

document.addEventListener("DOMContentLoaded", function() {
    form = document.getElementById("form")
    submitButton = document.getElementById("submitButton")
    submitButton.addEventListener("click", sendPoint)
})