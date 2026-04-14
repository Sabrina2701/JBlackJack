package model;

/**
 * 
 * 
 * La classe CardFactory serve per la creazione di oggetti {@link StandardCard}.
 * Abbiamo qui l'utilizzo del Factory Method Pattern che determina il tipo di carta da creare (Ace, Face
 * o Numeric), basandosi sul rango dato.
 * 
 * 
 */
public class CardMaker{
	
	
    /**
     * Metodo che crea e restituisce un oggetto {@link StandardCard} basato sul seme e sul
     * rango forniti.
     * 
     * @param suit seme della carta.
     * @param rank rango della carta
     * @return istanza di {@link StandardCard}.
     * @throws IllegalArgumentException se il rango fornito non è valido.
     * 
     * 
     */
	
    public static StandardCard makeCard(String suit, String rank) {
        if (rank.equals("ace")) {
            return new Ace(suit);
        } else if (rank.equals("jack") || rank.equals("queen") || rank.equals("king")) {
            return new Face(suit, rank);
        } else {
            try {
                int value = Integer.parseInt(rank);
                return new Numeric(suit, rank, value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Rango non valido: " + rank, e);
            }
        }
    }
}

