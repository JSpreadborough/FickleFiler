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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Justin
 */
public class Model {
    
    private final ArrayList<File> m_fileList;
    
    private int m_currentImageIndex = 0;
    
    // Supported image extensions.
    private final String imgExts;
    
    public Model(){
        m_fileList = new ArrayList<>(50);
        //Can't have spaces! Very literal!
        imgExts = "glob:**.{png,jpg,gif}";
    }
    
    public void filterForImages(File startingDirectory){
        m_fileList.addAll(Arrays.asList(startingDirectory.listFiles()));
        System.out.println("m_fileList size !! = " + m_fileList.size());
        
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(imgExts);
        Iterator<File> iterator = m_fileList.iterator();
        while (iterator.hasNext()) {
            File nextFile = iterator.next();
            if (!matcher.matches(nextFile.toPath())) {
                System.out.println("removing invalid file = " + nextFile.getName());
                iterator.remove();
            }
        }
        m_currentImageIndex = 0;
    }
    
    public Image getNextImage() {
        m_currentImageIndex++;
        System.out.println("m_currentImageIndex = " + m_currentImageIndex);
        if (m_currentImageIndex == m_fileList.size()) {
            //Should cause loop around behaviour for next image retrieval at end of list.
            m_currentImageIndex = 0;
        }
        
        File file = m_fileList.get(m_currentImageIndex);
        
        try {
            URL imageURL = file.toURI().toURL();
            System.out.println("imageURL = " + imageURL.toString());
            return new Image(imageURL.toString());
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURL Exception on next");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Image getPreviousImage() {
        m_currentImageIndex--;
        System.out.println("m_currentImageIndex = " + m_currentImageIndex);
        if (m_currentImageIndex < 0) {
            //Should cause loop around behaviour for previous image retrieval at begining of list.
            m_currentImageIndex = m_fileList.size() - 1;
        }
        
        File file = m_fileList.get(m_currentImageIndex);
        
        try {
            URL imageURL = file.toURI().toURL();
            System.out.println("imageURL = " + imageURL.toString());
            return new Image(imageURL.toString());
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURL Exception on next");
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
