package fr.eni.groupe1.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {
	private UtilisateurService utilisateurService;

	
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	///////////////////////////////// CREATION D UN PROFIL ///////////////////////////////// ////////////////////////////////////////////

	@GetMapping({ "/CreationProfil" })
	public String vueCreationProfil(Utilisateur utilisateur) {
		return "CreationProfil";
	}

	@PostMapping("/CreationProfil/Suite")
	public String ajouterUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        // Gérer les erreurs de validation ici
	        return "CreationProfil";
	    }
	    
	    try {
	        utilisateurService.enregistrer(utilisateur);
	        return "redirect:/login";
	    } catch (IllegalStateException e) {
	        model.addAttribute("pseudoError", e.getMessage());
	        bindingResult.addError(new ObjectError("global", e.getMessage()));
	        return "CreationProfil";
	    }
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

	@GetMapping({"/ProfilVendeur" })
	public String afficherVendeur(Model model, @PathVariable int index) {
		System.out.println("je passe par le profil vendeur");
		Utilisateur utilisateur = utilisateurService.findById(index);
		model.addAttribute("Vendeur", utilisateur);
		return "ProfilVendeur";
	}
		///////////////////////////////// MODIFICATION D UN PROFIL ///////////////////////////////// ////////////////////////////////////////////

	@GetMapping("ModifProfil")
	public String modificationProfil (Principal principal, Model model) {
		System.out.println("je passe par la modification du profil" );
		Utilisateur utilisateur = null;
		
		utilisateur = utilisateurService.findByPseudo(principal.getName());
		
				model.addAttribute("Utilisateur",utilisateur);	
				
		return "TestModif";
	}
	///////////////////////////////// SUPRESSION D UN PROFIL ///////////////////////////////// ////////////////////////////////////////////
	@GetMapping("/Delete")
	public String deleteFilm (Principal principal, Model model) {
		System.out.println("je passe par la suppression" );
Utilisateur utilisateur = null;
		
		utilisateur = utilisateurService.deleteProfil(principal.getName());
		
				model.addAttribute("Utilisateur",utilisateur);
		
		return "redirect:/";
	}
	
	
}
