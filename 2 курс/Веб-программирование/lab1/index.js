// for (let i = 0; i < 8; i++) {
//     let x = document.getElementById(`x(${i})` );
//     if (x != null) break
//     console.log(x);
// }

const submitButton = document.getElementById("submitButton")
submitButton.onclick = function () {
    let y = document.getElementById("inputY");
    let r = document.getElementById("inputR");

    if (!(-3 < y < 3)) {
        alert("Введите значение Y в пределах (-3;3)!")
    }
    if (!(2 < r < 5)) {
        alert("Введите значение R в пределах (2;5)!")
    }
    // else if (isNaN(parseFloat(y)) || isNaN(parseFloat(r))) {
    //     alert("Введите число!")
    else {
        alert("Топ")
    }

}



