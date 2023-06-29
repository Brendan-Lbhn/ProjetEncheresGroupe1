package fr.eni.groupe1.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;

@Controller
@RequestMapping("")
public class EncheresControllerAccueil {
	
	private EncheresCategoriesService encheresCategoriesService;
	private EncheresService encheresService;

	
	public EncheresControllerAccueil(EncheresCategoriesService encheresCategoriesService,
			EncheresService encheresService,
			UtilisateurService utilisateurService) {
		this.encheresCategoriesService = encheresCategoriesService;
		this.encheresService = encheresService;
		 
	}

	@GetMapping({"/","/accueil"})
	public String afficherAccueil(Model model) {
		
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		model.addAttribute("categorie",listeCategories); 
		
		List<ArticleVendu>listArticles = encheresService.getArticle();
		model.addAttribute("articleVendu",listArticles);		
		
		return "Index";
	}
	
	@PostMapping({"/rechercher"})
	public String rechercherArticle(@ModelAttribute Categorie categorie, Model model ) {
		System.out.println("Dans le controller /rechercher (noCategorie = " + categorie.getNoCategorie() + ")" );
		List<ArticleVendu>listArticles = encheresService.getArtcicleByCategorie(categorie.getNoCategorie()); 
		System.out.println("De retour dans le controller /rechercher" );
		model.addAttribute("articleVendu",listArticles);
		return "Index";
		
	}
	
}
