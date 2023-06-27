package fr.eni.groupe1.encheres.bll;

import java.util.List;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Retrait;

public interface EncheresService {

	List<ArticleVendu> getArticle();

	void ajouterArticle(ArticleVendu articleVendu);

	void ajouterInfoRetrait(Retrait infoRetrait);

}
