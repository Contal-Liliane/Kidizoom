package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CalculNiveauFacile extends JPanel {
    private JLabel labelCalcul;
    private JTextField champReponse;
    private JButton boutonVerifier;
    private int resultatAttendu;

    public CalculNiveauFacile() {
        setLayout(new FlowLayout());
        setBackground(Color.WHITE);

        labelCalcul = new JLabel();
        champReponse = new JTextField(5);
        boutonVerifier = new JButton("Vérifier");

        add(labelCalcul);
        add(champReponse);
        add(boutonVerifier);

        genererCalcul();

        boutonVerifier.addActionListener(e -> verifierReponse());
    }

    private void genererCalcul() {
        Random rand = new Random();
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        resultatAttendu = a + b;
        labelCalcul.setText(a + " + " + b + " = ?");
    }

    private void verifierReponse() {
        try {
            int reponse = Integer.parseInt(champReponse.getText());
            if (reponse == resultatAttendu) {
                champReponse.setBackground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Bravo !");
            } else {
                champReponse.setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, "Incorrect !");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrez un nombre valide.");
        }
    }
}
