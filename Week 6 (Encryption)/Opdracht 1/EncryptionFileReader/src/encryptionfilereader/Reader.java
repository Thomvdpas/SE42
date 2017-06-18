/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptionfilereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author Martijn
 */
public class Reader 
{

    String path = "../Files";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {        
        System.out.println("Signing .." + "\n");      
        Sign("Martijn");
    }
    
    /**
     * Function to get the private key from the file
     * @param fileUrl
     * @return PrivateKey
     */
    private static PrivateKey GetPrivateKeyFromFile(String fileUrl)
    {
        
        FileInputStream fileInput;
        PrivateKey foundKey = null;
        
        try
        {
            File privateKeyFile = new File(fileUrl);
            fileInput = new FileInputStream(fileUrl);
            byte[] encodedPrivateKey = new byte[ (int) privateKeyFile.length() ];
            fileInput.read(encodedPrivateKey);
            fileInput.close();
                                
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKey));
            foundKey = privateKey;
        }
        
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
        }
        
        finally
        {
            return foundKey;
        }
        
    }
    
    /**
     * Function to sign the file
     * @param signer 
     */
    public static void Sign(String signer)
    {

        // Path
        String path = "../Files";
        
        try 
        {
            // Get a signature instance of the SHA1withRA algo
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(GetPrivateKeyFromFile(path + "/PrivateKey.turing"));
            signature.update(ReadRandomFile(path + "/INPUT.txt"));

            byte[] signatureBytes = signature.sign(); // Get the signature in a byte[]
            int signatureLength = signatureBytes.length; // Get the length of the signature
 
            String signedMessagePath = path + "/INPUT(SignedBy" + signer + ").EXT";
            
            RandomAccessFile raf = new RandomAccessFile(signedMessagePath, "rw");
            
            raf.writeInt(signatureLength); // Write the signature length
            raf.write(signatureBytes); // Write the signature bytes
            raf.write(ReadRandomFile(path + "/INPUT.txt")); // Write the contents of the INPUT.txt file in a byte[]
            
            raf.close();
        }
        
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Function to read the random file (INPUT.TXT)
     * This function converts the file to a byte[] that can be used
     * to write to the SignedBy(..) file
     * @param fileUrl
     * @return byte[] of the INPUT.txt file
     */
    private static byte[] ReadRandomFile(String fileUrl)
    {
        File data = new File(fileUrl);
        byte[] dataArray = new byte[(int) data.length()];
        InputStream inputStream;

        try
        {
            inputStream = new FileInputStream(data);

            if (inputStream != null)
            {
                inputStream.close();   
            }  
        }
        
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
                          
        return dataArray;
    }
    
}
