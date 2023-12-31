package fr.eni.groupe1.encheres.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
public class EncheresController {

	EncheresService encheresService;
	EncheresCategoriesService encheresCategoriesService;
	UtilisateurService utilisateurService;

	public EncheresController(EncheresService encheresService, EncheresCategoriesService encheresCategoriesService,
			UtilisateurService utilisateurService) {
		this.encheresService = encheresService;
		this.encheresCategoriesService = encheresCategoriesService;
		this.utilisateurService = utilisateurService;

	}

	///////////////////////////////// CREATION D UN ARTICLE ///////////////////////////////////////////////////////////

	@GetMapping({ "CreationVente" })
	public String inscriptionVente(Principal principal, @ModelAttribute("article") ArticleVendu article,
			@ModelAttribute("retrait") Retrait infoRetrait, Model model) {
		
		List<Categorie> listeCategories = encheresCategoriesService.getCategories();
		Date date1 = java.sql.Date.valueOf(LocalDate.now());
		
		model.addAttribute("dateDuJour", date1);
		model.addAttribute("utilisateur", utilisateurService.findByPseudo(principal.getName()));
		model.addAttribute("listAticle", encheresService.getArticle());
		model.addAttribute("listRetrait", encheresService.getRetrait());
		model.addAttribute("categorie", listeCategories);
		return "/CreationVente";
	}

	@PostMapping({ "CreationVente" })
	public String inscriptionFaite(@ModelAttribute("articleVendu") ArticleVendu articleVendu,
			@ModelAttribute("retrait") Retrait infoRetrait, Model model) {
		
		Date date1 = java.sql.Date.valueOf(LocalDate.now());
		
		model.addAttribute("date", date1);
		encheresService.ajouterArticle(articleVendu);
		encheresService.ajouterInfoRetrait(infoRetrait, articleVendu);
		return "redirect:/accueil";
	}

	///////////////////////////////// AFFICHAGE D UN DETAIL DE VENTE /////////////////////////////////////////////////
	@GetMapping({ "DetailVente" })

	public String AfficherDetailVente(Principal principal, @ModelAttribute("article") ArticleVendu article,
			@ModelAttribute("retrait") Retrait infoRetrait, @ModelAttribute("enchere") Enchere infoEncheres,
			@RequestParam int id, Model model) {
		
		Date date1 = java.sql.Date.valueOf(LocalDate.now());
		ArticleVendu articleEnEnchere = encheresService.getArticleById(id);
		Date date2 = articleEnEnchere.getDateFinEncheres();
		// Date date1 = date2;
		int resultat = date1.compareTo(date2);
		int resultatDate = 0;
//////////////////////////////// COMPARAISON DATES POUR CLOTURER L ENCHERE	
		if (resultat < 0) {
			resultatDate = 2;
		} else if (resultat == 0) {
			resultatDate = 1;
		} else if (resultat > 0) {
			resultatDate = 3;
		}

		Utilisateur titi = utilisateurService.findByPseudo(principal.getName());

		model.addAttribute("utilisateur", titi);
		model.addAttribute("article", encheresService.getArticleById(id));
		model.addAttribute("retrait", encheresService.getRetraitByEnchere(id));
		model.addAttribute("resultatDate", resultatDate);
		var toto = encheresService.getEnchereById(id);
		if (toto != null) {
			model.addAttribute("enchere", toto);
			model.addAttribute("acheteur", utilisateurService.findById(toto.getNoUtilisateur()));
		}
///////////////////////////////// AJOUT UTILISATEUR A ARTICLE SI ENCHERE GAGNER	
		if (toto != null && resultatDate != 2) {
			var tutu = encheresService.getEnchereById(id);
			int idUser = tutu.getNoUtilisateur();
			encheresService.ajoutArticleAcheteur(idUser, articleEnEnchere);
		}
		return "/DetailVente";

	}
///////////////////////////////// MODIFIER VENTE //////////////////////////////////////////////////////////////////
	@GetMapping({ "/ModifVente" })
	public String ModifArticle(Principal principal, @ModelAttribute("article") ArticleVendu article,
			@ModelAttribute("retrait") Retrait infoRetrait, Model model, @RequestParam int id) {
		List<Categorie> listeCategories = encheresCategoriesService.getCategories();
		ArticleVendu articleModif = encheresService.getArticleById(id);

		model.addAttribute("utilisateur", utilisateurService.findByPseudo(principal.getName()));
		model.addAttribute("article", articleModif);
		model.addAttribute("categorie", listeCategories);

		return "ModifVente";
	}

	@PostMapping({ "ModifVente" })
	public String ModifArticleFaite(@ModelAttribute("articleVendu") ArticleVendu articleVendu,
			@ModelAttribute("retrait") Retrait infoRetrait, Model model) {

		int id = articleVendu.getNoArticle();
		List<Categorie> listeCategories = encheresCategoriesService.getCategories();
		
		model.addAttribute("categorie", listeCategories);

		encheresService.modifierArticle(articleVendu, id);
		encheresService.modifierInfoRetrait(infoRetrait, articleVendu);

		return "redirect:/accueil";
	}

	///////////////////////////////// ENCHERIR ////////////////////////////////////////////////////////////////////////

	@PostMapping({ "/EnchereAjout" })
	public String FaireUneEnchere(Principal principal, @ModelAttribute("article") ArticleVendu article,
			@ModelAttribute("enchere") Enchere infoEncheres, Model model) {

		int id = article.getNoArticle();		
		ArticleVendu articleEnEnchere = encheresService.getArticleById(id);
		Date date1 = java.sql.Date.valueOf(LocalDate.now());
		Date date2 = articleEnEnchere.getDateFinEncheres();
		// Date date1 = date2;
		int resultat = date1.compareTo(date2);
		int resultatDate = 0;
///////////////////////////////// COMPARAISON DATE 				
		if (resultat < 0) {
			resultatDate = 2;
		} else if (resultat == 0) {
			resultatDate = 1;
		} else if (resultat > 0) {
			resultatDate = 3;
		}
		
		encheresService.ajouterEnchere(principal, article, infoEncheres);
///////////////////////////////// DONNE LES DONNEES AU HTML				
		model.addAttribute("utilisateur", utilisateurService.findByPseudo(principal.getName()));
		model.addAttribute("article", encheresService.getArticleById(id));
		model.addAttribute("retrait", encheresService.getRetraitByEnchere(id));
		model.addAttribute("resultatDate", resultatDate);

		var toto = encheresService.getEnchereById(id);
///////////////////////////////// DONNE LES DONNEES DE LA TABLE ENCHERE AU HTML				
		model.addAttribute("enchere", toto);
		model.addAttribute("acheteur", utilisateurService.findById(toto.getNoUtilisateur()));

		return "/DetailVente";
	}
	/////////////////////////////////DELETE ARTICLE ////////////////////////////////////////////////////////////////////////
	
	@GetMapping("/DeleteArticle")
	public String deleteUtilisateur(Principal principal,
			@RequestParam int id, Model model) {
		
		ArticleVendu article;
		ArticleVendu articleDelete;
		
		article = encheresService.getArticleById(id);
		articleDelete = encheresService.deleteArticle(article);

		model.addAttribute("ArticleDelete", articleDelete);

		return "redirect:/accueil";
	}
}
