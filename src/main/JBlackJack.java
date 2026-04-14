package main;

import javax.swing.SwingUtilities;
import java.awt.event.*;
import view.NewProfile;
import view.StartMenu;
import model.Profile;

/**
 * La classe JBlackJack è il main del gioco. Fa partire la schermata iniziale dalla creazione del profilo,
 * per poi passare al menu principale. Per assicurarci che i thread vengano gestiti bene ci avvaliamo
 * del metodo SwingUtilities; l'interfaccia grafica viene avviata proprio nell'Event Dispatch Thread.
 *
 * @see NewProfile
 * @see StartMenu
 * @see Profile
 */
public class JBlackJack {
	
	
    /**
     * Il metodo main avvia l'applicazione sull'Event Dispatch Thread.
     * 
     * 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	
        	// finestra per creare profilo
            NewProfile profileCreationView = new NewProfile(null);
            profileCreationView.setVisible(true);

            profileCreationView.addWindowListener(new WindowAdapter() {
            	
                @Override
                public void windowClosed(WindowEvent e) {
                    Profile userProfile = profileCreationView.getUserProfile();
                    
                    // se l'utente ha creato il profilo, passas al menu principale.
                    
                    if (userProfile != null) {
                    	
                        StartMenu menuView = new StartMenu(userProfile);
                        menuView.setVisible(true);
                    }
                }
            });
        });
    }
}