document.addEventListener("DOMContentLoaded", () => {
    const check_button = document.getElementById("button");
    const clear_button = document.getElementById("clear");
    const text_field = document.getElementById("Ytext");
    const table_body = document.getElementById('table-body');
    const template = document.getElementById('row-template');
    const image = document.getElementById('image');
    const max_clicks = 3;
    const time_interval = 1000;
    let click_counter = 0;
    let time = Date.now()
    let img_time = Date.now();
    let img_click_counter = 0;

    text_field.addEventListener("input", () => {
       validate(text_field.value);
    });


    function clearServerTable() {
        fetch('/api', {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            table_body.innerHTML = '';
            return response;
        })
        .catch(error => {
            console.error('Ошибка при очистке данных:', error);
        });
    }

    function loadTableFromServer() {
        fetch('/api', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(tableData => {
            table_body.innerHTML = '';

            tableData.forEach(rowData => {
                const clone = template.content.cloneNode(true);
                const newRow = clone.querySelector('tr');

                newRow.classList.add('data-row');
                newRow.querySelector('.n').textContent = rowData.n;
                newRow.querySelector('.x').textContent = rowData.x;
                newRow.querySelector('.y').textContent = rowData.y;
                newRow.querySelector('.r').textContent = rowData.r;
                newRow.querySelector('.result').textContent = rowData.result;
                newRow.querySelector('.request-time').textContent = rowData.requestTime;
                newRow.querySelector('.local-time').textContent = rowData.localTime;

                table_body.appendChild(newRow);
            });
        })
        .catch(error => {
            console.error('Ошибка при загрузке данных:', error);
        });
    }

    function saveTable() {
        const data_rows = table_body.querySelectorAll('.data-row');
        const table_data = [];

        data_rows.forEach(row => {
            const rowData = {
                n: row.querySelector('.n').textContent,
                x: row.querySelector('.x').textContent,
                y: row.querySelector('.y').textContent,
                r: row.querySelector('.r').textContent,
                result: row.querySelector('.result').textContent,
                time: row.querySelector('.request-time').textContent,
                localTime: row.querySelector('.local-time').textContent
            };
            table_data.push(rowData);
        });
        localStorage.setItem('shotHistory', JSON.stringify(table_data));
    }

    function loadTable() {
        const savedData = localStorage.getItem('shotHistory');

        if (savedData) {
            const tableData = JSON.parse(savedData);

            table_body.innerHTML = '';

            tableData.forEach(rowData => {
                const clone = template.content.cloneNode(true);
                const newRow = clone.querySelector('tr');

                newRow.classList.add('data-row');
                newRow.querySelector('.n').textContent = rowData.n;
                newRow.querySelector('.x').textContent = rowData.x;
                newRow.querySelector('.y').textContent = rowData.y;
                newRow.querySelector('.r').textContent = rowData.r;
                newRow.querySelector('.result').textContent = rowData.result;
                newRow.querySelector('.request-time').textContent = rowData.time;
                newRow.querySelector('.local-time').textContent = rowData.localTime;

                table_body.appendChild(newRow);
            });
        }
    }

    loadTableFromServer();

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

        const outOfBoundsRe = /^-?([4-9]|\d{2,}|3\.\d*[1-9])/;
        if (outOfBoundsRe.test(val)) {
            text_field.setCustomValidity("Число не входит в диапазон");
            check_button.disabled = true;
            text_field.reportValidity();
            return;
        }

        const num = parseFloat(val);

        if (num <= 3 && num >= -3) {
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


    function sendRequest(url, requestData) {
        return fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Ошибка запроса: ${response.status}`);
                }
                return response.json();
            })
            .then(responseData => {
                const free_row = table_body.querySelector('tr:not(.data-row)');
                let target_row;

                if (free_row) {
                    target_row = free_row;
                } else {
                    const clone = template.content.cloneNode(true);
                    table_body.appendChild(clone);
                    target_row = table_body.lastElementChild;
                }

                target_row.classList.add('data-row');
                const row_num = table_body.querySelectorAll('.data-row').length;

                target_row.querySelector('.n').textContent = row_num;
                target_row.querySelector('.x').textContent = requestData.x;
                target_row.querySelector('.y').textContent = requestData.y;
                target_row.querySelector('.r').textContent = requestData.r;
                target_row.querySelector('.result').textContent = responseData.result;
                target_row.querySelector('.request-time').textContent = responseData.time;
                target_row.querySelector('.local-time').textContent = responseData.localTime;

                saveTable();

                return responseData;
            });
    }

    async function image_click() {
        img_click_counter++;
        if (img_click_counter > max_clicks) {
            await CaptchaManager.show()
            img_click_counter = 0;
            return;
        }
        else {
            if (Date.now() - img_time > time_interval) {
                img_time = Date.now();
                img_click_counter = 0;
            }
        }
    }

    async function button_click() {
        click_counter++;
        if (click_counter > max_clicks) {
            await CaptchaManager.show();
            click_counter = 0;
        }
        else {
            if (Date.now() - time > time_interval) {
                time = Date.now();
                click_counter = 0;
            }
        }

        const x_radio = document.querySelector('input[name="X"]:checked');
        let x_value = x_radio.value;
        let y_value = text_field.value;
        const r_radio = document.querySelector('input[name="R"]:checked');
        let r_value = r_radio.value;

        const data = { x: x_value, y: y_value, r: r_value };

        sendRequest('/api', data)
            .then(result => {
                console.log("Ответ от сервера:", result);
            })
            .catch(error => {
                console.error("Произошла ошибка:", error);
            });
    }

    function clear_click() {
        let clear = confirm("Вы уверены, что хотите отчистить таблицу?")

        if (clear) {
            clearServerTable();
        }
    }

    clear_button.addEventListener("click", clear_click);
    check_button.addEventListener("click", button_click);
    image.addEventListener("click", image_click);
});

let widgetId = null;
function show_captcha() {
    document.getElementById('captcha-overlay').classList.add('visible');
    document.getElementById('captcha-modal').classList.add('visible');
}

function hide_captcha() {
    document.getElementById('captcha-overlay').classList.remove('visible');
    document.getElementById('captcha-modal').classList.remove('visible');
    document.getElementById('captcha-error').innerText = '';

    CaptchaManager.resolve();
}

function handle_captcha_failure() {
    document.getElementById('captcha-error').innerText = 'Проверка не пройдена. Попробуйте снова.';
    if (widgetId !== null) {
        grecaptcha.reset(widgetId);
    }
}

function onRecaptchaLoad() {
    widgetId = grecaptcha.render('recaptcha-container', {
        'sitekey': '6LdNGdcrAAAAABsGJEBHfhIsxYD4lL0d7YRYajjV',
        'callback': handle_client_captcha_success
    });
}

function handle_client_captcha_success(token) {
    fetch('/api/captcha', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ token: token })
    })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                hide_captcha();
            } else {
                handle_captcha_failure();
            }
        })
        .catch(() => handle_captcha_failure());
}

const CaptchaManager = {
    resolvePromise: null,

    show() {
        return new Promise((resolve) => {
            this.resolvePromise = resolve;
            if (widgetId !== null) {
                grecaptcha.reset(widgetId);
            }
            show_captcha();
        });
    },

    resolve() {
        if (this.resolvePromise) {
            this.resolvePromise();
            this.resolvePromise = null;
        }
    }
};

