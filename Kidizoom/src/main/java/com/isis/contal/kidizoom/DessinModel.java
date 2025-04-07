package com.isis.contal.kidizoom;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DessinModel {

    private BufferedImage image;
    private Graphics2D g2d;
    Color currentColor = Color.BLACK;
    private int brushSize = 10;

    public DessinModel(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clear(); // Initialise avec un fond blanc
    }

    // 🎨 Dessiner un point
    public void draw(int x, int y) {
        if (g2d != null) {
            g2d.setColor(currentColor);
            g2d.fillOval(x - brushSize / 2, y - brushSize / 2, brushSize, brushSize);
        }
    }

    // 🧼 Effacer tout
    public void clear() {
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    // 🖌️ Modifier la couleur du pinceau
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    // ✏️ Modifier la taille du pinceau
    public void setBrushSize(int size) {
        this.brushSize = size;
    }

    // 📥 Définir une nouvelle image
    public void setImage(BufferedImage newImage) {
        this.image = newImage;
        this.g2d = newImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    // 📤 Obtenir l'image actuelle
    public BufferedImage getImage() {
        return image;
    }
}
