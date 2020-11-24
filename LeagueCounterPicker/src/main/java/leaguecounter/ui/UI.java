/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leaguecounter.ui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import leaguecounter.domain.DataHandler;

/**
 *
 * @author miikakoskela
 */
public class UI extends Application{
    
       public void start(Stage stage){
        stage.setTitle("League Counter Picker");
        BorderPane layout = new BorderPane();
        DataHandler handler = new DataHandler();        
        
        
        //Infotext on top
        HBox infoText = new HBox();
        Label startText = new Label("What the enemy has picked so far?");
        Label status = new Label("");
        infoText.getChildren().addAll(startText, status);
        infoText.setPadding(new Insets(10,10,10,10));
        infoText.setSpacing(5);
        
        
        //save features on bottom
        HBox saveInfo = new HBox();
        Label save = new Label("How did the match turnout?");
        Button victory = new Button("Victory!");
        Button defeat = new Button("Defeat..");
        saveInfo.getChildren().addAll(save, victory, defeat);
        saveInfo.setPadding(new Insets(10,10,10,10));
        saveInfo.setSpacing(20);
        
        layout.setBottom(saveInfo);
        saveInfo.setAlignment(Pos.CENTER);
        layout.setTop(infoText);
        

        
        //Enemy team picker in the middle
        GridPane enemyTeam = new GridPane();
        enemyTeam.setGridLinesVisible(true);
        enemyTeam.add(new Label("TOP"), 0, 0);
        enemyTeam.add(new Label("JUNGLE"), 1, 0);
        enemyTeam.add(new Label("MIDDLE"), 2, 0);
        enemyTeam.add(new Label("ADC"), 3, 0);
        enemyTeam.add(new Label("SUPPORT"), 4, 0);
        
        enemyTeam.setHalignment(enemyTeam.getChildren().get(1), HPos.CENTER);
        enemyTeam.setHalignment(enemyTeam.getChildren().get(2), HPos.CENTER);
        enemyTeam.setHalignment(enemyTeam.getChildren().get(3), HPos.CENTER);
        enemyTeam.setHalignment(enemyTeam.getChildren().get(4), HPos.CENTER);
        enemyTeam.setHalignment(enemyTeam.getChildren().get(5), HPos.CENTER);
        
        
        
        ChoiceBox<String> topPick = new ChoiceBox();
        ChoiceBox<String> jglPick = new ChoiceBox();
        ChoiceBox<String> midPick = new ChoiceBox();
        ChoiceBox<String> adcPick = new ChoiceBox();
        ChoiceBox<String> supPick = new ChoiceBox();
 
                
        
        enemyTeam.add(topPick, 0, 1);
        enemyTeam.add(jglPick, 1, 1);
        enemyTeam.add(midPick, 2, 1);
        enemyTeam.add(adcPick, 3, 1);
        enemyTeam.add(supPick, 4, 1);
        
        enemyTeam.add(new Label(""), 0,2);
        
        Label yourPick = new Label("Your pick?");
        ChoiceBox<String> championPicked = new ChoiceBox();

        
        enemyTeam.add(yourPick,0,3);
        enemyTeam.add(championPicked, 1, 3);
        
                
        
        enemyTeam.setPadding(new Insets(10,10,10,10));       
        
        enemyTeam.setHgap(5);
        enemyTeam.setVgap(5);
        layout.setCenter(enemyTeam);
        enemyTeam.setAlignment(Pos.TOP_CENTER);
        layout.setMinHeight(200);
        layout.setMinWidth(500);
        
        //Recommendation on the right
        VBox picker = new VBox();
        Button pick = new Button("Recommend a pick");
        Label yourRe = new Label("YOUR STATISTICS:");
        Label normRe = new Label("GAME STATISTICS:");
        Label yourRecommend = new Label("-----");
        Label normalRecommend = new Label("-----");
        picker.getChildren().addAll(pick, yourRe, yourRecommend, normRe, normalRecommend);

        picker.setPadding(new Insets(10,10,10,10));
        picker.setSpacing(5);
        
        layout.setRight(picker);
        
        ArrayList<String> champions = handler.listChampions();
        championPicked.getItems().addAll(champions);
        topPick.getItems().addAll(champions);
        jglPick.getItems().addAll(champions);
        midPick.getItems().addAll(champions);
        adcPick.getItems().addAll(champions);
        supPick.getItems().addAll(champions);
        topPick.getSelectionModel().clearAndSelect(0);
        jglPick.getSelectionModel().clearAndSelect(0);
        midPick.getSelectionModel().clearAndSelect(0);
        adcPick.getSelectionModel().clearAndSelect(0);
        supPick.getSelectionModel().clearAndSelect(0);
        championPicked.getSelectionModel().clearAndSelect(0);        
        
        
        
        victory.setOnAction((event) -> {
                    ArrayList<String> champs = new ArrayList<>();
                    champs.add(topPick.getValue());
                    champs.add(jglPick.getValue());
                    champs.add(midPick.getValue());
                    champs.add(adcPick.getValue());
                    champs.add(supPick.getValue());
            boolean re = handler.save(true,champs,championPicked.getValue());
            
            if (re){
                topPick.getSelectionModel().clearAndSelect(0);
                jglPick.getSelectionModel().clearAndSelect(0);
                midPick.getSelectionModel().clearAndSelect(0);
                adcPick.getSelectionModel().clearAndSelect(0);
                supPick.getSelectionModel().clearAndSelect(0); 
                championPicked.getSelectionModel().clearAndSelect(0);
                yourRecommend.setText("------");
                normalRecommend.setText("-----");
                status.setText("Match saved, gz for victory!");
            } else {
                status.setText("Set all champions before saving!");
            }
            
        });

        defeat.setOnAction((event) -> {
                    ArrayList<String> champs = new ArrayList<>();
                    champs.add(topPick.getValue());
                    champs.add(jglPick.getValue());
                    champs.add(midPick.getValue());
                    champs.add(adcPick.getValue());
                    champs.add(supPick.getValue());
            boolean re = handler.save(false,champs,championPicked.getValue());
            
            if(re){
                topPick.getSelectionModel().clearAndSelect(0);
                jglPick.getSelectionModel().clearAndSelect(0);
                midPick.getSelectionModel().clearAndSelect(0);
                adcPick.getSelectionModel().clearAndSelect(0);
                supPick.getSelectionModel().clearAndSelect(0); 
                yourRecommend.setText("------");
                normalRecommend.setText("-----");                
                championPicked.getSelectionModel().clearAndSelect(0);
                
                status.setText("Match saved, better luck next time!");                
            } else {
                status.setText("Set all champions before saving!");
            }
            
        });
        
        pick.setOnAction((event) -> {
            
            ArrayList<String> championList = new ArrayList<>();
            if(topPick.getValue() != "Champion"){
                championList.add(topPick.getValue());
            }
            if(jglPick.getValue() != "Champion"){
                championList.add(jglPick.getValue());
            }
            if(midPick.getValue() != "Champion"){
                championList.add(midPick.getValue());
            }
            if(adcPick.getValue() != "Champion"){
                championList.add(adcPick.getValue());
            }
            if(supPick.getValue() != "Champion"){
                championList.add(supPick.getValue());
            }
            
            String persPick = handler.getPersPick(championList);
            String normPick = handler.getNormPick(championList);
            
            yourRecommend.setText(persPick);
            normalRecommend.setText(normPick);
            
        });
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show(); 
    }
       
    public static void main(String[] args) {
        // TODO code application logic here
        launch(UI.class);
    }       
    
}
