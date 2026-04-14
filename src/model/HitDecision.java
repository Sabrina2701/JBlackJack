package model;

import java.util.List;

/**
 * L'interfaccia HitDecision si riferisce alla strategia di gioco.
 * Usiamo il Strategy Pattern per poter creare un'interfaccia da estendere nei singoli casi.
 * 
 */

public interface HitDecision {

    /**
     * L'interfaccia sfrutta un boolean per poter capire se, in base alla mano attuale,
     * il giocatore vuole un'altra carta.
     *  
     * Con questo singolo metodo si va a delineare la logica decisionale del gioco;
     * in base a come si usa questa interfaccia si avranno strategie diverse per chiedere un'altra
     * carta.
     *
     * @param hand Le carte attualmente nella mano del giocatore.
     * @return true se il giocatore vuole un'altra carta, false altrimenti.
     */
    boolean wantsToHit(List<StandardCard> hand);
}
