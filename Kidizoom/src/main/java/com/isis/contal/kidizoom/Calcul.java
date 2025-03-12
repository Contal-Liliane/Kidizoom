/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

/**
 *
 * @author lilia
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// Cette classe hérite de JFrame pour créer une interface graphique permettant aux enfants de s'entraîner aux calculs.
public class Calcul extends JFrame {
    // JLabel utilisé pour afficher le calcul mathématique généré dynamiquement.
    private JLabel labelCalcul;
    // JTextField utilisé pour permettre à l'utilisateur d'entrer sa réponse.
    private JTextField champReponse;
    // Boutons permettant de vérifier la réponse, afficher la solution, générer un nouveau calcul et supprimer l'entrée utilisateur.
    private JButton boutonVerifier, boutonSolution, boutonNouveau, boutonSupprimer;
    private int nombre1, nombre2, resultat; // Variables stockant les nombres du calcul et le résultat.
    private char operateur; // Opérateur utilisé pour le calcul (+ ou -).
    
    public Calcul() {
        // Configuration de la fenêtre principale.
        setTitle("Activité de Calcul");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialisation des composants graphiques.
        labelCalcul = new JLabel("Calcul :");
        champReponse = new JTextField(5); // Champ de texte pour la saisie de la réponse.
        boutonVerifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        boutonSupprimer = new JButton("Supprimer");
        
        // Ajout des composants à la fenêtre.
        add(labelCalcul);
        add(champReponse);
        add(boutonVerifier);
        add(boutonSolution);
        add(boutonNouveau);
        add(boutonSupprimer);
        
        // Génération du premier calcul au lancement.
        genererCalcul();

        // Ajout des actions aux boutons.
        boutonVerifier.addActionListener(e -> verifierReponse()); // Vérifie la réponse de l'utilisateur.
        boutonSolution.addActionListener(e -> montrerSolution()); // Affiche la solution.
        boutonNouveau.addActionListener(e -> genererCalcul()); // Génère un nouveau calcul.
        boutonSupprimer.addActionListener(e -> champReponse.setText("")); // Efface la réponse saisie.
        
        // Rendre la fenêtre visible.
        setVisible(true);
    }

    // Cette méthode génère un calcul aléatoire en fonction de l'opération choisie (+ ou -).
    private void genererCalcul() {
        Random random = new Random();
        nombre1 = random.nextInt(10) + 1; // Génère un nombre entre 1 et 10.
        nombre2 = random.nextInt(10) + 1; // Génère un second nombre entre 1 et 10.
        operateur = random.nextBoolean() ? '+' : '-'; // Choisit aléatoirement entre addition et soustraction.
        
        // Si l'opération est une soustraction, s'assurer que le résultat est positif.
        if (operateur == '-' && nombre1 < nombre2) {
            int temp = nombre1;
            nombre1 = nombre2;
            nombre2 = temp;
        }
        
        // Calculer le résultat en fonction de l'opérateur.
        resultat = (operateur == '+') ? nombre1 + nombre2 : nombre1 - nombre2;
        
        // Mettre à jour l'affichage du calcul.
        labelCalcul.setText(nombre1 + " " + operateur + " " + nombre2 + " = ?");
        champReponse.setText(""); // Réinitialiser la zone de saisie.
        champReponse.requestFocus(); // Donner automatiquement le focus à la zone de saisie.
    }

    // Cette méthode compare la réponse entrée par l'utilisateur avec la solution correcte.
    private void verifierReponse() {
        try {
            int reponse = Integer.parseInt(champReponse.getText()); // Convertit l'entrée utilisateur en entier.
            if (reponse == resultat) {
                JOptionPane.showMessageDialog(this, "Bravo ! Bonne réponse."); // Message en cas de bonne réponse.
            } else {
                JOptionPane.showMessageDialog(this, "Faux. Essayez encore !"); // Message en cas de mauvaise réponse.
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide."); // Message si l'entrée n'est pas un nombre.
        }
    }

    // Cette méthode affiche la solution correcte du calcul.
    private void montrerSolution() {
        JOptionPane.showMessageDialog(this, "La solution est : " + resultat);
    }

    // Cette méthode lance l'interface graphique en utilisant SwingUtilities.invokeLater.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calcul::new);
    }
}

