/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bapti
 */
public class Dictionnaire {
    private final List<String> mots;
    private static final String FILENAME = "dictionnaire.txt";
    
    public Dictionnaire(){
        mots=new ArrayList<>();
        chargerMots();
    }
    
    private void chargerMots(){
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                mots.add(ligne.toUpperCase());
            }
        } catch (IOException e) {
            mots.addAll(Arrays.asList("JAVA", "PROGRAMMATION", "ORDINATEUR", "CLAVIER", "SOURIS"));
        }
    }

    public void ajouterMot(String mot) {
        mots.add(mot.toUpperCase());
        sauvegarderMots();
    }

    public void supprimerMot(String mot) {
        mots.remove(mot.toUpperCase());
        sauvegarderMots();
    }

    private void sauvegarderMots() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (String mot : mots) {
                bw.write(mot);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMotAleatoire() {
        return mots.get(new Random().nextInt(mots.size()));
    }
}
