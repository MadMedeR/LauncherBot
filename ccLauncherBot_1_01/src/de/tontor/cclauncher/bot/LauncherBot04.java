package de.tontor.cclauncher.bot;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class LauncherBot04 extends JFrame{ //LauncherBot04
	
	private JLabel[] label = new JLabel[3];
	private GroupLayout layout;
	
	// Konstruktoren
//=====================================================================================
	public LauncherBot04(LauncherBot12 t) {
		this("ccLauncher Bot (Start: " + t.startTime + ")","");
	}
	
	public LauncherBot04(String title, String txt) {
		setTitle("ccLauncher Bot");
		label[0] = new JLabel(txt);
		label[1] = new JLabel();
		label[2] = new JLabel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,120);
		
		layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addComponent(label[0]).addComponent(label[1]).addComponent(label[2]));
		layout.setVerticalGroup(
			layout.createSequentialGroup()
				.addComponent(label[0]).addComponent(label[1]).addComponent(label[2]));
		
		ArrayList<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon("app/resources/logo-icon.png").getImage());
		icons.add(new ImageIcon("app/resources/icon32.png").getImage());
		icons.add(new ImageIcon("app/resources/icon16.png").getImage());
		icons.add(new ImageIcon("app/resources/icon.gif").getImage());
		setIconImages(icons);
		
		setVisible(true);
	}
	
	// Printer
//=====================================================================================
	public void print(String... s) {
		int i=0;
		for (; i<s.length && i<3; i++)
			label[i].setText(s[i]);
		for (; i<3 ; i++)
			label[i].setText("");
	}
	
	public void printBotError() {
		print("es liegt ein Fehler mit dem Bot vor","Bitte neu starten");
		try {Thread.sleep(5000);} catch (InterruptedException e) {}
		System.exit(0);
	}
	
	public void printLoadSuccess(String filename) {
		print(filename + " erfolgreich geladen!","Bitte warten...auf Login-Fenster");
	}
	
	public void printLoadError(String filename) {
		print(filename + " war inkorrekt oder existierte nicht.","Bitte Datei überprüfen und Programm neu starten.","");
	}
	
	public void printPathError() {
		print("es liegt ein Fehler mit dem Installationspfad vor","Bitte neu starten");
	}
	
	public void printLicenceError() {
		print("Lizenz ungültig!!!","Lizenz mit Benutzernamen über tontor@web.de anfordern","und Programm neu starten");
	}
	
	public void printLoginFound() {
		print("Login-Fenster gefunden!","Let the magic happen!!!","Bitte warten...auf Status-Fenster");
	}
	
	public void printTime(LauncherBot12 t) { //statusFound()
		print("ccLauncher läuft seit " + t.getDeltaTime() + " ("+ t.lifeTime + " total)",
				  "\"Anwesenheitskontrolle\" - Überprüfung jede halbe Minute.",
				  "Letzte Überprüfung und Programmende um " + t.endTime);
	}
	
	public void printCheckFound() {
		label[1].setText("\"Anwesenheitskontrolle\" gefunden!!!");
		label[2].setText("...Code wird extrahiert...");
	}
	
	public void printCode(String solution) {
		label[2].setText("...Buttons werden gedrückt. Code: "+ solution);
	}
}
