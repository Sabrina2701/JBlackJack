package model;

/**
 * L'interfaccia StandardCard rappresenta una carta da gioco. I Design Pattern usati sono lo Strategy,
 * per la gestione delle carte, e il Template Method usato nello specifico nella costruzione del
 * nome del file per l'immagine di una carta.
 * 
 */
public interface StandardCard {
	
	/**
     * Getter per il seme della carta.
     *
     * @return seme della carta ("hearts", "clubs", "spades", "diamonds").
     */
    String getSuit();
    

    /**
     * Getter per il rango della carta.
     *
     * @return rango della carta ("ace", "king", "queen").
     */
    String getRank();
    
    

    /**
     * Getter per il valore numerico della carta.
     *
     * @return Il valore numerico della carta.
     */
    int getValue();

    

    /**
     * Metodo di default che restituisce il nome del file immagine che rappresenta una 
     * specifica carta. Usiamo il rango e il seme della carta per realizzare il nome del file,
     * così da essere intuitivo.
     *
     * @return nome del file immagine della carta.
     */
    default String getImageFileName() {
        return getRank() + "_of_" + getSuit() + ".png";
    }
}
