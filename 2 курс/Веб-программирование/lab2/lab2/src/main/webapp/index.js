const submitButton = document.getElementById("submitButton")
let x
let y
let r
let form = document.getElementById("form")

function radioClick(e) {
    x = +e.target.value
}

for (let i = 0; i < form.x.length; i++) {
    form.x[i].addEventListener("click", radioClick)
}

// if (localStorage.length !== 0) {
//     loadTableFromLocalStorage()
// } else sendStorageLength()


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
                // updateTable(jsonToDict(json), true)
            }).catch(error => {
            alert("Ошибка сервера!: " + error.toString())
        })
    }

}


function errorMessage(elementId, inputField, errorMessage) {
    let error = document.getElementById(elementId)
    error.innerHTML = "<span style='color: red; animation: 3s fadeOut ease-in forwards'>" +
        `${errorMessage}</span>`
    if (errorMessage !== undefined && inputField !== "inputX") {
        document.getElementById(inputField).value = ""
    }
}

submitButton.onclick = function (e) {
    e.preventDefault()
    const regexp = /^[-+]?[0-9]*[.,][0-9]+$|^[-+]?[0-9]+$/
    y = document.getElementById("inputY").value;
    r = document.getElementById("inputR").value;
    let successX = true
    let successY = true
    let successR = true
    if (x === undefined) {
        errorMessage("xError", "inputX", "Выберите X!")
        successX = false
    }

    if (y === undefined) {
        errorMessage("yError", "inputY", "Введите Y!")
        successY = false
    }
    if (r === undefined) {
        errorMessage("rError", "inputR", "Введите R!")
        successR = false
    }

    if (!regexp.test(y) && successY) {
        errorMessage("yError", "inputY", "Введите Y в правильном формате!")
        successY = false
    }
    if (!regexp.test(r) && successR) {
        errorMessage("rError", "inputR", "Введите R в правильном формате!")
        successR = false
    }

    if (!(-3 <= y && y <= 3) && successY) {
        errorMessage("yError", "inputY", "Введите значение Y в пределах [-3;3]!")
        successY = false
    }
    if (!(2 <= r && r <= 5) && successR) {
        errorMessage("rError", "inputR", "Введите значение R в пределах [2;5]!")
        successR = false
    } else if (successX && successY && successR) {
        hit(x, y, r)
        makeFetch("GET", `x=${x}&y=${y}&r=${r}`, 'application/x-www-form-urlencoded')

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
        sendStorageLength()
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
        let dictItem = stringToDict(item)
        dictItem["index"] = index
        // sendStorageItem(dictToJson(dictItem))

        updateTable(dictItem, false)
        hit(dictItem["x"], dictItem["y"], dictItem["r"])
        console.log(`localStorageLineIndex: ${i} dictItem: ${dictItem}`)
    }
}