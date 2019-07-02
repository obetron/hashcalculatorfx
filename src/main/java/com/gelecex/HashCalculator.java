package com.gelecex;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eren.basaran
 */
public class HashCalculator {
    protected String calculate(File fileToBeHashed, String algorithm) {
        try {
            ByteArrayOutputStream byteOutStream;
            try (FileInputStream inStream = new FileInputStream(fileToBeHashed)) {
                byteOutStream = new ByteArrayOutputStream();
                int read = 0;
                while((read = inStream.read()) != -1) {
                    byteOutStream.write(read);
                }
            }
            byteOutStream.flush();
            byteOutStream.close();
            byte[] fileOfBytes = byteOutStream.toByteArray();
            
            MessageDigest message = MessageDigest.getInstance(algorithm);
            byte[] digestBytes = message.digest(fileOfBytes);
            BASE64Encoder encoder = new BASE64Encoder(); //TODO: write your own
            return encoder.encode(digestBytes);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashCalculator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HashCalculator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(HashCalculator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }        
    }
}
