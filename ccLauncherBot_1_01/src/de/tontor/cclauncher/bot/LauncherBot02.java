package de.tontor.cclauncher.bot;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.net.InetAddress;

public class LauncherBot02 { //LauncherBot02
	private LauncherBot04 info;
	private LauncherBot05 megaMan;
	private LauncherBot06 conf;
	private static final Dimension	LOGIN  = new Dimension(400,153),
									STATUS = new Dimension(680,203);
	
	public LauncherBot02() {
		String filename = "config.ini";
		info = new LauncherBot04("ccLauncher Bot","Bitte warten...Versuch " + filename + " zu laden");
		try {megaMan = new LauncherBot05();} catch (Exception e) {info.printBotError();}		
		conf = new LauncherBot06(filename);
		if (conf.readConfig()) {
			info.printLoadSuccess(filename);
		} else {
			info.printLoadError(filename);
			conf.openConfig(filename + " war inkorrekt oder existierte nicht.");
		}
	}

	//public
//===============================================================================================
	public boolean isLicenceValid() {
		boolean tmp = conf.getUser().equals(LauncherBot07.decodeLicence(conf.getLicence()));
		if (!tmp) {
			info.printLicenceError();
			conf.openConfig("Lizenz ungültig");
		}
		return tmp;
	}
	
	public void loginRealLife() {
		waitForWWW();
	    try {Desktop.getDesktop().open(new File(conf.getExePath()));
	    } catch (Exception e) {info.printPathError();}
	    waitForLoginAndDestroy();
		megaMan.pressEnter();
		waitForStatus();
		info.dispose();
	}

	//private
//===============================================================================================
	private void waitForWWW() {
		String task = "Warten auf Internetverbindung...";
		info.print(task);
		LauncherBot08 exit = new LauncherBot08(5,1,task);
		int counterStatus;
		do {
			counterStatus = exit.count();
			if (counterStatus==1) info.print(exit.getInfo());
			if (counterStatus==0) System.exit(0);
		} while(!isThereInternet());
	}
	
	private void waitForLoginAndDestroy() {
		String task = "Warten auf Login-Fenster...";
		info.print(task);
		LauncherBot08 exit = new LauncherBot08(1,1,task);
		int counterStatus;
		do {
			counterStatus = exit.count();
			if (counterStatus==1) info.print(exit.getInfo());
			if (counterStatus==0) System.exit(0);
		} while(!megaMan.searchPanel(LOGIN));
		info.printLoginFound();

		megaMan.login(conf.getUser(),conf.getPword());
	}
	
	private void waitForStatus() {
		String task = "Warten auf Status-Fenster...";
		info.print(task);
		LauncherBot08 exit = new LauncherBot08(1,1,task);
		int counterStatus;
		do {
			counterStatus = exit.count();
			if (counterStatus==1) info.print(exit.getInfo());
			if (counterStatus==0) System.exit(0);
		} while(!megaMan.searchPanel(STATUS));
	}
	
	private boolean isThereInternet () {
		String[] addresses = {	"comcave.de","portal.cc-student.com","google.de",
								"bing.com","facebook.com","wikipedia.org"};
		for (String host : addresses) {
			InetAddress tmp=null;
			try {
				tmp = InetAddress.getByName(host);
				if (tmp.isReachable(4000)) {
					info.print(new String[]{tmp.toString(),"ist erreichbar.","CCLauncher Client wird geöffnet."});
					return true;
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		return false;
	}
}
