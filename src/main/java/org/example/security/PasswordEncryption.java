package org.example.security;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadFactory;
import com.google.crypto.tink.aead.PredefinedAeadParameters;
import java.security.GeneralSecurityException;

public class PasswordEncryption {
  private KeysetHandle keysetHandle;

  {
    try {
      AeadConfig.register();
      keysetHandle = KeysetHandle.generateNew(PredefinedAeadParameters.AES128_GCM);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private String associatedData = "superStrongSecretKey";

  public byte[] getCiphertext(String password) {

    Aead aead;

    {
      try {
        aead = AeadFactory.getPrimitive(keysetHandle);
      } catch (GeneralSecurityException e) {
        throw new RuntimeException(e);
      }
    }

    byte[] ciphertext;

    {
      try {
        ciphertext = aead.encrypt(password.getBytes(), associatedData.getBytes());
      } catch (GeneralSecurityException e) {
        throw new RuntimeException(e);
      }
    }
    return ciphertext;
  }
}
