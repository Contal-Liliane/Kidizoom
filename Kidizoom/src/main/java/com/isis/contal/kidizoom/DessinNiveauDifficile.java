package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class DessinNiveauDifficile extends JPanel {
    private DrawPanel drawPanel;
    private JSlider eraserSlider, brushSizeSlider, eraserSizeSlider;
    private Color currentColor = Color.BLACK;
    private boolean erasing = false;
    private int brushSize = 10;
    private int eraserSize = 20;
    private String selectedShape = "Libre";

    public DessinNiveauDifficile() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500));
        
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.GRAY);

        JButton colorChooserButton = new JButton("Choisir La Couleur");
        colorChooserButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choisissez une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                erasing = false;
            }
        });
        colorPanel.add(colorChooserButton);
        
        JButton eraseButton = new JButton("Gomme");
        eraseButton.addActionListener(e -> {
            erasing = !erasing;
            eraseButton.setText(erasing ? "Dessiner" : "Gomme");
        });
        colorPanel.add(eraseButton);

        mainPanel.add(colorPanel, BorderLayout.NORTH);

        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
            }
        });

        brushSizeSlider = new JSlider(1, 50, 10);
        brushSizeSlider.addChangeListener(e -> brushSize = brushSizeSlider.getValue());

        eraserSizeSlider = new JSlider(5, 50, 20);
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
        shapePanel.setLayout(new GridLayout(4, 1, 5, 5));
        shapePanel.setBackground(Color.LIGHT_GRAY);

        ShapeButton freeDrawButton = new ShapeButton("✏", "Libre");
        ShapeButton circleButton = new ShapeButton("O", "Cercle");
        ShapeButton squareButton = new ShapeButton("■", "Carré");
        ShapeButton triangleButton = new ShapeButton("▲", "Triangle");

        shapePanel.add(freeDrawButton);
        shapePanel.add(circleButton);
        shapePanel.add(squareButton);
        shapePanel.add(triangleButton);

        mainPanel.add(shapePanel, BorderLayout.WEST);

        add(mainPanel);
    }

    private class DrawPanel extends JPanel {
        private Image image;
        private Graphics2D g2;
        private int startX, startY, endX, endY;
        private List<Shape> shapes = new ArrayList<>(); // Liste des formes dessinées

        public DrawPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startX = e.getX();
                    startY = e.getY();
                }

                public void mouseReleased(MouseEvent e) {
                    endX = e.getX();
                    endY = e.getY();

                    if (!erasing) {
                        drawShape();
                    }
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (erasing) {
                        erase(e.getX(), e.getY());
                    } else if (selectedShape.equals("Libre")) {
                        draw(e.getX(), e.getY());
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
                g2.setColor(currentColor);
                g2.fillOval(x, y, brushSize, brushSize);
                repaint();
            }
        }

        private void drawShape() {
            if (g2 == null) return;

            int width = Math.abs(endX - startX);
            int height = Math.abs(endY - startY);
            int x = Math.min(startX, endX);
            int y = Math.min(startY, endY);

            g2.setColor(currentColor);
            g2.setStroke(new BasicStroke(brushSize));

            Shape shape = null;

            switch (selectedShape) {
                case "Cercle":
                    shape = new Ellipse2D.Float(x, y, width, height);
                    break;
                case "Carré":
                    shape = new Rectangle2D.Float(x, y, width, height);
                    break;
                case "Triangle":
                    Path2D triangle = new Path2D.Double();
                    triangle.moveTo(startX, startY);
                    triangle.lineTo(startX + width, startY + height);
                    triangle.lineTo(startX - width, startY + height);
                    triangle.closePath();
                    shape = triangle;
                    break;
                default:
                    return;
            }

            shapes.add(shape);
            g2.draw(shape);
            repaint();
        }

        private void erase(int x, int y) {
            if (g2 != null) {
                g2.setColor(Color.WHITE);
                g2.fillRect(x - eraserSize / 2, y - eraserSize / 2, eraserSize, eraserSize);

                // Effacer les formes sous la gomme
                shapes.removeIf(shape -> shape.getBounds2D().intersects(x - eraserSize / 2, y - eraserSize / 2, eraserSize, eraserSize));

                repaint();
            }
        }

        public void clear() {
            if (g2 != null) {
                g2.setPaint(Color.WHITE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                shapes.clear(); // Efface aussi les formes
                repaint();
            }
        }
    }

    private class ShapeButton extends JButton {
        public ShapeButton(String text, String shapeType) {
            super(text);
            setPreferredSize(new Dimension(45, 30));
            setFocusPainted(false);
            addActionListener(e -> selectedShape = shapeType);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Dessin Niveau Difficile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new DessinNiveauDifficile());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
