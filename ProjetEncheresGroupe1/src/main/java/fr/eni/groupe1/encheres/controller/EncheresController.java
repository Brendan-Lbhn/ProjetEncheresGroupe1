package fr.eni.groupe1.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Retrait;

@Controller
public class EncheresController {
	
	EncheresService encheresService;
	EncheresCategoriesService encheresCategoriesService;
	UtilisateurService utilisateurService;
	
	public EncheresController(EncheresService encheresService,EncheresCategoriesService encheresCategoriesService,UtilisateurService utilisateurService) {
		this.encheresService = encheresService;
		this.encheresCategoriesService = encheresCategoriesService;
		this.utilisateurService = utilisateurService;
		
	}

	
	/////////////////////////////////        CREATION D UN ARTICLE     ////////////////////////////////////////////

	@GetMapping({"CreationVente"})
	public String inscriptionVente(Principal principal,@ModelAttribute ("article")ArticleVendu article,@ModelAttribute ("retrait") Retrait infoRetrait, Model model) {
	System.out.println("je passe par le get CreationVente");
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		
		model.addAttribute("utilisateur",utilisateurService.findByPseudo(principal.getName()));
		model.addAttribute("listAticle",encheresService.getArticle());
		model.addAttribute("listRetrait", encheresService.getRetrait());
		model.addAttribute("categorie",listeCategories);
		return "/CreationVente";
	}
	
	@PostMapping({"CreationVente"})
	public String inscriptionFaite( @ModelAttribute ("articleVendu") ArticleVendu articleVendu,@ModelAttribute ("retrait") Retrait infoRetrait, Model model ) {
		System.out.println("je passe par le post CreationVente");
		encheresService.ajouterArticle(articleVendu);
		System.out.println(articleVendu.toString());
		encheresService.ajouterInfoRetrait(infoRetrait, articleVendu);
		System.out.println(infoRetrait.toString());
		return "redirect:/accueil";
	}
	
	/////////////////////////////////        X     ////////////////////////////////////////////

}

