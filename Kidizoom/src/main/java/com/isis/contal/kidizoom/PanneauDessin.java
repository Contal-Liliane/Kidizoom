
/*
Liliane
*/

package com.isis.contal.kidizoom;
//Liliane
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class PanneauDessin extends JPanel {

    private DessinNiveauFacile dessinNiveauFacile;  // Instance de DessinNiveauFacile
    private Image image;
    private Graphics2D g2;
    private int startX, startY, endX, endY;

    public PanneauDessin (DessinNiveauFacile dessinNiveauFacile) {
        this.dessinNiveauFacile = dessinNiveauFacile;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();

                if (g2 == null) {
                    image = createImage(getWidth(), getHeight());
                    g2 = (Graphics2D) image.getGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    clear();
                }

                g2.setColor(dessinNiveauFacile.getCurrentColor());
                g2.setStroke(new BasicStroke(dessinNiveauFacile.getBrushSize()));

                switch (dessinNiveauFacile.getSelectedShape()) {
                    case "Cercle":
                        int radius = Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
                        g2.draw(new Ellipse2D.Float(startX - radius / 2, startY - radius / 2, radius, radius));
                        break;
                    case "Carré":
                        int size = Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
                        g2.draw(new Rectangle2D.Float(startX, startY, size, size));
                        break;
                    case "Triangle":
                        int[] xPoints = {startX, endX, (startX + endX) / 2};
                        int[] yPoints = {endY, endY, startY};
                        g2.drawPolygon(xPoints, yPoints, 3);
                        break;
                    default:
                        // Ne rien faire si c'est du dessin libre
                        break;
                }

                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dessinNiveauFacile.getSelectedShape().equals("Libre")) {
                    if (g2 == null) {
                        image = createImage(getWidth(), getHeight());
                        g2 = (Graphics2D) image.getGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        clear();
                    }

                    if (dessinNiveauFacile.isErasing()) {
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(dessinNiveauFacile.getEraserSize()));
                    } else {
                        g2.setColor(dessinNiveauFacile.getCurrentColor());
                        g2.setStroke(new BasicStroke(dessinNiveauFacile.getBrushSize()));
                    }
                    g2.draw(new Line2D.Float(startX, startY, e.getX(), e.getY()));
                    startX = e.getX();
                    startY = e.getY();
                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    public void clear() {
        if (g2 != null) {
            g2.setPaint(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
            repaint();
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        if (img != null) {
            image = img;
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            repaint();
        }
    }
}
