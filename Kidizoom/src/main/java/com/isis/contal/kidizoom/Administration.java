/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private final Dictionnaire dictionnaire = new Dictionnaire();
    private static final String MOTDEPASSE_FICHIER = "motdepasse.txt";
    private String motDePasseHache;
    
    public Administration(){
        chargerMotDePasse();
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
        setLayout(new GridLayout(4, 1));
        
        JButton ajouterBouton = new JButton("Ajouter un mot");
        ajouterBouton.addActionListener(e -> ajouterMot());
        add(ajouterBouton);
        
        JButton supprimerBouton = new JButton("Supprimer un mot");
        supprimerBouton.addActionListener(e -> supprimerMot());
        add(supprimerBouton);
        
        JButton afficherMotBouton = new  JButton("Afficher les mots");
        afficherMotBouton.addActionListener(e -> afficherMots());
        add(afficherMotBouton);
        
        JButton changeMotDePasseBouton= new  JButton("Modifier le mot de passe");
        changeMotDePasseBouton.addActionListener(e -> changerMotDePasse());
        add(changeMotDePasseBouton);
        
        setVisible(true);
    }
    
    private void chargerMotDePasse(){
        try (BufferedReader br = new BufferedReader(new FileReader(MOTDEPASSE_FICHIER))) {
           motDePasseHache = br.readLine();
        } catch (IOException e) {
           motDePasseHache = motDePasseHache("password");
        }
    }
    
    private void enregistrerMotDePasse(String h){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOTDEPASSE_FICHIER))) {
            bw.write(h);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean checkMotDePasse(String mdp){ 
        return motDePasseHache(mdp).equals(motDePasseHache);  
       
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
     private void afficherMots() {
        JOptionPane.showMessageDialog(this, "Mots du dictionnaire : " + String.join(", ", dictionnaire.getMots()));
    }
     
    private void changerMotDePasse(){
        String nouveauMotDePasse = JOptionPane.showInputDialog(this, "Entrez le nouveau mot de passe :");
        if (nouveauMotDePasse != null && !nouveauMotDePasse.trim().isEmpty()) {
            motDePasseHache = motDePasseHache(nouveauMotDePasse);
            enregistrerMotDePasse(motDePasseHache);
            JOptionPane.showMessageDialog(this, "Mot de passe mis à jour !");
        }
    }
}
