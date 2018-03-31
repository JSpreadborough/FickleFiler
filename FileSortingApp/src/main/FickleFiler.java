/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ficklefiler.Controller;
import ficklefiler.Model;
import ficklefiler.View;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Justin
 */
public class FickleFiler extends Application{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Model model = new Model();
        View view = new View(primaryStage);
        Controller controller = new Controller(model, view);
    }
    
}
