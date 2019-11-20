package edu.caece.app.config;

import org.springframework.stereotype.Component;

@Component
public class Hash {

	/* Retorna un hash MD5 a partir de un texto */
	public static String md5(String txt) {
		return getHash(txt, "MD5");
	}

	/* Retorna un hash SHA1 a partir de un texto */
	public static String sha1(String txt) {
		return getHash(txt, "SHA1");
	}

	/* Retorna un hash a partir de un tipo y un texto */
	private static String getHash(String txt, String hashType) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
			byte[] array = md.digest(txt.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println("method getHash :: " + e.getMessage());
		}
		return null;
	}
}
