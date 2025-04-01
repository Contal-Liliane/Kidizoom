package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage image;

    public DrawPanel(BufferedImage image) {
        this.image = image;
    }

    DrawPanel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Redessiner l'image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);  // Afficher l'image dans le panel
        }
    }

    // Mettre à jour l'image dans le DrawPanel
    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();  // Redessiner le panel avec la nouvelle image
    }
}
