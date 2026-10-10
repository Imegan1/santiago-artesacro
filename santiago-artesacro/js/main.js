// ==========================================
// MAIN.JS - INTERACTIVIDAD FRONT-END
// Proyecto: santiago.artesacro (SENA ADSO)
// ==========================================

document.addEventListener('DOMContentLoaded', () => {
    console.log("santiago.artesacro front-end inicializado correctamente.");

    // Validación y feedback interactivo para formularios de login/registro
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', (e) => {
            const inputs = form.querySelectorAll('input[required]');
            let isValid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    isValid = false;
                    input.style.borderColor = '#9B2C2C';
                } else {
                    input.style.borderColor = '#E2E8F0';
                }
            });

            if (!isValid) {
                e.preventDefault();
                alert('Por favor, complete todos los campos obligatorios antes de continuar.');
            } else {
                console.log('Formulario validado con éxito. Procesando datos...');
            }
        });
    });
});