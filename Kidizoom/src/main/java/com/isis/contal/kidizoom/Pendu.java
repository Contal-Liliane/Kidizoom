/*
Baptiste
*/
package com.isis.contal.kidizoom;
//Baptiste
import javax.swing.*;
import java.awt.*;

public class Pendu extends JPanel {
    private final PenduAffichage penduAffichage;

    public Pendu() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(780, 500)); // Définir la taille du JPanel
        
        // Instancier le panneau du jeu du pendu
        penduAffichage = new PenduAffichage();
        penduAffichage.setPreferredSize(new Dimension(780, 500)); // Assurer la bonne dimension

        // Ajouter le panneau du jeu au JPanel principal
        add(penduAffichage, BorderLayout.CENTER);
    }
}
