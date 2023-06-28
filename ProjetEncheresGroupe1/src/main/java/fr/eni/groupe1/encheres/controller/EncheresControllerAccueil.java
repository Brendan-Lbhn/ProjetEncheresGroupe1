package fr.eni.groupe1.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
@RequestMapping("")
public class EncheresControllerAccueil {
	
	private EncheresCategoriesService encheresCategoriesService;
	private EncheresService encheresService;
	private UtilisateurService utilisateurService;
	
	public EncheresControllerAccueil(EncheresCategoriesService encheresCategoriesService,
			EncheresService encheresService,
			UtilisateurService utilisateurService) {
		this.encheresCategoriesService = encheresCategoriesService;
		this.encheresService = encheresService;
		this.utilisateurService= utilisateurService; 
	}

	@GetMapping({"/","/accueil"})
	public String afficherAccueil(Model model) {
		
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		model.addAttribute("categorie",listeCategories); 
		
		List<ArticleVendu>listArticles = encheresService.getArticle();
		model.addAttribute("articleVendu",listArticles);		
		
		return "index";
	}
	
}
