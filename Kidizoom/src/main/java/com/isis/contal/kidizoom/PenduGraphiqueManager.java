/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author bapti
 */
public class PenduGraphiqueManager {
    private final PenduLogic penduLogic;
    private final JLabel motLabel;
    private final JLabel statuLabel;
    private final PenduDrawPanel drawPanel;
    
    public PenduGraphiqueManager(){
        penduLogic = new PenduLogic();
        JFrame frame = new JFrame ("Jeu du pendu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());
        
        motLabel = new JLabel(penduLogic.getMotMasque(), SwingConstants.CENTER);
        motLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(motLabel, BorderLayout.NORTH);
        
        JPanel clavierPanel = new JPanel(new GridLayout(3, 9));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton lettreButton = new JButton(String.valueOf(c));
            lettreButton.addActionListener(new LettreClickListener(c));
            clavierPanel.add(lettreButton);
        }
        frame.add(clavierPanel, BorderLayout.SOUTH);
        
        statuLabel = new JLabel("Erreurs : 0", SwingConstants.CENTER);
        frame.add(statuLabel, BorderLayout.WEST);
        
        frame.setVisible(true);
    }
    
    
    
}
