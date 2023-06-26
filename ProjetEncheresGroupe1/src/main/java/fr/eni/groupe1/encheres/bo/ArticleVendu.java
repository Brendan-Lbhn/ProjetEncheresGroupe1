package fr.eni.groupe1.encheres.bo;

import java.util.Date;

public class ArticleVendu {

	Integer noArticle;
	String nomArticle;
	String description;
	Date dateDebutEncheres;
	Date dateFinEncheres;
	Integer miseAPrix;
	Integer prixVente;
	boolean etatVente;
	Integer noCategorie;
	Integer noUtilisateur;
	
	public ArticleVendu () {
		
	}
	
	
	
	public ArticleVendu(Integer noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, Integer miseAPrix, Integer prixVente, boolean etatVente, Integer noCategorie,
			Integer noUtilisateur) {

		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.noCategorie = noCategorie;
		this.noUtilisateur = noUtilisateur;
	}




	public Integer getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}
	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}
	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}
	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
	public Integer getMiseAPrix() {
		return miseAPrix;
	}
	public void setMiseAPrix(Integer miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
	public Integer getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(Integer prixVente) {
		this.prixVente = prixVente;
	}
	public boolean isEtatVente() {
		return etatVente;
	}
	public void setEtatVente(boolean etatVente) {
		this.etatVente = etatVente;
	}
	public Integer getNoCategorie() {
		return noCategorie;
	}
	public void setNoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	
	
	
	
	
}
