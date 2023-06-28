package fr.eni.groupe1.encheres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
public class UtilisateurController {
	private UtilisateurService utilisateurService;
	
	@Autowired
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService=utilisateurService;
	}
	
	@GetMapping({"/CreationProfil"})
	public String vueCreationProfil( Utilisateur utilisateur) {
		return "CreationProfil";
	}
	@PostMapping({"/CreationProfil/Suite"})
	public String ajouterUtilisateur(@ModelAttribute("utilisateur")Utilisateur utilisateur) {
		utilisateurService.enregistrer(utilisateur);
		return "redirect:/";
		
	}
}
