package thinning;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author nayef
 */
public class Main {
	/**
	 * @param args the command line arguments
	 * @throws TesseractException
	 */

	public void Otsu(String path) {
		System.out.println("Iniciando Otsu...");
		Imagen obj = new Imagen(path);
		obj.binarizarImagen(130);
		try {
			obj.imprimirImagen("imagen4testfinal.jpg");
			System.out.println("Otsu terminado.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String stentiford() {
		ThinningService thinningService;
		try {
			thinningService = new ThinningService("imagen4testfinal.jpg");
			thinningService.thinning(false);
			thinningService.print("imagen4testfinal2.jpg");
			System.out.println("Stentiford terminado.");
			return "imagen4testfinal2.jpg";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String sobel() {
		System.out.println("Iniciando Sobel");
		Sobel obj1;
		try {
			obj1 = new Sobel("imagen4testfinal2.jpg");
			obj1.applySobel("imagen4testfinalsten.jpg");
			System.out.println("Sobel terminado.");
			return "imagen4testfinalsten.jpg";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String tesseract() {
		File f = new File("imagen4testfinalsten.jpg");
		System.out.println("iniciando Tesseract");
		ScanedImage scImg = new ScanedImage();
		try {
			return scImg.generar(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException, TesseractException {

		System.out.println("Iniciando Otsu...");
		Imagen obj = new Imagen("prueba.JPG");
		obj.binarizarImagen(130);
		obj.imprimirImagen("imagen4testfinal.jpg");
		System.out.println("Otsu terminado.");

		System.out.println("Iniciando Stentiford...");
		// ThinningService thinningService = new ThinningService("captura.jpg");
		ThinningService thinningService = new ThinningService("imagen4testfinal.jpg");
		thinningService.thinning(false);
		thinningService.print("imagen4testfinal2.jpg");
		System.out.println("Stentiford terminado.");

		System.out.println("Iniciando Sobel");
		Sobel obj1 = new Sobel("imagen4testfinal2.jpg");
		obj1.applySobel("imagen4testfinalsten.jpg");
		System.out.println("Sobel terminado.");

	}
}
