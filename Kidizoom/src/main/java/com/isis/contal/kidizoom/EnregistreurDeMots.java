/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EnregistreurDeMots extends JFrame {
    private JTextField textField;
    private JTextArea textArea;
    private ArrayList<String> wordsList;

    public EnregistreurDeMots() {
        // Configuration de la fenêtre
        setTitle("Enregistreur de Mots");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Initialisation des composants
        textField = new JTextField(20);
        JButton saveButton = new JButton("Enregistrer");
        JButton showButton = new JButton("Afficher");
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        wordsList = new ArrayList<>();

        // Ajout des composants à la fenêtre
        add(textField);
        add(saveButton);
        add(showButton);
        add(new JScrollPane(textArea));

        // Action du bouton Enregistrer
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = textField.getText().trim();
                if (!word.isEmpty()) {
                    wordsList.add(word);
                    textField.setText("");
                    JOptionPane.showMessageDialog(null, "Mot enregistré !");
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un mot.");
                }
            }
        });

        // Action du bouton Afficher
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(""); // Effacer le texte précédent
                for (String word : wordsList) {
                    textArea.append(word + "\n");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new EnregistreurDeMots();
    }
}
