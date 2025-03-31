/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.awt.GridLayout;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author bapti
 */
public class Administration extends JFrame{
    private static final String motDePasse_HACHE = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
    private final Dictionnaire dictionnaire = new Dictionnaire();
    
    public Administration(){
        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(this, passwordField, "Entrez le mot de passe:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION || !checkMotDePasse(new String(passwordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Accès refusé !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setTitle("Administration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));
        
        JButton ajouterBouton = new JButton("Ajouter un mot");
        ajouterBouton.addActionListener(e -> ajouterMot());
        add(ajouterBouton);
        
        JButton supprimerBouton = new JButton("Supprimer un mot");
        supprimerBouton.addActionListener(e -> supprimerMot());
        add(supprimerBouton);

        setVisible(true);
    }
    
    private boolean checkMotDePasse(String mdp){ 
        System.out.println(motDePasseHache(mdp));
        return motDePasseHache(mdp).equals(motDePasse_HACHE);  
       
    }
    
    private String motDePasseHache(String mdp) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(mdp.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
     private void ajouterMot() {
        String nouveauMot = JOptionPane.showInputDialog(this, "Entrez un nouveau mot :");
        if (nouveauMot != null && !nouveauMot.trim().isEmpty()) {
            dictionnaire.ajouterMot(nouveauMot.trim().toUpperCase());
            JOptionPane.showMessageDialog(this, "Mot ajouté avec succès !");
        }
    }
    private void supprimerMot() {
        String motSupprimer = JOptionPane.showInputDialog(this, "Entrez le mot à supprimer :");
        if (motSupprimer != null && !motSupprimer.trim().isEmpty()) {
            dictionnaire.supprimerMot(motSupprimer.trim().toUpperCase());
            JOptionPane.showMessageDialog(this, "Mot supprimé avec succès !");
        }
    }
}
