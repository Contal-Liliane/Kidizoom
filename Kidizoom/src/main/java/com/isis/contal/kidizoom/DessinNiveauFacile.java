package com.isis.contal.kidizoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


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
        drawPanel = new DrawPanel();
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        setPreferredSize(new Dimension(780, 500));
        JPanel controlPanel = new JPanel();
        JButton saveButton = new JButton("Sauvegarder");
        JButton loadButton = new JButton("Charger");
        JButton deleteButton = new JButton("Supprimer");

        saveButton.addActionListener(e -> saveDrawing());
        loadButton.addActionListener(e -> loadDrawing());
        deleteButton.addActionListener(e -> deleteDrawing());
        
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(deleteButton);
        
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        // Panel des couleurs
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

        // Panneau des formes
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

        // Panneau des sliders en bas
        JPanel bottomPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setPreferredSize(new Dimension(780, 100));

        brushSizeSlider = new JSlider(1, 50, 10);
        brushSizeSlider.addChangeListener(e -> brushSize = brushSizeSlider.getValue());

        eraserSizeSlider = new JSlider(5, 50, 20);
        eraserSizeSlider.addChangeListener(e -> eraserSize = eraserSizeSlider.getValue());

        eraserSlider = new JSlider(0, 100, 0);
        eraserSlider.addChangeListener(e -> {
            if (eraserSlider.getValue() == 100) {
                drawPanel.clear();
                eraserSlider.setValue(0);
            }
        });

        bottomPanel.add(new JLabel("Taille du crayon:"));
        bottomPanel.add(brushSizeSlider);
        bottomPanel.add(new JLabel("Taille de la gomme:"));
        bottomPanel.add(eraserSizeSlider);
        bottomPanel.add(new JLabel("Effacer tout:"));
        bottomPanel.add(eraserSlider);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
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

    private void saveDrawing() {
        String name = JOptionPane.showInputDialog(this, "Nom du dessin :");
        if (name != null && !name.trim().isEmpty()) {
            try (FileOutputStream fos = new FileOutputStream(name + ".ser");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(drawPanel.getImage());
                JOptionPane.showMessageDialog(this, "Dessin sauvegardé !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void loadDrawing() {
        File dir = new File(".");
        String[] files = dir.list((d, name) -> name.endsWith(".ser"));
        if (files != null && files.length > 0) {
            String selectedFile = (String) JOptionPane.showInputDialog(this, "Choisissez un dessin :",
                    "Charger un dessin", JOptionPane.PLAIN_MESSAGE, null, files, files[0]);
            if (selectedFile != null) {
                try (FileInputStream fis = new FileInputStream(selectedFile);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    drawPanel.setImage((Image) ois.readObject());
                    JOptionPane.showMessageDialog(this, "Dessin chargé !");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun dessin enregistré.");
        }
    }
    
    private void deleteDrawing() {
        File dir = new File(".");
        String[] files = dir.list((d, name) -> name.endsWith(".ser"));
        if (files != null && files.length > 0) {
            String selectedFile = (String) JOptionPane.showInputDialog(this, "Choisissez un dessin à supprimer :",
                    "Supprimer un dessin", JOptionPane.PLAIN_MESSAGE, null, files, files[0]);
            if (selectedFile != null) {
                File file = new File(selectedFile);
                if (file.delete()) {
                    JOptionPane.showMessageDialog(this, "Dessin supprimé !");
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de la suppression.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun dessin enregistré.");
        }
    }

    
    private class DrawPanel extends JPanel {
        private Image image;
        private Graphics2D g2;
        private int startX, startY, endX, endY;

        
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

                    if (g2 == null) {
                        image = createImage(getWidth(), getHeight());
                        g2 = (Graphics2D) image.getGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        clear();
                    }

                    g2.setColor(currentColor);
                    g2.setStroke(new BasicStroke(brushSize));

                    switch (selectedShape) {
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
                    if (selectedShape.equals("Libre")) {
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
    private class ShapeButton extends JButton {
        public ShapeButton(String text, String shapeType) {
            super(text);
            setPreferredSize(new Dimension(45, 30));
            setFocusPainted(false);
            addActionListener(e -> selectedShape = shapeType);
        }
    }
}