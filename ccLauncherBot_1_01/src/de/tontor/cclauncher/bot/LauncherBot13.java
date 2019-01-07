package de.tontor.cclauncher.bot;

import java.io.IOException;
import java.util.Scanner;

public class LauncherBot13 { //LauncherBot13
	public LauncherBot13 (String sProc) throws IOException, InterruptedException {
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			Process process = new ProcessBuilder("cmd", "/c", "tasklist").start();
			Scanner scanner = new Scanner(process.getInputStream());
			while (scanner.hasNextLine()) {
				String sTemp = scanner.nextLine();
				if(sTemp.contains(sProc)) {
					String sPID = sTemp.substring( 29, 34 );
					Process proc = Runtime.getRuntime().exec("taskkill /PID " + sPID + " /F");
					proc.waitFor();
				}
			}
			scanner.close();
			process.waitFor();
		} else {
			throw new LauncherBot09("Nicht Windows");
		}
	}
}
