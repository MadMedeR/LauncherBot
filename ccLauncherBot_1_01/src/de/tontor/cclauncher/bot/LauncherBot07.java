package de.tontor.cclauncher.bot;

public class LauncherBot07 { //LauncherBot07
	private static final char[] S = {'Z', 'B', 'Y', 'z', 'S', 'V', '!', 'O', 'I', 'o', 'N', 'M', 'q', 'n', 'P', 'd', 's', 'Q', '5', 'L', 'f', 'x', 'U', 'm', 't', '-', '3', 'E', 'e', 'R', 'b', 'p', 'c', '8', 'j', 'A', '#', 'g', '4', '%', 'K', 'r', '1', '$', 'i', 'l', 'W', 'X', 'w', 'y', '&', 'D', 'T', 'k', '6', 'F', '0', '9', 'a', 'u', '2', 'H', 'v', 'C', 'h', 'G', 'J', '7'};
	private static final String d = "5RCFTmW4IBTMNKyI#a%iS0Si0Lzj$lyw";
	
	public static String decodeLicence(String licence){
		String result="";
		boolean endIsNear = false;
		int l = S.length;
		for (int i=0;i<32;i++) {
			int k=0;
			for (;k<l;k++) if (S[k] == licence.charAt(i)) break;
			int c=0;
			for (;c<l;c++) if (S[c] == d.charAt(i)) break;
			int n = (k+c+1)%l;
			if (S[n]>=95) {
				result += S[n];
				endIsNear = true;
			}
			if (endIsNear && S[n]<95) break;
		}
		return result;
	}
}
