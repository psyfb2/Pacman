package main;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	public static final String RESPATH = new File("").getAbsolutePath() + File.separator + "Pacman" + File.separator + "resources";
	public static final String IMAGEPATH = new File("").getAbsolutePath() + File.separator + "Pacman" + File.separator + "resources" + File.separator + "images" + File.separator;
	public static final String MAPPATH = new File("").getAbsolutePath() + File.separator + "Pacman" + File.separator + "resources" + File.separator + "maps" + File.separator;
	public static final String SCOREPATH = new File("").getAbsolutePath() + File.separator + "Pacman" + File.separator + "resources" + File.separator + "scores" + File.separator;
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