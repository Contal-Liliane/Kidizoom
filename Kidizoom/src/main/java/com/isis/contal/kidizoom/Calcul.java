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
    private JButton boutonVerifier, boutonSolution, boutonNouveau, boutonSupprimer;
    private int nombre1, nombre2, resultat;
    private char operateur;
    
    public Calcul() {
        setTitle("Activité de Calcul");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        labelCalcul = new JLabel("Calcul :");
        champReponse = new JTextField(5);
        boutonVerifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        boutonSupprimer = new JButton("Supprimer");
        
        add(labelCalcul);
        add(champReponse);
        add(boutonVerifier);
        add(boutonSolution);
        add(boutonNouveau);
        add(boutonSupprimer);
        
        genererCalcul();

        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> montrerSolution());
        boutonNouveau.addActionListener(e -> genererCalcul());
        boutonSupprimer.addActionListener(e -> champReponse.setText(""));
        
        setVisible(true);
    }

    // Cette méthode génère un calcul aléatoire en fonction de l'opération choisie (+ ou -).
    private void genererCalcul() {
        Random random = new Random();
        nombre1 = random.nextInt(10) + 1;
        nombre2 = random.nextInt(10) + 1;
        operateur = random.nextBoolean() ? '+' : '-';
        
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
        champReponse.setText("");
        champReponse.requestFocus();
    }

    private void verifierReponse() {
        try {
            int reponse = Integer.parseInt(champReponse.getText());
            if (reponse == resultat) {
                JOptionPane.showMessageDialog(this, "Bravo ! Bonne réponse.");
            } else {
                JOptionPane.showMessageDialog(this, "Faux. Essayez encore !");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
        }
    }

    private void montrerSolution() {
        JOptionPane.showMessageDialog(this, "La solution est : " + resultat);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calcul::new);
    }
}

