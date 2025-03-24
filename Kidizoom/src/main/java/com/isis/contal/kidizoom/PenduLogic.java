package com.isis.contal.kidizoom;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author bapti
 */
public class PenduLogic {
    private String[] mots;
    private final String motDeviner;
    private final Set<Character> lettreTrouvee = new HashSet<>();
    private int erreur;

    public PenduLogic(){
        this.motDeviner = mots[new Random().nextInt(mots.length)];
        this.erreur=0;
    }

    public String getMotMasque(){
        String motMasque = "";
        for (char c : motDeviner.toCharArray()) {
            motMasque += (lettreTrouvee.contains(c) ? c + " " : "_ ");
        }
        return motMasque.trim();
    }

    public boolean devineLettre(char lettre) {
        lettreTrouvee.add(lettre);
        if (!motDeviner.contains(String.valueOf(lettre))) {
            erreur++;
            return false;
        }
        return true;
    }

     public boolean jeuFini() {
        return erreur >= 6 || getMotMasque().replace(" ", "").equals(motDeviner);
    }

    public boolean jeuGagne() {
        return getMotMasque().replace(" ", "").equals(motDeviner);
    }

     public int getErreur() {
        return erreur;
    }
}