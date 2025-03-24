/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.util.Random;

/**
 *
 * @author louis
 */
// Classe qui génère et vérifie les calculs
class Calculateur {
    private int nombre1, nombre2, resultat;
    private char operateur;
    private Random random;

    public Calculateur() {
        random = new Random();
    }

    // Génère un calcul en fonction du niveau choisi (facile ou difficile)
    public void genererCalcul(boolean facile) {
        if (facile) {
            // Niveau facile : addition et soustraction de nombres à un chiffre
            nombre1 = random.nextInt(9) + 1;
            nombre2 = random.nextInt(9) + 1;
            operateur = random.nextBoolean() ? '+' : '-';
            if (operateur == '-' && nombre1 < nombre2) {
                int temp = nombre1;
                nombre1 = nombre2;
                nombre2 = temp;
            }
        } else {
            // Niveau difficile : addition et soustraction de nombres à trois chiffres et multiplication à un chiffre
            int typeOperation = random.nextInt(3);
            if (typeOperation == 2) {
                nombre1 = random.nextInt(9) + 1;
                nombre2 = random.nextInt(9) + 1;
                operateur = '*';
            } else {
                nombre1 = random.nextInt(999) + 1;
                nombre2 = random.nextInt(999) + 1;
                operateur = random.nextBoolean() ? '+' : '-';
            }
        }
        // Calcul du résultat en fonction de l'opérateur
        resultat = (operateur == '+') ? nombre1 + nombre2 : (operateur == '-') ? nombre1 - nombre2 : nombre1 * nombre2;
    }

    // Retourne l'affichage du calcul sous forme de texte
    public String getCalculAffiche() {
        return nombre1 + " " + operateur + " " + nombre2 + " = ?";
    }

    // Vérifie si la réponse entrée est correcte
    public boolean verifierReponse(int reponse) {
        return reponse == resultat;
    }

    // Retourne la solution du calcul
    public int getResultat() {
        return resultat;
    }
}