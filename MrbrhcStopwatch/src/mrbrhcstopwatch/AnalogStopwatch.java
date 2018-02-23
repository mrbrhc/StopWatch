/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrbrhcstopwatch;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
//import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 *
 * @author Mason
 */
public class AnalogStopwatch {
    
    public Timeline timeline;
    public KeyFrame keyFrame;
    
    ImageView dialImageView;
    ImageView handImageView;
    
    public boolean resetSelected = false;
    public boolean startSelected = false;
    public boolean isRunning;   
    public double secondsElapsed = 0.0;
    public double tickTimeInSeconds = 1.0;
    public double angleDeltaPerSeconds= 6.0;
    
    public StackPane clockContainer;
    public GridPane uiDisplay = new GridPane();
    
    public int lapCounter = 0;
    public int totalLapCounter = 0;
    public int minCounter = 0;
    public int secCounter = 0;
    public int centiCounter = 0;
    public int minRecord =0;
    public int secRecord = 0;
    public int centiRecord = 0;
    
    public String displayString1 = "--:--.--";
    public String displayString2 = "--:--.--";
    public String displayString3 = "--:--.--";
    public String digitalTimeString = "--:--.--";
    
    public Label display1 = new Label(displayString1);
    public Label display2 = new Label(displayString2);
    public Label display3 = new Label(displayString3);
    public Text digitalTimer = new Text(digitalTimeString);
    
    
    private Image dialImage;
    private Image handImage;
    
    Button start = new Button("Start");
    Button record = new Button("Record");
    
    
    public AnalogStopwatch(){
        setupUI();
        setupTimer();
    }
    
    
    
    public void setupUI(){
        
        clockContainer = new StackPane();
        dialImageView = new ImageView();
        handImageView = new ImageView();
        
        dialImage = new Image(getClass().getResourceAsStream("clockface.png"));
        handImage = new Image(getClass().getResourceAsStream("hand.png"));
        
        dialImageView.setImage(dialImage);
        handImageView.setImage(handImage);
        
        HBox controls = new HBox();
        
//        Button start = new Button("Start");
//        Button record = new Button("Record");
        
        start.setMaxWidth(Double.MAX_VALUE);
        record.setMaxWidth(Double.MAX_VALUE);
        
        controls.setAlignment(Pos.BOTTOM_CENTER);
        controls.setSpacing(10);
        controls.setPadding(new Insets(25,25,25,25));
        controls.getChildren().addAll(record,start);
        
        VBox records = new VBox();
        
        records.setAlignment(Pos.CENTER);
        records.setSpacing(10);
        records.setPadding(new Insets(25,25,25,25));
        records.getChildren().addAll(display1,display2,display3);
        
        VBox timerContainer = new VBox();
        timerContainer.setAlignment(Pos.CENTER);
        timerContainer.setPadding(new Insets(25,25,25,25));
        timerContainer.getChildren().addAll(digitalTimer);
        
        record.setOnAction((ActionEvent event) -> {
          if(resetSelected){
            resetMethod();
          }
          else{ 
            recordMethod();
          }
        });
        
        start.setOnAction((ActionEvent event) -> {
            if(!startSelected){
                startMethod();
            }
            else{
                stopMethod();
            }
        });
        
        
        //add Digital display
        
        
        
        clockContainer.getChildren().addAll(dialImageView, handImageView);
        
        uiDisplay.add(timerContainer,0,0);
        uiDisplay.add(clockContainer,0,1);
        uiDisplay.add(controls,0,2);
        uiDisplay.add(records,0,3);
    }
    
    public void setupTimer(){
        keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds*1000),(ActionEvent) ->{
            update();
        });
        
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        
        
    }
    
    public void startMethod(){
      
        timeline.play();
        
        start.setText("Stop");
        record.setText("Record");
        startSelected = true;    
    }
    
    public void stopMethod(){
        
        start.setText("Start");
        record.setText("Reset");
        timeline.pause();
        startSelected = false;
        resetSelected = true;
    }
    
    public void recordMethod(){ //record should be total time - last time Newest record should replace oldest 
        if(startSelected == false){
            return;
        }
          
        
       minCounter = (int) Math.floor(secondsElapsed/60);
       secCounter = (int) secondsElapsed%60;
       centiCounter = (int) tickTimeInSeconds%100;
       
       minRecord = minCounter - minRecord;
       secRecord = secCounter - secRecord;
       centiRecord = centiCounter - centiRecord;
       
       totalLapCounter = lapCounter % 3 +1;
       
       ++lapCounter; 
       
        if(totalLapCounter == 1){
            display1.setText("Lap"+lapCounter + ": " + minRecord + ":" + secRecord + "." + centiRecord);
        }
        if(totalLapCounter==2){
            display2.setText("Lap"+lapCounter + ": " + minRecord + ":" + secRecord + "." + centiRecord);
        }
        if(totalLapCounter==3){
            display3.setText("Lap"+lapCounter + ": " + minRecord + ":" + secRecord + "." + centiRecord);
        }
        
        //record
        
    }
    
    public void resetMethod(){
        //reset
        handImageView.setRotate(0);
        timeline.stop();
        start.setText("Start");
        record.setText("Record");
        
        displayString1 = "--:--.--";
        displayString2 = "--:--.--";
        displayString3 = "--:--.--";
        digitalTimeString = "--:--.--";
                
        display1.setText(displayString1);
        display2.setText(displayString2);
        display3.setText(displayString3);
        
        startSelected = false;
        resetSelected = false;
        lapCounter = 0;
        totalLapCounter = 0;
        secondsElapsed = 0;
        minCounter =0;
        secCounter = 0;
        centiCounter = 0;
        digitalTimer.setText("--:--.--");
    }
    
    private void update(){
        secondsElapsed +=  tickTimeInSeconds;
        double rotation = secondsElapsed * angleDeltaPerSeconds;
        handImageView.setRotate(rotation);
        
        centiCounter = (int) tickTimeInSeconds%100;
        minCounter = (int) Math.floor(secondsElapsed/60);
        secCounter = (int) secondsElapsed%60;
        
        digitalTimer.setText(minCounter + ":" + secCounter + "." + centiCounter);
        
        
    }
    
    public void start(){
//        timeline.play();
    }
        
        
    public Double getWidth(){
        if(dialImage != null){
            return dialImage.getWidth();
        }
        else{
            return 0.0;
        }
    }
    
    public Double getHeight(){
        if(dialImage != null)return dialImage.getHeight();
        else return 0.0;
    }   
    
    public Parent getRootContainer(){
        return uiDisplay;
    }
    
    public void setTickTimeInSeconds(Double tickTimeInSeconds){
        this.tickTimeInSeconds = tickTimeInSeconds;
    }
    
}
