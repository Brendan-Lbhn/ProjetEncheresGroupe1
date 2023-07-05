window.onload = init()

function init(){
    document.getElementById('achats').addEventListener('change',desactiverVentes)
    document.getElementById('ventes').addEventListener('change',desactiverAchats)
	console.log("coucou")
	}

function desactiverVentes(){
	document.getElementById('encheres-ouvertes').setAttribute('disabled', true)
	document.getElementById('encheres-en-cours').setAttribute('disabled', true)
	document.getElementById('encheres-remportees').setAttribute('disabled', true)
	document.getElementById('ventes-en-cours').setAttribute('disabled', false)
	document.getElementById('ventes-non-debutees').setAttribute('disabled', false)
	document.getElementById('ventes-terminees').setAttribute('disabled', false)
	console.log("coucou")
}

function desactiverAchats(){
	document.getElementById('encheres-ouvertes').setAttribute('disabled', false)
	document.getElementById('encheres-en-cours').setAttribute('disabled', false)
	document.getElementById('encheres-remportees').setAttribute('disabled', false)
	document.getElementById('ventes-en-cours').setAttribute('disabled', true)
	document.getElementById('ventes-non-debutees').setAttribute('disabled', true)
	document.getElementById('ventes-terminees').setAttribute('disabled', true)
	console.log("coucou")
}