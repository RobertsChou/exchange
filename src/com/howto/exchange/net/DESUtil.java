package com.howto.exchange.net;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtil {
	   
	 // 默认密钥--字节数必须是8的倍数
    private String DESKey = "des_key_8";
    private static byte[] IV = { (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x90,
        (byte) 0xAB, (byte) 0xCD, (byte) 0xEF };
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DESUtil des = new DESUtil("szicity8");
	      System.out.println( "加密：" + des.encrypt("YWJjMTIz5Lit5Zu95Zu95a625Zyw55CGJSQ="));
	      System.out.println("解码："+des.decrypt("QSqbk/PvzzaX+6OYTbCihLnr+IMp4ToEFT4zpVB7BmnNhJuTp5QawoM8YlGr9v4HVjfeKvgJTdc="));

	}
	 /**
     * @description 设置密钥
     * @author guoxiang.ruan
     * @param key
     * @time Aug 23, 2012
     */
    public DESUtil(String key)
    {
        this.setDESKey(key);
    }
	 /**
     * 以下是加密方法
     */
    /**
     * @description des加密String加密输出String
     * @author guoxiang.ruan
     * @param input
     * @return
     * @time Aug 23, 2012
     */
    public String encrypt(String input)
    {
        String result = "";
        try
        {
            // result = base64Encode(desEncrypt(input.getBytes()));
            // 执行加密操作
            byte data[] = desEncrypt(input.getBytes("utf-8"));
            // 对加密后的密文进行base64编码，以保存格式，确保密文能被成功解密
            result = Base64.encode(data,"utf-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error initializing DES2IOSUtil class. Cause:加密 ", e);
        }
        return result;
    }
    
    /**
     * @description byte[]加密成byte[]
     * @author guoxiang.ruan
     * @param plainText
     * @return
     * @throws Exception
     * @time Aug 23, 2012
     */
    public byte[] desEncrypt(byte[] plainText) throws Exception
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec(IV);
            DESKeySpec dks = new DESKeySpec(DESKey.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte data[] = plainText;
            byte encryptedData[] = cipher.doFinal(data);
            return encryptedData;
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("Error initializing DES2IOSUtil class. Cause:desEncrypt ", e);
        }
    }
	public String getDESKey() {
		return DESKey;
	}
	public void setDESKey(String dESKey) {
		DESKey = dESKey;
	}
    
	 /**
     * 以下是解密方法
     */
    /**
     * @description des解密String加密输出String
     * @author guoxiang.ruan
     * @param input
     * @return
     * @time Aug 23, 2012
     */
    public String decrypt(String input)
    {
        String result = "";
        try
        {
            // 先对解密串进行base64解码，以还原格式，确保成功解密
            byte data[] = Base64.decode(input.getBytes("utf-8"));
            // 执行解密操作
            result = new String(desDecrypt(data), "utf-8");
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error initializing DES2IOSUtil class. Cause:解密 ", e);
        }
        return result;
    }

    /**
     * @description byte[]解密成byte[]
     * @author guoxiang.ruan
     * @param plainText
     * @return
     * @throws Exception
     * @time Aug 23, 2012
     */
    public byte[] desDecrypt(byte[] plainText) throws Exception
    {
        try
        {
            IvParameterSpec iv = new IvParameterSpec(IV);
            DESKeySpec dks = new DESKeySpec(DESKey.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte data[] = plainText;
            byte decryptedData[] = cipher.doFinal(data);
            return decryptedData;
        }
        catch (RuntimeException e)
        {
            throw new RuntimeException("Error initializing DES2IOSUtil class. Cause:desDecrypt ", e);
        }
    }
}
