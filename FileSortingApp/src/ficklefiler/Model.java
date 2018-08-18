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
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Justin
 */
public class Model {
    
    private final ArrayList<File> m_fileList;
    
    private ListIterator m_fileListIterator;
    
    // Supported image extensions.
    private final String imgExts;
    
    public Model(){
        m_fileList = new ArrayList<>();
        m_fileListIterator = m_fileList.listIterator();
        //Can't have spaces! Very literal!
        imgExts = "glob:**.{png,jpg,gif}";
    }
    
    public void filterForImages(File startingDirectory){
        m_fileList.addAll(Arrays.asList(startingDirectory.listFiles()));
        System.out.println("m_fileList size = " + m_fileList.size());
        
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(imgExts);
        Iterator<File> iterator = m_fileList.iterator();
        while (iterator.hasNext()) {
            File nextFile = iterator.next();
            if (!matcher.matches(nextFile.toPath())) {
                System.out.println("removing invalid file = " + nextFile.getName());
                iterator.remove();
            }
        }
    }
    
    public Image getNextImage() {
        if (m_fileListIterator.hasNext()) {
            File file = (File) m_fileListIterator.next();
            try {
                if (file != null) {
                    URL imageURL = file.toURI().toURL();
                    System.out.println("imageURL = " + imageURL.toString());
                    return new Image(imageURL.toString());
                }
            } catch (MalformedURLException ex) {
                System.out.println("MalformedURL Exception on next");
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
           
        return null;
        
    }
    
    public Image getPreviousImage() {
        if (m_fileListIterator.hasPrevious()) {
            File file = (File) m_fileListIterator.previous();

            try {
                if (file != null) {
                    URL imageURL = file.toURI().toURL();
                    System.out.println("imageURL = " + imageURL.toString());
                    return new Image(imageURL.toString());
                }
            } catch (MalformedURLException ex) {
                System.out.println("MalformedURL Exception on next");
                Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }
    
}
