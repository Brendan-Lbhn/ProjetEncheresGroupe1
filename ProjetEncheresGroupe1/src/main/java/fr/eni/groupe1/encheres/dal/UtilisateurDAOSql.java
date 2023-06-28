package fr.eni.groupe1.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOSql implements UtilisateurDAO {
	private final static String INSERT = "insert into UTILISATEURS ( pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,credit,administrateur ) values (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse,:credit,:administrateur)" ;
	
	@Autowired
	private NamedParameterJdbcTemplate njt;
	
	
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate njt) {
		this.njt=njt;
	}
	
	@Override
	public void save(Utilisateur utilisateur) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		njt.update(INSERT,new BeanPropertySqlParameterSource(utilisateur),keyHolder);
		utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
		System.out.println("insert de l'utilisateur : "+ utilisateur);
	}

}
