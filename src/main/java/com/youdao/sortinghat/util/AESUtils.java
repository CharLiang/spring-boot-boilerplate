package com.youdao.sortinghat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

/**
 * Created by wn on 2018/7/6.
 */
public class AESUtils {
    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    // 数据加密-秘钥与iv
    public static final int ENCODE_BITNUM = 128;
    public static final String ENCODE_SECRETKEY = "8O1tw%-LtblnZiq#rbB3sqxy)eOIP3";
    public static final String ENCODE_IV = "ILnR1kDZyP[sx(txiHPd_CcgYg67rk";

    /**
     * 预设Initialization Vector，为16 Bits的0
     */
    private static final IvParameterSpec DEFAULT_IV = new IvParameterSpec(
            new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
    /**
     * 加密算法使用AES
     */
    private static final String ALGORITHM = "AES";
    /**
     * AES使用CBC模式与PKCS5Padding
     */
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * 取得AES加解密的秘钥
     */
    private Key key;
    /**
     * AES CBC模式使用的Initialization Vector
     */
    private IvParameterSpec iv;
    /**
     * Cipher
     */
    private Cipher cipher;

    /**
     * 使用128 Bits的AES秘钥(计算任意长度秘钥的MD5)和预设IV
     *
     * @param key 传入任意长度的AES秘钥
     */
    public AESUtils(final String key) {
        this(key, 128);
    }

    /**
     * 使用128 Bits或是256 Bits的AES秘钥(计算任意长度密钥的MD5或是SHA256)和预设IV
     *
     * @param key 传入任意長度的AES秘钥
     * @param bit 传入AES秘钥长度，数值可以是128、256 (Bits)
     */
    public AESUtils(final String key, final int bit) {
        this(key, bit, null);
    }

    /**
     * 使用128 Bits或是256 Bits的AES密钥(计算任意长度密钥的MD5或是SHA256)，用MD5计算IV值
     *
     * @param key 传入任意长度的AES密钥
     * @param bit 传入AES密钥长度，数值可以是128、256 (Bits)
     * @param iv  传入任意长度的IV字串
     */
    public AESUtils(final String key, final int bit, final String iv) {
        if (bit == 256) {
            this.key = new SecretKeySpec(getHash("SHA-256", key), ALGORITHM);
        } else {
            this.key = new SecretKeySpec(getHash("MD5", key), ALGORITHM);
        }
        if (iv != null) {
            this.iv = new IvParameterSpec(getHash("MD5", iv));
        } else {
            this.iv = DEFAULT_IV;
        }
        init();
    }

    /**
     * 取得字串的Hash值
     *
     * @param algorithm 传入Hash算法名称
     * @param text      传入要hash的字串
     * @return 返回对应的hash值
     */
    private static byte[] getHash(final String algorithm, final String text) {
        try {
            return getHash(algorithm, text.getBytes("UTF-8"));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 获取Hash值
     *
     * @param algorithm 传入Hash算法
     * @param data      传入被散列的data字节数组
     * @return 返回hash值
     */
    private static byte[] getHash(final String algorithm, final byte[] data) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return digest.digest();
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String encrypt(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return encrypt(object.toString());
        } catch (final Exception ex) {
            logger.error("encrypt error...{}", ex);
        }

        return null;
    }

    /**
     * 加密字符串
     *
     * @param str 要加密的字符串
     * @return 返回密文
     */
    public String encrypt(final String str) {
        try {
            return encrypt(str.getBytes("UTF-8"));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 加密字节数组
     *
     * @param data 传入要加密的字节数组
     * @return 返回密文
     */
    public String encrypt(byte[] data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            final byte[] encryptData = cipher.doFinal(data);
            return Base64.getUrlEncoder().encodeToString(encryptData);
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 解密字符串
     *
     * @param str 传入要解密的字符串
     * @return 返回解密后的明文
     */
    public String decrypt(final String str) {
        try {
            return decrypt(Base64.getUrlDecoder().decode(str));
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 解密字节数组
     *
     * @param data 要解密的字节数组
     * @return 返回解密后的明文
     */
    public String decrypt(final byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decryptData = cipher.doFinal(data);
            return new String(decryptData, "UTF-8");
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 生成秘钥
     *
     * @return
     */
    public static String genSecretKey() {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-#$%()_[]".toCharArray();
        Random rand = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            stringBuilder.append(chars[rand.nextInt(chars.length)]);
        }

        return stringBuilder.toString();
    }
}