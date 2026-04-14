package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.IntStream;

/**
 * 
 * La classe LogicGame implementa la logica del gioco. Sfruttiamo sia il pattern Observer, così
 * da poter notificare la View quando avviene un cambio di stato; e in più questa è la colonna portante
 * della parte del model, nel pattern MVC, poichè gestiamo i giocatori, il mazzo e lo stato del gioco.
 * 
 * 
 */
public class LogicGame extends Observable {
	
	/**
	 * lista dei giocatori 
	 */
    private List<BlackJackPlayer> players;
    
    /**
     * mazzo di carte
     */
    private DeckBlackJack deck;
    
    /**
     * l'indice del giocatore corrente
     */
    private int currentPlayerIndex;
    
    /**
     * profilo giocatore umano
     *      
     */
    private Profile userProfile;
    
    /**
     * mazziere
     */
    private BlackJackPlayer dealer;
    
    

    /**
     * 
     * Costruttore della classe LogicGame. Inizializziamo il giocatore umano, quelli artificiali,
     *il Banco e il mazzo di carte. Inoltre carichiamo il profilo dell'utente del giocatore umano 
     *per poter avere le informazioni per le statistiche.
     * 
     * 
     * @param userProfile profilo utente del giocatore umano
     */
    
    
    public LogicGame(Profile userProfile) {
        players = new ArrayList<>();
        
        players.add(new BlackJackPlayer(userProfile.getNickname(), new Human(), true)); //prendiamo il nickname creato
                                                                                        // dall'utente
        
        players.add(new BlackJackPlayer("RIVAL1", new ArtificialPlayer(), false));  //i due bot presenti nel gioco
        players.add(new BlackJackPlayer("RIVAL2", new ArtificialPlayer(), false));
        
        dealer = new BlackJackPlayer("Banco", new Dealer(), false); //il mazziere
        
        deck = DeckBlackJack.getInstance();   //prendiamo istanza del mazzo
        
        currentPlayerIndex = 0;
        
        this.userProfile = userProfile;
        userProfile.loadProfile();
    }

    /**
     * 
     * Metodo che avvia il gioco, mescola il mazzo di carte e distribuisce le
     * due carte iniziali a tutti i giocatori.
     */
    public void startGame() {
        deck.shuffle();
        deliverInitialCards();
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo per dare due carte iniziali ai giocatori e al Banco.
     * 
     */
    private void deliverInitialCards() {
        IntStream.range(0, 2).forEach(i -> {
            players.forEach(player -> player.addCard(deck.drawCard()));
            dealer.addCard(deck.drawCard());
        });
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che esegue l'azione "Pesca Carta" per il giocatore corrente,
     * aggiungendo una nuova carta alla sua mano.
     * 
     * Se il giocatore sballa, il metodo restituirà true.
     * 
     * 
     * @return true se il giocatore ha sballato, altrimenti false.
     */
    
    public boolean hit() {
        BlackJackPlayer currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        setChanged();
        notifyObservers();
        return currentPlayer.getHandValue() > 21;
    }

    /**
     * 
     * Metodo che esegue l'azione "Stai" per il giocatore corrente,
     * così da passare al giocatore successivo e concludere il turno attuale.
     * 
     */
    
    public void stand() {
        currentPlayerIndex++;
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che verifica se è il turno del giocatore umano.
     * 
     * @return true se è il turno del giocatore umano, altrimenti false.
     */
    
    public boolean isHumanTurn() {
        return currentPlayerIndex == 0;
    }

    /**
     * 
     * Metodo per capire se il bot vuole eseguire l'azione "Pesca Carta".
     * 
     * @return true se il bot vuole pescare una carta, altrimenti false.
     */
    public boolean artificialWantsToHit() {
        BlackJackPlayer currentPlayer = players.get(currentPlayerIndex);
        return currentPlayer.wantsToHit();
    }

    
    /**
     * 
     * Metodo per finire il round, con il turno del Banco e la proclamazione del vincitore.
     * 
     * Salva il profilo utente del giocatore umano e notifica gli Observer con un segnale che
     * la partita è finita.
     */
    
    public void endRound() {
        playDealerTurn();
        isTheWinner();
        userProfile.saveProfile();
        setChanged();
        notifyObservers("GAME_OVER");
    }
    
    /**
     * 
     * Metodo che ripristina le mani dei giocatori e del Banco nel round attuale.
     * 
     * Mescola il mazzo e distribuisce nuove carte.
     * 
     */
    public void resetRound() {
    	
        players.forEach(BlackJackPlayer::clearHand);
        dealer.clearHand();
        
        currentPlayerIndex = 0;
        
        deck = DeckBlackJack.getInstance();
        deck.shuffle();
        deliverInitialCards();
        setChanged();
        notifyObservers();
    }

    /**
     * Metodo che verifica se il gioco è terminato.
     * 
     * @return true se tutti i giocatori hanno completato il loro turno,
     *         altrimenti false.
     */
    public boolean isGameOver() {
        return currentPlayerIndex >= players.size();
    }

    /**
     * Metodo che verifica se è il turno di un bot o del Banco.
     * 
     * @return true se è il turno di un bot o del Banco, altrimenti false.
     */
    public boolean isBotOrDealerTurn() {
        if (currentPlayerIndex < 0 || currentPlayerIndex >= players.size()) {
            return false;
        }
        
        BlackJackPlayer currentPlayer = players.get(currentPlayerIndex);
        return !currentPlayer.isHuman() || currentPlayer == dealer;
    }

    
    /**
     * 
     * Metodo che esegue il turno del Banco.
     * 
     * Il Banco continua a pescare carte finché non decide
     * di fermarsi o sballa (ossia che supera 21).
     * 
     * 
     */
    private void playDealerTurn() {
        while (dealer.wantsToHit()) {
            dealer.addCard(deck.drawCard());
            if (dealer.getHandValue() > 21) {
                break;
            }
        }
    }

    
    /**
     * 
     * Metodo che per decidere il vincitore valuta il valore della mano del giocatore umano e quello del Banco.
     * 
     * 
     */
    
    private void isTheWinner() {
    	
        BlackJackPlayer humanPlayer = players.get(0);
        int humanHandValue = humanPlayer.getHandValue();
        int dealerHandValue = dealer.getHandValue();

       
        if (humanHandValue > 21 && dealerHandValue > 21) {        
            System.out.println("Tutti hanno sballato. Nessun vincitore.");
            
        } else if (humanHandValue > 21) {
            System.out.println("Il giocatore ha sballato. Il Banco vince.");
            
            
        } else if (dealerHandValue > 21) {
            System.out.println("Il Banco ha sballato. Il giocatore vince.");
            
            
        } else if (humanHandValue > dealerHandValue) {
            System.out.println("Il giocatore vince.");
            
            
        } else {
            System.out.println("Il Banco vince.");
        }
    }

    
    

    /**
     * Getter che restituisce la lista dei giocatori in gioco.
     * 
     * @return una lista di oggetti {@link Player}.
     */
    public List<BlackJackPlayer> getPlayers() {
        return players;
    }

    /**
     * Getter che restituisce il Banco.
     * 
     * @return l'oggetto {@link Player} che rappresenta il Banco.
     */
    public BlackJackPlayer getDealer() {
        return dealer;
    }

    /**
     * Getter che restituisce il profilo utente del giocatore umano.
     * 
     * @return il profilo utente del giocatore umano {@link UserProfile}.
     */
    public Profile getUserProfile() {
        return userProfile;
    }

    /**
     * Getter che restituisce l'indice del giocatore corrente.
     * 
     * @return l'indice del giocatore corrente.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Metodo che restituisce un messaggio che indica il vincitore della mano.
     * 
     * @return una stringa che rappresenta il messaggio del vincitore.
     */
    public String getWinnerMessage() {
        // Filtra solo i giocatori che non hanno sballato
        List<BlackJackPlayer> validPlayers = players.stream()
                .filter(p -> p.getHandValue() <= 21)
                .toList();

        // Se tutti i giocatori hanno sballato e il dealer ha sballato
        if (validPlayers.isEmpty() && dealer.getHandValue() > 21) {
            return "Tutti hanno sballato. Nessun vincitore.";
        }

        // Se solo il dealer ha sballato
        if (dealer.getHandValue() > 21) {
            return "Il Banco ha sballato. I giocatori vincono!";
        }

        // Trova il giocatore con la mano più alta che non ha sballato
        BlackJackPlayer winner = validPlayers.stream()
                .max(Comparator.comparingInt(BlackJackPlayer::getHandValue))
                .orElse(dealer); // In questo caso, dealer è il vincitore se nessun giocatore è valido

        // Confronta il valore della mano del Banco con quello del vincitore
        if (dealer.getHandValue() >= winner.getHandValue()) {
            return "Il Banco ha vinto la partita!";
        } else {
            return winner.getName() + " ha vinto la partita!";
        }
    }
}
