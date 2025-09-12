document.getElementById('sumForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const x = 1
    const y = 1

    fetch('/fcgi-bin/server.jar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `x=${x}&y=${y}`
})
.then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const resultText = doc.querySelector('p').textContent;
            document.getElementById('result').textContent = resultText;
        })
        .catch(error => {
            document.getElementById('result').textContent = 'Ошибка: ' + error.message;
        });
});