package fr.eni.groupe1.encheres.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {
	private UtilisateurService utilisateurService;

	@Autowired
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	///////////////////////////////// CREATION D UN PROFIL ///////////////////////////////// ////////////////////////////////////////////

	@GetMapping({ "/CreationProfil" })
	public String vueCreationProfil(Utilisateur utilisateur) {
		return "CreationProfil";
	}

	@PostMapping({"/CreationProfil/Suite"})
	public String ajouterUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        // Gérer les erreurs de validation ici
	        return "CreationProfil";
	    }
	    
	    utilisateurService.enregistrer(utilisateur);
	    return "redirect:/";
	}

	///////////////////////////////// AFFICHAGE D UN PROFIL ///////////////////////////////// ////////////////////////////////////////////
	@GetMapping({ "/ProfilUtilisateur" })
	public String afficherProfil(Principal principal, Model model) {
		System.out.println("je passe par le get profil");
		principal.getName();

		System.out.println(principal.getName());
		model.addAttribute("Utilisateur",utilisateurService.findByPseudo(principal.getName()));

		return "ProfilUtilisateur";
	}
	
	@GetMapping("/ModifProfil")
	public String modificationFilm (Principal principal, Model model) {
		System.out.println("je passe par la modification du profil" );
		model.addAttribute("Utilisateur",utilisateurService.findByPseudo(principal.getName()));		
		return "/CreationProfil";
	}
}
