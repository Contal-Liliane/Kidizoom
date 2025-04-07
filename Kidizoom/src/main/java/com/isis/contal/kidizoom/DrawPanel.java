package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage image;

    public DrawPanel() {
        // Initialisation du DrawPanel sans image au départ
        this.image = new BufferedImage(780, 500, BufferedImage.TYPE_INT_ARGB);
    }

    // Méthode pour mettre à jour l'image
    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();  // Redessiner le panel avec la nouvelle image
    }

    // Méthode de rendu (peinture de l'image sur le panel)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);  // Afficher l'image dans le panel
        }
    }
}
