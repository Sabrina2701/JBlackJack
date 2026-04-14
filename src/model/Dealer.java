package model;

import java.util.List;

/**
 * La classe Dealer implementa la strategia per il Banco.
 *
 */


public class Dealer implements HitDecision {
	
    /**
     * 
     * Metodo che decide in base al valore in mano,
     * se il banco vuole chiedere un'altra carta. Il banco chiede
     * la carta sempre se ha un valore in mano inferiore a 17.
     * 
     * 
     * @param hand carte nella mano del mazziere.
     * @return true se il mazziere vuole un'altra carta, false altrimenti.
     */
    @Override
    public boolean wantsToHit(List<StandardCard> hand) {
        int handValue = hand.stream().mapToInt(StandardCard::getValue).sum();
        return handValue < 17;
    }
}