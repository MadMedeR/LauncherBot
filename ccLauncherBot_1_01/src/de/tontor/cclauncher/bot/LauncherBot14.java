package de.tontor.cclauncher.bot;

import java.awt.Color;

public class LauncherBot14 { //LauncherBot14
	Color color;
	
	public LauncherBot14 (int color) {
		this.color = new Color (color);
	}
	
	public LauncherBot14 (Color color) {
		this.color = color;
	}
	
	public int getRGB() { // Methode macht das gleiche wie: new Color(int r, int g, int b, int a).getRGB()
		int a = color.getAlpha();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		return a*256*256*256 + r*256*256 + g*256 + b;
	}
	
	public String toString(){
		int c = color.getRGB();
		int n=4;
		int[] channel = new int[n];
		channel[0] = (c & 0xff000000) >>> 24; //Alpha
		channel[1] = (c & 0xff0000)    >> 16; //Red
		channel[2] = (c & 0xff00)      >>  8; //Green
		channel[3] = (c & 0xff)				; //Blue
		
		String[] format = new String[n+1];
		for (int i = 0; i < n; i++) {
			format[i] = String.format("%3d",channel[i]);
		}
		format[n] = String.format("%10d",c);
		return "[a=" + format[0] + ",r=" + format[1] + ",g=" + format[2] + ",b=" + format[3] + "]"+ format[4];
	}
}
