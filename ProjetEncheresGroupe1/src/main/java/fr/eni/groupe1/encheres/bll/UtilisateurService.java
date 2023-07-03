package fr.eni.groupe1.encheres.bll;

import java.util.List;


import fr.eni.groupe1.encheres.bo.Utilisateur;

public interface UtilisateurService {

	void enregistrer(Utilisateur utilisateur);
	
	List<Utilisateur> getUtilisateur();

	Utilisateur findById(Integer id);

	Utilisateur findByPseudo(String name);

	Utilisateur deleteProfil(String name);
	
}
