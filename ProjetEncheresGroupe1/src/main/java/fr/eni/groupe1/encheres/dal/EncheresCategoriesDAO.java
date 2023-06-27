package fr.eni.groupe1.encheres.dal;

import java.util.List;

import fr.eni.groupe1.encheres.bo.Categorie;

public interface EncheresCategoriesDAO {
	
	List<Categorie> findAll(); 
	
	void addCategorie(); 

}
