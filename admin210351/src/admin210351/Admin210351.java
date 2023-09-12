/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin210351;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Cpt.EB
 */
public class Admin210351 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      
        
        primaryStage.setTitle("LMS Admin Interface");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(grid,600,400);
        primaryStage.setScene(scene);
        
        Label usernameLabel = new Label("Username: ");
        Label passwordLabel = new Label("Password: ");
        TextField usernameValue = new TextField();
        PasswordField passwordValue = new PasswordField();
        Button loginButton = new Button("Log in");
        
        loginButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               if ((usernameValue.getText().equals("admin")) && (passwordValue.getText().equals("admin"))){
                   mainPage();
               } 
            }
        });
        
        grid.add(usernameLabel, 0, 0); // column 0 row 0
        grid.add(passwordLabel, 0, 1); // column 0 row 1
        grid.add(usernameValue, 1, 0);
        grid.add(passwordValue, 1,1);
        grid.add(loginButton, 0, 2);
        primaryStage.show();
    }
    
    private void mainPage(){
        
        Stage mainStage = new Stage();
        mainStage.setTitle("LMS Admin Controls");
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,800,600);
        mainStage.setScene(scene);
        
        Button viewAllGroups = new Button("View all Available Groups");
        viewAllGroups.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                viewAllGroupsForAdmin();
            }
        
        });
        
        grid.add(viewAllGroups,0,0);
        
        mainStage.show();
    }
    
    private void viewAllGroupsForAdmin(){
        Stage viewAllStage = new Stage();
        viewAllStage.setTitle("Available Groups");
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(5);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(grid);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setPannable(true);
        
        Scene scene = new Scene(scroll,800,600);
        viewAllStage.setScene(scene);
        
        Text groupID = new Text("Group ID");
        Text groupName = new Text("Group Name");
        
        grid.add(groupID, 0, 0);
        grid.add(groupName, 1, 0);
        
        
        //Get the groups from the service
        BufferedReader br = null;
        Text tempText;
        Hyperlink hyper =null;
        int row = 1;
        try{
            URL selectedURL = new URL("http://localhost:8080/rest210351/webresources/lmsserv/viewAllGroups");
            br = new BufferedReader(new InputStreamReader(selectedURL.openStream()));
            String temp;
            while((temp=br.readLine())!=null){
                //tempText = new Text(temp.substring(0,temp.indexOf(",")));
                hyper = new Hyperlink();
                String t=temp.substring(0,temp.indexOf(","));
                hyper.setText(t);
                hyper.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        viewGroupStudents(t);
                    }
                
                });
                grid.add(hyper,0,row);
                temp=temp.substring(temp.indexOf(",")+1);
                tempText = new Text(temp);
                grid.add(tempText,1,row);
                row++;
            }
            br.close();
        }catch (Exception ex){
            
        }
        
        viewAllStage.show();
    }

    private void viewGroupStudents(String groupID){
        Stage viewAllStage = new Stage();
        viewAllStage.setTitle("Students of group" + groupID);
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(5);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(grid);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        scroll.setPannable(true);
        
        Scene scene = new Scene(scroll,800,600);
        viewAllStage.setScene(scene);
        
        Text studentName = new Text("Students Names: ");
        grid.add(studentName, 0, 0);
        
        BufferedReader br=null;
        int row=1;
        Text tempText = null;
        
        try{
            URL selectedURL = new URL("http://localhost:8080/rest210351/webresources/lmsserv/viewGroupMembers/" + groupID);
            br=new BufferedReader(new InputStreamReader(selectedURL.openStream()));
            String temp;
            while((temp = br.readLine()) != null){
                tempText = new Text(temp);
                grid.add(tempText, 0, row);
                row++;
            }
            br.close();
        }catch(Exception ex){
            
        }
        
        
        viewAllStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
