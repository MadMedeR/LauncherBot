package de.tontor.cclauncher.bot;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class LauncherBot11 extends BufferedImage{ //LauncherBot11
	private static final LauncherBot11[]  PATTERNS_BOLD   = setPatternsBold();
	private static final LauncherBot11[]  PATTERNS_BUTTON = setPatternsButton();
	private static final Point[] POINTS_TO_PRESS = setPointsToPress();
	private final int width,height;
	private final int[] rgbArray;
	private int equivalent=-1;
	
	// Konstruktoren
//=========================================================================================
	public LauncherBot11(BufferedImage bI) {
		super(bI.getWidth(),bI.getHeight(),bI.getType());
		this.width		= getWidth();
		this.height		= getHeight();
		this.rgbArray	= bI.getRGB(0,  0, width, height, new int[width*height], 0, width);
		setRGB(0,  0, width, height, rgbArray, 0, width);
	}
	
	public LauncherBot11(int width, int height, int[] rgbArray, int offset, int scansize) {
		super(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		this.width		= getWidth();
		this.height		= getHeight();
		setRGB(0,  0, width, height, rgbArray, offset, scansize);
		this.rgbArray   = getRGB(0,  0, width, height, new int[width*height], 0, width);
	}
	
	public LauncherBot11(int width, int height, int[] rgbArray) {
		this(width, height, rgbArray, 0, width);
	}
	
	private LauncherBot11() {
		this(new BufferedImage(1,1,6));
	}
	
	// private static Methods
	// werden alle nur einmal ausgeführt zur Initialisierung
//=========================================================================================
	private static LauncherBot11[] setPatternsBold() {
		URL bold = new LauncherBot11().getClass().getResource("images/numbers_bold_home.png");
		try {
			LauncherBot11 img = new LauncherBot11(ImageIO.read(bold));
			return img.makeItGrey().makeItBlackAndWhite(64).extractDigits(10);
		} catch (Exception e) {
			return new LauncherBot11[10];
		}
	}
	
	private static LauncherBot11[] setPatternsButton() {
		URL buttons = new LauncherBot11().getClass().getResource("images/buttons_home.png");
		try {
			LauncherBot11 img = new LauncherBot11(ImageIO.read(buttons));
			return img.makeItGrey().makeItBlackAndWhite(180).extractButtons(true);
		} catch (Exception e) {
			return new LauncherBot11[10];
		}
	}
	
	private static Point[] setPointsToPress() {
		Point[] result = new Point[10];
		for(int i=0, y=94 ; y<=181 ; y+=29)
			for (int x=147 ; x<=201 ; x+=27) {
				result[i] = new Point(x,y);
				i++;
				if (i>=10) break;
			}
		return result;
	}
	
	// public Instance Methods 
//=========================================================================================
	public LauncherBot11 makeItGrey() {
		int a = width*height;
		int[] greyRgb = new int[a];
		
		for (int i=0 ; i<a ; i++) {
			Color c = new Color(rgbArray[i]);
			int gray = (int) (0.299 * c.getRed() + 0.587 *c.getGreen() + 0.114*c.getBlue());
			Color g = new Color(gray, gray, gray);
			greyRgb[i]=g.getRGB();
		}
		return new LauncherBot11(width, height, greyRgb);
	}

	public LauncherBot11 makeItBlackAndWhite(int grey) {
		int a = width*height;
		int[] blackRgb = new int[a];
		Arrays.fill(blackRgb, -1);		  //white = new Color(255,255,255).getRGB()
		int black = Color.BLACK.getRGB(); //black = new Color(0,0,0).getRGB()
		int greyRef = new Color(grey,grey,grey).getRGB();

		int[] greyRgbArray = this.makeItGrey().rgbArray;
		
		for (int i=0 ; i<a ; i++) if (greyRgbArray[i]<=greyRef) blackRgb[i]=black;
		
		return new LauncherBot11(width, height, blackRgb);
	}
	
	public int[] getDigits() {
		LauncherBot11[] digits = extractDigits(4);
		int[] result = new int[digits.length];
		for(int i=0;i<digits.length;i++) {
			result[i]=digits[i].equivalent;
		}
		return result;
	}
	
	public Point[] getButtons() {
		LauncherBot11[] buttons = extractButtons(false);
		Point[] result = new Point[buttons.length];
		for(int i=0;i<buttons.length;i++) {
			result[buttons[i].equivalent] = POINTS_TO_PRESS[i];
		}
		return result;
	}
	
	// Methods only for debugging
//=========================================================================================
	
	public boolean saveImage(String filenameWithoutExtension) {
		String folder = "tmp_images/";
		Path tmpImage = Paths.get(folder);
		if (!Files.exists(tmpImage))
		try {
			Files.createDirectory(tmpImage);
			System.out.println("Der Ordner " + tmpImage + " wurde erzeugt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String filename = folder + filenameWithoutExtension + "_" + timestamp + ".png";
		
		File outputfile = new File(filename);

		for (int i=0;i<10;i++)
			try {
				ImageIO.write(this, "png", outputfile);
				System.out.println(outputfile + " erzeugt");
				return true;
			} catch (IOException e) {}
		return false;
	}
	
	public void printArrayAsARGB() {
		int h = ((int)Math.log10(height))+1;
		int w = ((int)Math.log10(width))+1;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				String xStr = String.format("%"+w+"d",x);
				String yStr = String.format("%"+h+"d",y);
				System.out.print("["+xStr+"|"+yStr+"]: " + new LauncherBot14(rgbArray[x+y*width]).toString() + " ");
			}
			System.out.println();
		}
	}

	// private Instance Methods 
//=========================================================================================
	private LauncherBot11[] extractDigits(int n) {
		LauncherBot11[] digits = new LauncherBot11[n];
		boolean isWhiteColumn;
		for (int x=0, counter=0, activeColumn=0 ; x<width ; x++) {
			isWhiteColumn=true;
			for (int y=0;y<height;y++) if (rgbArray[x+y*width]!=-1) { isWhiteColumn=false; break; }
			if (isWhiteColumn) {
				//System.out.println(x + ". Spalte ist weiß");
				int delta = x-activeColumn;
				if (delta==0) activeColumn++;
				else {
					digits[counter] = new LauncherBot11(delta,height,rgbArray,activeColumn,width);
					if (n==10) {
						digits[counter].equivalent = counter;
						//digits[counter].saveImage("boldnumber_pattern_" + counter);
					} else  {
						digits[counter].validate(PATTERNS_BOLD);
						//digits[counter].saveImage("boldnumber_scan_" + counter);
					}
					counter++;
					activeColumn=x+1;
				}
			}
		}
		return digits;
	}
	
	private LauncherBot11[] extractButtons(boolean isModel) {	
	LauncherBot11[] buttons = new LauncherBot11[10];
	for(int i=0, y=2 ; y<=89 ; y+=29)
		for(int x=2 ; x<=56 ; x+=27) {
			buttons[i] = new LauncherBot11(getSubimage(x,y,19,21));
			if (isModel) {
				buttons[i].equivalent = i;
				//buttons[i].saveImage("button_model_" + i);
			} else {
				buttons[i].validate(PATTERNS_BUTTON);
				//buttons[i].saveImage("button_scan_" + i);
			}
			i++;
			if(i>=10)break;
		}
	return buttons;
	}
	
	private boolean validate(LauncherBot11[] vorlagen) {
		double percent=0;
		for (LauncherBot11 p: vorlagen) {
			int length = (rgbArray.length < p.rgbArray.length) ? rgbArray.length : p.rgbArray.length;
			int parity=0;
			for(int i=0;i<length;i++) {
				if (p.rgbArray[i]==rgbArray[i]) parity++;
			}
			if (parity*100.0/length > percent) {
				percent = parity*100.0/length;
				equivalent = p.equivalent;
			}
		}
		//System.out.println("Die Zahl ist zu " + percent + "% eine " + equivalent);
		return percent>75;
	}
}