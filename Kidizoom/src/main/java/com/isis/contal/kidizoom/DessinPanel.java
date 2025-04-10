/*
Baptiste
 */
package com.isis.contal.kidizoom;
//Baptiste
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author bapti
 */
public class DessinPanel extends JPanel {

    private final PenduLogic penduLogic;

    public DessinPanel(PenduLogic pl) {
        this.penduLogic = pl;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int erreur = penduLogic.getErreur();

        if (erreur > 0) {
            g.drawLine(50, 250, 150, 250);//Barre sol
        }
        if (erreur > 1) {
            g.drawLine(100, 250, 100, 50);//Barre verticale
        }
        if (erreur > 2) {
            g.drawLine(100, 50, 200, 50); //Barre Horizontale
        }
        if (erreur > 3) {
            g.drawLine(200, 50, 200, 80);//Petite Barre
        }
        if (erreur > 4) {
            g.drawOval(185, 80, 30, 30); // Tête
        }
        if (erreur > 5) {
            g.drawLine(200, 110, 200, 170); // Corps
        }
        if (erreur > 6) {
            g.drawLine(200, 120, 180, 150); // Bras gauche
        }
        if (erreur > 7) {
            g.drawLine(200, 120, 220, 150); // Bras droit
        }
        if (erreur > 8) {
            g.drawLine(200, 170, 180, 200); // Jambe gauche
        }
        if (erreur > 9) {
            g.drawLine(200, 170, 220, 200); // Jambe droite
        }
    }
}
