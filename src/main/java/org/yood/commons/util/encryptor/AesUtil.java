package org.yood.commons.util.encryptor;


public class AesUtil {
    private static final String IV = "00000000000000000000000000000000";
    private static final String SALT = "00000000000000000000000000000000";
    private static final int KEY_SIZE = 128;
    private static final int ITERATION_COUNT = 10000;
    private static final String PASSPHRASE = "aesalgoisbestbes";

    public static String Encrypt(String PLAIN_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION_COUNT);
        String encrypt = util.encrypt(SALT, IV, PASSPHRASE, PLAIN_TEXT);
        //   System.out.println(encrypt);
        return encrypt;
    }


    public static String Decrypt(String CIPHER_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION_COUNT);
        String decrypt = util.decrypt(SALT, IV, PASSPHRASE, CIPHER_TEXT);
        return decrypt;
    }

    public static void main(String args[]) {
        String PLAIN_TEXT = "101";
        String CIPHER_TEXT = Encrypt(PLAIN_TEXT);
        CIPHER_TEXT = "wRVrc4DZU2d660vBpauTSA==";
        Decrypt(CIPHER_TEXT);
    }
}
