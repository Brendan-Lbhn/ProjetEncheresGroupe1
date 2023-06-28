package fr.eni.groupe1.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
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

}
