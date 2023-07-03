package fr.eni.groupe1.encheres.dal;

import java.util.List;

import fr.eni.groupe1.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	void save(Utilisateur utilisateur);
	
	List<Utilisateur>findAll();
	
	Utilisateur findById(Integer id);

	Utilisateur findByPseudo(String pseudo);

	Utilisateur deleteProfil(String pseudo); 
	List<Utilisateur> VendeurByName(String nomVendeur);
}
