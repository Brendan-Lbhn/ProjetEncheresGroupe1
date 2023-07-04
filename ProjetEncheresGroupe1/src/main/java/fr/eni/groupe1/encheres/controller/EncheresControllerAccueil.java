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
			System.out.println("aucune recherche");
			return "redirect:/accueil";
		}else if(categorie.getNoCategorie() != 1 && article.getNomArticle().isEmpty()) {
			System.out.println("controller categorie");
			List<ArticleVendu>listArticles = encheresService.getArtcicleByCategorie(categorie.getNoCategorie());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}else if(categorie.getNoCategorie() == 1 && !article.getNomArticle().isEmpty()) {
			System.out.println("controller get nom");
			System.out.println("Dans le controller /rechercher, catégorie vide et nom article non vide.");
			List<ArticleVendu>listArticles = encheresService.getArticleByName(article.getNomArticle());
			model.addAttribute("articleVendu",listArticles);
			System.out.println("De retour dans le controller, listArticles = " + listArticles);
			return "Index";
		}else {
			System.out.println("controller name categorie");
			List<ArticleVendu>listArticles = encheresService.getArticleByNameAndCategorie(article.getNomArticle(), categorie.getNoCategorie());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}
		
	}
	
	@PostMapping({"rechercher2"})
	public String rechercherArticle2(@RequestParam("filtre") int filtre,
			@RequestParam(value = "encheres-ouvertes", defaultValue = "false") boolean encheresOuvertes,
			@RequestParam (value = "encheres-en-cours", defaultValue = "false") boolean encheresEnCours,
			@RequestParam (value = "encheres-remportees", defaultValue = "false") boolean encheresRemportees,
			@RequestParam (value = "ventes-en-cours", defaultValue = "false") boolean ventesEnCours,
			@RequestParam (value = "ventes-non-debutees", defaultValue = "false") boolean ventesNonDebutees,
			@RequestParam (value = "ventes-terminées", defaultValue = "false") boolean ventesTerminees,
			Model model) {
		
		System.out.println("dans le controller /rechercher 2");
		System.out.println("filtre = " + filtre);
		System.out.println("encheres-ouvertes = " + encheresOuvertes);
		System.out.println("encheres-en-cours = " + encheresEnCours);
		System.out.println("encheres-remportees = " + encheresRemportees);
		System.out.println("ventes-en-cours = " + ventesEnCours);
		System.out.println("ventes-non-debutees = " + ventesNonDebutees);
		System.out.println("ventes-terminées = " + ventesTerminees);
		
		List<ArticleVendu> listArticles = encheresService.getArticleByFilters(filtre, encheresOuvertes, encheresEnCours, encheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees);
		model.addAttribute("article", listArticles);
			
		return "redirect:/accueil";
		
	}
	
}
