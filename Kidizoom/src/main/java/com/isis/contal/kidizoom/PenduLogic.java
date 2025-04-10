/*
Baptiste
 */
package com.isis.contal.kidizoom;
//Baptiste
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author bapti
 */
public class PenduLogic {

    private final Dictionnaire dictionnaire;
    private String motSecret;
    private final Set<Character> lettresDevinees = new HashSet<>();
    private int erreur = 0;

    public PenduLogic() {
        dictionnaire = new Dictionnaire();
        choisirMotRandom();
    }

    public void choisirMotRandom() {
        motSecret = dictionnaire.getMotAleatoire();
        lettresDevinees.clear();
        erreur = 0;
    }

    public String getMotSecret() {
        String motMasque = "";
        for (char c : motSecret.toCharArray()) {
            motMasque += (lettresDevinees.contains(c) ? c + " " : "_ ");
        }
        return motMasque.trim();
    }

    public String getMotOriginal() {
        return motSecret;  // Retourne le mot sans les tirets
    }

    public boolean TrouveLettre(char l) {
        if (lettresDevinees.contains(l)) {
            return false;
        }
        lettresDevinees.add(l);
        if (!motSecret.contains(String.valueOf(l))) {
            erreur++;
        }
        return motSecret.contains(String.valueOf(l));
    }

    public boolean siJeuFini() {

        return erreur >= 10 || getMotSecret().replace(" ", "").equals(motSecret);
    }

    public boolean siJeuGagne() {
        return getMotSecret().replace(" ", "").equals(motSecret);
    }

    public int getErreur() {
        return erreur;
    }

    public void rejouerJeu() {
        lettresDevinees.clear();
        erreur = 0;
        choisirMotRandom();
    }
}
