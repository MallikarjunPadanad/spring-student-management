document.addEventListener('DOMContentLoaded', function () {
    // Delete confirmation dialog
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
            if (event.target === dialog) {
                dialog.close();
            }
        });
    }

    // Fade out the success message after a few seconds
    var flash = document.querySelector('.flash');
    if (flash) {
        setTimeout(function () { flash.remove(); }, 4500);
    }
});
