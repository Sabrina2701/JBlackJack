package model;

import java.util.List;

/**
 * 
 * La classe ArtificialPlayer implementa HitDecision e definisce la strategia di gioco per i bot.
 * 
 */
public class ArtificialPlayer implements HitDecision {

    /**
     * 
     * Metodo che determina se il bot vuole pescare un'altra carta.
     * Facciamo in modo che peschi finchè il valore totale della mano è inferiore a 17.
     *
     * 
     * @param hand lista di carte attualmente nella mano del bot.
     * @return true il bot vuole pescare un'altra carta, false altrimenti.
     */
    @Override
    public boolean wantsToHit(List<StandardCard> hand) {
        int handValue = hand.stream().mapToInt(StandardCard::getValue).sum();
        return handValue < 17;
    }
}