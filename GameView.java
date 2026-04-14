package view;

import javax.swing.*;

import controller.BlackjackController;
import model.*;

import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 * La classe GameView rappresenta la View del gioco, ossio l'interfaccia grafica. 
 * 
 * E' la parte fondamentale della view per l'appunto, proprio perchè stiamo usando l'MVC pattern, 
 * ed è presente anche l'Observer per ricevere aggiornamenti sull'avanzamento
 * del gioco in base alle modifiche nel Model.
 * 
 * 
 */


public class GameView extends JFrame implements Observer {
	
	/**
	 * pannello per la schermata di gioco (quindi il tavolo verde)
	 * 
	 */
    private JPanel gamePanel;
    
    /**
     * pannello per il giocatore 
     * 
     */
    private JPanel playerPanel;
    
    /**
     * 
     * pannello per il banco
     */
    private JPanel dealerPanel;
    
    /**
     * pannello per il profilo
     * 
     */
    
    private JPanel profilePanel;
    
    /**
     * pulsante per pescare la carta 
     * 
     */
    private JButton hitButton;
    
    /**
     * pulsante per stare
     * 
     */
    private JButton standButton;
    
    /**
     * pulsante per ricominciare partita
     * 
     */
    private JButton restartButton;
    
    /**
     * etichetta per profilo
     * 
     */
    private JLabel profileLabel;
    
    /**
     * etichetta per lo stato
     */
    private JLabel statusLabel;
    
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 116;
    private static final int CARD_SPACING = 20;

    private static final int DEALER_PANEL_WIDTH = 400;
    private static final int DEALER_PANEL_HEIGHT = 300;
    
    private static final int PLAYER_PANEL_HEIGHT = 300;
    
    

    /**
     * 
     * Costruttore che inizializza la grafica del gioco, con i pannelli per i giocatori e 
     * i pulsanti, modellando una nuova istanza di GameView.
     * 
     * @param model modello di gioco.
     */
    
    public GameView(LogicGame model) {
    	
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());
        
        
        Color greenColor = new Color(0, 128, 0);  //Sfondo tavolo
        getContentPane().setBackground(Color.BLACK); // Sfondo della finestra
        
        /**
         * Metodo per colorare.
         * 
         */

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {   
                super.paintComponent(g);
                g.setColor(greenColor);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gamePanel.setLayout(null);
        gamePanel.setOpaque(false);
        
        /**
         * 
         * abbiamo settato altezza e larghezza del pannello del banco
         */

        dealerPanel = new JPanel();
        dealerPanel.setOpaque(false);
        dealerPanel.setBounds(50, 50, DEALER_PANEL_WIDTH, DEALER_PANEL_HEIGHT);
        gamePanel.add(dealerPanel);
        
        /**
         * 
         * abbiamo settato altezza del pannello del giocatore
         */

        playerPanel = new JPanel(null);
        playerPanel.setOpaque(false);
        playerPanel.setBounds(50, 400, 1820, PLAYER_PANEL_HEIGHT); 
        gamePanel.add(playerPanel);
        
        /**
         * 
         * abbiamo settato la grafica per il nome dell'account che comparirà scritto
         */

        profilePanel = new JPanel();
        profilePanel.setBackground(greenColor);
        profilePanel.setLayout(new BorderLayout());

        profileLabel = new JLabel();
        profileLabel.setForeground(Color.WHITE);
        profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePanel.add(profileLabel, BorderLayout.CENTER);

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(greenColor);
        statusLabel.setOpaque(true);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER); 

        hitButton = createStyledButton("HIT");
        standButton = createStyledButton("STAND");
        restartButton = createStyledButton("RESTART GAME");
        restartButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK); 
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton);

        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(profilePanel, BorderLayout.EAST);
        add(statusLabel, BorderLayout.NORTH);

        /**
         * etichetta per aggiungere il retro della carta, come se fosse il mazzo, accanto al banco
         * 
         */
        JLabel cardBackLabel = createCardBackLabel();
        dealerPanel.add(cardBackLabel);
        
        restartButton.addActionListener(e -> {
            BlackjackController.getInstance().restartGame();
            restartButton.setEnabled(false);
        });
    }

    /**
     * Metodo che aggiorna la View in base ai cambiamenti nel Model.
     *
     * @param o   Observable che ha segnalato il cambiamento.
     * @param arg argomento da passare eventualmente all'Observable.
     */
    
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LogicGame) {
            LogicGame model = (LogicGame) o;
            updateDealerHand(model.getDealer());
            updatePlayerHands(model.getPlayers(), model.getCurrentPlayerIndex());

            if (arg != null && arg.equals("GAME_OVER")) {
                String winnerMessage = model.getWinnerMessage();
                JOptionPane.showMessageDialog(this, winnerMessage, "Partita Terminata",
                        JOptionPane.INFORMATION_MESSAGE);
                restartButton.setEnabled(true);
            }

            updateButtons(model.getCurrentPlayerIndex() == 0 && !model.isBotOrDealerTurn());
        }
    }

    /**
     * Metodo che aggiorna la visualizzazione della mano del Banco.
     *
     * @param dealer Il giocatore che rappresenta il Banco.
     */
    private void updateDealerHand(BlackJackPlayer dealer) {
        dealerPanel.removeAll();
        dealerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel dealerInfoPanel = createInfo(dealer, true);
        dealerPanel.add(dealerInfoPanel);
        
        
        JLabel cardBackLabel = createCardBackLabel();
        dealerPanel.add(cardBackLabel);
        
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }
    
    /**
     * Metodo che crea un JLabel per rappresentare una carta, a partire da un percorso per le immagini
     * specificato, e che la ridimensiona per poter poi entrare nel pannello.
     * 
     * @param card carta da rappresentare.
     * @return immagine ridimensionata della carta, se non trovata, ritorna frase di errore.
     * 
     */
    private JLabel createCardLabel(StandardCard card) {
        String imagePath = "src/risorse/images/" + card.getImageFileName();
        java.io.File imageFile = new java.io.File(imagePath);
        
        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            return new JLabel(scaledIcon);
            
            
        } else {
            return new JLabel("Immagine non trovata");
        }
    }

    /**
     * Metodo che crea un JLabel per rappresentare il retro della carta.
     * 
     * @return Un JLabel contenente l'immagine del retro della carta.
     */
    private JLabel createCardBackLabel() {
        String imagePath = "src/risorse/images/retro_blu.png"; // Percorso dell'immagine del retro della carta
        java.io.File imageFile = new java.io.File(imagePath);
        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            return new JLabel(scaledIcon);
        } else {
            return new JLabel("Retro non trovato");
        }
    }

    /**
     * Metodo che aggiorna lo stato dei pulsanti di gioco.
     *
     * @param enable true per abilitare i pulsanti, false per disabilitarli.
     */
    private void updateButtons(boolean enable) {
        hitButton.setEnabled(enable);
        standButton.setEnabled(enable);
    }

    /**
     * Crea un pulsante stilizzato.
     * 
     * @param text testo del pulsante.
     * @return pulsante creato.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 128, 0));
        button.setBorder(BorderFactory.createEtchedBorder(Color.WHITE, Color.GRAY));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    /**
     * Metodo che aggiorna la visualizzazione delle mani di tutti i giocatori.
     *
     * @param players            lista dei giocatori.
     * @param currentPlayerIndex indice del giocatore attuale.
     */
    private void updatePlayerHands(List<BlackJackPlayer> players, int currentPlayerIndex) {
        playerPanel.removeAll();

        int totalWidth = players.size() * (CARD_WIDTH * 5 + CARD_SPACING * 4);
        int startX = 0; // Iniziare da 0 per allineare a sinistra

        for (int i = 0; i < players.size(); i++) {
            BlackJackPlayer player = players.get(i);
            JPanel playerInfoPanel = createInfo(player, false);

            int x = startX + i * (CARD_WIDTH * 5 + CARD_SPACING * 4);
            playerInfoPanel.setBounds(x, 0, CARD_WIDTH * 5 + CARD_SPACING * 4, PLAYER_PANEL_HEIGHT);

            playerPanel.add(playerInfoPanel);
        }

        playerPanel.revalidate();
        playerPanel.repaint();
    }
    
    /**
     * Metodo per rendere lo sfondo dell'etichetta del valore e del nome dei giocatori scuro,
     * con testo bianco e allineato al centro.
     * 
     * 
     * @param text
     * @return JLabel
     */
    private JLabel createValueLabel(String text) {
    	
    	
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(new Color(34, 34, 34)); // colore scuro
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 128, 0), 2), 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label.setBackground(new Color(0, 100, 0)); // colore diverso se ci passa sopra mouse
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                label.setBackground(new Color(34, 34, 34)); // colore originale
            }
        });

        return label;
    }
    

    /**
     * Metodo che crea un pannello per i giocatori e per il Banco.
     * 
     * @param player   giocatore o il banco.
     * @param isDealer true se il JPanel è per il banco, false altrimenti.
     * @return pannello con info giocatore.
     */
    
    private JPanel createInfo(BlackJackPlayer player, boolean isDealer) {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel(isDealer ? "Banco" : player.getName());
        
        nameLabel.setBackground(new Color(34, 34, 34));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setOpaque(true);
        
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10));

        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, CARD_SPACING, 0));
        cardsPanel.setOpaque(false);

        for (StandardCard card : player.getHand()) {
            JLabel cardLabel = createCardLabel(card);
            cardsPanel.add(cardLabel);
        }

        panel.add(cardsPanel);
        panel.add(Box.createVerticalStrut(10));
        JLabel valueLabel = createValueLabel("Valore: " + player.getHandValue());
        panel.add(valueLabel);

        return panel;
    }
    

    

    /**
     * Getter che restituisce il pulsante "HIT".
     *
     * @return JButton per l'azione di pescare una carta.
     */
    public JButton getHitButton() {
        return hitButton;
    }

    /**
     * Getter che restituisce il pulsante "STAND".
     *
     * @return JButton per l'azione di fermarsi.
     */
    public JButton getStandButton() {
        return standButton;
    }
}
