package fr.eni.groupe1.encheres.bll;

import java.util.List;

import fr.eni.groupe1.encheres.bo.ArticleVendu;

public interface EncheresService {

	List<ArticleVendu> getArticle();

	void ajouterArticle(ArticleVendu articleVendu);

}
