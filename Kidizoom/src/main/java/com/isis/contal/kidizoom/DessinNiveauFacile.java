package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DessinNiveauFacile extends JPanel {
    private DrawPanel drawPanel;
    private JSlider eraserSlider, brushSizeSlider, eraserSizeSlider;
    private Color currentColor = Color.BLACK; // Couleur du pinceau
    private boolean erasing = false; // Indicateur pour savoir si on utilise la gomme
    private int brushSize = 10; // Taille du pinceau par défaut
    private int eraserSize = 20; // Taille de la gomme par défaut
    

    public DessinNiveauFacile() {
        setLayout(new BorderLayout());
        
        // Conteneur principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // Zone de dessin blanche
        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500)); // Ajuste les dimensions selon tes besoins
        
        // Palette de couleurs
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.GRAY);

        JButton blackButton = new JButton("Noir");
        blackButton.setBackground(Color.BLACK);
        blackButton.setForeground(Color.WHITE);
        blackButton.addActionListener(e -> {
            currentColor = Color.BLACK;
            erasing = false;
        });
        colorPanel.add(blackButton);
        
        JButton redButton = new JButton("Rouge");
        redButton.setBackground(Color.RED);
        redButton.addActionListener(e -> {
            currentColor = Color.RED;
            erasing = false;
        });
        colorPanel.add(redButton);

        JButton greenButton = new JButton("Vert");
        greenButton.setBackground(Color.GREEN);
        greenButton.addActionListener(e -> {
            currentColor = Color.GREEN;
            erasing = false;
        });
        colorPanel.add(greenButton);

        JButton blueButton = new JButton("Bleu");
        blueButton.setBackground(Color.BLUE);
        blueButton.addActionListener(e -> {
            currentColor = Color.BLUE;
            erasing = false;
        });
        colorPanel.add(blueButton);

        // Bouton gomme
        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing; // Alterner entre gomme et dessin
            eraseButton.setText(erasing ? "Dessiner" : "Gomme");
        });
        colorPanel.add(eraseButton);

        mainPanel.add(colorPanel, BorderLayout.NORTH);
        
        // Slider pour effacer complètement
        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
    }
        });

        // Slider pour changer la taille du crayon
        brushSizeSlider = new JSlider(1, 50, 10);
        brushSizeSlider.setOrientation(JSlider.HORIZONTAL);
        brushSizeSlider.addChangeListener(e -> brushSize = brushSizeSlider.getValue());

        // Slider pour changer la taille de la gomme
        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSizeSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSizeSlider.addChangeListener(e -> eraserSize = eraserSizeSlider.getValue());

        // Panneau du bas pour les contrôles
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.YELLOW);
        bottomPanel.setLayout(new GridLayout(3, 2, 10, 5));

        bottomPanel.add(new JLabel("Taille du crayon:"));
        bottomPanel.add(brushSizeSlider);
        
        bottomPanel.add(new JLabel("Taille de la gomme:"));
        bottomPanel.add(eraserSizeSlider);

        bottomPanel.add(new JLabel("Effacer tout:"));
        bottomPanel.add(eraserSlider);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    // Panneau de dessin
    private class DrawPanel extends JPanel {
        private Image image;
        private Graphics2D g2;
        
        public DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (erasing) {
                        erase(e.getX(), e.getY()); // Gomme
                    } else {
                        draw(e.getX(), e.getY()); // Dessiner
                    }
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (erasing) {
                        erase(e.getX(), e.getY()); // Gomme
                    } else {
                        draw(e.getX(), e.getY()); // Dessiner
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image == null) {
                image = createImage(getWidth(), getHeight());
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }
            g.drawImage(image, 0, 0, null);
        }

        private void draw(int x, int y) {
            if (g2 != null) {
                g2.setColor(currentColor); // Utiliser la couleur actuelle
                g2.fillOval(x, y, brushSize, brushSize); // Dessiner avec la taille du pinceau
                repaint();
            }
        }

        private void erase(int x, int y) {
            if (g2 != null) {
                g2.setColor(Color.WHITE); // Utiliser le blanc pour effacer
                g2.fillRect(x - eraserSize / 2, y - eraserSize / 2, eraserSize, eraserSize); // Effacer avec la taille de la gomme
                repaint();
            }
        }
        public void clear() {
            if (g2 != null) {
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                repaint();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DessinNiveauFacile().setVisible(true));
}
}
