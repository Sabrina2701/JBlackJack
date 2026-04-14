package model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe BlackJackPlayer rappresenta un giocatore, in particolare definisce la mano del giocatore,
 * il suo nome, la strategia di gioco e se il giocatore è umano o meno.
 * 
 */


public class BlackJackPlayer {
	
	
    /** 
     * 
     * mano del giocatore 
     * 
     * */
    private List<StandardCard> hand;
    
    
    /**
     * 
     *nome del giocatore 
     *
     **/
    
    private String name;
    
    
    /** 
     * 
     * strategia di gioco
     * 
     * */
    private HitDecision strategy;
    
    
    /** 
     * 
     * se umano o bot
     * 
     * */
    private boolean isHuman;
    

    /**
     * 
     * Costruttore per un nuovo giocatore.
     *
     * @param name     nome del giocatore.
     * @param strategy strategia di gioco.
     * @param isHuman  true se il giocatore è umano, false altrimenti.
     */
    
    public BlackJackPlayer(String name, HitDecision strategy, boolean isHuman) {
        this.name = name;
        this.strategy = strategy;
        this.isHuman = isHuman;
        this.hand = new ArrayList<>();
    }
    

    /**
     * Metodo che elimina dalla mano del giocatore le carte.
     */
    public void clearHand() {
        hand.clear();
    }
    

    /**
     * Metodo che indica se il giocatore è umano o un bot.
     *
     * @return true se il giocatore è umano, false altrimenti.
     */
    public boolean isHuman() {
        return isHuman;
    }
    

    /**
     * Metodo che aggiunge una carta alla mano del giocatore.
     *
     * @param card La carta da aggiungere.
     */
    public void addCard(StandardCard card) {
        hand.add(card);
    }

    /**
     * Metodo per capire se il giocatore vuole un'altra carta.
     *
     * @return true se il giocatore vuole un'altra carta, false altrimenti.
     */
    public boolean wantsToHit() {
        return strategy.wantsToHit(hand);
    }


    /**
     * Getter che restituisce il valore totale della mano del giocatore.
     * Viene impostato il valore dell'Asso a 11, piuttosto che 1.
     *
     *
     * @return il valore totale della mano
     */
    
    public int getHandValue() {
        return hand.stream()
                .mapToInt(card -> card.getRank().equals("ace") ? 11 : card.getValue())
                .sum();
    }

    
    /**
     * Getter per ottenere la mano attuale del giocatore.
     *
     * @return La lista di carte nella mano del giocatore.
     */
    public List<StandardCard> getHand() {
        return hand;
    }

    
    /**
     * Getter per ottenere il nome del giocatore.
     *
     * @return Il nome del giocatore.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter per ottenere il tipo di strategia adottata da ogni giocatore.
     *
     * @return La strategia di gioco del giocatore.
     */
    public HitDecision getHitDecisionStrategy() {
        return strategy;
    }

	

}