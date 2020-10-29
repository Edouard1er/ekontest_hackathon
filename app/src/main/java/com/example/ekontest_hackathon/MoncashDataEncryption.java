package com.example.ekontest_hackathon;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MoncashDataEncryption {
    String apiKey;
    String encryptText;
    String decryptText;

    public MoncashDataEncryption(String apiKey) {
        this.apiKey = apiKey;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean encryptData(String plaintText) throws NoSuchProviderException, NoSuchAlgorithmException {
        try {
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
            byte[] bytes = Base64.getDecoder().decode(apiKey);
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(bytes);
            PublicKey key = KeyFactory.getInstance("RSA", "BC").generatePublic(encodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
            SecureRandom random = new SecureRandom();
            cipher.init(Cipher.ENCRYPT_MODE, key, random);
            byte[] cipherText = cipher.doFinal(plaintText.getBytes());
            encryptText = String.valueOf(Base64.getEncoder().encode(cipherText));
        } catch (InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return true;
    }

}