/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht_2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import static sun.security.util.Password.readPassword;

/**
 * FXML Controller class
 *
 * @author Thom
 */
public class Opdracht2Controller implements Initializable {

    static PBEKeySpec pbeKeySpec;
    static PBEParameterSpec pbeParameterSpec;
    static SecretKeyFactory secretKeyFactory;
    static byte[] salt;
    static int iterations = 25;
    
    @FXML Button buttonEncryptFile;
    @FXML Button buttonDecryptFile;
    
    @FXML TextField passwordEncryptInput;
    @FXML TextField passwordDecryptInput;
    @FXML TextField fileName;
    
    @FXML TextArea MessageAreaInput;
    @FXML TextArea MessageAreaOutput;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML public void Encrypt() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        // Een salt van 8 bytes (256 bits) creëren
        salt = new byte[8];
        new SecureRandom().nextBytes(salt);
        
        // Maak InputStream van wachtwoord
        InputStream inputPassword = new ByteArrayInputStream(passwordEncryptInput.getText().getBytes("UTF-8"));

        // Paramters specificeren.
        pbeParameterSpec = new PBEParameterSpec(salt, iterations);
       
        pbeKeySpec = new PBEKeySpec(readPassword(inputPassword));
        
        // Een sleutel maken met behulp van het wachtwoord.
        secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        // De encryptiemethode initializeren. 
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
        
        // Het bericht converteren naar een encrypted bericht.
        byte[] ciphertext = cipher.doFinal(MessageAreaInput.getText().getBytes());
        
         // Een random filenaam genereren met de UUID klasse.
        String uuid = UUID.randomUUID().toString();
        
        // Filenaam was te lang, dus wat korter gemaakt.
        File file = new File(String.format(uuid.substring(0, uuid.indexOf("-"))));
        file.createNewFile();

        //Writing the salt and the encrypted message.
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(salt);
        objectOutputStream.writeObject(ciphertext);
        objectOutputStream.close();
    }
 
    @FXML public void Decrypt() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException 
    {
        String result = "";
        InputStream fileNameInput = new ByteArrayInputStream(fileName.getText().getBytes("UTF-8"));
        result = new BufferedReader(new InputStreamReader(fileNameInput)).readLine();
        
        // File ophalen en de salt en het geëncrypte bericht lezen.
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(result));
        salt = (byte[]) objectInputStream.readObject();
        byte[] encryptedMessage = (byte[]) objectInputStream.readObject();
        objectInputStream.close();
        
        // Parameters voor de decryptie specificeren.
        pbeParameterSpec = new PBEParameterSpec(salt, iterations);
        
        InputStream password = new ByteArrayInputStream(passwordDecryptInput.getText().getBytes("UTF-8"));
        
        pbeKeySpec = new PBEKeySpec(readPassword(password));

         // De key genereren op basis van het wachtwoord.
        secretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

        // Klaarmaken voor het decrypten.
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);

        // Het bericht daadwerkelijk decrypten.
        byte[] message = cipher.doFinal(encryptedMessage);
        
        MessageAreaOutput.setText(new String(message, "UTF-8"));
    }

}
