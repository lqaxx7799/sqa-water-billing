package web.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtils {
	public static int[] getPreviousPeriod(int month, int year) {
		if (month != 1) {
			return new int[] { month - 1, year };
		}
		return new int[] { 12, year - 1 };
	}
	
	public static String generateSHA1(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return convertByteToHex(hash);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static String convertByteToHex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

}
