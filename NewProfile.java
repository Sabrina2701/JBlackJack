package view;

import javax.swing.*;

import model.Profile;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * La Classe NewProfile estende JFrame e gestisce la grafica della schermata per creare un nuovo account,
 * scegliere l'avatar e così accedere.
 * 
 * 
 */

public class NewProfile extends JFrame {
	
	/***
	 * 
	 * campo per poter scrivere il nickname
	 */
    private JTextField nicknameField;
    
    /**
     * etichetta per l'avatar selezionato
     */
    private JLabel selectedAvatarLabel; 
    
    /**
     * pannello per la griglia degli avatar disponibili per la scelta
     */
    private JPanel avatarGridPanel; 
    
    /**
     * profilo utente che deve essere salvato
     */
    private Profile userProfile;
    
    /**
     * 
     * percorso dell'avatar selezionato
     */
    private String selectedAvatarPath;
    
    /**
     * immagine di sfondo da inserire tramite path
     */
    private Image backgroundImage; 

    public NewProfile(Profile userProfile) {
        this.userProfile = userProfile;

        // immagine di sfondo caricata
        backgroundImage = new ImageIcon("src/resources/images/hero.png").getImage();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeFrame();
        setupPanels();
        loadAvatars(); // Carica gli avatar nella griglia
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Creazione Profilo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 
    }

    private void setupPanels() {
    	
    	
        // Crea il pannello di sfondo
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new GridBagLayout()); // Imposta layout del pannello di sfondo

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Margini
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /**
         * pannello per il nickname
         */
        gbc.gridx = 0;
        gbc.gridy = 30; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        backgroundPanel.add(createNicknamePanel(), gbc);

        /**
         * pannello per gli avatar
         */
        gbc.gridx = 0;
        gbc.gridy = 34; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(createAvatarGridPanel(), gbc); 

        /**
         * 
         * bottone per poter accedere
         */
        gbc.gridx = 0;
        gbc.gridy = 36; 
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        backgroundPanel.add(createConfirmButton(), gbc);

        /**
         * pannello di sfondo per la finestra
         * 
         */
        add(backgroundPanel, BorderLayout.CENTER);
    }
    
    /**
     * Metodo per creare il pannello per l'inserimento del nickname
     * 
     * @return pannello del nickname
     */

    private JPanel createNicknamePanel() {
        // Crea un pannello con sfondo e bordo
        JPanel nicknamePanel = new JPanel();
        nicknamePanel.setBackground(new Color(255, 255, 255, 200)); // Sfondo bianco con trasparenza
        nicknamePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2), "Nickname", 
                0, 0, new Font("Verdana", Font.BOLD, 20), Color.BLACK));

        nicknameField = new JTextField(15); // 15 caratteri
        nicknameField.setFont(new Font("Verdana", Font.PLAIN, 16)); // Font del campo di testo

       
        JLabel iconLabel = new JLabel(new ImageIcon("src/resources/images/icon_nickname.png")); // Icona 
        iconLabel.setPreferredSize(new Dimension(40, 40)); // Dimensioni icona

        nicknamePanel.add(iconLabel); // Aggiungi l'icona al pannello
        nicknamePanel.add(nicknameField); // Aggiungi il campo del nickname

        return nicknamePanel;
    }
    
    /**
     * 
     * Metodo per creare la griglia degli avatar
     * 
     * @return griglia avatar
     */

    private JPanel createAvatarGridPanel() {
        avatarGridPanel = new JPanel(new GridLayout(2, 3, 10, 10)); // Griglia 2x3 con spazi tra le immagini
        avatarGridPanel.setBackground(Color.WHITE); // Sfondo bianco per contrasto

        selectedAvatarLabel = new JLabel(); // Etichetta per l'avatar selezionato 
        selectedAvatarLabel.setPreferredSize(new Dimension(150, 150));
        selectedAvatarLabel.setHorizontalAlignment(JLabel.CENTER);
        selectedAvatarLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Bordi grigi iniziali

        return avatarGridPanel;
    }
    
    /**
     * Metodo per il pulsante per accedere e passare alla finestra successiva.
     * 
     * @return pulsante per accedere
     */

    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Accedi");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.setBackground(new Color(0, 150, 136));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false); 

        
        confirmButton.addMouseListener(new MouseAdapter() {  //effetto hover
            @Override
            public void mouseEntered(MouseEvent e) {
                confirmButton.setBackground(new Color(0, 180, 136)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                confirmButton.setBackground(new Color(0, 150, 136)); // Torna al colore originale
            }
        });

        confirmButton.addActionListener(e -> createProfile());
        return confirmButton;
    }
    
    /**
     * 
     * Metodo per caricare le immagini degli avatar disponibili da scegliere
     */

    private void loadAvatars() {
        String[] avatarFiles = { "avatar1.png", "avatar2.png", "avatar3.png", "avatar4.png", "avatar5.png", "avatar6.png" };
        for (String fileName : avatarFiles) {
            addAvatar(fileName);
        }
    }
    
    /**
     * 
     * Metodo per caricare e quindi poter visualizzare nell'interfaccia le immagini degli avatar.
     * 
     * @param fileName
     */

    private void addAvatar(String fileName) {
        ImageIcon icon = new ImageIcon("src/risorse/images/avatars/" + fileName);
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Errore nel caricamento dell'immagine: " + fileName);
            return;
        }

        ImageIcon resizedIcon = resizeIcon(icon, 80, 80); // Icona ridimensionata per la griglia
        JLabel avatarLabel = new JLabel(resizedIcon);
        avatarLabel.setPreferredSize(new Dimension(80, 80));
        avatarLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Bordi grigi iniziali

        
        avatarLabel.addMouseListener(new MouseAdapter() {  // effetto hover e selezione
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedAvatarPath = fileName; // Imposta l'avatar selezionato
                highlightSelectedAvatar(avatarLabel); // Evidenzia l'avatar selezionato
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                avatarLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); // Cambio bordo su hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!fileName.equals(selectedAvatarPath)) {
                    avatarLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Torna al bordo originale
                }
            }
        });

        avatarGridPanel.add(avatarLabel); // Aggiungi l'avatar alla griglia
    }
    
    /**
     * Metodo per avere una grafica più accattivante, con bordi che cambiano colore al 
     * momento di selezionare l'avatar.
     * 
     * @param selectedLabel
     */

    private void highlightSelectedAvatar(JLabel selectedLabel) {
        
        for (Component comp : avatarGridPanel.getComponents()) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Bordo grigio per gli altri
            }
        }
        // Imposta il bordo per l'avatar selezionato
        selectedLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3)); // Bordo verde per l'avatar selezionato
    }
    
    /**
     * Metodo per poter effettivamente creare il profilo, solo dopo aver selezionato un avatar, altrimenti torna un 
     * messaggio d'errore.
     * 
     */

    private void createProfile() {
        String nickname = nicknameField.getText();

        if (selectedAvatarPath == null) {
            JOptionPane.showMessageDialog(this, "Seleziona un avatar prima di continuare.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        userProfile = new Profile(nickname, selectedAvatarPath);
        userProfile.loadProfile();

        dispose();
        new StartMenu(userProfile).setVisible(true);
    }
    
    

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    
    /**
     * Getter per ottenere profilo utente. 
     * 
     * @return profilo utente
     */

    public Profile getUserProfile() {
        return userProfile;
    }
}


/**
 * Classe per il pannello che fa da sfondo.
 * 
 */
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        this.backgroundImage = new ImageIcon("src/risorse/images/hero.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Disegna l'immagine di sfondo
    }
}
