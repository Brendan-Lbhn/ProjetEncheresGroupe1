package fr.eni.groupe1.encheres.bll;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurDAO utilisateurDAO;
    private PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, PasswordEncoder passwordEncoder) {
        this.utilisateurDAO = utilisateurDAO;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void enregistrer(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurDAO.save(utilisateur);
    }
}
