package tictactoe.src;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 *   and define the related variable/constructor/getter.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
   CROSS("X", "tictactoe/src/images/catjam-cat.gif"),   // displayName, imageFilename
   NOUGHT("O", "tictactoe/src/images/dog-pov.gif"),
   NO_SEED(" ", null);

   // Private variables
   private String displayName;
   private Image img = null;

   // Constructor (must be private)
   private Seed(String name, String imageFilename) {
      this.displayName = name;

      if (imageFilename != null) {
         File imgFile = new File(imageFilename);
         if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imageFilename);
            img = icon.getImage();
         } else {
            System.err.println("Couldn't find file " + imageFilename);
         }
      }
   }

   // Public getters
   public String getDisplayName() {
      return displayName;
   }
   public Image getImage() {
      return img;
   }
}