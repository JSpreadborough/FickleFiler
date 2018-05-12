/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficklefiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * This is what the user sees! All the Java FX stuff
 * buttons and things I think...
 * 
 * @author Justin
 */
public class View {
    
    //This should be a dynamic/relative path someday.
    String INITIAL_DIRECTORY_PATH = "C:\\Users\\Justin\\Pictures";
    
    private final Stage m_primaryStage;
    
    StackPane m_imagePreviewPane;
    
    //Directory Chooser
    private final DirectoryChooser m_directoryChooser;

    //Labels
    private final Label m_mainWindowLabel;
    
    //Buttons
    private final Button m_chooseDirectoryButton;
    private final Button m_quitButton;
    private final Button m_previousButton;
    private final Button m_nextButton;
    private final Button m_addCategoryButton;
    
    //Category Buttons List
    private final ArrayList<Button> m_categoryButtons;
    
    //Scenes
    private Scene m_openingScene;
    private Scene m_sortScene;
    
    //Boxes
    private final FlowPane m_categoryBox;
    
    public View(Stage primaryStage)  {
        this.m_primaryStage = primaryStage;
        m_primaryStage.setHeight(250);
        m_primaryStage.setWidth(250);
        m_primaryStage.setTitle("Fickle Filer");
        
        m_directoryChooser = new DirectoryChooser();
        m_directoryChooser.setTitle("Open Directory of Images");
        File initialDirectory = new File(INITIAL_DIRECTORY_PATH);
        if (initialDirectory.isDirectory()) {
            m_directoryChooser.setInitialDirectory(initialDirectory);
        }
        
        
        m_categoryButtons = new ArrayList<>();
        
        // Init Labels
        m_mainWindowLabel = new Label("Welcome to Fickle Filer!");
        
        // Init Buttons
        m_chooseDirectoryButton = new Button();
        m_chooseDirectoryButton.setText("Choose Directory");
        
        m_quitButton = new Button();
        m_quitButton.setText("Quit");
        
        m_previousButton = new Button();
        m_previousButton.setText("Previous");

        m_nextButton = new Button();
        m_nextButton.setText("Next");

        m_addCategoryButton = new Button();
        m_addCategoryButton.setText("+");

        // Boxes
        m_categoryBox = new FlowPane();
        
        // Create first scene user will see and set the stage.
        buildOpeningScene();
        m_primaryStage.show();
    }
    
    private void buildOpeningScene() {
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(0, 10, 10, 10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(m_quitButton, m_chooseDirectoryButton);
        
        GridPane gPane = new GridPane();
        gPane.setVgap(25);
        gPane.setHgap(5);
        gPane.setAlignment(Pos.CENTER);
        gPane.add(m_mainWindowLabel, 0, 0);
        gPane.add(buttonBox, 0, 1);
        
        m_openingScene = new Scene(gPane, 250, 250);
        m_primaryStage.setScene(m_openingScene);
    }
    
    public void buildSortScene(Image image) {
        
        if (image == null) {
            // Complaint Dialog "No Images in Chosen Directory", force new directory choice
        }
        m_imagePreviewPane = new StackPane();
        ImageView iv = new ImageView(image);
        iv.setFitWidth(600);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);
        m_imagePreviewPane.getChildren().add(iv);
        
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(0, 10, 10, 10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(m_quitButton, m_previousButton, m_nextButton);
        
        FlowPane buildCategoryBox = buildCategoryBox();
        
        AnchorPane rootSortPane = new AnchorPane(buttonBox,buildCategoryBox, m_imagePreviewPane);
        
        AnchorPane.setBottomAnchor(buttonBox, 2.5);
        AnchorPane.setLeftAnchor(buttonBox, 2.5);
        
        AnchorPane.setTopAnchor(m_imagePreviewPane, 2.5);
        AnchorPane.setLeftAnchor(m_imagePreviewPane, 2.5);
        
        AnchorPane.setRightAnchor(buildCategoryBox, 2.5);
        
        m_sortScene = new Scene(rootSortPane);
        m_primaryStage.setHeight(600);
        m_primaryStage.setWidth(800);
        m_primaryStage.setScene(m_sortScene);
    }
    
    public void updateImagePreviewPane(StackPane imagePreviewPane, Image image) {
        System.out.println("Update Image Preview Pane");
        ObservableList<Node> children = imagePreviewPane.getChildren();
        Node childNode = children.get(0);
        if (childNode instanceof ImageView) {
            System.out.println("Is ImageView");
            ImageView iv = (ImageView) childNode;
            iv.setImage(image);
            iv.setFitWidth(600);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);
            //Should only ever have one node. Remove it and replace with next image.
            childNode = iv;
        }
        
    } 
    
    private FlowPane buildCategoryBox() {
        m_categoryBox.setPrefWrapLength(50);
        m_categoryBox.setLayoutX(1);
        m_categoryBox.setLayoutY(0);
        m_categoryBox.setAlignment(Pos.TOP_RIGHT);
        
        Button landscapeCategoryButton = new Button("Landscape");
        Button peopleCategoryButton = new Button("People");
        Button animalCategoryButton = new Button("Animals");
        
        m_categoryButtons.add(m_addCategoryButton);
        
        m_categoryButtons.add(landscapeCategoryButton);
        m_categoryButtons.add(peopleCategoryButton);
        m_categoryButtons.add(animalCategoryButton);
        
        for (Button m_categoryButton : m_categoryButtons) {
            m_categoryBox.getChildren().add(m_categoryButton);
        }
        
        return m_categoryBox;
    }
    
    public void createNewCategory(String categoryName) {
        Button newCategoryButton = new Button(categoryName);
        m_categoryButtons.add(newCategoryButton);
        m_categoryBox.getChildren().addAll(newCategoryButton);
        
    }
    
    public String getCategoryName() {
        TextInputDialog textInputDialog = new TextInputDialog();
        
        Optional<String> showAndWait = textInputDialog.showAndWait();
        if (showAndWait.isPresent()) {
            return showAndWait.get();
        } else {
            // Add loopback until actual string provided.
            return "";
        }
        
    }
    
    public DirectoryChooser getDirectoryChooser() {
        return m_directoryChooser;
    }
    
    public Button getDirectoryButton() {
        return m_chooseDirectoryButton;
    }
    
    public Button getQuitButton() {
        return m_quitButton;
    }
    
    public Button getPreviousButton() {
        return m_previousButton;
    }
    
    public Button getNextButton() {
        return m_nextButton;
    }
    
    public Button getAddCategoryButton() {
        return m_addCategoryButton;
    }
    
    public Stage getPrimaryStage() {
        return m_primaryStage;
    }
}
