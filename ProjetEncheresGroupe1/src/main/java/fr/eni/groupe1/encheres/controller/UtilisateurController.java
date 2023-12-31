package fr.eni.groupe1.encheres.controller;

import java.security.Principal;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	///////////////////////////////// CREATION D UN PROFIL //////////////////////////////////////////////////////////
	

	@GetMapping({ "/CreationProfil" })
	public String vueCreationProfil(Utilisateur utilisateur) {
		return "CreationProfil";
	}

	@PostMapping("/CreationProfil/Suite")
	public String ajouterUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
			BindingResult bindingResult, Model model) {
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
		} catch (DuplicateKeyException e) {
			// Traitement de l'erreur
			bindingResult.addError(new ObjectError("global", "Un utilisateur possede déja ce mail ou ce pseudo"));
			return "redirect:/ModifProfil";
		}

	}

	///////////////////////////////// AFFICHAGE D UN PROFIL ///////////////////////////////////////////////////////
	
	@GetMapping({ "/ProfilUtilisateur" })
	public String afficherProfil(Principal principal, Model model) {

		principal.getName();

		model.addAttribute("Utilisateur", utilisateurService.findByPseudo(principal.getName()));

		return "ProfilUtilisateur";
	}

	@GetMapping("ProfilVendeur")
	public String afficherVendeur(Model model, @RequestParam int id) {

		Utilisateur utilisateur = utilisateurService.findById(id);
		model.addAttribute("Vendeur", utilisateur);
		return "ProfilVendeur";
	}
	///////////////////////////////// MODIFICATION D UN PROFIL ///////////////////////////////////////////////////////
	

	@GetMapping("ModifProfil")
	public String modificationProfil(@Valid Principal principal, Model model) {

		Utilisateur utilisateur = null;

		utilisateur = utilisateurService.findByPseudo(principal.getName());
		model.addAttribute("utilisateur", utilisateur);

		return "CreationProfil";
	}

	@GetMapping("AjoutCredit")
	public String ajoutCredil(Principal principal, Model model) {

		Utilisateur utilisateur = null;

		utilisateur = utilisateurService.findByPseudo(principal.getName());
		model.addAttribute("Utilisateur", utilisateur);

		return "AjoutCredit";
	}

	@PostMapping("AjoutCredit/Suite")
	public String ajoutCreditOk(Principal principal, @ModelAttribute("utilisateur") Utilisateur utilisateur,
			Model model) {

		Utilisateur utilisateurCredit;
		utilisateurCredit = utilisateurService.findByPseudo(principal.getName());
		int creditActuel = utilisateurCredit.getCredit();

		model.addAttribute("Utilisateur", utilisateurService.findByPseudo(principal.getName()));
		utilisateurService.ajoutCredit(principal, utilisateur, creditActuel);

		return "ProfilUtilisateur";
	}

	///////////////////////////////// SUPRESSION D UN PROFIL //////////////////////////////////////////////////////
	@GetMapping("/Delete")
	public String deleteUtilisateur(Principal principal, Model model) {

		Utilisateur utilisateur = null;

		utilisateur = utilisateurService.deleteProfil(principal.getName(), principal);

		model.addAttribute("Utilisateur", utilisateur);

		return "redirect:/logout";
	}

}
