package controller;

import model.LogicGame;
import view.GameView;
import view.StartMenu;
import audio.AudioManager;

import javax.swing.JOptionPane;

/**
 * La classe BlackjackController controlla per l'appunto il model, la view e gestisce l'audio.
 * Notiamo l'uso del Singleton Pattern per assicurarci che ci sia un'unica istanza del controller e in più
 * vi è l'Observer che è il tramite tra model e view. Questa è l'unica classe che serve al controller, che fa parte
 * del pattern MVC, per funzionare.
 * 
 * @see LogicGame
 * @see GameView
 * @see AudioManager
 * @see StartMenu
 */
public class BlackjackController {
    private LogicGame model;
    private GameView view;
    private AudioManager audioManager;
    private static BlackjackController instance;

    /**
     * Costruttore della classe BlackjackController che inizializza il model e la view,
     * gestisce gli action listener per i bottoni di HIT e STAND; avvia il gioco.
     * 
     * 
     * @param model    modello del gioco.
     * @param view     vista associata al Blackjack.
     * @param mainMenu menu principale.
     */
    public BlackjackController(LogicGame model, GameView view, StartMenu mainMenu) {
        this.model = model;
        this.view = view;
        this.audioManager = AudioManager.getInstance();
        instance = this;

        model.addObserver(view);

        view.getHitButton().addActionListener(e -> hit());
        view.getStandButton().addActionListener(e -> stand());

        startGame();
    }
    
    
    /**
     * Metodo che gestisce il turno successivo.
     * 
     */
    private void playNextTurn() {
        if (model.isGameOver()) { 
            model.endRound();
            
        } else if (!model.isHumanTurn()) {  
            playBotTurn();
        }
    }

    /**
     * 
     * Metodo che esegue il turno del bot e del banco in un thread separato. Entrambi attendono 2 secondi
     * prima di far comparire la carta nel loro pannello, per rappresentare l'atto di pensare a stare o pescare.
     * 
     */
    private void playBotTurn() {
        new Thread(() -> {
            while (!model.isGameOver() && !model.isHumanTurn()) {
                try {
                    Thread.sleep(2000);
                    if (model.artificialWantsToHit()) {
                        model.hit();
                    } else {
                        model.stand();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (model.isGameOver()) {
                model.endRound();
            }
        }).start();
    }

    /**
     * 
     * 
     * Metodo che riavvia la partita, ripristinando il round corrente.
     *
     */
    public void restartGame() {
        model.resetRound();
        audioManager.playBackgroundMusic("src/risorse/audio/game_music.wav"); // Riprendi la musica in loop
    }

    /**
     * Metodo per avviare una nuova partita. Viene riprodotto l'audio come sottofondo.
     * 
     */
    private void startGame() {
        model.startGame();
        audioManager.playBackgroundMusic("src/risorse/audio/game_music.wav"); // loop continuo
        view.setVisible(true);
    }



    /**
     * Metodo che gestisce l'azione del pescare la carta del giocatore, in particolare
     * gestendo anche l'audio di quando si gira una carta.
     * 
     */
    
    private void hit() {
        audioManager.playSoundEffect("src/risorse/audio/flip_card.wav"); // Suono delle carte
        boolean busted = model.hit();
        if (busted) {
            JOptionPane.showMessageDialog(view, "Hai sballato! Il tuo turno è finito.", "Sballato",
                    JOptionPane.INFORMATION_MESSAGE);
            stand();
        }
    }

    /**
     * Metodo che gestisce l'azione dello stare del giocatore. Riproduce inoltre
     * il suono del chip, che indica che il turno è finito.
     * 
     */
    private void stand() {
        audioManager.playSoundEffect("src/risorse/audio/chip_place.wav"); 
        model.stand();
        playNextTurn();
    }
    
    
   

    /**
     * Getter per l'istanza Singleton del controller.
     * 
     * @return istanza del Controller.
     */
    public static BlackjackController getInstance() {
        return instance;
    }

	
	
}