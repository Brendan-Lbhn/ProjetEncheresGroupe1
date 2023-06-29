package fr.eni.groupe1.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Utilisateur;

public class ArticlesVendusRowMapper implements RowMapper<ArticleVendu> {
	private UtilisateurDAO utilisateurDAO;
	private EncheresCategoriesDAO encheresCategoriesDAO;

	public ArticlesVendusRowMapper(UtilisateurDAO utilisateurDAO, EncheresCategoriesDAO encheresCategoriesDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.encheresCategoriesDAO = encheresCategoriesDAO;
	}
	
	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArticleVendu article = new ArticleVendu();
		System.out.println("vous êtes dans le RowMapper");
		article.setNoArticle(rs.getInt("no_article"));
		article.setNomArticle(rs.getString("nom_article"));
		article.setDescription(rs.getString("description"));
		article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
		article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
		article.setMiseAPrix(rs.getInt("prix_initial"));
		article.setPrixVente(rs.getInt("prix_vente"));
		article.setNoUtilisateur(rs.getInt("no_utilisateur"));
		article.setNoCategorie(rs.getInt("no_categorie"));
		Utilisateur vendeur = null;
		vendeur = utilisateurDAO.findById(rs.getInt("no_utilisateur"));
		article.setVendeur(vendeur);
		Categorie categorie = null;
		categorie = encheresCategoriesDAO.findById(rs.getInt("no_categorie"));
		article.setCategorie(categorie);
		return article;
	}

}
