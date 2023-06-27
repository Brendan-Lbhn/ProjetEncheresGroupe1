package fr.eni.groupe1.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.Categorie;

@Repository
public class EncheresCategoriesDAOImpl implements EncheresCategoriesDAO{
	
	private static final String SELECT_ALL = "select libelle FROM dbo.CATEGORIES";

	@Autowired
	private NamedParameterJdbcTemplate njt;
	
	
	
	@Override
	public List<Categorie> findAll() {
		List<Categorie> listCategories = njt.query(SELECT_ALL, 
				new BeanPropertyRowMapper<>(Categorie.class));
		return listCategories;
	}

	@Override
	public void addCategorie() {
		// TODO Auto-generated method stub
		
	}
	

}
