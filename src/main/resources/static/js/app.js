document.addEventListener('DOMContentLoaded', function () {

    // ---------- Text size controls (A- / reset / A+) ----------
    var SIZES = [75, 87.5, 100, 112.5, 125, 137.5, 150];
    var root = document.documentElement;
    var label = document.querySelector('[data-size-value]');
    var downBtn = document.querySelector('[data-size="down"]');
    var upBtn = document.querySelector('[data-size="up"]');
    var resetBtn = document.querySelector('[data-size="reset"]');

    function currentScale() {
        try {
            var saved = parseFloat(localStorage.getItem('uiScale'));
            return SIZES.indexOf(saved) !== -1 ? saved : 100;
        } catch (e) {
            return 100;
        }
    }

    function applyScale(value) {
        root.style.fontSize = value + '%';
        try { localStorage.setItem('uiScale', String(value)); } catch (e) { /* ignore */ }
        if (label) { label.textContent = value + '%'; }
        if (downBtn) { downBtn.disabled = value === SIZES[0]; }
        if (upBtn) { upBtn.disabled = value === SIZES[SIZES.length - 1]; }
    }

    function step(direction) {
        var index = SIZES.indexOf(currentScale()) + direction;
        index = Math.max(0, Math.min(SIZES.length - 1, index));
        applyScale(SIZES[index]);
    }

    if (downBtn) { downBtn.addEventListener('click', function () { step(-1); }); }
    if (upBtn) { upBtn.addEventListener('click', function () { step(1); }); }
    if (resetBtn) { resetBtn.addEventListener('click', function () { applyScale(100); }); }
    applyScale(currentScale());

    // ---------- Delete confirmation dialog ----------
    var dialog = document.getElementById('delete-dialog');
    if (dialog) {
        var form = dialog.querySelector('form');
        var nameEl = dialog.querySelector('[data-student-name]');

        document.querySelectorAll('[data-delete-url]').forEach(function (button) {
            button.addEventListener('click', function () {
                form.action = button.dataset.deleteUrl;
                nameEl.textContent = button.dataset.studentName;
                dialog.showModal();
            });
        });

        dialog.querySelector('[data-close]').addEventListener('click', function () {
            dialog.close();
        });

        // Close when clicking the dimmed backdrop
        dialog.addEventListener('click', function (event) {
            if (event.target === dialog) { dialog.close(); }
        });
    }

    // ---------- Success message fades after a few seconds ----------
    var flash = document.querySelector('.flash');
    if (flash) {
        setTimeout(function () { flash.remove(); }, 4500);
    }
});
