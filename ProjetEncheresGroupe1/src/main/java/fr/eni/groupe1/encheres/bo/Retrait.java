package fr.eni.groupe1.encheres.bo;

import java.util.Objects;

public class Retrait {
	private String rue;
	private Integer codePostal;
	private String ville;
	
	public Retrait() {
		// TODO Auto-generated constructor stub
	}
	
	public Retrait(String rue,Integer codePostal,String ville) {
		this.rue=rue;
		this.codePostal=codePostal;
		this.ville=ville;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codePostal, rue, ville);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Retrait other = (Retrait) obj;
		return Objects.equals(codePostal, other.codePostal) && Objects.equals(rue, other.rue)
				&& Objects.equals(ville, other.ville);
	}

	@Override
	public String toString() {
		return "Retrait [rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}
	
}
