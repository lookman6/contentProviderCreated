package com.tp_examen.projetcrud.model;

public class Personne {

    int id, age;
    String nom, prenom;

    public Personne(int id, String nom, String prenom, int age) {
        this.id = id;
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne(String nom, String prenom, int age) {
        this.age = age;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Personne() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
}
