/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isis.contal.kidizoom;

import javax.swing.SwingUtilities;

/**
 *
 * @author bapti
 */
public class Pendu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PenduAffichage :: new);
        SwingUtilities.invokeLater(Administration::new);
    }
    
}
