package fr.eni.groupe1.encheres.bll;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.dal.EncheresDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;

	
	
	public EncheresServiceImpl(EncheresDAO encheresDAO) {
		this.encheresDAO = encheresDAO;
	}



	@Override
	public List<ArticleVendu> getArticle() {
		return encheresDAO.articleAll();
	}



	@Override
	public void ajouterArticle(ArticleVendu articleVendu) {
		encheresDAO.setArticle(articleVendu);		
	}



	@Override
	public void ajouterInfoRetrait(Retrait infoRetrait, ArticleVendu article) {
		encheresDAO.setInfoRetrait(infoRetrait, article);		
	}



	@Override
	public List<Retrait> getRetrait() {
		return encheresDAO.retraitAll();
	}



	@Override
	public List<ArticleVendu> getArtcicleByCategorie(Integer noCategorie) {
		System.out.println("Dans EncheresServiceImpl.getArticleByCategorie");
		return encheresDAO.articleByCategorie(noCategorie);
	}



	@Override
	public List<ArticleVendu> getArticleByName(String nameCategorie) {
		return encheresDAO.articleByName(nameCategorie);
	}



	@Override
	public List<ArticleVendu> getArticleByNameAndCategorie(String nomArticle, Integer noCategorie) {
		return encheresDAO.articleByNameAndCategorie(nomArticle, noCategorie);
	}



	@Override
	public List<ArticleVendu> getArticleByFilters(Integer filtre, boolean encheresOuvertes, boolean encheresEnCours,
			boolean encheresRemportees, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees) {
		// TODO Auto-generated method stub
		return encheresDAO.articleByFilter(filtre, encheresOuvertes, encheresEnCours, encheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees);
	}
		
	@Override	
	public ArticleVendu getArticleById(int id) {
		return encheresDAO.articleById(id) ;
	}



	@Override
	public Retrait getRetraitByEnchere(int id) {
		return encheresDAO.retraitById(id);
	}



	@Override
	public void ajouterEnchere(Principal principal, ArticleVendu article, Enchere infoEncheres) {
		encheresDAO.ajouterEnchere(principal,article,infoEncheres);		
	}



	@Override
	public Enchere getEnchereById(int id) {
		return encheresDAO.enchereById(id);
	}

}
