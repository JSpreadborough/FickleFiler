/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficklefiler;

import java.io.File;
import java.util.Stack;
import javafx.scene.image.Image;

/**
 *
 * @author Justin
 */
public class DesignatedImage {
    // The actual image that was categorized/designated to a folder.
    private Image m_image;
    
    private Stack<File> m_locations;
    
    
    
    DesignatedImage(Image image, File designatedLocation) {
        m_image = image;  
        m_locations.add(designatedLocation);
    }
}
