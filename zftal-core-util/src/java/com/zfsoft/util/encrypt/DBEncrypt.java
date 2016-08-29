package com.zfsoft.util.encrypt;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.crypto.Cipher;

import org.springframework.beans.factory.FactoryBean;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DBEncrypt implements FactoryBean {

	private Properties properties;

	public Object getObject() throws Exception {
		return getProperties();
	}

	public Class getObjectType() {
		return java.util.Properties.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties inProperties) {
		this.properties = inProperties;
		String originalUsername = properties.getProperty("user");
		String originalPassword = properties.getProperty("password");
		String originalJdbcUrl = properties.getProperty("jdbcUrl");
		if (originalUsername != null) {
			String newUsername = deEncryptUsername(originalUsername);
			properties.put("user", newUsername);
		}
		if (originalPassword != null) {
			String newPassword = deEncryptPassword(originalPassword);
			properties.put("password", newPassword);
		}
//		if (originalJdbcUrl != null) {
//			String newJdbcUrl = deEncryptJdbcUrl(originalJdbcUrl);
//			properties.put("jdbcUrl", newJdbcUrl);
//		}
	}

	private String deEncryptUsername(String originalUsername) {
		return dCode(originalUsername.getBytes());
	}
	
	private String deEncryptJdbcUrl(String originalJdbcUrl) {
		return dCode(originalJdbcUrl.getBytes());
	}

	private String deEncryptPassword(String originalPassword) {
		return dCode(originalPassword.getBytes());
	}

	public String eCode(String needEncrypt){
		byte result[] = null;
		try {
			Cipher enCipher = Cipher.getInstance("DES");
			javax.crypto.SecretKey key = Key.loadKey();
			enCipher.init(1, key);
			result = enCipher.doFinal(needEncrypt.getBytes());
			BASE64Encoder b = new BASE64Encoder();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			b.encode(result, bos);
			result = bos.toByteArray();
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return new String(result);
	}

	public String dCode(byte result[]){
		String s = null;
		try {
			Cipher deCipher = Cipher.getInstance("DES");
			deCipher.init(2, Key.loadKey());
			BASE64Decoder d = new BASE64Decoder();
			result = d.decodeBuffer(new String(result));
			byte strByte[] = deCipher.doFinal(result);
			s = new String(strByte);
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return s;
	}

	public static void main(String[] args){
    	String s = "jdbc:oracle:thin:@10.71.32.37:1521:devdb";
    	String s1 = "sjcj";
		//String s = "jdbc:oracle:thin:@10.71.32.62:1521:orcl";
    	DBEncrypt p = new DBEncrypt();
    	String afterE = p.eCode(s);
    	String afterE1 = p.eCode(s1);
    	System.out.println(afterE);
    	System.out.println(afterE1);
   // 	System.out.println(p.dCode("Kbs2u6NELkMD+i6RnR+aSRYguMAm9SijbqHt/ihv0WLV36Lvw81gIA==".getBytes()));
    }
}