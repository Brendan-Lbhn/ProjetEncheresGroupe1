package fr.eni.groupe1.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String afficherAccueil(@ModelAttribute("categorie") Categorie categorie,@ModelAttribute("article") ArticleVendu article, Model model) {
		
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		model.addAttribute("categorie",listeCategories); 
		System.out.println(listeCategories);
		
		List<ArticleVendu>listArticles = encheresService.getArticle();
		model.addAttribute("articleVendu",listArticles);		
		System.out.println(listArticles);
		
		return "Index";
	}
	
	@PostMapping({"rechercher"})
	public String rechercherArticle(@ModelAttribute("categorie") Categorie categorie, @ModelAttribute("article") ArticleVendu article, Model model ) {
		
		System.out.println("Dans le controller /rechercher (noCategorie = " + categorie.getNoCategorie() + " / Caractères recherchés ="+ article.getNomArticle() + ")" );
		
		if(categorie.getNoCategorie() == 1 && article.getNomArticle().isEmpty()) {
			return "redirect:/accueil";
		}else if(categorie.getNoCategorie() != 1 && article.getNomArticle().isEmpty()) {
			List<ArticleVendu>listArticles = encheresService.getArtcicleByCategorie(categorie.getNoCategorie());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		//TODO : remplacer l'expression !article.getNomArticle().isEmpty() : on ne rentre jamais dans cette condition
		}else if(categorie.getNoCategorie() == 1 && !article.getNomArticle().isEmpty()) {
			List<ArticleVendu>listArticles = encheresService.getArticleByName(article.getNomArticle());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}else {
			List<ArticleVendu>listArticles = encheresService.getArticleByNameAndCategorie(article.getNomArticle(), categorie.getNoCategorie());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}
		
	}
	
}
