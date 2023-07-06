window.onload = init;

function init() {
    var achatsRadio = document.getElementById('achats');
    var ventesRadio = document.getElementById('ventes');

    achatsRadio.addEventListener('change', handleRadioButtonChange);
    ventesRadio.addEventListener('change', handleRadioButtonChange);
    console.log("coucou");
}

function handleRadioButtonChange(event) {
    var achatsRadio = document.getElementById('achats');
    var ventesRadio = document.getElementById('ventes');
    var encheresOuvertes = document.getElementById('encheres-ouvertes');
    var encheresEnCours = document.getElementById('encheres-en-cours');
    var encheresRemportees = document.getElementById('encheres-remportees');
    var ventesEnCours = document.getElementById('ventes-en-cours');
    var ventesNonDebutees = document.getElementById('ventes-non-debutees');
    var ventesTerminees = document.getElementById('ventes-terminees');

    if (achatsRadio.checked) {
        encheresOuvertes.disabled = false;
        encheresEnCours.disabled = false;
        encheresRemportees.disabled = false;
        ventesEnCours.disabled = true;
        ventesNonDebutees.disabled = true;
        ventesTerminees.disabled = true;
    } else if (ventesRadio.checked) {
        encheresOuvertes.disabled = true;
        encheresEnCours.disabled = true;
        encheresRemportees.disabled = true;
        ventesEnCours.disabled = false;
        ventesNonDebutees.disabled = false;
        ventesTerminees.disabled = false;
    }
}
