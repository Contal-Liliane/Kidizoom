/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.*;   
import javax.swing.JOptionPane;;
/**
/**
 *
 * @author bapti
 */
public class PenduAffichage {
    private final PenduLogic penduLogic;
    private final JLabel motLabel;
    private final JLabel statusLabel;
    private final DessinPanel dessinPanel;
    private final JPanel clavierPanel;
    private final JButton rejouerBouton;
    
    public PenduAffichage() {
        penduLogic = new PenduLogic();
        JFrame frame = new JFrame("Jeu du Pendu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());
        
        motLabel = new JLabel(penduLogic.getMotSecret(), SwingConstants.CENTER);
        motLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        frame.add(motLabel, BorderLayout.NORTH);
        
        dessinPanel = new DessinPanel(penduLogic);
        frame.add(dessinPanel, BorderLayout.CENTER);
        
        clavierPanel = new JPanel(new GridLayout(3, 9));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton lettreBouton = new JButton(String.valueOf(c));
            lettreBouton.setFont(new Font("Arial", Font.BOLD, 16));
            lettreBouton.setBackground(Color.CYAN);
            lettreBouton.addActionListener(new LettreClickListener(c, lettreBouton));
            clavierPanel.add(lettreBouton);
        }
        frame.add(clavierPanel, BorderLayout.SOUTH);
        
        statusLabel = new JLabel("Erreurs : 0", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(statusLabel, BorderLayout.WEST);
        
        rejouerBouton = new JButton("Rejouer");
        rejouerBouton.setFont(new Font("Arial", Font.BOLD, 16));
        rejouerBouton.addActionListener(e -> penduLogic.rejouerJeu());
        rejouerBouton.setVisible(false);
        frame.add(rejouerBouton, BorderLayout.EAST);
        
        frame.setVisible(true);
    }
    

    private class LettreClickListener implements ActionListener{
        private final char lettre;
        private final JButton bouton;
        
        public LettreClickListener(char l, JButton b) {
            this.lettre = l;
            this.bouton=b;
        }
        
        public void actionPerformed(ActionEvent e) {
            if (!penduLogic.siJeuFini()) {
                penduLogic.TrouveLettre(lettre);
                motLabel.setText(penduLogic.getMotSecret());
                statusLabel.setText("Erreurs : " + penduLogic.getErreur());
                dessinPanel.repaint();
                bouton.setEnabled(false);
                
                if (penduLogic.siJeuFini()) {
                    rejouerBouton.setVisible(true);
                    String message = penduLogic.siJeuGagne() ? "Bravo, tu as gagné !" : "Perdu ! Le mot était " + penduLogic.getMotSecret();
                    JOptionPane.showMessageDialog(null, message);
                }
            }
        }
    }
}
