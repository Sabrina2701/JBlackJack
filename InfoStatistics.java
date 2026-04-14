package view;

import javax.swing.*;

import model.Profile;

import java.awt.*;

/**
 * La classe InfoStatistics rappresenta la View per visualizzare le statistiche del profilo utente del giocatore.
 * 
 * 
 */

public class InfoStatistics extends JFrame {
    private Profile userProfile;

    /**
     * Costruttore per inizializzare la schermata e visualizzare le statistiche del giocatore.
     *
     * @param userProfile profilo utente contenente le statistiche.
     */
    
    
    public InfoStatistics(Profile userProfile) {
    	
        this.userProfile = userProfile;
        initializeFrame();
        setupComponents();
        setVisible(true);
    }

    
    /**
     * Metodo che inizializza le proprietà base della finestra.
     */
    private void initializeFrame() {
    	
    	
        setTitle("Statistiche Giocatore");
        setSize(450, 500); 
        
        setLocationRelativeTo(null); //per avere la schermata al centro 
        setLayout(new BorderLayout(10, 10)); // Margini tra i componenti
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // chiusura solo della finestra delle statistiche
        getContentPane().setBackground(new Color(24, 24, 24)); //no ridimensionamento della finestra
      
    }

    
    /**
     * Metodo che configura e aggiunge i componenti alla finestra.
     * 
     * 
     */
    private void setupComponents() {
        JPanel headerPanel = createHeaderPanel();
        JPanel statsPanel = createStatisticsPanel(); 
        
        JButton closeButton = createStyledCloseButton(); 

        add(headerPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    /**
     * Metodo per il pannello dell'intestazione.
     *
     * @return JPanel stilizzato per l'intestazione.
     */
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 120, 215)); // blu
        headerPanel.setPreferredSize(new Dimension(450, 80)); 

        JLabel headerLabel = new JLabel("Statistiche del Giocatore");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22)); 
        headerLabel.setForeground(Color.WHITE); // testo bianco

        headerPanel.add(headerLabel);
        return headerPanel;
    }

    /**
     * Metodo per il pannello contenente le statistiche.
     *
     * @return JPanel contenente le etichette con le statistiche.
     */
    private JPanel createStatisticsPanel() {
    	
        JPanel panel = new JPanel(new GridLayout(6, 1, 0, 15)); 
        panel.setBackground(new Color(24, 24, 24)); // nero 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margine interno

        
        JLabel nicknameLabel = createStyledLabel("Nickname: " + userProfile.getNickname());
        JLabel gamesPlayedLabel = createStyledLabel("Partite Giocate: " + userProfile.getGamesPlayed());
        JLabel gamesWonLabel = createStyledLabel("Partite Vinte: " + userProfile.getGamesWon());
        JLabel gamesLostLabel = createStyledLabel("Partite Perse: " + userProfile.getGamesLost());
        JLabel levelLabel = createStyledLabel("Livello: " + userProfile.getLevel());
        JLabel experienceLabel = createStyledLabel("Esperienza: " + userProfile.getExperience());
        

        panel.add(nicknameLabel);
        panel.add(gamesPlayedLabel);
        panel.add(gamesWonLabel);
        panel.add(gamesLostLabel);
        panel.add(levelLabel);
        panel.add(experienceLabel);
        

        return panel;
    }

    
    /**
     * Metodo per etichetta per visualizzare le statistiche.
     *
     * @param text testo da visualizzare nell'etichetta.
     * @return JLabel.
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16)); 
        label.setForeground(Color.WHITE); 
        label.setHorizontalAlignment(SwingConstants.CENTER); 

        
        label.setBorder(BorderFactory.createCompoundBorder(           // bordo con ombra per etichetta più evidente
                BorderFactory.createLineBorder(new Color(0, 150, 136), 2, true), //verde acqua
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // marg. interno per testo
        ));
        return label;
    }

    /**
     * Metodo per pulsante di chiusura della finestra.
     *
     * @return JButton per chiudere la finestra.
     * 
     * 
     */
    private JButton createStyledCloseButton() {
    	
        JButton closeButton = new JButton("Chiudi");
        closeButton.setFocusPainted(false); 
        closeButton.setPreferredSize(new Dimension(150, 40)); // dim pulsante
        closeButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 100), 2, true)); // bordi arrotondati
        closeButton.setFont(new Font("Arial", Font.BOLD, 16)); 
        closeButton.setBackground(new Color(0, 150, 136)); // verde acqua 
        closeButton.setForeground(Color.WHITE); // txt bianco
        

        /**
         * azione per chiudere finestra
         * 
         */
        closeButton.addActionListener(e -> dispose());

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() { //effetto hover
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(0, 180, 136)); // colore più chiaro
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(0, 150, 136)); // torno a colore originale
            }
        });

        return closeButton;
    }
}
