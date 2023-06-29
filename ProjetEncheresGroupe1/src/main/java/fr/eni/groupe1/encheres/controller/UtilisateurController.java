package fr.eni.groupe1.encheres.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
public class UtilisateurController {
	private UtilisateurService utilisateurService;
	
	@Autowired
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService=utilisateurService;
	}
	/////////////////////////////////        CREATION D UN PROFIL     ////////////////////////////////////////////
	
	@GetMapping({"/CreationProfil"})
	public String vueCreationProfil( Utilisateur utilisateur) {
		return "CreationProfil";
	}
	@PostMapping({"/CreationProfil/Suite"})
	public String ajouterUtilisateur(@ModelAttribute("utilisateur")Utilisateur utilisateur) {
		utilisateurService.enregistrer(utilisateur);
		return "redirect:/";
		
	}
	/////////////////////////////////        CREATION D UN ARTICLE     ////////////////////////////////////////////
	@GetMapping({"/ProfilUtilisateur"})
	@ResponseBody
	public String afficherProfil (Principal principal) {
		System.out.println("je passe par le get profil");
	 principal.getName();
	 
		System.out.println(principal.getName());
		
		Utilisateur utilisateurProfil = utilisateurService.findByPseudo(principal.getName());
			
		return "ProfilUtilisateur";
	}
}
