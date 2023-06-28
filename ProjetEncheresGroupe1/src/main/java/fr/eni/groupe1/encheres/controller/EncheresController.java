package fr.eni.groupe1.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Retrait;

@Controller
public class EncheresController {
	
	EncheresService encheresService;
	EncheresCategoriesService encheresCategoriesService;
		
	public EncheresController(EncheresService encheresService,EncheresCategoriesService encheresCategoriesService) {
		this.encheresService = encheresService;
		this.encheresCategoriesService = encheresCategoriesService;
	}

	
	
	/////////////////////////////////        CREATION D UN ARTICLE     ////////////////////////////////////////////

	@GetMapping({"/CreationVente"})
	public String inscriptionVente(@ModelAttribute ("article")ArticleVendu article,@ModelAttribute ("retrait") Retrait infoRetrait, Model model) {
	
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		System.out.println("je passe par le get CreationVente");
		
		model.addAttribute("listAticle",encheresService.getArticle());
		model.addAttribute("listRetrait", encheresService.getRetrait());
		model.addAttribute("categorie",listeCategories);
		//System.out.println(encheresService.getArticle());
		return "/CreationVente";
	}
	
	@PostMapping({"/CreationVente"})
	public String inscriptionFaite( @ModelAttribute ("article") ArticleVendu articleVendu,@ModelAttribute ("retrait") Retrait infoRetrait, Model model ) {
		System.out.println("je passe par le post CreationVente");
		encheresService.ajouterArticle(articleVendu);
		System.out.println(articleVendu.toString());
		encheresService.ajouterInfoRetrait(infoRetrait, articleVendu);
		System.out.println( infoRetrait.toString());
		return "redirect:/";
	}
	
	/////////////////////////////////        X     ////////////////////////////////////////////

}

