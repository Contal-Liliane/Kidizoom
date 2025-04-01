package com.isis.contal.kidizoom;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dictionnaire {
    private final List<String> mots;
    private static final String FILENAME = "dictionnaire.txt";
    
    public Dictionnaire() {
        mots = new ArrayList<>();
        chargerMots();
    }
    
    private void chargerMots() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                mots.add(ligne.trim().toUpperCase());
            }
        } catch (IOException e) {
            mots.add("JAVA");
            mots.add("PROGRAMMATION");
            mots.add("ORDINATEUR");
            mots.add("CLAVIER");
            mots.add("SOURIS");
        }
    }

    public void ajouterMot(String mot) {
        if (!mots.contains(mot.toUpperCase())) {
            mots.add(mot.toUpperCase());
            sauvegarderMots();
        }
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
        if (mots.isEmpty()) return "JAVA";
        return mots.get(new Random().nextInt(mots.size()));
    }
}
