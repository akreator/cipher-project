package ciphergui;

import texttools.Crypter;

/**
 *
 * @author Audrey
 */
public class BinaryGui extends BasicGui {
    
    public BinaryGui() {
        super(850, 400, "Binary Code");
    }
    
    @Override
    public String encryptText() {
        return Crypter.toBinary(originalTextArea.getText());
    }
    
    @Override
    public String decryptText() {
        return Crypter.fromBinary(originalTextArea.getText());
    }
    
}
