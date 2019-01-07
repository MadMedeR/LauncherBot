package de.tontor.cclauncher.bot;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class LauncherBot10 extends JDialog { //LauncherBot10
	
	private JLabel exePathLabel, userLabel, pwordLabel, licenceLabel, infoLabel;
	private JTextField exePathTextField, userTextField, pwordTextField, licenceTextField;
    private JButton saveButton, searchButton;
	private GroupLayout layout;
	private boolean isSaved = false;
	private List<String> entries;

	public LauncherBot10(String... s) {
		initComponents(s);
	}
	
	public List<String> getEntries () {
		return entries;
	}
	
	public boolean isSaved() {
		return isSaved;
	}

	private void initComponents(String... s) {
		
		exePathLabel = new JLabel();
		userLabel = new JLabel();
		pwordLabel = new JLabel();
		licenceLabel = new JLabel();
		infoLabel = new JLabel();
		exePathTextField = new JTextField();
		userTextField = new JTextField();
		pwordTextField = new JTextField();
		licenceTextField = new JTextField();
		searchButton = new JButton();
		saveButton = new JButton();
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("config.ini fehlt oder ist fehlerhaft");
		exePathLabel.setText("CCLauncher.exe Installationspfadname:");
		userLabel.setText("Benutzername:");
		pwordLabel.setText("Passwort:");
		licenceLabel.setText("Lizenzschlüssel:");
		infoLabel.setText("Bitte Daten in die Felder eingeben.");
		exePathTextField.setColumns(30);
		exePathTextField.setText(s[0]);
		userTextField.setText(s[1]);
		pwordTextField.setText(s[2]);
		licenceTextField.setText(s[3]);
		searchButton.setText("Suche");
		saveButton.setText("Speichern");
		searchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchButtonActionPerformed(evt);
			}
		});
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				isSaved = saveButtonActionPerformed(evt);
			}
		});
		
		layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
					.addComponent(exePathLabel)
					.addComponent(userLabel)
					.addComponent(pwordLabel)
					.addComponent(licenceLabel)
					.addComponent(infoLabel))
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addComponent(exePathTextField)
						.addComponent(searchButton))
					.addComponent(userTextField)
					.addComponent(pwordTextField)
					.addComponent(licenceTextField)
					.addComponent(saveButton))
		);
		
		layout.setVerticalGroup(
			layout.createSequentialGroup()				
				.addGroup(layout.createParallelGroup()
					.addComponent(exePathLabel)
					.addComponent(exePathTextField)
					.addComponent(searchButton))
				.addGroup(layout.createParallelGroup()
					.addComponent(userLabel)
					.addComponent(userTextField))
				.addGroup(layout.createParallelGroup()
					.addComponent(pwordLabel)
					.addComponent(pwordTextField))
				.addGroup(layout.createParallelGroup()
					.addComponent(licenceLabel)
					.addComponent(licenceTextField))
				.addGroup(layout.createParallelGroup()
					.addComponent(infoLabel)
					.addComponent(saveButton))
		);
		
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon("app/resources/logo-icon.png").getImage());
		icons.add(new ImageIcon("app/resources/icon32.png").getImage());
		icons.add(new ImageIcon("app/resources/icon16.png").getImage());
		icons.add(new ImageIcon("app/resources/icon.gif").getImage());
		setIconImages(icons);
		
		pack();
		//setLocationRelativeTo(null); //Ausrichtung mittig
	 	setLocation(40, 60);
	}
	
	private void searchButtonActionPerformed(ActionEvent evt) {
		JFileChooser chooser = new JFileChooser("C:/");
		chooser.setDialogTitle("Installationsverzeichnis \"CCLauncher_Client\" suchen");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String cclPath = chooser.getSelectedFile().getAbsolutePath();
			exePathTextField.setText(cclPath + "\\CCLauncher_Client.exe");
			String cclFolder = "CCLauncher_Client";
			int l = cclPath.length();
			int n = cclFolder.length();
			if (l<n || !cclFolder.equals(cclPath.substring(l-n))) {
				infoLabel.setText("Falsches Verzeichnis ausgewählt!!!");
				searchButtonActionPerformed(evt);
			} else {
				infoLabel.setText("Verzeichnis korrekt.");
			}
		}
	}
	
	private boolean saveButtonActionPerformed(ActionEvent evt) {
		if (!exePathTextField.getText().isEmpty() &&
			!userTextField.getText().isEmpty() &&
			!pwordTextField.getText().isEmpty() &&
			!licenceTextField.getText().isEmpty()) {
				entries = Arrays.asList(
						"installpath="	+ exePathTextField.getText(),
						"user="			+ userTextField.getText(),
						"pword="		+ pwordTextField.getText(),
						"licence="		+ licenceTextField.getText()
					);
			infoLabel.setText("config-Datei wird gespeichert...");
			dispose();
			return true;
		} else {
			infoLabel.setText("Bitte alle Felder ausfüllen");
			return false;
		}
	}
	
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LauncherBot10().setVisible(true);
			}
		});
	}
}
