package audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


/**
 * 
 * 
 * Classe che gestisce l'audio nel gioco, permettendo di riprodurre separatamente musica ed effetti sonori.
 * Implementa il pattern Singleton per garantire un'unica istanza della gestione audio.
 * 
 */

public class AudioManager {
    private static AudioManager instance; // Unica istanza della classe
    private Clip musicClip;  // Clip per la musica di sottofondo
    private Clip soundEffectClip; // Clip per gli effetti sonori

    /**
     * Restituisce l'istanza singleton di AudioManager. Se non esiste, la crea.
     * 
     * @return istanza di AudioManager.
     */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    /**
     * Costruttore privato per il pattern Singleton. Non permette di creare altre istanze dall'esterno.
     */
    private AudioManager() {
    }

    /**
     * Riproduce la musica di sottofondo in loop continuo. Se una musica è già in esecuzione, viene chiusa 
     * e sostituita dalla nuova.
     * 
     * @param filename percorso del file audio da riprodurre.
     */
    public void playBackgroundMusic(String filename) {
        try {
        	
            // Chiudi il clip della musica se esiste
            if (musicClip != null && musicClip.isOpen()) {
                musicClip.close();
            }

            // Apri il file audio per la musica di sottofondo
            File audioFile = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuo
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore durante la riproduzione della musica di sottofondo: " + e.getMessage());
        }
    }

    /**
     * Riproduce un effetto sonoro una volta. Se un effetto sonoro è già in esecuzione,
     * viene chiuso prima di riprodurre il nuovo effetto.
     * 
     * @param filename percorso del file audio dell'effetto sonoro.
     */
    public void playSoundEffect(String filename) {
        try {
            // Chiudi il clip degli effetti sonori se esiste
            if (soundEffectClip != null && soundEffectClip.isOpen()) {
                soundEffectClip.close();
            }

            // Apri il file audio per gli effetti sonori
            File audioFile = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(audioStream);
            soundEffectClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore durante la riproduzione dell'effetto sonoro: " + e.getMessage());
        }
    }

    /**
     * Ferma la riproduzione della musica di sottofondo se è attualmente in esecuzione.
     */
    public void stopBackgroundMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    /**
     * Chiude il clip della musica di sottofondo, liberando le risorse.
     */
    public void closeBackgroundMusic() {
        if (musicClip != null) {
            musicClip.close();
        }
    }

    /**
     * Ferma la riproduzione dell'effetto sonoro se è attualmente in esecuzione.
     */
    public void stopSoundEffect() {
        if (soundEffectClip != null && soundEffectClip.isRunning()) {
            soundEffectClip.stop();
        }
    }

    /**
     * Chiude il clip degli effetti sonori, liberando le risorse.
     */
    public void closeSoundEffect() {
        if (soundEffectClip != null) {
            soundEffectClip.close();
        }
    }
}
