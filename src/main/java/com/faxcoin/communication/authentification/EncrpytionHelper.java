package com.faxcoin.communication.authentification;

import com.faxcoin.communication.Message;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncrpytionHelper {
  private EncrpytionHelper() {}

  public static boolean verifySignature(Message msg) {
    try {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(msg.getSender().getAddress()));
      PublicKey pubKey = kf.generatePublic(keySpecX509);
      Signature publicSignature = Signature.getInstance("SHA256withRSA");
      publicSignature.initVerify(pubKey);
      publicSignature.update(msg.getContent().getBytes(StandardCharsets.UTF_8));
      byte[] signatureBytes = Base64.getDecoder().decode(msg.getSigning());
      return publicSignature.verify(signatureBytes);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException | SignatureException | InvalidKeyException e) {
      e.printStackTrace();
    }
    return false;
  }
  }
