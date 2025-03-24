package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class DessinNiveauFacile extends JPanel {
    private DrawPanel drawPanel;
    private JSlider eraserSlider, brushSizeSlider, eraserSizeSlider;
    private Color currentColor = Color.BLACK;
    private boolean erasing = false;
    private int brushSize = 10;
    private int eraserSize = 20;
    private String selectedShape = "Libre";

    public DessinNiveauFacile() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500));

        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.GRAY);

        JButton blackButton = createColorButton("Noir", Color.BLACK);
        JButton redButton = createColorButton("Rouge", Color.RED);
        JButton greenButton = createColorButton("Vert", Color.GREEN);
        JButton blueButton = createColorButton("Bleu", Color.BLUE);

        colorPanel.add(blackButton);
        colorPanel.add(redButton);
        colorPanel.add(greenButton);
        colorPanel.add(blueButton);

        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing;
            eraseButton.setText(erasing ? "Dessiner" : "Gomme");
        });
        colorPanel.add(eraseButton);

        mainPanel.add(colorPanel, BorderLayout.NORTH);

        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
            }
        });

        brushSizeSlider = new JSlider(1, 50, 10);
        brushSizeSlider.setOrientation(JSlider.HORIZONTAL);
        brushSizeSlider.addChangeListener(e -> brushSize = brushSizeSlider.getValue());

        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSizeSlider.setOrientation(JSlider.HORIZONTAL);
        eraserSizeSlider.addChangeListener(e -> eraserSize = eraserSizeSlider.getValue());

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

        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new GridLayout(3, 1, 5, 5));
        shapePanel.setBackground(Color.LIGHT_GRAY);

        ShapeButton circleButton = new ShapeButton("O", "Cercle");
        ShapeButton squareButton = new ShapeButton("■", "Carré");
        ShapeButton triangleButton = new ShapeButton("▲", "Triangle");

        shapePanel.add(circleButton);
        shapePanel.add(squareButton);
        shapePanel.add(triangleButton);

        mainPanel.add(shapePanel, BorderLayout.WEST);
        add(mainPanel);
    }

    private JButton createColorButton(String name, Color color) {
        JButton button = new JButton(name);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> {
            currentColor = color;
            erasing = false;
        });
        return button;
    }

    private class DrawPanel extends JPanel {
        private Image image;
        private Graphics2D g2;
        private int prevX, prevY;

        public DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                }
            });
            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (g2 == null) {
                        image = createImage(getWidth(), getHeight());
                        g2 = (Graphics2D) image.getGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        clear();
                    }
                    if (erasing) {
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(eraserSize));
                    } else {
                        g2.setColor(currentColor);
                        g2.setStroke(new BasicStroke(brushSize));
                    }
                    g2.draw(new Line2D.Float(prevX, prevY, e.getX(), e.getY()));
                    prevX = e.getX();
                    prevY = e.getY();
                    repaint();
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
    }

    private class ShapeButton extends JButton {
        public ShapeButton(String text, String shapeType) {
            super(text);
            this.setPreferredSize(new Dimension(45, 30)); // Taille plus petite
            setFocusPainted(false);
            addActionListener(e -> selectedShape = shapeType);
        }
    }
}
