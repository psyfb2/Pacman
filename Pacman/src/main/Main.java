package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * 5. create class diagram
 * 6. refactor code by introducing design patterns etc
 * 7. update class diagram
 * 10. create video
 */
public class Main extends Application {

	public static final int WIDTH = 1225;
	public static final int HEIGHT = 700;
	
    @Override
    public void start(Stage theStage) throws Exception{
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainMenu.fxml"));
        theStage.setTitle( "Pacman" );
        Scene mainMenu = new Scene( root );
        mainMenu.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        
        theStage.setScene( mainMenu );
        theStage.show();
    
    }

    public static void main(String[] args) {
        launch(args);
    }
}