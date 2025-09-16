const submitButton = document.getElementById("submitButton")
let x
let y
let r
const form = document.getElementById("form")

function radioClick(e) {
    x = +e.target.value
}

for (let i = 0; i < form.x.length; i++) {
    form.x[i].addEventListener("click", radioClick)
}

submitButton.onclick = function (e) {
    e.preventDefault()
    if (x === undefined) {
        alert("Выберите X!")
    }
    y = +document.getElementById("inputY").value;
    r = +document.getElementById("inputR").value;
    if (!(-3 <= y && y <= 3)) {
        alert("Введите значение Y в пределах [-3;3]!")
    } else if (!(2 <= r && r <= 5)) {
        alert("Введите значение R в пределах [2;5]!")
    } else {
        hit(x, y, r)
        fetch('/fcgi-bin/server.jar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `x=${x}&y=${y}&r=${r}`
        }).then(response => response.json()).then(data => {
            console.log(data)
            if (data.error != null) {
                alert('error')
            } else {
                updateTable(parseServerData(data))
            }
        }).catch(error => {
            alert("Ошибка! " + error.toString())
        })
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

    function parseServerData(json) {
        let dict = {}
        dict["x"] = json.x
        dict["y"] = json.y
        dict["r"] = json.r
        dict["status"] = json.status
        dict["currentTime"] = json.currentTime
        dict["executionTime"] = json.executionTime
        return dict
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
    }
}