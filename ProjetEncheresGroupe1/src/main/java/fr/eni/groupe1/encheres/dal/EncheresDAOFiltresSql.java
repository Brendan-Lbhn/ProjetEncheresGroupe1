package fr.eni.groupe1.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;

@Repository
public class EncheresDAOFiltresSql implements EncheresDAOFiltres{
	
	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UtilisateurDAO utilisateurDAO;
	private EncheresCategoriesDAO encheresCategoriesDAO;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(UtilisateurDAO utilisateurDAO,
			EncheresCategoriesDAO encheresCategoriesDAO, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.utilisateurDAO = utilisateurDAO;
		this.encheresCategoriesDAO = encheresCategoriesDAO;
	}
	
	
	
	@Override
	public List<ArticleVendu> articleByFilter(Integer filtre, boolean encheresOuvertes, boolean encheresEnCours,
			boolean encheresRemportees, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees, int userId) {

		StringBuilder requete = new StringBuilder();
		List<ArticleVendu> listArticle;
		
		if (filtre == 1) {
			if (encheresOuvertes == false && encheresEnCours == false && encheresRemportees == false) {
				requete.append(SELECT_ALL_ARTICLE);
			} else {
				requete.append(
						"SELECT ARTICLES_VENDUS.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES_VENDUS.no_utilisateur, ARTICLES_VENDUS.no_categorie\r\n"
						+ "FROM ARTICLES_VENDUS INNER JOIN ENCHERES\r\n"
						+ "ON (ARTICLES_VENDUS.no_article = ENCHERES.no_article) ");
				if (encheresOuvertes == true) {
					requete.append("WHERE ARTICLES_VENDUS.date_fin_encheres >= GETDATE() ");
				}
				if (encheresEnCours == true) {
					if (requete.toString().contains("WHERE")) {
						requete.append("OR ");
					} else {
						requete.append("WHERE ");
					}
					requete.append("ARTICLES_VENDUS.date_fin_encheres >= GETDATE() AND  ENCHERES.no_utilisateur = :id ");
				}

				if (encheresRemportees == true) {
					if (requete.toString().contains("WHERE")) {
						requete.append("OR ");
					} else {
						requete.append("WHERE ");
					}
					requete.append(
							"ENCHERES.no_utilisateur = :id AND ARTICLES_VENDUS.date_fin_encheres <= GETDATE() AND montant_enchere = ARTICLES_VENDUS.prix_vente");
				}
			}

		} else if (filtre == 2) {

			if (ventesEnCours == false && ventesNonDebutees == false && ventesTerminees == false) {
				requete.append(SELECT_ALL_ARTICLE);
			} else {
				requete.append(
						"SELECT ARTICLES_VENDUS.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, ARTICLES_VENDUS.no_utilisateur, ARTICLES_VENDUS.no_categorie "
								+ "FROM ARTICLES_VENDUS ");
				if (ventesEnCours == true) {
					requete.append(
							"WHERE ARTICLES_VENDUS.no_utilisateur = :id AND ARTICLES_VENDUS.date_debut_encheres <= GETDATE() AND ARTICLES_VENDUS.date_fin_encheres > GETDATE() ");
				}

				if (ventesNonDebutees == true) {
					if (requete.toString().contains("WHERE")) {
						requete.append("OR ");

					} else {
						requete.append("WHERE ");
					}

					requete.append(
							"ARTICLES_VENDUS.no_utilisateur = :id AND ARTICLES_VENDUS.date_debut_encheres >= GETDATE() ");
				}

				if (ventesTerminees == true) {
					if (requete.toString().contains("WHERE")) {
						requete.append("OR ");
					} else {
						requete.append("WHERE ");
					}
					requete.append(
							"ARTICLES_VENDUS.no_utilisateur = :id AND ARTICLES_VENDUS.date_fin_encheres < GETDATE()-1");
				}
			}
		}		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id",userId);

		listArticle = namedParameterJdbcTemplate.query(requete.toString(), params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		
		return listArticle;
	}

}
