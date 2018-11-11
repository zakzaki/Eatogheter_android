package Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private String id;
    private String nom;
    private String prenom;
    private String age;
    private String pseudo;

    public User(String nom, String prenom, String age, String pseudo){

        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.age=age;
        this.pseudo=pseudo;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Map<String, Object> convert_map(){
        HashMap<String, Object> hm=new HashMap<>();

        hm.put("nom",nom);
        hm.put("prenom",prenom);
        hm.put("age",age);
        hm.put("pseudo",pseudo);

        return hm;
    }


}
