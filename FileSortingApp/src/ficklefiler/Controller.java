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
    
    //This should be a dynamic/relative path someday.
    String INITIAL_DIRECTORY_PATH = "\\Pictures\\TestDirectory";
    
    File defaultDirectory;
    
    Model m_model;
    View m_view;

    public Controller(Model model, View view) {
        defaultDirectory = new File(INITIAL_DIRECTORY_PATH);
        
        this.m_model = model;
        this.m_view = view;
        
        setQuitButtonAction();
        setDirectoryButtonAction();
        setNextButtonAction();
        setPreviousButtonAction();
        setAddNewCategoryButtonAction();
    }

    public void setQuitButtonAction(){
        m_view.getQuitButton().setOnAction(
            event -> {
                try {
                    Platform.exit();
                } catch (Exception e) {
                    // Do nothing, killing app already.  
                }
            }
        );
    }

    public void setDirectoryButtonAction(){
        m_view.getDirectoryButton().setOnAction(
            event -> {
                try {
                    //The top level directory to sort.
                    File showDialog = m_view.getDirectoryChooser().showDialog(m_view.getPrimaryStage());

                    if (showDialog == null) {
                        if (defaultDirectory.isDirectory()) {
                            m_model.filterForImages(defaultDirectory);
                        }
                    } else if (showDialog.isDirectory()) {
                        m_model.filterForImages(showDialog);
                    } else {
                        //Unable to get a directory; display directory chooser again?
                        m_view.getDirectoryChooser().showDialog(m_view.getPrimaryStage());
                    }
                    
                    //Send list of images to viewer to iterate over with next and previous buttons.
                    m_view.buildSortScene(m_model.getNextImage());
                } catch (NullPointerException e) {
                    //Directory not selected.
                }
            }
        );
    }
    
    public void setNextButtonAction() {
        m_view.getNextButton().setOnAction(
            event -> {
                try {
                    Image nextImage = m_model.getNextImage();
                    // If image null do nothing.
                    if (nextImage != null) {
                        m_view.updateImagePreviewPane(m_view.m_imagePreviewPane, nextImage);
                    } else {
                        System.out.println("Next Image Null");
                    }
                } catch (Exception e) {
                    
                }
            });
    }

    public void setPreviousButtonAction() {
        m_view.getPreviousButton().setOnAction(
            event -> {
                try {
                    Image previousImage = m_model.getPreviousImage();
                    if (previousImage != null) {
                        m_view.updateImagePreviewPane(m_view.m_imagePreviewPane, previousImage);
                    } else {
                        System.out.println("Previous Image Null");
                    }
                    
                } catch (Exception e) {
                    
                }
            });
    }
    
    public void setAddNewCategoryButtonAction() {
        m_view.getAddCategoryButton().setOnAction(
                event -> {
                    try {
                        m_view.createNewCategory(m_view.getCategoryName());
                    } catch (Exception e) {

                    }
                });
    }

}
