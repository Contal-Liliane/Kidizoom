/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import javax.swing.SwingUtilities;

/**
 *
 * @author louis
 */
// Classe principale qui lance l'application
public class ActiviteCalcul {
    public static void main(String[] args) {
        // Lance l'interface graphique en s'assurant qu'elle tourne sur le thread de l'UI
        SwingUtilities.invokeLater(() -> new FenetreCalcul());
    }
}
