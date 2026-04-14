package model;

/**
 * E’ la classe che rappresenta le carte figura (jack, queen e king), 
 * tutte con valore fissato a 10, come da regola per il BlackJack.
 * Fornisce il comportamento per la carta Face e infatti implementa l'interfaccia {@link StandardCard}.
 * 
 */
public class Face implements StandardCard {
	
	
    /** 
     * 
     * seme della carta 
     * 
     * */
    private String suit;

    /** 
     * 
     * rango della carta (jack, queen e king) 
     * 
     * */
    private String rank;

    
    /**
     * Costruttore che modella una nuova carta figura.
     *
     * @param suit Il seme della carta
     * @param rank Il rango della carta (deve essere "jack", "queen", o "king")
     */
    public Face(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    /**
     * Getter per il seme della carta.
     *
     * @return seme della carta.
     */
    @Override
    public String getSuit() {
        return suit;
    }

    /**
     * Getter per il rango della carta.
     *
     * @return rango della carta ("jack", "queen", o "king").
     */
    @Override
    public String getRank() {
        return rank;
    }

    /**
     * Getter per il valore della carta figura, fissato a 10.
     *
     *
     * @return valore della carta, sempre 10 per le carte figura.
     */
    @Override
    public int getValue() {
        return 10;
    }

    
}