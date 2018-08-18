/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficklefiler;

import java.io.File;
import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 *
 * @author Justin
 */
public final class Controller {
    
    Model m_model;
    View m_view;

    
    // Add stack
    public Controller(Model model, View view) {
        
        this.m_model = model;
        this.m_view = view;
        
        setupQuitButtonAction();
        setupDirectoryButtonAction();
        setupNextButtonAction();
        setupPreviousButtonAction();
        setupAddNewCategoryButtonAction();
    }

    public void setupQuitButtonAction() {
        m_view.assignQuitButtonAction(event -> {
            try {
                Platform.exit();
            } catch (Exception e) {
                // Do nothing, killing app already.  
            }
        });
    }

    public void setupDirectoryButtonAction(){
        m_view.assignDirectoryButtonAction(event -> {
            try {
                // Use a listener for the directory.
                File choosenDirectoryPath = m_view.getChoosenDirectory();

                if (choosenDirectoryPath.isDirectory()) {
                    m_model.filterForImages(choosenDirectoryPath);
                }
                System.out.println("Hello?");
                m_view.buildSortScene(m_model.getNextImage());
                System.out.println("Whats going on?");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void buildSortScene() {
        //Send next image to viewer to iterate over with next and previous buttons.
        m_view.buildSortScene(m_model.getNextImage());
    }
    
    public void setupNextButtonAction() {
        m_view.assignNextButtonAction(event -> {
            try {
                m_view.updateImagePreview(m_model.getNextImage());
            } catch (Exception e) {

            }
        });
    }

    public void setupPreviousButtonAction() {
        m_view.assignPreviousButtonAction(event -> {
            try {
                m_view.updateImagePreview(m_model.getPreviousImage());
            } catch (Exception e) {

            }
        });
    }
    
    public void setupAddNewCategoryButtonAction() {
        m_view.assignAddCategoryButtonAction(event -> {
            try {
                m_view.createNewCategory();
            } catch (Exception e) {

            }
        });
    }

}
