/*
Liliane
*/
package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    private int xPosition = 0;

    public BackgroundPanel(String imagePath) {
        setBackgroundImage(imagePath);
        
        // Lancer un timer pour déplacer l'arrière-plan
        Timer timer = new Timer(30, e -> moveBackground());
        timer.start();
    }

    public void setBackgroundImage(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        if (backgroundImage == null) {
            System.out.println("Erreur : Image non trouvée !");
        }
        repaint(); // Redessiner avec la nouvelle image
    }

    private void moveBackground() {
        xPosition -= 2;
        if (xPosition <= -getWidth()) {
            xPosition = 0;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, xPosition, 0, getWidth(), getHeight(), this);
            g.drawImage(backgroundImage, xPosition + getWidth(), 0, getWidth(), getHeight(), this);
        }
    }
}