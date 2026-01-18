document.addEventListener("DOMContentLoaded", () => {
    const apiUrl = `${contextPath}/api`;
    const check_button = document.getElementById("button");
    const clear_button = document.getElementById("clear");
    const text_field = document.getElementById("Ytext");
    const image = document.getElementById('image');
    const container = document.querySelector('.x-button-container');
    const buttons = document.querySelectorAll('button');
    const rb1 = document.getElementById("R1");
    const rb2 = document.getElementById("R2");
    const rb3 = document.getElementById("R3");
    const rb4 = document.getElementById("R4");
    const rb5 = document.getElementById("R5");
    const form = document.getElementById("button-form")

    let x_value = 0;

    text_field.addEventListener("input", () => {
       validate(text_field.value);
    });

    container.addEventListener('click', function(event) {
        if (event.target.tagName === 'BUTTON') {
            const clickedButton = event.target;
            buttons.forEach(button => {
                button.classList.remove('active-button');
            });

            clickedButton.classList.add('active-button');

            x_value = clickedButton.textContent;
        }
    });


    function clearServerTable() {
        fetch(apiUrl, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response;
        })
        .catch(error => {
            console.error('Ошибка при очистке данных:', error);
        });
    }

    function updateGraph(value) {
        value = parseFloat(value);
        let xCaption1 = document.getElementById("XR");
        let xCaption2 = document.getElementById("XR/2");
        let xCaption3 = document.getElementById("X-R/2");
        let xCaption4 = document.getElementById("X-R");

        let yCaption1 = document.getElementById("YR");
        let yCaption2 = document.getElementById("YR/2");
        let yCaption3 = document.getElementById("Y-R/2");
        let yCaption4 = document.getElementById("Y-R");

        xCaption1.textContent = value;
        xCaption2.textContent = value / 2;
        xCaption3.textContent = -1 * value / 2;
        xCaption4.textContent = -1 * value;

        yCaption1.textContent = value;
        yCaption2.textContent = value / 2;
        yCaption3.textContent = -1 * value / 2;
        yCaption4.textContent = -1 * value;

        document.querySelectorAll("circle.point").forEach(point => {
            console.log("x:", point.dataset.x, "y:", point.dataset.y);
            let x = parseFloat(point.dataset.x);
            let y = parseFloat(point.dataset.y);

            let cx = 200 + (x / value) * 150;
            let cy = 200 - (y / value) * 150;

            point.setAttribute("cx", cx);
            point.setAttribute("cy", cy);
        });
    }

    function validate(value) {
        const val = value.trim().replace(',', '.');

        if (val === "") {
            check_button.disabled = true;
            text_field.setCustomValidity("Число не введено");
            text_field.reportValidity();
            return;
        }

        const re = /^-?((\d+\.\d+)|(\d+)|(\.\d+))$/;
        if (!re.test(val)) {
            text_field.setCustomValidity("Введено не число");
            check_button.disabled = true;
            text_field.reportValidity();
            return;
        }

        const negativeOutOfBoundsRe = /^-([6-9]|\d{2,}|5\.\d*[1-9])/;
        const positiveOutOfBoundsRe = /^([4-9]|\d{2,}|3\.\d*[1-9])/;
        let isOutOfBounds = false;
        if (val.startsWith('-')) {
            if (negativeOutOfBoundsRe.test(val)) {
                isOutOfBounds = true;
            }
        }
        else {
            if (positiveOutOfBoundsRe.test(val)) {
                isOutOfBounds = true;
            }
        }
        if (isOutOfBounds) {
            text_field.setCustomValidity("Число не входит в диапазон");
            check_button.disabled = true;
            text_field.reportValidity();
            return;
        }

        text_field.setCustomValidity("");
        check_button.disabled = false;

        const num = parseFloat(val);

        if (num <= 3 && num >= -5) {
            text_field.setCustomValidity("");
            check_button.disabled = false;
            text_field.reportValidity();
        }

        else {
            text_field.setCustomValidity("Число не входит в диапазон");
            check_button.disabled = true;
            text_field.reportValidity();
        }
    }

    /*
    function sendRequest(url, requestData) {
        const params = new URLSearchParams(requestData);
        const fullUrl = `${url}?${params.toString()}`;
        return fetch(fullUrl, {
            method: 'GET'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка запроса: ${response.status}`);
                }
                return response.text();
            })
            .then(newPageHtml => {
                document.open();
                document.write(newPageHtml);
                document.close();
            })
            .catch(error => {
                console.error('Произошла ошибка:', error);
                throw error;
            });
    }
     */

    function radio_click(event) {
        const clicked = event.target;
        const value = clicked.value;
        updateGraph(parseFloat(value));
        saveR(parseFloat(value));
        updatePointsColor(parseFloat(value));
    }

    function image_click(event) {
        const r_radio = document.querySelector('input[name="R"]:checked');
        const r_value = parseFloat(r_radio.value);

        const svg = event.currentTarget;
        const rect = svg.getBoundingClientRect();

        const svgX = event.clientX - rect.left;
        const svgY = event.clientY - rect.top;

        const centerX = rect.width / 2;
        const centerY = rect.height / 2;

        const scale = rect.width * (150 / 400);

        const mathX = ((svgX - centerX) / scale) * r_value;
        const mathY = ((-(svgY - centerY)) / scale) * r_value;

        const requestData = {
            x: mathX,
            y: mathY,
            r: r_value,
            type: "svg"
        };
        const params = new URLSearchParams(requestData);
        const fullUrl = `${apiUrl}?${params.toString()}`;
        fetch(fullUrl, {
            method: 'GET'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка запроса: ${response.status}`);
                }
                return response.text();
            })
            .then(newPageHtml => {
                document.open();
                document.write(newPageHtml);
                document.close();
            })
            .catch(error => {
                console.error('Error: ', error);
            });
    }

    function updatePointsColor(value) {
        document.querySelectorAll("circle.point").forEach(point => {
                    let x = parseFloat(point.dataset.x);
                    let y = parseFloat(point.dataset.y);

                    let hit = false;
                    if (x >= 0 && y >= 0) {
                        hit = x ** 2 + y ** 2 <= (value / 2) ** 2;
                    }
                    else if (x <= 0 && y <= 0) {
                        hit = (y >= -1 * value / 2) && (x >= -1 * value);
                    }
                    else if (x <= 0 && y >= 0) {
                        hit = (y <= 0.5 * x + value / 2) && (x >= -1 * value / 2);
                    }
                    else {
                        hit = false;
                    }
                    console.log(hit);
                    if (hit) {
                        point.setAttribute("fill", "green");
                    }
                    else {
                        point.setAttribute("fill", "red");
                    }
                });
    }

    /*
    function button_click() {
        let y_value = text_field.value;
        const r_radio = document.querySelector('input[name="R"]:checked');
        let r_value = r_radio.value;

        const data = { x: x_value, y: y_value, r: r_value, type: "btn" };

        sendRequest(apiUrl, data)
            .then(result => {
                console.log("Ответ от сервера:", result);
            })
            .catch(error => {
                console.error("Произошла ошибка:", error);
            });
    }
     */

    function clear_click() {
        let clear = confirm("Вы уверены, что хотите отчистить таблицу?")

        if (clear) {
            clearServerTable();
            location.reload();
        }
    }

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        let y_value = text_field.value;
        const r_radio = document.querySelector('input[name="R"]:checked');
        let r_value = r_radio.value;

        const data = { x: x_value, y: y_value, r: r_value, type: "btn" };

        const params = new URLSearchParams(data);
        const finalURL = `${apiUrl}?${params.toString()}`;
        window.location.href = finalURL;
    });

    function saveR(r) {
        localStorage.setItem('rValue', r.toString());
    }

    function updateChecked(value) {
        rb1.checked = "false";
        rb2.checked = "false";
        rb3.checked = "false";
        rb4.checked = "false";
        rb5.checked = "false";

        if (value == 1) {
            rb1.checked = "true";
        }
        if (value == 2) {
            rb2.checked = "true";
        }
        if (value == 3) {
            rb3.checked = "true";
        }
        if (value == 4) {
            rb4.checked = "true";
        }
        if (value == 5) {
            rb5.checked = "true";
        }
    }

    updateGraph(Number(localStorage.getItem('rValue')) || 1);
    updateChecked(Number(localStorage.getItem('rValue')) || 1);
    updatePointsColor(Number(localStorage.getItem('rValue')) || 1);


    clear_button.addEventListener("click", clear_click);
    //check_button.addEventListener("click", button_click);
    image.addEventListener("click", image_click);
    rb1.addEventListener("click", radio_click);
    rb2.addEventListener("click", radio_click);
    rb3.addEventListener("click", radio_click);
    rb4.addEventListener("click", radio_click);
    rb5.addEventListener("click", radio_click);
});