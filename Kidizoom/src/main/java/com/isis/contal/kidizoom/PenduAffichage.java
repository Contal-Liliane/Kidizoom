package com.isis.contal.kidizoom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class PenduAffichage extends JPanel {
    private final PenduLogic penduLogic;
    private final JLabel motLabel;
    private final JLabel statusLabel;
    private final DessinPanel dessinPanel;
    private final JPanel clavierPanel;
    private final JButton rejouerBouton;

    public PenduAffichage() {
        penduLogic = new PenduLogic();
        
        // Mise en place du layout BorderLayout sur ce JPanel
        setLayout(new BorderLayout());

        // Création du label pour afficher le mot à deviner
        motLabel = new JLabel(penduLogic.getMotSecret(), SwingConstants.CENTER);
        motLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        add(motLabel, BorderLayout.NORTH);

        // Création du panneau pour le dessin du pendu
        dessinPanel = new DessinPanel(penduLogic);
        add(dessinPanel, BorderLayout.CENTER);

        // Création du clavier sous forme de grille de boutons
        clavierPanel = new JPanel(new GridLayout(3, 9));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton lettreBouton = new JButton(String.valueOf(c));
            lettreBouton.setFont(new Font("Arial", Font.BOLD, 16));
            lettreBouton.setBackground(Color.CYAN);
            lettreBouton.addActionListener(new LettreClickListener(c, lettreBouton));
            clavierPanel.add(lettreBouton);
        }
        add(clavierPanel, BorderLayout.SOUTH);

        // Création du label de statut pour afficher le nombre d'erreurs
        statusLabel = new JLabel("Erreurs : 0", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(statusLabel, BorderLayout.WEST);

        // Création du bouton "Rejouer"
        rejouerBouton = new JButton("Rejouer");
        rejouerBouton.setFont(new Font("Arial", Font.BOLD, 16));
        rejouerBouton.addActionListener(e -> {
            penduLogic.rejouerJeu();  // Réinitialiser la logique du jeu
            motLabel.setText(penduLogic.getMotSecret());  // Réinitialiser le mot à deviner
            statusLabel.setText("Erreurs : 0");  // Réinitialiser le statut des erreurs
            dessinPanel.repaint();  // Réinitialiser l'affichage du dessin du pendu
            rejouerBouton.setVisible(false);  // Cacher le bouton "Rejouer"
            resetClavier();  // Réactiver les boutons du clavier
        });
        rejouerBouton.setVisible(false);
        add(rejouerBouton, BorderLayout.EAST);
    }

    // Gestion du clic sur les lettres
    private class LettreClickListener implements ActionListener {
        private final char lettre;
        private final JButton bouton;

        public LettreClickListener(char l, JButton b) {
            this.lettre = l;
            this.bouton = b;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!penduLogic.siJeuFini()) {
                penduLogic.TrouveLettre(lettre);
                motLabel.setText(penduLogic.getMotSecret());
                statusLabel.setText("Erreurs : " + penduLogic.getErreur());
                dessinPanel.repaint();
                bouton.setEnabled(false);

                if (penduLogic.siJeuFini()) {
                    rejouerBouton.setVisible(true);
                    String message = penduLogic.siJeuGagne() ? "Bravo, tu as gagné !" : "Perdu ! Le mot était " + penduLogic.getMotOriginal();
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        }
    }

    // Réinitialisation de tous les boutons du clavier
    private void resetClavier() {
        for (Component comp : clavierPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(true);
            }
        }
    }
}
