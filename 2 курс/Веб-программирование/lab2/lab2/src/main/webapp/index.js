let x
let yValues
let r
let form
let submitButton
let rInput
let svg
let rPxSize
const svgCenterX = 150
const svgCenterY = 150
let scale
let dot
let previousR

document.addEventListener("DOMContentLoaded", function () {
    // svg params
    svg = document.getElementById("svg")
    rPxSize = svg.clientWidth / 3


    form = document.getElementById("form")
    submitButton = document.getElementById("submitButton")
    submitButton.addEventListener("click", sendPoint)
    rInput = document.getElementById("inputR")
    rInput.addEventListener("input", function (e) {
        const newR = e.target.value
        if (newR === "" || isNaN(newR) || newR === undefined) return
        if (rInput.value === "" || isNaN(rInput.value) || rInput.value === undefined) return;
        changeRadius(previousR, newR)
        console.log(`evL previous: ${previousR}, new: ${newR}`)
        r = newR
        previousR = newR
    })
    // rInput.addEventListener("change", changeRadius)
    svg.addEventListener("click", drawByClick)
    loadPoints()
})
// if (localStorage.length !== 0) {
//     loadTableFromLocalStorage()
// }
//
// function sendStorageLength() {
//     makeFetch("GET", `storageLength=${localStorage.length}`)
// }
//
// function sendStorageItem(jsonItem) {
//     makeFetch('POST', jsonItem, 'application/json')
// }

function makeFetch(method, body, contentType) {
    if (method === "POST") {
        alert("Ошибка: Введите GET запрос!")
    } else if (method === "GET") {
        if (body.drawByClick === false) {
            console.log(body)
            fetch(`http://localhost:25230/lab2/controller?x=${body.x}&y=${body.y}&r=${body.r}`, {
                method: "GET"
                // }).then(response => response.json())
                //     .then(json => {
                //         // console.log(json)
                //         // if (window.location.href !== "http://localhost:25230/lab2/") {
                //         //     window.location.href = "http://localhost:25230/lab2/"
                //         // }
                //         if (json.status !== "error") {
                //             let point = jsonToDict(json)
                //             hit(point.x, point.y, point.r, point.status)
                //             updateTable(point, true)
                //         } else {
                //             console.log(json)
                //             return json
                //         }

                // window.location.href = "http://localhost:25230/lab2/result"
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
            fetch(`http://localhost:25230/lab2/controller?x=${body.x}&y=${body.y}&r=${body.r}&drawByClick=${body.drawByClick}`, {
                method: "GET"
            }).then(response => response.json())
                .then(json => {
                    console.log(json)
                    if (json.status !== "error") {
                        let point = jsonToDict(json)
                        hit(point.x, point.y, point.r)
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
        document.getElementById(inputField).value = ""
    }
}

function hit(x, y, r) {
    dot = document.createElementNS("http://www.w3.org/2000/svg", "circle")
    scale = rPxSize / r
    dot.setAttributeNS(null, "r", "1%")
    setPointXY(x, y, dot, scale)
    changeColor(x, y, r, dot)
    dot.setAttributeNS(null, "visibility", "visible")
    svg.appendChild(dot)
}

function setPointXY(x, y, point, scale) {
    console.log(`PointXY (x, y, point, scale): ${x} ${y} ${point} ${scale}`)
    point.setAttributeNS(null, "cx", (svgCenterX + x * scale).toString())
    point.setAttributeNS(null, "cy", (svgCenterY - y * scale).toString())
}

function changePointR(oldR, newR) {
    let points = document.querySelectorAll("circle")
    for (let i = 0; i < points.length; i++) {
        let p = points[i]
        let svgX = +p.getAttributeNS(null, "cx")
        let svgY = +p.getAttributeNS(null, "cy")
        console.log(`svg: ${svgX} ${svgY}`)
        let mathCoords = svgToMathCoords(svgX, svgY, oldR)
        let x = +mathCoords.x
        let y = +mathCoords.y
        console.log(`math: ${x} ${x}`)
        scale = rPxSize / newR
        setPointXY(x, y, p, scale)

        changeColor(x, y, newR, p)
    }
}

function loadPoints() {
    let points = document.querySelectorAll("circle")
    for (let i = 0; i < points.length; i++) {
        let p = points[i]
        if (r === undefined) {
            resetColor(p)
        }
    }
}

function changeColor(x, y, r, point) {
    let hitFlag = checkHit(x, y, r)
    if (hitFlag) {
        point.setAttributeNS(null, "fill", "green")
    } else {
        point.setAttributeNS(null, "fill", "red")
    }
}

function resetColor(point) {
    point.setAttributeNS(null, "fill", "black")
}

function changeRadius(oldR, newR) {
    let r = newR
    // let currentR = rInput.value
    console.log(`old: ${oldR}, new: ${r}`)
    document.getElementById("-rx").textContent = `-${r}`
    document.getElementById("-ry").textContent = `-${r}`
    document.getElementById("-rx/2").textContent = `-${r / 2}`
    document.getElementById("-ry/2").textContent = `-${r / 2}`
    document.getElementById("rx").textContent = `${r}`
    document.getElementById("ry").textContent = `${r}`
    document.getElementById("rx/2").textContent = `${r / 2}`
    document.getElementById("ry/2").textContent = `${r / 2}`
    changePointR(oldR, newR) /// fix
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
    // if (firstAdd) {
    //     updateLocalStorage(localStorage.length, dict)
    // }
}

//
// function updateLocalStorage(index, dict) {
//     let item = dictToString(dict)
//     console.log(`index: ${localStorage.length}\nitem: ${item}`)
//     localStorage.setItem(index.toString(), item)
// }
//
// function dictToString(dict) {
//     return `x: ${dict["x"]},
//             y: ${dict["y"]},
//             r: ${dict["r"]},
//             status: ${dict["status"]},
//             currentTime: ${dict["currentTime"]},
//             executionTime: ${dict["executionTime"]}`
// }

// function stringToDict(string) {
//     let statsList = string.split(",\n")
//     let dictItem = {}
//     for (let i = 0; i < statsList.length; i++) {
//         let keyVal = statsList[i].split(": ")
//         let key = keyVal[0].trim()
//         let value = keyVal[1].trim()
//         dictItem[key] = value
//
//     }
//     // console.log(`parsedDictItem: ${dictItem}`)
//     return dictItem
// }
//
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

//
// function dictToJson(dict) {
//     let json = JSON.stringify(dict)
//     console.log(json)
//     return json
// }
//
// function loadTableFromLocalStorage() {
//     let storageLength = window.localStorage.length
//     for (let i = 0; i < storageLength; i++) {
//         let index = i.toString()
//         let item = window.localStorage.getItem(index)
//
//         console.log(item)
//         if (item === null) continue
//         let dictItem = stringToDict(item)
//         dictItem["index"] = index
//         // sendStorageItem(dictToJson(dictItem))
//
//         updateTable(dictItem, false)
//         // hit(dictItem["x"], dictItem["y"], dictItem["r"])
//         console.log(`localStorageLineIndex: ${i} dictItem: ${dictItem}`)
//     }
// }

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

function svgToMathCoords(svgX, svgY, r) {
    const scale = rPxSize / r
    return {
        x: ((svgX - svgCenterX) / scale).toFixed(2),
        y: ((svgCenterY - svgY) / scale).toFixed(2)
    }
}

function drawByClick(e) {
    const r = rInput.value
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
        errorMessage("svgError", undefined, "Невозможно определить координаты точки! Введите R!")
    } else {
        makeFetch("GET", {x: coords.x, y: coords.y, r: r, drawByClick: true})
    }

}


function sendPoint(e) {
    e.preventDefault()
    const regexp = /^[-+]?[0-9]*[.,][0-9]+$|^[-+]?[0-9]+$/
    x = document.getElementById("inputX").value;
    yValues = initYvalues()
    r = rInput.value;
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
            makeFetch("GET", {x: x, y: y, r: r, drawByClick: false}, 'application/x-www-form-urlencoded')
        }

    }
}


function checkHit(x, y, r) {
    return checkCircle(x, y, r) || checkTriangle(x, y, r) || checkRectangle(x, y, r);
}

function checkTriangle(x, y, r) {
    if (x <= 0 && y >= 0) {
        return x > y - r && y < x + r;
    }
    return false;
}

function checkRectangle(x, y, r) {
    if (x <= 0 && y <= 0) {
        return x > -r / 2 && y > -r;
    }
    return false;
}

function checkCircle(x, y, r) {
    if (x >= 0 && y <= 0) {
        return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2);
    }
    return false;
}