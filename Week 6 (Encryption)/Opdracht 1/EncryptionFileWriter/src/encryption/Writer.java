/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 *
 * @author Martijn
 */
public class Writer 
{

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException 
    {
   
        String path = "../Files";
        
        // Generate KeyPair
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        
        // Get the PrivateKey and PublicKey from the KeyPair
        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();
        
        byte publicKeyData[] = publicKey.getEncoded();
        byte privateKeyData[] = privateKey.getEncoded();
        
        // Create two FileOutputStreams
        FileOutputStream privateKeyOutput = null;
        FileOutputStream publicKeyOutput = null;
        
        try 
        {
            privateKeyOutput = new FileOutputStream(path + "/PrivateKey.turing");
            publicKeyOutput = new FileOutputStream(path + "/PublicKey.turing");
                    
            privateKeyOutput.write(privateKeyData);
            publicKeyOutput.write(publicKeyData);
        }
        
        catch (FileNotFoundException ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        finally 
        {
            if(privateKeyOutput != null && publicKeyOutput != null) 
            {
                privateKeyOutput.close();
                publicKeyOutput.close();   
            }
        }
    }
    
}
