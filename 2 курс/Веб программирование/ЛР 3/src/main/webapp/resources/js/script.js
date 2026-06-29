function updateUserTimeZone() {
    const timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    const hiddenInputId = 'main-form:user-timezone-hidden';
    const hiddenInput = document.getElementById(hiddenInputId);
    if (hiddenInput) {
        hiddenInput.value = timeZone;
    }
}

function restore() {
    const svg = document.getElementById('image');
    if (!svg) return;
    const r = parseFloat(svg.getAttribute('data-r'));
    if (isNaN(r)) return;
    updateLabels(r);
    drawAllPoints(r);
}

function handleRChange(r) {
    localStorage.setItem('rValue', r);
    const svg = document.getElementById('image');
    if (svg) {
        svg.setAttribute('data-r', r);
    }
    updateLabels(r);
    repositionPoints(r);
    updatePointsColor(r);
}

function drawAllPoints(r) {
    const svg = document.getElementById('image');
    const historyStorage = document.querySelector('.history-data-storage');
    if (!svg || !historyStorage) return;

    const pointsDataJson = historyStorage.getAttribute('data-history');

    if (!pointsDataJson) return;

    const pointsData = JSON.parse(pointsDataJson);
    const scale = 150;
    svg.querySelectorAll('.click-point').forEach(point => point.remove());

    pointsData.forEach(point => {
        const cx = 200 + (point.x / r) * scale;
        const cy = 200 - (point.y / r) * scale;
        const isHit = checkHit(point.x, point.y, r);
        const color = isHit ? 'green' : 'red';

        const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
        circle.setAttribute('r', '2');
        circle.setAttribute('cx', cx);
        circle.setAttribute('cy', cy);
        circle.setAttribute('fill', color);
        circle.setAttribute('class', 'click-point');
        circle.setAttribute('data-x', point.x);
        circle.setAttribute('data-y', point.y);
        svg.appendChild(circle);
    });
}

function repositionPoints(r) {
    const scale = 150;
    document.querySelectorAll('.click-point').forEach(point => {
        const x = parseFloat(point.dataset.x);
        const y = parseFloat(point.dataset.y);
        const cx = 200 + (x / r) * scale;
        const cy = 200 - (y / r) * scale;
        point.setAttribute('cx', cx);
        point.setAttribute('cy', cy);
    });
}

function updatePointsColor(r) {
    document.querySelectorAll('.click-point').forEach(point => {
        const x = parseFloat(point.dataset.x);
        const y = parseFloat(point.dataset.y);
        const isHit = checkHit(x, y, r);
        point.setAttribute('fill', isHit ? 'green' : 'red');
    });
}

function checkHit(x, y, r) {
    if (x >= 0 && y >= 0) {
        return (x <= r / 2) && (y <= -2 * x + r);
    } else if (x <= 0 && y >= 0) {
        return (x ** 2 + y ** 2) <= (r ** 2 / 4);
    } else if (x <= 0 && y <= 0) {
        return (x >= -1 * r) && (y >= -1 * r / 2);
    }
    return false;
}

function handleGraphClick(event) {
    const svg = event.currentTarget;
    const rValue = parseFloat(svg.dataset.r);

    if (isNaN(rValue) || rValue === 0) {
        alert('Не выбрано значение R');
        return;
    }

    const rect = svg.getBoundingClientRect();

    const svgX = event.clientX - rect.left;
    const svgY = event.clientY - rect.top;

    const centerX = rect.width / 2;
    const centerY = rect.height / 2;

    const scale = rect.width * (150 / 400);

    const mathX = ((svgX - centerX) / scale) * rValue;
    const mathY = ((-(svgY - centerY)) / scale) * rValue;
    document.getElementById('main-form:svg-x').value = mathX.toFixed(3);
    document.getElementById('main-form:svg-y').value = mathY.toFixed(3);

    sendSvgPoint();
}

function updateLabels(rValue) {
    if (isNaN(rValue) || rValue <= 0) return;

    document.getElementById('xr').textContent = rValue;
    document.getElementById('xr/2').textContent = rValue / 2;
    document.getElementById('x-r').textContent = -1 * rValue;
    document.getElementById('x-r/2').textContent = -1 * rValue / 2;

    document.getElementById('yr').textContent = rValue;
    document.getElementById('yr/2').textContent = rValue / 2;
    document.getElementById('y-r').textContent = -1 * rValue;
    document.getElementById('y-r/2').textContent = -1 * rValue / 2;
}

function loadRFromStorage() {
    const savedR = localStorage.getItem('rValue');
    if (savedR) {
        const rValue = parseFloat(savedR);
        const buttonId = `main-form:r-button-${rValue}`;
        const button = document.getElementById(buttonId);

        if (button) {
            button.click();
        }
    }
    else {
        const svg = document.getElementById('image');
        if (svg) {
            const initialR = parseFloat(svg.getAttribute('data-r'));
            updateLabels(initialR);
        }
    }
}

window.addEventListener('DOMContentLoaded', loadRFromStorage);