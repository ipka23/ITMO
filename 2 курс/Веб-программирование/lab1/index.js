const submitButton = document.getElementById("submitButton")
let x
let y
let r

const form = document.getElementById("form")

function radioClick(e) {
    // console.log(e.target.value)
    x = e.target.value
}

for (let i = 0; i < form.x.length; i++) {
    form.x[i].addEventListener("click", radioClick)
}

submitButton.onclick = function () {
    if (x === undefined) {
        alert("Выберите X!")
    }
    y = +document.getElementById("inputY").value;
    r = +document.getElementById("inputR").value;
    let currentTime = new Date()
    if (isNaN(y) || isNaN(r)) {
        console.log(`nums: ${y}, ${r}`)
        alert("Введите число!")
    } else if (-3 >= y && y >= 3) {
        alert("Введите значение Y в пределах (-3;3)!")
    } else if (2 >= r && r >= 5) {
        alert("Введите значение R в пределах (2;5)!")
    } else {
        console.log(`nums: ${x}, ${y}, ${r}\ntime:${currentTime}`)
        alert("Топ")
    }

    function hit(x, y) {
        const svg = document.getElementById("svg")
        const centerX = 150
        const centerY = 150
        const dot = document.createElement("circle")
        dot.x1 = centerX + x
        
        svg.appendChild()
    }
}



