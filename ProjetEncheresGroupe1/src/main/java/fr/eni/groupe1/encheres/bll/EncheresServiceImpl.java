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



	@Override
	public List<ArticleVendu> getArtcicleByCategorie(Integer noCategorie) {
		System.out.println("Dans EncheresServiceImpl.getArticleByCategorie");
		return encheresDAO.articleByCategorie(noCategorie);
	}



	@Override
	public List<ArticleVendu> getArticleByName(String nameCategorie) {
		// TODO Auto-generated method stub
		return encheresDAO.articleByName(nameCategorie);
	}



	@Override
	public List<ArticleVendu> getArticleByNameAndCategorie(String nomArticle, Integer noCategorie) {
		// TODO Auto-generated method stub
		return encheresDAO.articleByNameAndCategorie(nomArticle, noCategorie);
	}

}
