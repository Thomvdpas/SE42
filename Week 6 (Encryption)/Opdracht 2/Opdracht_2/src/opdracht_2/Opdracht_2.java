/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opdracht_2;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Thom
 */
public class Opdracht_2 extends Application {
               
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);  
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Opdracht2.fxml"));

        Scene scene = new Scene(root);
        
        // Set Icon
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Opdracht 2 - Encryptie");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
}
