package email;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecryptionTest {
    private Encryption encryption = EncryptionTest.createEncryption();

    @Test
    public void decrypt() throws Exception {
        String decryption = encryption.decrypt(FileUtils.loadFile("EncryptedEmail.txt"));
        assertEquals("Dear consultant,\n\nWe are facing an unprecedented challenge in Christmas Town.\n\nThe systems that keep our magical operations running smoothly are outdated, fragile, and in dire need of modernization. \nWe urgently require your expertise to ensure Christmas happens this year.\nOur town is located within a mountain circlet at the North Pole, surrounded by high peaks and protected by an advanced communication and shield system to hide it from the outside world.\n\nYou have been selected for your exceptional skills and dedication. \nPlease report to the North Pole immediately. \n\nEnclosed are your travel details and a non-disclosure agreement that you must sign upon arrival.\nOur dwarf friends from the security will receive and escort you in as soon as you check security.\nIn the following days, you will receive bracelets to be able to pass through the magic shield.\n\nTime is of the essence.\nYou must arrive before the beginning of December to be able to acclimate yourself with all the systems.\n\nWe are counting on you to help save Christmas.\n\nSincerely,\n\nSanta Claus \uD83C\uDF85", decryption);
    }
}
