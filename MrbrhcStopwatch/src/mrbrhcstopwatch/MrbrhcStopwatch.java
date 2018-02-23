/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrbrhcstopwatch;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Mason
 */
public class MrbrhcStopwatch extends Application {
        private String appName = "mrbrhcStopwatch";
    
    @Override
    public void start(Stage primaryStage){
        
        AnalogStopwatch analog = new AnalogStopwatch();
        
        Scene scene = new Scene(analog.getRootContainer(),
                                300,
                                600
        );
        
        primaryStage.setTitle(appName);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        analog.setTickTimeInSeconds(1.0);
        
        analog.start();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
