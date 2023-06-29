package fr.eni.groupe1.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.dal.EncheresCategoriesDAO;

@Service
public class EncheresCategoriesServiceImpl implements EncheresCategoriesService{

	EncheresCategoriesDAO encheresCategoriesDAO; 
	
	@Autowired
	public EncheresCategoriesServiceImpl(EncheresCategoriesDAO encheresCategoriesDAO) {
		this.encheresCategoriesDAO = encheresCategoriesDAO;
	}
	
	@Override
	public List<Categorie> getCategories() {
		List<Categorie>listeCategories = encheresCategoriesDAO.findAll();
		return listeCategories;
	}

	@Override
	public void ajouterCategorie(Categorie categorie) {
		encheresCategoriesDAO.addCategorie();
		
	}

	@Override
	public Categorie findById(Integer id) {
		return encheresCategoriesDAO.findById(id);
	}
	
	

}
