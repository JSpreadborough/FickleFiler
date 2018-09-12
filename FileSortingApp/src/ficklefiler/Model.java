/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficklefiler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Justin
 */
public class Model {
    
    private Stack m_actionHistory;
    
    private final ArrayList<File> m_fileList;
    
    private int currentImageIndex;
    
    // Supported image extensions.
    private final String imgExts;
    
    public Model(){
        m_fileList = new ArrayList<>();
        // filter needs to happen before init of iterator.
        currentImageIndex = 0;
        //Can't have spaces! Very literal!
        imgExts = "glob:**.{png,jpg,gif}";
    }
    
    public void populateImages(File startingDirectory){
        m_fileList.addAll(Arrays.asList(startingDirectory.listFiles()));
        System.out.println("m_fileList size = " + m_fileList.size());
    }
    
    public Image getNextImage() {
        System.out.println("File List Size: " + m_fileList.size());
        currentImageIndex++;
        if (currentImageIndex == m_fileList.size()) {
            currentImageIndex = 0;
        }
            File nextFile = verifyImage((File) m_fileList.get(currentImageIndex), true);
            
            try {
                if (nextFile != null) {
                    
                    

                    URL imageURL = nextFile.toURI().toURL();
                    System.out.println("imageURL = " + imageURL.toString());
                    return new Image(imageURL.toString());
                }
            } catch (MalformedURLException ex) {
                System.out.println("MalformedURL Exception on next");
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
        
    }
    
    public Image getPreviousImage() {
        
        currentImageIndex--;
        if (currentImageIndex < 0) {
            currentImageIndex = m_fileList.size() - 1;
        }
        
        File previousFile = verifyImage((File) m_fileList.get(currentImageIndex), false);

        try {
            if (previousFile != null) {

                verifyImage(previousFile, false);

                URL imageURL = previousFile.toURI().toURL();
                System.out.println("imageURL = " + imageURL.toString());
                return new Image(imageURL.toString());
            }
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURL Exception on previous");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private File verifyImage(File file, boolean isNext) {
        if (file == null) {
            return null;
        }
        
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(imgExts);
        if (!matcher.matches(file.toPath())) {
            System.out.println("removing invalid file = " + file.getName());
            m_fileList.remove(currentImageIndex);
            if (isNext) {
                currentImageIndex--;
            } else {
                // If not next it is previous
                currentImageIndex++;
            }
            // Retrieved an invalid file format.
            return null;
        } else {
            return file;
        }
    }
}
