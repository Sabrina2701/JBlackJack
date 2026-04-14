package view;

import javax.swing.*;

import controller.BlackjackController;
import model.LogicGame;
import model.Profile;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * La classe StartMenu è l'interfaccia del menu del gioco visualizzato prima di poter incominciare una partita,
 * creare il proprio profilo o vedere le statistiche di volta in volta aggiornate.
 * 
 * 
 */
public class StartMenu extends JFrame {
    private Profile userProfile;

    /**
     * Costruttore per una nuova istanza di StartMenu, inizializzando la grafica del
     * menu principale e caricando il profilo dell'utente.
     * 
     * 
     * @param userProfile profilo utente associato a questa sessione di gioco
     */
    public StartMenu(Profile userProfile) {
        this.userProfile = userProfile;

        
        this.userProfile.loadProfile();

        setTitle("Menu Principale");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK); //sfondo su nero
        setLayout(new GridBagLayout()); // GridBagLayout per centrare i bottoni

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spaziatura intorno agli elementi
        gbc.anchor = GridBagConstraints.CENTER; // Centra gli elementi

        
        JLabel titleLabel = createTitleLabel("Entra nel mondo del BlackJack!");
        gbc.gridy = 0; // riga
        add(titleLabel, gbc);

        //bottoni
        JButton playButton = createStyledButton("Gioca");
        JButton statsButton = createStyledButton("Statistiche");
        JButton createProfileButton = createStyledButton("Crea Profilo");

        /**
         * bottoni aggiunti al layout
         * 
         */
        gbc.gridy = 1;
        add(playButton, gbc);
        gbc.gridy = 2;
        add(statsButton, gbc);
        gbc.gridy = 3;
        add(createProfileButton, gbc);

        
        playButton.addActionListener(e -> startGame());
        statsButton.addActionListener(e -> showStatistics());
        createProfileButton.addActionListener(e -> createProfile());

        setVisible(true);
    }
    
    /**
     * Crea un pulsante.
     * 
     * @param text testo da visualizzare nel pulsante
     * @return pulsante stilizzato
     */
    private JButton createStyledButton(String text) {
    	
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 150, 136)); // sfondo
        button.setForeground(Color.WHITE); // testo
        button.setFocusPainted(false); // tolto l'effetto di focus
        button.setPreferredSize(new Dimension(150, 40));
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 100), 2, true)); // Bordi arrotondati

        /**
         * 
         * inserito effetto hover
         * 
         */
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 180, 136)); //sfondo più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 150, 136)); //ritorna a colore originale
            }
        });

        return button;
    }

    /**
     * Crea un'etichetta per il titolo.
     * 
     * @param text testo da visualizzare nell'etichetta
     * @return etichetta stilizzata
     */
    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18)); 
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER); 
        return label;
    }

    /**
     * 
     * Metodo che avvia una nuova partita; ogni volta creiamo un nuovo model, view e controller.
     * 
     */
    
    private void startGame() {
        LogicGame model = new LogicGame(userProfile);
        GameView view = new GameView(model);
        BlackjackController controller = new BlackjackController(model, view, this);
        view.setVisible(true);
        this.dispose();
    }

    /**
     * Metodo che crea una nuova istanza di InfoStatistics
     */
    private void showStatistics() {
        InfoStatistics statsView = new InfoStatistics(userProfile);
        statsView.setVisible(true);
    }

    /**
     * Metodo che crea una nuova istanza di NewProfile, con un nuovo account da poter creare.
     * Chiude la finestra del menu principale.
     */
    private void createProfile() {
        NewProfile profileCreationView = new NewProfile(userProfile);
        profileCreationView.setVisible(true);
        this.dispose();
    }

    
}
