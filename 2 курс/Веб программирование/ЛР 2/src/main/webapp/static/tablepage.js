document.addEventListener("DOMContentLoaded", () => {
    const button_back = document.getElementById('back-btn');

    function back_click() {
        location.href = "/weblab/";
    }

    button_back.addEventListener("click", back_click);
});