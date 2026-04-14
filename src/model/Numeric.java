package model;

/**
 * La classe Numeric rappresenta l'interfaccia di una carta numerica.
 * 
 */


public class Numeric implements StandardCard {
	
	
	/** 
     * 
     *rango della carta (da '2' a '10') 
     * 
     */
    private String rank;
    
   
    /** 
     * 
     * valore numerico della carta 
     * 
     */
    private int value;

    
    /** 
     * 
     * seme della carta
     *  
     */
    private String suit;

    
    
    
    
    /**
     * Costruttore per una nuova carta numerica.
     *
     * @param rank  rango della carta.
     * @param value valore numerico della carta.
     * @param suit  seme della carta.
    
     */
    
    public Numeric(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
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
     * @return rango della carta.
     */
    @Override
    public String getRank() {
        return rank;
    }
    
    
    

    /**
     * 
     * Getter per il valore numerico della carta.
     *
     * @return valore numerico della carta.
     */
    @Override
    public int getValue() {
        return value;
    }

    
}