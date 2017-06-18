/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptionfiledecrypter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author Martijn
 */
public class Decrypter 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Verify();
    }
    
    /**
     * Function to get the public key
     * @param fileUrl
     * @return 
     */
    private static PublicKey GetPublicKeyFromFile(String fileUrl)
    {
        
        FileInputStream fileInput;
        PublicKey foundKey = null;
        
        try
        {
            File publicKeyFile = new File(fileUrl);
            fileInput = new FileInputStream(fileUrl);
            byte[] encodedPublicKey = new byte[ (int) publicKeyFile.length() ];
            fileInput.read(encodedPublicKey);
            fileInput.close();
                                
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(encodedPublicKey));
            foundKey = publicKey;
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
     * Function to verify the signature
     */
    private static void Verify()
    {
        // Path
        String path = "../Files";
        
        try
        {
            RandomAccessFile randomAccesFile = new RandomAccessFile(path + "/INPUT(SignedByMartijn).EXT", "r");

            int sigLength = randomAccesFile.readInt();

            byte[] signatureBytes = new byte[sigLength];
            randomAccesFile.read(signatureBytes);

            int msgLength = (int) randomAccesFile.length() - 4 - sigLength;

            byte[] message = new byte[msgLength];
            randomAccesFile.read(message);
            randomAccesFile.close();

            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(GetPublicKeyFromFile(path + "/PublicKey.turing"));
            sig.update(message);
            
            if(sig.verify(signatureBytes)) 
            {
                randomAccesFile = new RandomAccessFile(path + "/INPUT.txt", "rw");
                randomAccesFile.write(message);
                randomAccesFile.close();
                System.out.println("Succes! :-)");
            }
            
            else 
            {
                System.out.println("It failed .. :-(");
            }
        }
        
        catch(IOException | InvalidKeyException | NoSuchAlgorithmException | SignatureException ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
