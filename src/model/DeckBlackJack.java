package model;

import java.util.*;

/**
 * 
 * La classe Deck rappresenta il mazzo di carte.Viene adottato il Singleton pattern
 * per essere sicuri che ci sia solamente un'istanza del mazzo durante il gioco.
 * 
 */

public class DeckBlackJack {
	
	
	/** 
     * 
     * Lista di carte nel mazzo 
     * 
     **/
    private List<StandardCard> cards;
	
    /** 
     * 
     * Istanza della classe Deck 
     * 
     */
    private static DeckBlackJack instance;

    

    /**
     * Costruttore privato che inizializza il mazzo di carte ed
     * evita l'instanza diretta.
     */
    private DeckBlackJack() {
        initializeDeck();
    }

    
    /**
     * 
     * Getter per avere l'istanza Singleton del mazzo. Se c'è viene
     * restituita quella corrente, altrimenti viene creata.
     * 
     * @return l'istanza di DeckBlackJack.
     */
    
    
    public static DeckBlackJack getInstance() {
        if (instance == null) {
            instance = new DeckBlackJack();
        }
        return instance;
    }
    
    
    /**
     * 
     * Metodo per estrarre una carta dal mazzo. Se il mazzo è vuoto viene
     * di nuovo inizializzato e mescolato, prima di pescare una carta.
     *
     * 
     * @return carta estratta.
     */
    public StandardCard drawCard() {
        if (cards.isEmpty()) {
            initializeDeck();
            shuffle();
        }
        return cards.remove(cards.size() - 1);
    }
    
    /**
     * Metodo per mescolare le carte nel mazzo.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    
    /**
     * 
     * Metodo che inizializza il mazzo di carte e che, grazie a CardMaker, crea
     * tutte le carte possibili con semi e ranghi disponibili, sfruttando gli stream.
     * 
     */
    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] suits = { "hearts", "diamonds", "clubs", "spades" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace" };

        Arrays.stream(suits)
                .flatMap(suit -> Arrays.stream(ranks).map(rank -> CardMaker.makeCard(suit, rank))).forEach(cards::add);
    }

    

    
}