package semester.projekt.core;

/*
*
*   IMPORTS!
*
*/

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class Picture implements ActionListener {

    private BufferedImage image;               // the rasterized image
    private JFrame frame;                      // on-screen view
    private String filename;                   // name of file
    private boolean isOriginUpperLeft = true;  // location of origin
    private final int width, height;           // width and height

    
    public Picture(int width, int height) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be nonnegative");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height must be nonnegative");
        }
        this.width = width;
        this.height = height;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // set to TYPE_INT_ARGB to support transparency
    }

    public Picture(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }

        width = picture.width();
        height = picture.height();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        filename = picture.filename;
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                image.setRGB(col, row, picture.get(col, row).getRGB());
            }
        }
    }

    
    public Picture(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }

        this.filename = filename;
        try {
            // try to read from file in working directory
            File file = new File(filename);
            if (file.isFile()) {
                image = ImageIO.read(file);
            } // now try to read from file in same directory as this .class file
            else {
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                image = ImageIO.read(url);
            }

            if (image == null) {
                throw new IllegalArgumentException("could not read image file: " + filename);
            }

            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("could not open image file: " + filename, ioe);
        }
    }

    public Picture(File file) {
        if (file == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }

        try {
            image = ImageIO.read(file);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("could not open file: " + file, ioe);
        }
        if (image == null) {
            throw new IllegalArgumentException("could not read file: " + file);
        }
        width = image.getWidth(null);
        height = image.getHeight(null);
        filename = file.getName();
    }

    public Picture() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    public JLabel getJLabel() {
        if (image == null) {
            return null;         // no image available
        }
        ImageIcon icon = new ImageIcon(image);
        return new JLabel(icon);
    }

    public void setOriginUpperLeft() {
        isOriginUpperLeft = true;
    }

  
    public void setOriginLowerLeft() {
        isOriginUpperLeft = false;
    }

    public void show() {

      
        if (frame == null) {
            frame = new JFrame();

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem menuItem1 = new JMenuItem(" Save...   ");
            menuItem1.addActionListener(this);
            menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
            menu.add(menuItem1);
            frame.setJMenuBar(menuBar);

            frame.setContentPane(getJLabel());
            // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            if (filename == null) {
                frame.setTitle(width + "-by-" + height);
            } else {
                frame.setTitle(filename);
            }
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        }

        // draw
        frame.repaint();
    }

 
    public int height() {
        return height;
    }

  
    public int width() {
        return width;
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= height()) {
            throw new IllegalArgumentException("row index must be between 0 and " + (height() - 1) + ": " + row);
        }
    }

    private void validateColumnIndex(int col) {
        if (col < 0 || col >= width()) {
            throw new IllegalArgumentException("column index must be between 0 and " + (width() - 1) + ": " + col);
        }
    }

    public Color get(int col, int row) {
        validateColumnIndex(col);
        validateRowIndex(row);
        int rgb = getRGB(col, row);
        return new Color(rgb);
    }

    
    public int getRGB(int col, int row) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (isOriginUpperLeft) {
            return image.getRGB(col, row);
        } else {
            return image.getRGB(col, height - row - 1);
        }
    }


    public void set(int col, int row, Color color) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (color == null) {
            throw new IllegalArgumentException("color argument is null");
        }
        int rgb = color.getRGB();
        setRGB(col, row, rgb);
    }


    public void setRGB(int col, int row, int rgb) {
        validateColumnIndex(col);
        validateRowIndex(row);
        if (isOriginUpperLeft) {
            image.setRGB(col, row, rgb);
        } else {
            image.setRGB(col, height - row - 1, rgb);
        }
    }


    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Picture that = (Picture) other;
        if (this.width() != that.width()) {
            return false;
        }
        if (this.height() != that.height()) {
            return false;
        }
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (!this.get(col, row).equals(that.get(col, row))) {
                    return false;
                }
            }
        }
        return true;
    }

  
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because pictures are mutable");
    }

 
    public void save(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("argument to save() is null");
        }
        save(new File(filename));
    }

  
    public void save(File file) {
        if (file == null) {
            throw new IllegalArgumentException("argument to save() is null");
        }
        filename = file.getName();
        if (frame != null) {
            frame.setTitle(filename);
        }
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        if ("jpg".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(image, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: filename must end in .jpg or .png");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame,
                "Use a .png or .jpg extension", FileDialog.SAVE);
        chooser.setVisible(true);
        if (chooser.getFile() != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    public BufferedImage getImage() {
        return image;
    }
    
}
