/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author louis
 */
// Classe qui gère l'interface graphique
class FenetreCalcul extends JFrame {
    // JLabel pour afficher le calcul généré
    private JLabel labelCalcul;
    // Champ de saisie pour permettre à l'utilisateur d'entrer sa réponse
    private JTextField champReponse;
    // Boutons pour interagir avec l'application
    private JButton boutonVerifier, boutonSolution, boutonNouveau, boutonSupprimer;
    // Boutons radio pour choisir le niveau de difficulté
    private JRadioButton niveauFacile, niveauDifficile;
    // Objet responsable de la génération et de la vérification des calculs
    private Calculateur calculateur;

    public FenetreCalcul() {
        // Configuration de la fenêtre principale
        setTitle("Activité de Calcul");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        calculateur = new Calculateur();

        // Création des composants de l'interface
        labelCalcul = new JLabel("Calcul :");
        champReponse = new JTextField(5);
        boutonVerifier = new JButton("Vérifier");
        boutonSolution = new JButton("Solution");
        boutonNouveau = new JButton("Nouveau");
        boutonSupprimer = new JButton("Supprimer");

        // Création des options de niveau
        niveauFacile = new JRadioButton("Facile", true);
        niveauDifficile = new JRadioButton("Difficile");
        ButtonGroup groupNiveau = new ButtonGroup();
        groupNiveau.add(niveauFacile);
        groupNiveau.add(niveauDifficile);

        // Ajout des composants à la fenêtre
        add(labelCalcul);
        add(champReponse);
        add(niveauFacile);
        add(niveauDifficile);
        add(boutonVerifier);
        add(boutonSolution);
        add(boutonNouveau);
        add(boutonSupprimer);

        // Génération du premier calcul
        genererCalcul();

        // Ajout des actions des boutons
        boutonVerifier.addActionListener(e -> verifierReponse());
        boutonSolution.addActionListener(e -> montrerSolution());
        boutonNouveau.addActionListener(e -> genererCalcul());
        boutonSupprimer.addActionListener(e -> champReponse.setText(""));

        setVisible(true);
    }

    // Génère un calcul en fonction du niveau sélectionné.
    private void genererCalcul() {
        calculateur.genererCalcul(niveauFacile.isSelected());
        labelCalcul.setText(calculateur.getCalculAffiche());
        champReponse.setText("");
        champReponse.requestFocus(); // Donne automatiquement le focus au champ de saisie
    }

    // Compare la réponse entrée par l'utilisateur avec la solution correcte.
    private void verifierReponse() {
        try {
            int reponse = Integer.parseInt(champReponse.getText());
            if (calculateur.verifierReponse(reponse)) {
                JOptionPane.showMessageDialog(this, "Bravo ! Bonne réponse.");
            } else {
                JOptionPane.showMessageDialog(this, "Faux. Essayez encore !");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
        }
    }

    // Affiche la solution correcte du calcul.
    private void montrerSolution() {
        JOptionPane.showMessageDialog(this, "La solution est : " + calculateur.getResultat());
    }
}
