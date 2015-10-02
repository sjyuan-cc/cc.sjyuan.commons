package org.yood.commons.util.encryptor;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static junit.framework.Assert.assertEquals;

public class RSAUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtilsTest.class);

    private static KeyPair keyPair;
    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    @BeforeClass
    public static void setup() {
        keyPair = RSAUtils.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        LOGGER.info("Public Key = {}, Private Key = {}", publicKey, privateKey);
    }

    @Test
    public void testGenerateKeyPair() throws Exception {
        Assert.assertNotNull(keyPair);
        Assert.assertNotNull(publicKey);
        Assert.assertNotNull(privateKey);
    }

    @Test
    public void testGetPublicKey() throws Exception {
        String publicKeyString = RSAUtils.getPublicKey(RSAUtilsTest.publicKey);
        assertEquals(publicKey, RSAUtils.getPublicKey(publicKeyString));
    }

    @Test
    public void testGetPublicKey1() throws Exception {
        testGetPublicKey();
    }

    @Test
    public void testGetPrivateKey() throws Exception {
        String privateKeyString = RSAUtils.getPrivateKey(RSAUtilsTest.privateKey);
        assertEquals(privateKey, RSAUtils.getPrivateKey(privateKeyString));
    }

    @Test
    public void testGetPrivateKey1() throws Exception {
        testGetPrivateKey();
    }

    @Test
    public void testEncryptAsByteArray() throws Exception {
        String encryptData = "中华人民共和国";
        LOGGER.info("Encrypt data = {}", encryptData);
        byte[] encryptedData = RSAUtils.encryptAsByteArray(encryptData, publicKey);
        String decryptData = RSAUtils.decrypt(encryptedData, privateKey);
        LOGGER.info("Decrypt data = {}", decryptData);
        assertEquals(encryptData, decryptData);
    }

    @Test
    public void testEncryptAsString() throws Exception {
        String encryptData = "中华人民共和国";
        LOGGER.info("Encrypt data = {}", encryptData);
        String encryptedData = RSAUtils.encryptAsString(encryptData, publicKey);
        String decryptData = RSAUtils.decrypt(encryptedData, privateKey);
        LOGGER.info("Decrypt data = {}", decryptData);
        assertEquals(encryptData, decryptData);
    }

    @Test
    public void testDecryptForByteArray() throws Exception {
        testEncryptAsByteArray();
    }

    @Test
    public void testDecryptForString() throws Exception {
        testEncryptAsString();
    }
}