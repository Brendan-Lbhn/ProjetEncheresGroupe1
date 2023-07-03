package fr.eni.groupe1.encheres.bll;

import java.util.List;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Retrait;

public interface EncheresService {

	List<ArticleVendu> getArticle();
	List<ArticleVendu> getArtcicleByCategorie(Integer noCategorie);
	List<ArticleVendu> getArticleByName(String nameCategorie); 
	List<ArticleVendu> getArticleByNameAndCategorie(String nomArticle, Integer noCategorie);
	void ajouterArticle(ArticleVendu articleVendu);

	void ajouterInfoRetrait(Retrait infoRetrait, ArticleVendu article);

	List<Retrait> getRetrait();
	ArticleVendu getArticleById(int id);
	Retrait getRetraitByEnchere(int id);
	

}
