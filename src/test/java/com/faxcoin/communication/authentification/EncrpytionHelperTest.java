package com.faxcoin.communication.authentification;

import com.faxcoin.communication.Message;
import com.faxcoin.communication.messenger.NamedMessengerAddress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EncrpytionHelperTest {
  @Test
  public void testVerifySignature() {
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBDyBta2l+QM25DoMUokDBnpT8w14/uz2JZjrxSAlP7uf3TB2DlFX4j4ibXtSY5rNaqpPYD26MXgEeLIqUdRq7FMajVyktewHBeJgXbDIuPZBe0CYwgAdiNSKKMv9VFyhE4DUb19Ca8vYw3NwJNUm8GgOj1CxiAId9KWQuP05cyQIDAQAB";
    String signature = "uBaXAzaiX75NBP+mRkkp5RuBfdnT9LXD1crOYVeFe5+KF0se7uRoo5+ZrwJE9JtxrJJF3EZNQq7kWE/ZRw/KSANmXBnAQMILrl8lao4+FN5mljyMbDZI56DlUcCPCK6qUO1ivmhjMiiX9oXoALBmKEsW1gRNV07HXXwAEI1wVfQ=";
    //String signature = "UJcTC6tPEhf9IWSFgtYK4vXxx24LbUlZp8zl0PylinNfemrXNaHuNzRPsYSZw9zsThooy7WZsRGZ67nU63NdzBFmZhwY8iSURfi6jYkfZEgOQ8FYRsnd0Oitah5GUoAD8gt6DpmytnRgenwusnjiq/O6KA1WAyPh5WSXt1Od3Uc=";
    Message msg = new Message(
        "cuck",
        new NamedMessengerAddress("receiver"),
        new NamedMessengerAddress(publicKey),
        signature
    );
    assertTrue(EncrpytionHelper.verifySignature(msg));
  }
}