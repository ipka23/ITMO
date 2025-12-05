document.addEventListener("DOMContentLoaded", function () {
    (function setTimeZone() {
        let localDate = new Date();
        let offset = localDate.getTimezoneOffset();
        document.cookie = "TIMEZONE_COOKIE=" + offset;
    })();
})

