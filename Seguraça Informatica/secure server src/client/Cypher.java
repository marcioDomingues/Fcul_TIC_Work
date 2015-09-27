package client;

import java.io.*;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class Cypher {

	/////////////////////////////////////////
	//key generation
	/////////////////////////////////////////
	/**
	 * generate a AES Key from a give string //password 
	 * @param String PassWord
	 * @return SecretKey
	 * @throws Throwable 
	 */
	public static SecretKey generateKey() throws Throwable {

		KeyGenerator kg = KeyGenerator.getInstance("AES");
	    kg.init(128);
	    SecretKey key = kg.generateKey();
		
		return key;
	}

	/**
	 * put a key in a given FileOutputStream 
	 * @param SecretKey
	 * @param outputStream  
	 */
	public static void saveKeyToFile( SecretKey key, String file, PublicKey pKey ) throws Throwable  {
		
		File f = new File(file);
		
		Cipher c = Cipher.getInstance("RSA");
		c.init(Cipher.ENCRYPT_MODE, pKey);
	    
		FileOutputStream fos = new FileOutputStream(f);
		CipherOutputStream cos = new CipherOutputStream(fos, c);
	    
	    byte[] b = key.getEncoded();
	    cos.write(b, 0, b.length);

		cos.close();
		fos.close();
	}

	/**
	 * gets a key from a given FileInputStream
	 * @param InputStream
	 * @return SecretKey  
	 */
	public static SecretKey getKeyFromFile( String file, PrivateKey pKey ) throws Throwable {
		
		File f = new File(file);
		
		Cipher c = Cipher.getInstance("RSA");
	    c.init(Cipher.DECRYPT_MODE, pKey);
		
		FileInputStream fis = new FileInputStream(f);
	    CipherInputStream cis = new CipherInputStream(fis, c);
	    
	    byte[] b = new byte[16];
	    cis.read(b);
	    cis.close();
	    fis.close();
	    
		SecretKey key2 = new SecretKeySpec(b, "AES");
		return key2;
	}
	
	public static KeyPair getKeyPairFromKeystore( String user ) throws Exception{
		FileInputStream fis = new FileInputStream("client/"+user+"/"+user+".ks");

	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    String password = user+"01";

	    keystore.load(fis, password.toCharArray());

	    String alias = user;
	    
	    Key key = keystore.getKey(alias, password.toCharArray());
	    PublicKey publicKey = null;
	    KeyPair kp = null;
	    if (key instanceof PrivateKey) {
	      // Get certificate of public key
	      java.security.cert.Certificate cert = keystore.getCertificate(alias);

	      // Get public key
	      publicKey = cert.getPublicKey();
	      
	      // Return a key pair
	      kp = new KeyPair(publicKey, (PrivateKey) key);
	    }
	    return kp;
	}
	
	public static PublicKey getPkFromCertificate( String user, String certOwner ) throws Exception{
		FileInputStream fis = new FileInputStream("client/"+user+"/"+user+".ks");
	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    String password = user+"01";
	    
	    keystore.load(fis, password.toCharArray());
	    
	    java.security.cert.Certificate cert = keystore.getCertificate(certOwner);
	    
	    return cert.getPublicKey();
	}

	/////////////////////////////////////////
	//symmetric cipher
	/////////////////////////////////////////
	public static void encrypt(SecretKey key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecrypt( key, Cipher.ENCRYPT_MODE, is, os);
	}

	public static void decrypt(SecretKey key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecrypt( key, Cipher.DECRYPT_MODE, is, os);
	}

	/**
	 * performs cipher and decipher depending on the mode
	 * @param SecretKey stored in file
	 * @param mode for cipher or decipher
	 * @param InputStream
	 * @param OutputStream   
	 */
	public static void encryptOrDecrypt(SecretKey key, int mode, InputStream is, OutputStream os) throws Throwable {

		Cipher cipher = Cipher.getInstance("AES"); // DES/ECB/PKCS5Padding for SunJCE

		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			doCopy(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, key);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}

	/**
	 * Auxiliary function
	 * to change for socket streams 
	 */
	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[1024];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
	}
}