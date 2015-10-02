package org.yood.commons.util.encryptor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class RSAUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtilsTest.class);

    @Test
    public void testGenerateKeyPair() throws Exception {
        KeyPair keyPair = RSAUtils.generateKeyPair();
        assertNotNull(keyPair);

    }

    @Test
    public void testEncrypt() throws Exception {
        KeyPair keyPair = RSAUtils.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        LOGGER.info("Public Key = {}, Private Key = {}", publicKey, privateKey);

        String encryptData = "中华人民共和国";
        LOGGER.info("Encrypt data = {}", encryptData);
        byte[] encryptedData = RSAUtils.encrypt(encryptData, publicKey);
        String decryptData = RSAUtils.decrypt(encryptedData, privateKey);
        LOGGER.info("Decrypt data = {}", decryptData);
        assertEquals(encryptData,decryptData);
    }

    @Test
    public void testDecrypt() throws Exception {
        testEncrypt();
    }
}