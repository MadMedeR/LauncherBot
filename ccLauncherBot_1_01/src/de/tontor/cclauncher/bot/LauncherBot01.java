package de.tontor.cclauncher.bot;

import javax.swing.UIManager;

public class LauncherBot01 { //LauncherBot01
	public static void main (String [] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
		
		LauncherBot02 cl = new LauncherBot02();
		if(cl.isLicenceValid()) {
			cl.loginRealLife();
			cl=null;
			
			int minTime;
			try {
				minTime = Integer.parseInt(args[0]);
			} catch(RuntimeException e) {
				minTime = 3;
			}
			new LauncherBot03(minTime).realLifeCheck();
		}
	}
}