
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import thinning.Imagen;
import thinning.Sobel;
import thinning.ThinningService;

/**
 *
 * @author nayef
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    	File fichero = new File("foto.jpg");
		String formato = "jpg";
    	System.out.println("Iniciando Otsu...");
        Imagen obj = new Imagen("prueba.JPG");
        obj.binarizarImagen(130);
        BufferedImage im= obj.imprimirImagen();
        Graphics g = im.getGraphics();
		g.setColor(Color.red);
		g.fillRect(50, 50, 100, 100);
		g.setColor(Color.green);
		g.fillRect(0, 0, 50, 50);
		g.setColor(Color.yellow);
		g.fillOval(25, 25, 50, 50);

		// Escribimos la imagen en el archivo.
		try {
			ImageIO.write(im, formato, fichero);
		} catch (IOException e) {
			System.out.println("Error de escritura");
		}
        System.out.println("Otsu terminado.");
        
        
//        System.out.println("Iniciando Stentiford...");
//        ThinningService thinningService = new ThinningService("imagen4testfinal.jpg");
//        thinningService.thinning(false);
//        thinningService.print("imagen4testfinal.jpg");
//        System.out.println("Stentiford terminado.");
//        
//        System.out.println("Iniciando Sobel");
//		Sobel obj1 = new Sobel("imagen4testfinal.jpg");
//		obj1.applySobel("imagen4testfinalsten.jpg");
//		System.out.println("Sobel terminado.");
    }
}
