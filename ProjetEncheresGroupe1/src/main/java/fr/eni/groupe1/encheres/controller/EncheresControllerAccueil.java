package fr.eni.groupe1.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;

@Controller
@RequestMapping("")
public class EncheresControllerAccueil {
	
	private EncheresCategoriesService encheresCategoriesService;
	private EncheresService encheresService;
	
	public EncheresControllerAccueil(EncheresCategoriesService encheresCategoriesService) {
		this.encheresCategoriesService = encheresCategoriesService;
	}

	@GetMapping({"/","/accueil"})
	public String afficherAccueil(Model model) {
		
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		model.addAttribute("categorie",listeCategories); 
		
		List<ArticleVendu>listArticles = encheresService.getArticle();
		model.addAttribute("articlevendu",listArticles);
		
		return "index";
	}
	
}
