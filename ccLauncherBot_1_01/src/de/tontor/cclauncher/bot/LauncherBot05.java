package de.tontor.cclauncher.bot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class LauncherBot05 extends Robot{ //LauncherBot05
	public BufferedImage screenshot;
	private final Rectangle screensize;
	private static final int PANEL_COLOR = new Color(244,244,244).getRGB();
	private Rectangle activePanel;
	private Point[] toPress = new Point[5];
	
	// Konstruktor
//=====================================================================================
	public LauncherBot05() throws AWTException {
		super();
		screensize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
	// Methode für Login und Checker
//=====================================================================================
	public boolean searchPanel(Dimension d) {
		//keyPress(KeyEvent.VK_CONTROL); //Nur bei Bildschirmschoner, Funktioniert nicht 100%ig
		//keyRelease(KeyEvent.VK_CONTROL); //Nur bei Bildschirmschoner, Funktioniert nicht 100%ig
		try {Thread.sleep(100);} catch (InterruptedException e) {}
		screenshot = createScreenCapture(screensize);
		activePanel = null;
		int x1,y1,x2,y2;
		final int x_max=screensize.width-d.width,
				  y_max=screensize.height-d.height;
		
		for (y1=0 ; y1<y_max ; y1++)
			inner:
			for(x1=0 ; x1<x_max ; x1++) {
				for(x2=x1,y2=y1 ; x2<x1+d.width	; x2++) { //links->rechts
					if (PANEL_COLOR != screenshot.getRGB(x2, y2)) continue inner;
				}					
				for(x2--		; y2<y1+d.height ; y2++) { //oben->unten
					if (PANEL_COLOR != screenshot.getRGB(x2, y2)) continue inner;
				}
				for(y2--		; x2>=x1			; x2--) { //rechts->links
					if (PANEL_COLOR != screenshot.getRGB(x2, y2)) continue inner;
				}
				for(x2++		; y2>=y1			; y2--) { //unten->oben
					if (PANEL_COLOR != screenshot.getRGB(x2, y2)) continue inner;
				}
				activePanel = new Rectangle(x1,y1,d.width,d.height);
				setAutoDelay(100);
				mouseMove(x1+5, y1+5);
				mousePress(InputEvent.BUTTON1_DOWN_MASK);
				mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				return true;
			}
		return false;
	}

	private void randDelay(int z) {
		int d = (int)(Math.random()*z+z);
		setAutoDelay(d);
	}
	
	// Methoden nur für Login
//=====================================================================================
	public void login(String user, String pword) {
		try {Thread.sleep(1000);} catch (Exception e) {}
		write(user);
		keyPress(KeyEvent.VK_TAB);
		keyRelease(KeyEvent.VK_TAB);
		try {Thread.sleep(1000);} catch (Exception e) {}
		write(pword);
	}
	
	public void pressEnter() {
		keyPress(KeyEvent.VK_ENTER);
		keyRelease(KeyEvent.VK_ENTER);
	}
	
	private void write(String input) {
		randDelay(100);
		String[] arry = input.toUpperCase().split("");
		for(String value: arry) {
			int i = value.charAt(0);
			keyPress(i);
			keyRelease(i);
		}
	}
	
	// Methoden nur für Checker
//=====================================================================================
	public String getButtonsToPress() {
		LauncherBot11 boldNumbers = new LauncherBot11(screenshot.getSubimage(activePanel.x+29,activePanel.y+17,50,13));
		//boldNumbers.saveImage("boldNumbers_color");
		int[] boldDigits = boldNumbers.makeItBlackAndWhite(64).getDigits();
		
		LauncherBot11 buttonsScan = new LauncherBot11(screenshot.getSubimage(activePanel.x+136,activePanel.y+82,77,112));
		//buttonsScan.saveImage("buttons_color");
		Point[] buttons = buttonsScan.makeItBlackAndWhite(180).getButtons();
		
		for(int i=0;i<4;i++) {
			toPress[i] = new Point(
					activePanel.x + (int)buttons[boldDigits[i]].getX(),
					activePanel.y + (int)buttons[boldDigits[i]].getY()
			);
		}
		toPress[4] = new Point(activePanel.x + 310 , activePanel.y + 290);
		String code = "";
		for (int i:boldDigits) {
			code += i + " ";
		}
		return code;
	}
	
	public void pushTheButtons() {
		int x_origin = MouseInfo.getPointerInfo().getLocation().x;
		int y_origin = MouseInfo.getPointerInfo().getLocation().y;
		
		for(Point button : toPress) {
			randDelay(10);
			slowMouseMover(x_origin,y_origin,button.x,button.y);
			mousePress(InputEvent.BUTTON1_DOWN_MASK);
			mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			try { Thread.sleep(500); } catch (Exception e) {}
			x_origin = button.x;
			y_origin = button.y;
		}
		try { Thread.sleep(1000); } catch (Exception e) {}
	}
	
	private void slowMouseMover(int x0, int y0, int x1, int y1) {
		int dx = x1-x0;
		int dy = y1-y0;
		int s = 10;
		if (dx+dy==0) return;
		for (int i=1;i<=s;i++) {
			double ds = i/(double)s;
			int x = (int)Math.round(x0+dx*ds);
			int y = (int)Math.round(y0+dy*ds);
			mouseMove(x,y);
		}
	}
	
	public void closeCcl(){
		int x_origin = MouseInfo.getPointerInfo().getLocation().x;
		int y_origin = MouseInfo.getPointerInfo().getLocation().y;
		randDelay(100);
		slowMouseMover(x_origin,y_origin,activePanel.x+657,activePanel.y-161);
		mousePress(InputEvent.BUTTON1_DOWN_MASK);
		mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		try { Thread.sleep(1500); } catch (InterruptedException e) {}
		keyPress(KeyEvent.VK_ENTER);
		keyRelease(KeyEvent.VK_ENTER);
	}
}