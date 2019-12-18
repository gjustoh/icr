package thinning;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterators;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.pdfbox.multipdf.Splitter;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Point;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author nayef
 */
public class Main {
	  private static final int
      CV_MOP_CLOSE = 3,
      CV_THRESH_OTSU = 8,
      CV_THRESH_BINARY = 0,
      CV_ADAPTIVE_THRESH_GAUSSIAN_C  = 1,
      CV_ADAPTIVE_THRESH_MEAN_C = 1,
      CV_THRESH_BINARY_INV  = 1;
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
	public static String tesseract(String nombre) {
		File f = new File(nombre);
		System.out.println("iniciando Tesseract "+f.getAbsolutePath());
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
	
	public static Mat histogram(Mat input) {
		List<Mat> bgrPlanes = new ArrayList<>();
        Core.split(input, bgrPlanes);
        int histSize = 256;
        float[] range = {0, 256}; //the upper boundary is exclusive
        MatOfFloat histRange = new MatOfFloat(range);
        boolean accumulate = false;
        Mat bHist = new Mat(), gHist = new Mat(), rHist = new Mat();
        Imgproc.calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), histRange, accumulate);
        int histW = 512, histH = 400;
        int binW = (int) Math.round((double) histW / histSize);
        Mat histImage = new Mat( histH, histW, CvType.CV_8UC3, new Scalar( 0,0,0) );
        Core.normalize(bHist, bHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(gHist, gHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(rHist, rHist, 0, histImage.rows(), Core.NORM_MINMAX);
        float[] bHistData = new float[(int) (bHist.total() * bHist.channels())];
        bHist.get(0, 0, bHistData);
        float[] gHistData = new float[(int) (gHist.total() * gHist.channels())];
        gHist.get(0, 0, gHistData);
        float[] rHistData = new float[(int) (rHist.total() * rHist.channels())];
        rHist.get(0, 0, rHistData);
        for( int i = 1; i < histSize; i++ ) {
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(bHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(gHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(gHistData[i])), new Scalar(0, 255, 0), 2);
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(rHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(rHistData[i])), new Scalar(0, 0, 255), 2);
        }
        HighGui.imshow( "Source image", input );
        HighGui.imshow( "calcHist Demo", histImage );
        HighGui.waitKey(0);
return bHist;
	}
	
	public static void enhanceImageBrightness(String s) {
        double alpha = 1;   // Change to 2 for more brightness
        double beta = 50;

        Mat source = Imgcodecs.imread(s);
        Mat destination = new Mat(source.rows(), source.cols(),
                source.type());
        source.convertTo(destination, -1, 1, 50);
        Imgcodecs.imwrite("output/brighterCat.jpg", destination);
    }
	
	public static void enhanceImageContrast(Mat source) {
        
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.equalizeHist(source, destination);
        Imgcodecs.imwrite("output/enhancedParrot.jpg", destination);
    }

	public static void main(String[] args) throws IOException, TesseractException {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = new Mat();
		//img = Imgcodecs.imread("test-data/eurotext_unlv.png");
		img = Imgcodecs.imread("prueba.JPG");
		Imgcodecs.imwrite("output/True_Image.png", img); 
		enhanceImageBrightness("output/True_Image.png");
		
		Mat dst = new Mat(); 
		Imgproc.bilateralFilter(img, dst, 3, 5, 3);
		Imgcodecs.imwrite("output/dst.png", dst);
		
		Mat imgGray = new Mat(); 
		Imgproc.cvtColor(dst, imgGray, Imgproc.COLOR_BGR2GRAY,0); 
		Imgcodecs.imwrite("output/Gray.png", imgGray); 
		
		enhanceImageContrast(imgGray);
		Mat binary = new Mat(); 
		Imgproc.threshold(imgGray,binary,127,255,Imgproc.THRESH_BINARY);
		Imgcodecs.imwrite("output/binary.png", binary);
		
//		Mat hist = histogram(img);
//		Imgcodecs.imwrite("output/binary.png", hist);
//		
		
		Mat ycrcb= new Mat();
		Imgproc.cvtColor(img, ycrcb, Imgproc.COLOR_BGR2YCrCb);
		Imgcodecs.imwrite("output/ycrcb.png", ycrcb);
		
		Mat vmat= new Mat(binary.rows(), binary.cols(), binary.type());
		Imgproc.equalizeHist(binary, vmat);
		Imgcodecs.imwrite("output/vmat.png", vmat);
//		Mat imgGaussianBlur = new Mat(); 
//		Imgproc.GaussianBlur(imgGray,imgGaussianBlur,new Size(5, 5 ),1); 
//		Imgcodecs.imwrite("output/gaussian_blur.png", imgGaussianBlur);
		
//		
//		Mat unsharp = new Mat(imgGaussianBlur.rows(), imgGaussianBlur.cols(), imgGaussianBlur.type()); 
//		Core.addWeighted(imgGaussianBlur, 1.5, unsharp, -0.5, 0, unsharp);
//		
//		
//		
//		
//		MatOfInt params = new MatOfInt(Imgcodecs.IMWRITE_PNG_COMPRESSION);
//		File ocrImage = new File("output/ocrImage.png");
//		Imgcodecs.imwrite(ocrImage.getAbsolutePath(),binary,params);
//
//		Mat imgAdaptiveThreshold = new Mat(); 
//		Imgproc.Sobel(imgGaussianBlur, imgAdaptiveThreshold, -10, 1, 0); 
//		Imgcodecs.imwrite("output/adaptive_threshold.png", imgAdaptiveThreshold); 
		//System.out.println(tesseract("output/adaptive_threshold.png"));
		
		// Instantiating the Tesseract class 
		// which is used to perform OCR 
		Tesseract it = new Tesseract(); 
		//it.setLanguage("spa_old");
		it.setLanguage("a1");
		Runtime cmd= Runtime.getRuntime();
		cmd.exec("cmd /C tesserac F:\\aaa.png a -l eng");
//		it.setDatapath("Tess4J/tessdata"); 
////File bi= new File("output/binary.png");
//		File bi= new File("prueba/prueba2.png");
//		// doing OCR on the image 
//		// and storing result in string str 
//		String str = it.doOCR(bi); 
//		System.out.println(str);
		//System.out.println(str); 
		//String output_file="D:\\cursos\\2019-II\\CAS\\CAS_TEO\\Tess4J\\example1";
		//Read_File.read_a_file(str + ".txt");
		//File archivo = new File ("D:\\cursos\\2019-II\\CAS\\CAS_TEO\\Tess4J\\example1.txt");
		//FileWriter fr = new FileWriter (archivo);
		//BufferedWriter br = new BufferedWriter(fr);
		//br.write(str);

//		System.out.println("Iniciando Otsu...");
//		Imagen obj = new Imagen("prueba.JPG");
//		obj.binarizarImagen(130);
//		obj.imprimirImagen("imagen4testfinal.jpg");
//		System.out.println("Otsu terminado.");
//
//		System.out.println("Iniciando Stentiford...");
//		// ThinningService thinningService = new ThinningService("captura.jpg");
//		ThinningService thinningService = new ThinningService("imagen4testfinal.jpg");
//		thinningService.thinning(false);
//		thinningService.print("imagen4testfinal2.jpg");
//		System.out.println("Stentiford terminado.");
//
//		System.out.println("Iniciando Sobel");
//		Sobel obj1 = new Sobel("imagen4testfinal2.jpg");
//		obj1.applySobel("imagen4testfinalsten.jpg");
//		System.out.println("Sobel terminado.");

	}
}
