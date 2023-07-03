package fr.eni.groupe1.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
public class LoginController {

	 @GetMapping("/login")
	    public String vueLogin() {
	        return "Login";
	    }
	 
	 @PostMapping("/login")
	    public String vueLogin(@ModelAttribute("utilisateur") Utilisateur utilisateur) {
	        return "Login";
	    }
}
