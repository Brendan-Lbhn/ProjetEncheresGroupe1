package fr.eni.groupe1.encheres.bo;

import java.util.Date;

public class ArticleVendu {

    public Integer noArticle;
    private String nomArticle;
    private String description;
    private Date dateDebutEncheres;
    private Date dateFinEncheres;
    private Integer miseAPrix;
    private Integer prixVente;
    private String etatVente = "En attente de vente" ;
    private Integer noCategorie;
    public Integer noUtilisateur;
    
    private Utilisateur vendeur;
    private Categorie categorie; 
    
    public ArticleVendu () {
        
    }
    
    
    
    @Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", noCategorie=" + noCategorie
				+ ", noUtilisateur=" + noUtilisateur + ", vendeur=" + vendeur + ", categorie=" + categorie + "]";
	}



	public ArticleVendu(Integer noArticle, String nomArticle, String description, Date dateDebutEncheres,
            Date dateFinEncheres, Integer miseAPrix, Integer prixVente, String etatVente, Integer noCategorie,
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
    
    public ArticleVendu(Integer noArticle, String nomArticle, String description, Date dateDebutEncheres,
            Date dateFinEncheres, Integer miseAPrix, Integer prixVente, String etatVente, Integer noCategorie,
            Integer noUtilisateur,Utilisateur vendeur,Categorie categorie) {

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
        this.vendeur=vendeur; 
        this.categorie=categorie;
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
    public String getEtatVente() {
        return etatVente;
    }
    public void setEtatVente(String etatVente) {
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



    public Utilisateur getVendeur() {
        return vendeur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    

    
    
    
    
    
}