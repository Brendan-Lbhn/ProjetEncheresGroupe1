package fr.eni.groupe1.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO=utilisateurDAO;
	}

	@Override
	public void enregistrer(Utilisateur utilisateur) {
		utilisateurDAO.save(utilisateur);
	}

	@Override
	public List<Utilisateur> getUtilisateur() {
		// TODO Auto-generated method stub
		return utilisateurDAO.findAll();
	}

}
