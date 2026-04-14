package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La classe Profile corrisponde al profilo del giocatore. Gestisce le informazioni 
 * dell'utente e le statistiche di gioco.
 * 
 * I pattern utilizzati sono:
 * 
 * ->Serialization: serializzazione all'interno di un file txt per salvare l'oggetto;
 * 
 * ->Deserialization: deserializzazione per caricare lo stato di un oggetto da un file txt;
 * 
 * ->Domain Model: rappresenta l'utente e gestisce la logica legata alle sue statistiche 
 * e progressi nel gioco.
 * 
 */


public class Profile {
	
	/**
	 * nome inventato dall'utente
	 */
    private String nickname;
    
    /**
	 * percorso per mostrare le varie immagini dell'avatar
	 */
    private String avatarPath;
    
    /**
	 * partite giocate 
	 */
    private int gamesPlayed;
    
    /**
	 * partite vinte 
	 */
    private int gamesWon;
    
    /**
	 * partite perse
	 */
    private int gamesLost;
    
    /**
	 * livello corrente
	 */
    private int level;
    
    /**
	 * esperienza acquisita giocando più volte
	 */
    private int experience;
    
    

    /**
     * Costruttore che modella un nuovo profilo utente con il nickname e
     * il percorso dell'avatar scelti dal giocatore.
     *
     * @param nickname   Il nickname dell'utente.
     * @param avatarPath Il percorso dell'immagine avatar dell'utente.
     */
    
    public Profile(String nickname, String avatarPath) {
        this.nickname = nickname;
        this.avatarPath = avatarPath;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.level = 1;
        this.experience = 0;
    }

    /**
     * Metodo che incrementa il numero di partite giocate dall'utente.
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    /**
     * Metodo che incrementa il numero di partite vinte dall'utente e
     * aggiunge punti esperienza se il giocatore umano dovesse vincere.
     */
    public void incrementGamesWon() {
        this.gamesWon++;
        addExperience(10);
    }

    /**
     * Metodo che incrementa il numero di partite perse dall'utente.
     */
    public void incrementGamesLost() {
        this.gamesLost++;
    }

    /**
     * Metodo che aggiunge punti esperienza al profilo dell'utente e
     * controlla se è necessario salire di livello.
     *
     * @param exp La quantità di punti esperienza da aggiungere.
     */
    private void addExperience(int exp) {
        this.experience += exp;
        while (this.experience >= experienceNeededForNextLevel()) {
            levelUp();
        }
    }

    /**
     * Metodo che aumenta il livello dell'utente.
     */
    private void levelUp() {
        this.level++;
    }

    /**
     * Metodo che calcola l'esperienza necessaria per il prossimo livello.
     *
     * @return La quantità di esperienza necessaria per il prossimo livello.
     */
    private int experienceNeededForNextLevel() {
        return this.level * 100;
    }

    /**
     * Metodo che salva il profilo utente in un file di testo nella cartella "accounts".
     * Il nome del file è basato sul nickname inserito dall'utente nel NewProfile nella view.
     * 
     */
    public void saveProfile() {
        File profilesDir = new File("accounts");
        if (!profilesDir.exists()) {
            profilesDir.mkdirs();
        }

        String filePath = "accounts" + File.separator + nickname + "_profilo.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(nickname + "\n");
            writer.write(avatarPath + "\n");
            writer.write(gamesPlayed + "\n");
            writer.write(gamesWon + "\n");
            writer.write(gamesLost + "\n");
            writer.write(level + "\n");
            writer.write(experience + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che carica il profilo utente da un file di testo nella cartella "accounts".
     * Il nome del file sarà proprio il nome scelto dall'utente.
     */
    public void loadProfile() {
        String filePath = "accounts" + File.separator + nickname + "_profilo.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            this.nickname = reader.readLine();
            this.avatarPath = reader.readLine();
            this.gamesPlayed = Integer.parseInt(reader.readLine());
            this.gamesWon = Integer.parseInt(reader.readLine());
            this.gamesLost = Integer.parseInt(reader.readLine());
            this.level = Integer.parseInt(reader.readLine());
            this.experience = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Getter che riporta il livello dell'utente.
     *
     * @return Il livello dell'utente.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Getter che riporta i punti esperienza dell'utente.
     *
     * @return I punti esperienza dell'utente.
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Getter che riporta il nickname dell'utente.
     *
     * @return Il nickname dell'utente.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter che riporta il percorso dell'immagine avatar dell'utente.
     *
     * @return Il percorso dell'immagine avatar.
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Getter che riporta il numero di partite giocate dall'utente.
     *
     * @return Il numero di partite giocate.
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Getter che riporta il numero di partite vinte dall'utente.
     *
     * @return Il numero di partite vinte.
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Getter che riporta il numero di partite perse dall'utente.
     *
     * @return Il numero di partite perse.
     */
    public int getGamesLost() {
        return gamesLost;
    }

    
}