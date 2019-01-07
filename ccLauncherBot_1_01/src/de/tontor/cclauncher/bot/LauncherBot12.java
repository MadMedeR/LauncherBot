package de.tontor.cclauncher.bot;

import java.util.Calendar;

public class LauncherBot12 { //LauncherBot12
	final String startTime, endTime, lifeTime;
	final long startMillis, endMillis;
	final int lifeTimeInMinutes;
	
	long actMillis;
	
	public LauncherBot12(int minTime){
		lifeTimeInMinutes = (int)(60*(minTime+0.25+Math.random()*0.5));
		lifeTime = String.format("%.1f", lifeTimeInMinutes/60.0) + "h";
		
		Calendar cal = Calendar.getInstance();
		startTime = String.format("%02d",cal.get(Calendar.HOUR_OF_DAY)) + ":"
				  + String.format("%02d",cal.get(Calendar.MINUTE));
		startMillis = cal.getTimeInMillis();
		
		cal.add(Calendar.MINUTE,lifeTimeInMinutes);
		endTime	  = String.format("%02d",cal.get(Calendar.HOUR_OF_DAY)) + ":"
				  + String.format("%02d",cal.get(Calendar.MINUTE));
		endMillis = cal.getTimeInMillis();
	}
	
	public String getDeltaTime() {
		actMillis = Calendar.getInstance().getTimeInMillis();

		int h=(int)((actMillis-startMillis)/1000/60/60);
		int m=(int)((actMillis-startMillis)/1000/60)-h*60;
		
		return h + "h " + String.format("%02d",m) + "min";
	}
}
