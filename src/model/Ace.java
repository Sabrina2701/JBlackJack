package model;

/**
 * La classe Ace rappresenta la carta dell'Asso, che ha sempre valore=11.
 * Implementa l'interfaccia {@link StandardCard}.
 * 
 * 
 */
public class Ace implements StandardCard {
	
	
    /** 
     * 
     * Il seme della carta 
     * 
     * */
    private String suit;
    

    /**
     * Costruttore per inizializzare Asso con il seme specificato.
     * 
     * @param suit seme della carta Asso.
     */
    public Ace(String suit) {
        this.suit = suit;
    }
    
    /**
     * 
     * 
     * Getter per il rango della carta Asso.
     * 
     * @return rango della carta, che è sempre "ace" in questa classe.
     */
    @Override
    public String getRank() {
        return "ace";
    }

    /**
     * 
     * Getter per il valore della carta Asso.
     * 
     * @return valore della carta Asso, che in questa implementazione vale sempre 11.
     */
    @Override
    public int getValue() {
        return 11;
    }

    /**
     * 
     * Getter per il seme della carta Asso.
     * 
     * @return seme della carta Asso.
     */
    @Override
    public String getSuit() {
        return suit;
    }

    
}
