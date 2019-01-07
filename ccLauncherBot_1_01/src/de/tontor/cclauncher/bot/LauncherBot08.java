package de.tontor.cclauncher.bot;

public class LauncherBot08 {
	private int counter,iterations,sec;
	private String task;
	
	public LauncherBot08(int lifeTimeInMinutes, int pauseInSec, String task) {
		iterations = lifeTimeInMinutes*60/pauseInSec;
		sec = pauseInSec;
		this.task = task;
		counter = 0;
	}
	
	public int count() {
		counter++;
		System.out.print(counter + ", ");
		try {Thread.sleep(1000*sec);} catch (Exception e) {}
		if (counter>=iterations) return 0;
		if (counter>=iterations-10) return 1;
		return -1;
	}
	
	public String[] getInfo() {
		return new String[] {
			task,
			"Timeout steht bevor.",
			"Program wird geschlossen in: " + (iterations-counter)
		};
	}
}