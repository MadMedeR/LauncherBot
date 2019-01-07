package de.tontor.cclauncher.bot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LauncherBot06 { //LauncherBot06
	
	private final Path destination;
	private String  exePath="C:\\CCLauncher_Client\\CCLauncher_Client.exe",
					user="mmustermann",
					pword="123",
					licence="für Lizenz, email an tontor@web.de senden";
	private String[] info = new String[3];
	
	public LauncherBot06(String filename) {
		destination =  Paths.get(filename);
	}
	
	// Getters
//==========================================================================================
	public String getExePath() {return exePath;}

	public String getUser() {return user;}

	public String getPword() {return pword;}
	
	public String getLicence() {return licence;}
	
	public String[] getInfo() {return info;}

	// public
//==========================================================================================
	public boolean readConfig() {
		List<String> read;
		try {
			read = Files.readAllLines(destination);
			
			exePath = read.get(0).split("=")[1];
			user    = read.get(1).split("=")[1];
			pword   = read.get(2).split("=")[1];
			licence = read.get(3).split("=")[1];

			if(		!isExePathValid() ||
					!user.matches("^[a-z]+$") ||
					!pword.matches("^[0-9a-z]+$") ||
					licence.isEmpty())
				throw new LauncherBot09("Werte sind falsch");
			
		} catch (RuntimeException|IOException e) {
			createConfig();
			return false;
		}
		return true;
	}

	public void openConfig(String error) throws LauncherBot09 {
		try { Runtime.getRuntime().exec("notepad.exe " + destination); } catch (Exception e) {}
		throw new LauncherBot09(error);
	}
	
	// private
//==========================================================================================
	private boolean isExePathValid() {
		File f = new File(exePath);
		return  f.exists() &&
				f.isFile() &&
				f.getName().equals("CCLauncher_Client.exe") &&
				f.getParentFile().getName().equals("CCLauncher_Client");
	}
	
	private void createConfig() {
		LauncherBot10 cgui = new LauncherBot10(exePath, user, pword, licence);
		cgui.setVisible(true);
		List<String> text = cgui.getEntries();
		if (!cgui.isSaved() || text==null) {		
		text = Arrays.asList(
				"installpath=" + exePath,
				"user=" + user,
				"pword=" + pword,
				"licence=" + licence
			);}
		try { Files.write(destination, text); } catch (Exception e) {}
	}
}