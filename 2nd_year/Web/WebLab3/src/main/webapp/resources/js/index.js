document.addEventListener("DOMContentLoaded", () => {
    const hourHand = document.querySelector(".hour-hand");
    const minuteHand = document.querySelector(".minute-hand");
    const secondHand = document.querySelector(".second-hand");
    const dateDisplay = document.querySelector(".date-display");
    const updateInterval = 8000;

    async function updateTimeAndDate() {
        const response = await fetch("/weblab/timer");
        const text = await response.text();
        const now = parseServerTime(text);
        const hours = now.getHours();
        const minutes = now.getMinutes();
        const seconds = now.getSeconds();

        const hoursDeg = (360 / 12) * (hours % 12 + minutes / 60);
        const minutesDeg = (360 / 60) * (minutes + seconds / 60);
        const secondsDeg = (360 / 60) * seconds;

        hourHand.style.transform = `translateX(-50%) rotate(${hoursDeg}deg)`;
        minuteHand.style.transform = `translateX(-50%) rotate(${minutesDeg}deg)`;
        secondHand.style.transform = `translateX(-50%) rotate(${secondsDeg}deg)`;

        if (dateDisplay) {
            const day = String(now.getDate()).padStart(2, '0');
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const year = now.getFullYear();
            const formattedDate = `${day}.${month}.${year}`;
            dateDisplay.textContent = formattedDate;
        }
    }

    function parseServerTime(text) {
            const [datePart, timePart] = text.split(" ");
            const [year, month, day] = datePart.split("-").map(Number);
            const [hours, minutes, seconds] = timePart.split(":").map(Number);

            return new Date(year, month - 1, day, hours, minutes, seconds);
        }

    setInterval(updateTimeAndDate, updateInterval);
    updateTimeAndDate();
});