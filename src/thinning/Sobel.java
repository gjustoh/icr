package thinning;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sobel {
	File file;
	int x, y;
	int[][] edgeColors = new int[x][y];
    int maxGval, maxGradient;
    BufferedImage image;
	
	public Sobel(String filename) throws IOException {
		file = new File(filename);
        image = ImageIO.read(file);

        x = image.getWidth();
        y = image.getHeight();
        maxGval = 0;
        edgeColors = new int[x][y];
        maxGradient = -1;
	}
	
	public void applySobel(String outfilename) throws IOException {
		for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                int val00 = getGrayScale(image.getRGB(i - 1, j - 1));
                int val01 = getGrayScale(image.getRGB(i - 1, j));
                int val02 = getGrayScale(image.getRGB(i - 1, j + 1));

                int val10 = getGrayScale(image.getRGB(i, j - 1));
                int val11 = getGrayScale(image.getRGB(i, j));
                int val12 = getGrayScale(image.getRGB(i, j + 1));

                int val20 = getGrayScale(image.getRGB(i + 1, j - 1));
                int val21 = getGrayScale(image.getRGB(i + 1, j));
                int val22 = getGrayScale(image.getRGB(i + 1, j + 1));

                int gx =  ((-1 * val00) + (0 * val01) + (1 * val02)) 
                        + ((-2 * val10) + (0 * val11) + (2 * val12))
                        + ((-1 * val20) + (0 * val21) + (1 * val22));

                int gy =  ((-1 * val00) + (-2 * val01) + (-1 * val02))
                        + ((0 * val10) + (0 * val11) + (0 * val12))
                        + ((1 * val20) + (2 * val21) + (1 * val22));

                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;

                if(maxGradient < g) {
                    maxGradient = g;
                    
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int)(edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                image.setRGB(i, j, edgeColor);
            }
        }
        
        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
            	int p = image.getRGB(i,j);
            	int a = (p>>24)&0xff;
            	int r = (p>>16)&0xff;
            	int g = (p>>8)&0xff;
            	int b = p&0xff;
            	r = 255 - r;
            	g = 255 - g;
            	b = 255 - b;
            	p = (a<<24) | (r<<16) | (g<<8) | b;
            	image.setRGB(i, j, p);
            }
        }
        
        File outputfile = new File(outfilename);
        ImageIO.write(image, "png", outputfile);

        System.out.println("max : " + maxGradient);
	}
	
	public static int  getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;

        //from https://en.wikipedia.org/wiki/Grayscale, calculating luminance
        int gray = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
        //int gray = (r + g + b) / 3;

        return gray;
    }
}
