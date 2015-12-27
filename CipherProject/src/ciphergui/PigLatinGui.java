package ciphergui;

import texttools.Crypter;

public class PigLatinGui extends BasicGui {
    
  public PigLatinGui () {
      super(850, 400, "Pig Latin");
  }
  
  @Override
  public String encryptText() {
      return Crypter.pigLatin(originalTextArea.getText(), true);
  }
  
  @Override
  public String decryptText() {
      return Crypter.pigLatin(originalTextArea.getText(), false);
  }

}