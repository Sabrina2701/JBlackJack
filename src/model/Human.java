package model;

import java.util.List;

/**
 * La classe Human implementa HitDecision e infatti rappresenta la strategia di gioco per il
 * giocatore umano.
 * 
 * 
 */
public class Human implements HitDecision {

    /**
     * Metodo per la decisione del giocatore umano di voler chiedere un'altra carta.
     *Serivirà come placeholder e per questo restituisce sempre false.
     *
     * @param hand lista di carte nella mano del giocatore.
     * @return false, il giocatore non vuole un'altra carta.
     */
    @Override
    public boolean wantsToHit(List<StandardCard> hand) {
        return false;
    }
}