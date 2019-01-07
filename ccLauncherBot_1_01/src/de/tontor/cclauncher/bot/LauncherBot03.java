package de.tontor.cclauncher.bot;

import java.awt.Dimension;

public class LauncherBot03 { //LauncherBot03
	private LauncherBot04 info;
	private LauncherBot05 megaMan;
	private LauncherBot12 t;
	private static final Dimension CHECK  = new Dimension(358,313),
								   STATUS = new Dimension(680,203);

	public LauncherBot03(int minTime) {
		t = new LauncherBot12(minTime);
		info = new LauncherBot04(t);
        try {megaMan = new LauncherBot05();} catch (Exception e) {System.exit(0);}
	}
	
	public void realLifeCheck() {
		String task = "Warten auf Programm-Ende...";
		LauncherBot08 close = new LauncherBot08(t.lifeTimeInMinutes,30,task);
		int counterStatus;
		do {
			info.printTime(t);
			counterStatus = close.count();
			if (counterStatus==1) info.print(close.getInfo());
			if (megaMan.searchPanel(CHECK)) destroyCheckWindow();
		} while(t.endMillis > t.actMillis && counterStatus!=0);
		close();
	}
	
	private void destroyCheckWindow() {
		new LauncherBot11(megaMan.screenshot).saveImage("Panel");
		info.printCheckFound();
		info.printCode(megaMan.getButtonsToPress());
		megaMan.pushTheButtons();
	}
	
	private void close() {
		if (megaMan.searchPanel(STATUS)) {
			megaMan.closeCcl();
		} else {
			try { new LauncherBot13("CCLauncher_Client.exe"); } catch (Exception e) {
				try { Runtime.getRuntime().exec("shutdown /s /t 30 /f"); } catch (Exception e1) {}
			}
		}
		info.dispose();
		System.exit(0);
	}
}