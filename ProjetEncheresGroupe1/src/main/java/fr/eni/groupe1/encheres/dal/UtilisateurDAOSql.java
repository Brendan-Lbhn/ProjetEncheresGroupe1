package fr.eni.groupe1.encheres.dal;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOSql implements UtilisateurDAO {
	private final static String SELECT_ALL_UTILISATEURS = "Select * from UTILISATEURS";
	private final static String FIND_BY_ID = "select * from UTILISATEURS where no_utilisateur=:id";
	private final static String FIND_BY_PSEUDO = "select * from UTILISATEURS where pseudo=?";

	private final static String INSERT = "insert into UTILISATEURS ( pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,credit,administrateur ) values (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse,:credit,:administrateur)";
	private static final String DELETE = "delete UTILISATEURS where no_Utilisateur= :noUtilisateur";
	private static final String DELETE_ARTICLE = "delete ARTICLES_VENDUS where no_article= :no_article";
	private static final String DELETE_RETRAIT = "delete RETRAITS where no_article= :no_article";
	private static final String DELETE_ENCHERE ="delete ENCHERES where no_article= :no_article";

	private static final String SELECT_ARTICLE = "select * from ARTICLES_VENDUS where no_utilisateur= :no_utilisateur";
	private static final String UPDATE = "update UTILISATEURS set pseudo=:pseudo, nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, rue=:rue, code_postal=:codePostal, ville=:ville, mot_de_passe=:motDePasse where no_Utilisateur=:noUtilisateur";
	private final static String CHECK_PSEUDO = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = ?";
	private final static String CHECK_EMAIL = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ?";
	private static final String UPDATE_NEW_CREDIT = "update UTILISATEURS set credit=:credit where no_utilisateur=:no_utilisateur ";

	@Autowired
	private NamedParameterJdbcTemplate njt;

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate njt, UtilisateurDAO utilisateurDAO) {
		this.njt = njt;
	}

	class UtilisateursRowMapper implements RowMapper<Utilisateur> {

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur user = new Utilisateur();

			user.setNoUtilisateur(rs.getInt("no_utilisateur"));
			user.setPseudo(rs.getString("pseudo"));
			user.setNom(rs.getString("nom"));
			user.setPrenom(rs.getString("prenom"));
			user.setEmail(rs.getString("email"));
			user.setTelephone(rs.getString("telephone"));
			user.setRue(rs.getString("rue"));
			user.setCodePostal(rs.getInt("code_postal"));
			user.setVille(rs.getString("ville"));
			user.setMotDePasse(rs.getString("mot_de_passe"));
			user.setCredit(rs.getInt("credit"));
			user.setAdministrateur(rs.getInt("administrateur"));
			return user;
		}
	}
/////////////////////////////////       ENREGISTRER     ////////////////////////////////////////////

	@Override
	public void save(Utilisateur utilisateur) {
		if (utilisateur.getNoUtilisateur() == null) {

			StringBuilder errors = new StringBuilder();
			int countPseudo = njt.getJdbcOperations().queryForObject(CHECK_PSEUDO, Integer.class,utilisateur.getPseudo());
			if (countPseudo > 0) {
				errors.append("Le pseudo est déjà utilisé par un autre utilisateur. ");

			}

			int countEmail = njt.getJdbcOperations().queryForObject(CHECK_EMAIL, Integer.class, utilisateur.getEmail());
			if (countEmail > 0) {
				errors.append("L'email est déjà utilisé par un autre utilisateur.");

			}
			if (errors.length() > 0) {
				throw new IllegalStateException(errors.toString());
			}

			// Insertion utilisateur
			KeyHolder keyHolder = new GeneratedKeyHolder();
			njt.update(INSERT, new BeanPropertySqlParameterSource(utilisateur), keyHolder);
			utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());

			// mise à jours
		} else {// update

			njt.update(UPDATE, new BeanPropertySqlParameterSource(utilisateur));

		}

	}

	@Override
	public void ajouterCredit(Utilisateur utilisateur, Principal principal, int creditActuel) {
		MapSqlParameterSource newCreditMap = new MapSqlParameterSource();
		Utilisateur acheteur;

		int creditFinal = ((utilisateur.getCredit()) + creditActuel);
		acheteur = njt.getJdbcOperations().queryForObject(FIND_BY_PSEUDO, new UtilisateursRowMapper(),principal.getName());
		
		newCreditMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
		newCreditMap.addValue("credit", creditFinal);

		njt.update(UPDATE_NEW_CREDIT, newCreditMap);
	}

/////////////////////////////////       FIND BY     ////////////////////////////////////////////

	@Override
	public List<Utilisateur> findAll() {
		List<Utilisateur> listUtilisateur;

		listUtilisateur = njt.query(SELECT_ALL_UTILISATEURS, new UtilisateursRowMapper());

		return listUtilisateur;
	}

	@Override
	public Utilisateur findById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		Utilisateur user = null;

		user = njt.queryForObject(FIND_BY_ID, params, new UtilisateursRowMapper());

		return user;

	}

	// @Override
	public Utilisateur findByPseudo(String pseudo) {

		Utilisateur user = null;

		user = njt.getJdbcOperations().queryForObject(FIND_BY_PSEUDO, new UtilisateursRowMapper(), pseudo);
		System.out.println(user);

		return user;

	}

	@Override
	public List<Utilisateur> VendeurByName(String nomVendeur) {

		List<Utilisateur> listVendeur;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("pseudo", nomVendeur);

		listVendeur = njt.query(FIND_BY_PSEUDO, params, new UtilisateursRowMapper());

		return listVendeur;
	}
/////////////////////////////////       DELETE     ////////////////////////////////////////////

	@Override
	public Utilisateur deleteProfil(String pseudo, Principal principal) {

		Utilisateur user = null;
		Utilisateur UserActuel = findByPseudo(principal.getName());
		List<ArticleVendu> article;
		MapSqlParameterSource params = new MapSqlParameterSource();
		MapSqlParameterSource paramArticle = new MapSqlParameterSource();

		params.addValue("no_utilisateur", UserActuel.getNoUtilisateur());

		article = njt.query(SELECT_ARTICLE, params, new BeanPropertyRowMapper<>(ArticleVendu.class));
		for (ArticleVendu noArticle : article) {
			paramArticle.addValue("no_article", noArticle.getNoArticle());
			njt.update(DELETE_ENCHERE, paramArticle);
			njt.update(DELETE_RETRAIT, paramArticle);
			njt.update(DELETE_ARTICLE, paramArticle);
		}

		user = njt.getJdbcOperations().queryForObject(FIND_BY_PSEUDO, new UtilisateursRowMapper(), pseudo);
		njt.update(DELETE, new BeanPropertySqlParameterSource(user));
		return user;
	}

}
