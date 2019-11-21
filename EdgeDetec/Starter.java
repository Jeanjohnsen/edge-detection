package semester.projekt.starter;

/*
*
*   IMPORTS!
*
 */
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import semester.projekt.core.EdgeDetector;
import semester.projekt.core.Ascii;
import semester.projekt.core.RobotClient;

/**
 * Class is final because we do not want to extend it.
 */
public class Starter {

    public static void main(String[] args) throws IOException, Exception {

        // __INIT__ GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "gif", "png"));
                while (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = fileChooser.getSelectedFile();
                        BufferedImage image = ImageIO.read(f);
                        if (image == null) {
                            throw new IllegalArgumentException(f + " is not a valid image.");
                        }
                        EdgeDetector ed = new EdgeDetector();
                        ed.setFilename(f.getName());
                        ed.setGetImage(image);
                        BufferedImage buffImg = ed.getBufferedImage();
                        String ascii = new Ascii(55.0).conv(buffImg);
                        String message = ascii;
                        JTextArea textArea = new JTextArea(ascii, image.getHeight(), image.getWidth());
                        textArea.setFont(new Font("Monospaced", Font.BOLD, 1));
                        textArea.setEditable(false);
                        JDialog dialog = new JOptionPane(new JScrollPane(textArea), JOptionPane.PLAIN_MESSAGE).createDialog(Starter.class.getName());
                        dialog.setResizable(true);
                        dialog.setVisible(true);

                        semester.projekt.core.RobotClient rc = new semester.projekt.core.RobotClient("localhost", 12345);
                        try {
                            Socket connection = new Socket("localhost", 12345);
                            rc.out = new PrintWriter(connection.getOutputStream());

                            rc.out.write(message);

                            rc.out.close();
                        } catch (UnknownHostException e) {
                            System.err.println("Unknown host");
                        } catch (IOException e) {
                            System.err.println("couldnt connect");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                System.exit(0);
            }
        });

        //System.out.println("max : " + con.getClass());
        //System.out.println("Finished");
        //Encoding
        //String encodestring = encodeFile(file);
        //System.out.println("ENCODED STRING :");
        //System.out.println(encodestring);
    }

    /*
    *   This method takes the "sobel" file created by the main method
    *   and encodes it to a Base64 string https://da.wikipedia.org/wiki/Base64
     */
 /*
    private static String encodeFile(File file) throws Exception {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = Files.readAllBytes(file.toPath());
        fileInputStreamReader.read(bytes);

        return new String(Base64.getEncoder().encode(bytes), "UTF-8");
    }
     */
}
