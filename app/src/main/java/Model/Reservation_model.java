package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Reservation_model implements Serializable {

    private String key;
    private Reponse_requete r;
    private String date;
    private String heure;
    private ArrayList<String>users;
    private String key_key;

    public Reservation_model(String key, Reponse_requete r, String date, String heure, ArrayList<String> users) {
        this.key=key;
        this.r = r;
        this.date = date;
        this.heure = heure;
        this.users = users;
    }

    public Reservation_model(String key, Reponse_requete r, String date, String heure, ArrayList<String> users,String key_key) {
        this.key=key;
        this.r = r;
        this.date = date;
        this.heure = heure;
        this.users = users;
        this.key_key=key_key;
    }

    public Reservation_model(Reponse_requete r, String date, String heure, ArrayList<String> users) {
        this.r = r;
        this.date = date;
        this.heure = heure;
        this.users = users;
    }

    public Reservation_model(Reponse_requete r, String date, String heure,String key_key) {
        this.r = r;
        this.date = date;
        this.heure = heure;
        this.key_key=key_key;
    }

    public Reservation_model() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Reponse_requete getR() {
        return r;
    }

    public void setR(Reponse_requete r) {
        this.r = r;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getKey_key() {
        return key_key;
    }

    public void setKey_key(String key_key) {
        this.key_key = key_key;
    }
}
