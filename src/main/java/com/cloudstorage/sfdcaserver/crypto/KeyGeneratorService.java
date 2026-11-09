package com.cloudstorage.sfdcaserver.crypto;

import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class KeyGeneratorService {

    public List<String> rsaKeyGenerator() throws NoSuchAlgorithmException {
        // Generate Key Pair
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();


        String publicKeyString = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        List<String> keyList = new ArrayList<>();
        keyList.add(publicKeyString);
        keyList.add(privateKeyString);

        return keyList;
    }
}
