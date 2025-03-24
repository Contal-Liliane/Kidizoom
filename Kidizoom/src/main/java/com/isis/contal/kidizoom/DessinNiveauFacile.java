package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DessinNiveauFacile extends JPanel {
    private DrawPanel drawPanel;
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

        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new GridLayout(3, 1, 5, 5));
        shapePanel.setBackground(Color.LIGHT_GRAY);

        ShapeButton circleButton = new ShapeButton("Cercle", "Cercle");
        ShapeButton squareButton = new ShapeButton("Carré", "Carré");
        ShapeButton triangleButton = new ShapeButton("Triangle", "Triangle");

        shapePanel.add(circleButton);
        shapePanel.add(squareButton);
        shapePanel.add(triangleButton);

        mainPanel.add(shapePanel, BorderLayout.WEST);
        add(mainPanel);
    }

    private class ShapeButton extends JButton {
        private final String shapeType;

        public ShapeButton(String text, String shapeType) {
            super(text);
            this.shapeType = shapeType;
            setPreferredSize(new Dimension(60, 60));
            setContentAreaFilled(false);
            setFocusPainted(false);
            addActionListener(e -> selectedShape = shapeType);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);

            int w = getWidth(), h = getHeight();

            switch (shapeType) {
                case "Cercle" -> g2.fillOval(10, 10, w - 20, h - 20);
                case "Carré" -> g2.fillRect(10, 10, w - 20, h - 20);
                case "Triangle" -> {
                    int[] xPoints = {w / 2, 10, w - 10};
                    int[] yPoints = {10, h - 10, h - 10};
                    g2.fillPolygon(xPoints, yPoints, 3);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dessin Niveau Facile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new DessinNiveauFacile());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
