package fr.eni.groupe1.encheres.dal;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Repository
public class EncheresDAOSql implements EncheresDAO {

	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS";
	private final static String SELECT_ALL_RETRAITS = "select * from RETRAITS";

	private final static String SELECT_ARTICLE_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie =:noCategorie";
	private final static String SELECT_ARTICLE_BY_NAME = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE '%' + :nomArticle + '%'";
	private final static String SELECT_ARTICLE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article =:noArticle";
	private final static String SELECT_ARTICLE_BY_NAME_AND_CATEGORY = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE '%' + :nomArticle + '%' AND no_categorie=:noCategorie";

	private static final String SELECT_COUNT_ENCHEREPARAM_BY_ID = "SELECT COUNT(*) FROM ENCHERES WHERE no_article = ?";

	private static final String SELECT_COUNT_ENCHERE_BY_ID = "SELECT COUNT(*) FROM ENCHERES WHERE no_article = ?";
	private static final String SELECT_RETRAIT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article =:noArticle";
	private static final String SELECT_ENCHERE_BY_ID = "SELECT * FROM ENCHERES WHERE no_article =:noArticle";

	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";
	private final static String INSERT_NEW_INFORETRAIT = "insert into RETRAITS ( no_article, rue, code_postal, ville ) values (:no_article, :rue, :code_postal, :ville)";

	private static final String INSERT_NEW_ENCHERE = "insert into ENCHERES ( no_utilisateur, no_article, date_enchere, montant_enchere ) values (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";
	private static final String UPDATE_NEW_ENCHERE = "update ENCHERES set no_utilisateur=:no_utilisateur,no_article=:no_article,date_enchere=:date_enchere, montant_enchere=:montant_enchere WHERE no_article =:no_article";

	private static final String DELETE_MODIF_ARTICLE = null;
	private static final String UPDATE = null;

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

/////////////////////////////////       AFFICHAGE     ////////////////////////////////////////////

	@Override
	public List<ArticleVendu> articleAll() {
		List<ArticleVendu> listArticle;

		System.out.println("Dans articleAll()");
		listArticle = namedParameterJdbcTemplate.query(SELECT_ALL_ARTICLE,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		return listArticle;
	}

	@Override
	public List<Retrait> retraitAll() {
		List<Retrait> listRetrait;
		listRetrait = namedParameterJdbcTemplate.query(SELECT_ALL_RETRAITS, new BeanPropertyRowMapper<>(Retrait.class));
		return listRetrait;
	}
/////////////////////////////////       ARTICLE BY     ////////////////////////////////////////////

	@Override
	public List<ArticleVendu> articleByCategorie(Integer noCategorie) {
		List<ArticleVendu> listArticle;
		System.out.println("Dans articlebyCategorie() , numero categorie :" + noCategorie);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noCategorie", noCategorie);
		System.out.println(params);

		listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_CATEGORIE, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		System.out.println("liste articles = " + listArticle.toString());

		return listArticle;
	}

	@Override
	public List<ArticleVendu> articleByName(String nomArticle) {
		System.out.println("dans la méthode article by name de la DAO, nomArticle = " + nomArticle);
		List<ArticleVendu> listArticle;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nomArticle", nomArticle);

		listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_NAME, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));

		System.out.println("dans la methode articleByName de la DAO, listArticle = " + listArticle);

		return listArticle;
	}

	@Override
	public List<ArticleVendu> articleByNameAndCategorie(String nomArticle, Integer noCategorie) {
		List<ArticleVendu> listArticle;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nomArticle", nomArticle);
		params.addValue("noCategorie", noCategorie);

		listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_NAME_AND_CATEGORY, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		return listArticle;
	}

	@Override
	public List<ArticleVendu> articleByFilter(Integer filtre, boolean encheresOuvertes, boolean encheresEnCours,
			boolean encheresRemportees, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees) {

		StringBuilder requete = new StringBuilder();
		List<ArticleVendu> listArticle;

		System.out.println("dans la DAL TEMP méthode ArticleByfilter");

		if (filtre == 1) {
			System.out.println("Mes achats");

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
					requete.append(
							"ARTICLES_VENDUS.date_fin_encheres >= GETDATE() AND  ENCHERES.no_utilisateur = :id ");
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

			System.out.println("Mes ventes");
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
							"ARTICLES_VENDUS.no_utilisateur = :id AND ARTICLES_VENDUS.date_fin_encheres <= GETDATE()");
				}
			}
		}
		System.out.println("!!!!!!!!!!!" + requete);

//		Utilisateur user =  (Utilisateur) SecurityContextHolder
//				.getContext().getAuthentication().getPrincipal();
// 
//		System.out.println("!!!!!!!!!!! Id utilisateur : " + user.getNoUtilisateur() );
//		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", 1);

		listArticle = namedParameterJdbcTemplate.query(requete.toString(), params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));

		System.out.println("liste articles = " + listArticle.toString());

//		listArticle = null;

		return listArticle;
	}

/////////////////////////////////       BY ID     ////////////////////////////////////////////

	public ArticleVendu articleById(int id) {
		System.out.println("je passe par le article by id");
		ArticleVendu article;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", id);
		System.out.println("avant le query");
		article = namedParameterJdbcTemplate.queryForObject(SELECT_ARTICLE_BY_ID, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		System.out.println("après le query");
		System.out.println("dans la methode articleById de la DAO, listArticle = " + article);
		return article;
	}

	@Override
	public Retrait retraitById(int id) {
		System.out.println("je passe par le retrait by id");
		Retrait retrait;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", id);

		retrait = namedParameterJdbcTemplate.queryForObject(SELECT_RETRAIT_BY_ID, params,
				new BeanPropertyRowMapper<>(Retrait.class));

		System.out.println("dans la methode RetraitById de la DAO, listRetrait = " + retrait);
		return retrait;
	}

	@Override
	public Enchere enchereById(int id) {
		Enchere enchere;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", id);
		int countEnchere = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SELECT_COUNT_ENCHERE_BY_ID,Integer.class, id);
		if (countEnchere != 0) {
			enchere = namedParameterJdbcTemplate.queryForObject(SELECT_ENCHERE_BY_ID, params,
					new BeanPropertyRowMapper<>(Enchere.class));

			return enchere;
		} else {
			return null;
		}
	}
/////////////////////////////////       SET     ////////////////////////////////////////////

	@Override
	public void setArticle(ArticleVendu article) {
		MapSqlParameterSource newArticleMap = new MapSqlParameterSource();
		if (article.getNoArticle() != null) {
			newArticleMap.addValue("no_article", article.getNoArticle());
		}
		newArticleMap.addValue("nom_article", article.getNomArticle());
		newArticleMap.addValue("description", article.getDescription());
		newArticleMap.addValue("date_debut_encheres", article.getDateDebutEncheres());
		newArticleMap.addValue("date_fin_encheres", article.getDateFinEncheres());
		newArticleMap.addValue("prix_initial", article.getMiseAPrix());
		newArticleMap.addValue("prix_vente", article.getPrixVente());
		newArticleMap.addValue("no_utilisateur", article.getNoUtilisateur());
		newArticleMap.addValue("no_categorie", article.getNoCategorie());
		System.out.println(article.toString());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		System.out.println("debut du set article");

		if (article.getNoArticle() == null) {
			System.out.println("debut du if save " + article);
			namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, newArticleMap, keyHolder);
			System.out.println("juste avant le keyholder");
			article.setNoArticle(keyHolder.getKey().intValue());
			// article.setNoArticle((Integer)keyHolder.getKeys().get("no_article"));//
			// recuperqtion de lq vqleur de l qrticle qfin de vqloriser lq FK
			System.out.println(article.getNoArticle());
			// setArticleByNoArticle(article.getNoArticle(), article.getNoUtilisateur());
			System.out.println("article : " + article.toString());

			// methode
		} else {// update
			System.out.println("je passe par le update du set article");
			MapSqlParameterSource articleModifMap = new MapSqlParameterSource();
			articleModifMap.addValue("no_article", article.noArticle);
			System.out.println("ajout map noArticle");
			namedParameterJdbcTemplate.update(DELETE_MODIF_ARTICLE, articleModifMap);
			System.out.println("delete article");
			// setActeurByFilm(film.getActeurs(), film.getIndexFilm());
			System.out.println("ajout acteur");
			namedParameterJdbcTemplate.update(UPDATE, newArticleMap);
			System.out.println("ajout des nouveaux param au film grace a la map");
		}
	}

	@Override
	public void setInfoRetrait(Retrait infoRetrait, ArticleVendu article) {
		MapSqlParameterSource newInfoRetraitMap = new MapSqlParameterSource();

		newInfoRetraitMap.addValue("no_article", article.getNoArticle());
		newInfoRetraitMap.addValue("rue", infoRetrait.getRue());
		newInfoRetraitMap.addValue("code_postal", infoRetrait.getCodePostal());
		newInfoRetraitMap.addValue("ville", infoRetrait.getVille());

		namedParameterJdbcTemplate.update(INSERT_NEW_INFORETRAIT, newInfoRetraitMap);
	}

	@Override
	public void ajouterEnchere(Principal principal, ArticleVendu article, Enchere infoEncheres) {
		System.out.println("je passe par le ajout enchere");

		MapSqlParameterSource newEnchereMap = new MapSqlParameterSource();
		MapSqlParameterSource modifEnchereMap = new MapSqlParameterSource();
		
		Utilisateur acheteur;
		LocalDate date = LocalDate.now();
		int idArticle = article.noArticle;
		acheteur = utilisateurDAO.findByPseudo(principal.getName());
		System.out.println("utilisateur no : " + acheteur.getNoUtilisateur());
		
		int countEnchere = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SELECT_COUNT_ENCHEREPARAM_BY_ID,
				Integer.class, idArticle);
		
		if (countEnchere == 0) {
			System.out.println("je passe par le if no article == null");

			newEnchereMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
			newEnchereMap.addValue("no_article", idArticle);
			newEnchereMap.addValue("date_enchere", date);
			newEnchereMap.addValue("montant_enchere", infoEncheres.getMontantEnchere());

			namedParameterJdbcTemplate.update(INSERT_NEW_ENCHERE, newEnchereMap);

		} else {
			System.out.println("je passe par le update du ajout enchere");

			modifEnchereMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
			modifEnchereMap.addValue("no_article", idArticle);
			modifEnchereMap.addValue("date_enchere", date);
			modifEnchereMap.addValue("montant_enchere", infoEncheres.getMontantEnchere());

			namedParameterJdbcTemplate.update(UPDATE_NEW_ENCHERE, modifEnchereMap);

		}
	}

/////////////////////////////////       SET   ////////////////////////////////////////////

}
