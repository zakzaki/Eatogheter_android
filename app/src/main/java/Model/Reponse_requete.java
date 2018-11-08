package Model;

import java.io.Serializable;

public class Reponse_requete implements Serializable {

    private String id;
    private String nom;
    private String adresse;
    private String categorie;
    private String photo;

    public Reponse_requete(String id,String nom, String adresse, String categorie, String photo){

        this.id=id;
        this.nom=nom;
        this.adresse=adresse;
        this.categorie=categorie;
        this.photo=photo;
    }

    public Reponse_requete(String id,String nom, String adresse, String categorie){

        this.id=id;
        this.nom=nom;
        this.adresse=adresse;
        this.categorie=categorie;
    }

    public Reponse_requete(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
