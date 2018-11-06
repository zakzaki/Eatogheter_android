package Model;

public class Restaurant {
    private String id_resaurant;
    private String nom_resaurant;
    private String adresse_resaurant;
    private String cat_resaurant;


    public Restaurant(String id_resaurant, String nom_resaurant, String adresse_resaurant, String cat_resaurant) {
        this.id_resaurant = id_resaurant;
        this.nom_resaurant = nom_resaurant;
        this.adresse_resaurant = adresse_resaurant;
        this.cat_resaurant = cat_resaurant;
    }

    public String getId_resaurant() {
        return id_resaurant;
    }

    public void setId_resaurant(String id_resaurant) {
        this.id_resaurant = id_resaurant;
    }

    public String getNom_resaurant() {
        return nom_resaurant;
    }

    public void setNom_resaurant(String nom_resaurant) {
        this.nom_resaurant = nom_resaurant;
    }

    public String getAdresse_resaurant() {
        return adresse_resaurant;
    }

    public void setAdresse_resaurant(String adresse_resaurant) {
        this.adresse_resaurant = adresse_resaurant;
    }

    public String getCat_resaurant() {
        return cat_resaurant;
    }

    public void setCat_resaurant(String cat_resaurant) {
        this.cat_resaurant = cat_resaurant;
    }
}
