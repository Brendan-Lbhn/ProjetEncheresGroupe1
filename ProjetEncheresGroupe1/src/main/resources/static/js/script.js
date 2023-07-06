window.onload = init;

function init() {
    var achatsRadio = document.getElementById('achats');
    var ventesRadio = document.getElementById('ventes');
    handleRadioButtonChange();
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
    var txtEncheresOuvertes = document.getElementById('encheres-ouvertes-txt');
    var txtEncheresEnCours = document.getElementById('encheres-en-cours-txt');
    var txtEncheresRemportees = document.getElementById('encheres-remportees-txt');
    var txtVentesEnCours = document.getElementById('ventes-en-cours-txt');
    var txtVentesNonDebutees = document.getElementById('ventes-non-debutees-txt');
    var txtVentesTerminees = document.getElementById('ventes-terminees-txt');

    if (achatsRadio.checked) {
        encheresOuvertes.disabled = false;
        encheresEnCours.disabled = false;
        encheresRemportees.disabled = false;
        ventesEnCours.disabled = true;
        ventesNonDebutees.disabled = true;
        ventesTerminees.disabled = true;
        txtVentesEnCours.setAttribute('class','option-grisee')
        txtVentesNonDebutees.setAttribute('class','option-grisee')
        txtVentesTerminees .setAttribute('class','option-grisee')
        txtEncheresOuvertes.setAttribute('class','option-valide') 
        txtEncheresEnCours.setAttribute('class','option-valide')
        txtEncheresRemportees.setAttribute('class','option-valide')
        
    } else if (ventesRadio.checked) {
        encheresOuvertes.disabled = true;
        encheresEnCours.disabled = true;
        encheresRemportees.disabled = true;
        ventesEnCours.disabled = false;
        ventesNonDebutees.disabled = false;
        ventesTerminees.disabled = false;
        txtEncheresOuvertes.setAttribute('class','option-grisee') 
        txtEncheresEnCours.setAttribute('class','option-grisee')
        txtEncheresRemportees.setAttribute('class','option-grisee')
       	txtVentesEnCours.setAttribute('class','option-valide')
        txtVentesNonDebutees.setAttribute('class','option-valide')
        txtVentesTerminees .setAttribute('class','option-valide')
        
    }
}
