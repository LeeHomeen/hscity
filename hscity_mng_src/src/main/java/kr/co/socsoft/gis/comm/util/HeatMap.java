package kr.co.socsoft.gis.comm.util;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RGBImageFilter;
import java.awt.image.Raster;
import java.util.List;
import java.util.Vector;


import kr.co.socsoft.gis.pop.vo.PopAnalsGisVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class HeatMap {    
    private BufferedImage backgroundImage;    
    private int radius = 13;
    private int numColours = 10;
    public BufferedImage dotImage; 
    public BufferedImage monochromeImage;
    public BufferedImage heatmapImage;
    public BufferedImage colorImage;
    private LookupTable colorTable;    
    @SuppressWarnings("unused")
	private LookupOp colorOp;
    
    public HeatMap(PopAnalsGisVO popVO) {
        initImages(popVO);
    } 

    private void initImages(PopAnalsGisVO popVO) {
        try {
        	backgroundImage = new BufferedImage(Integer.parseInt(popVO.getSCREENX()), Integer.parseInt(popVO.getSCREENY()), BufferedImage.TYPE_INT_ARGB);            
            dotImage = getDotImageFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = backgroundImage.getWidth();
        int height = backgroundImage.getHeight();        
        int alpha = 150;
        
        setHeatColor(popVO.getVCOLOR(), alpha);

        colorTable = createColorLookupTable(colorImage, .5f);
        colorOp = new LookupOp(colorTable, null);

        monochromeImage = createCompatibleTranslucentImage(
                width, height);

        Graphics g = monochromeImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }
    
    public static LookupTable createColorLookupTable(BufferedImage im,
            float alpha) {
        int tableSize = 256;
        Raster imageRaster = im.getData();
        double sampleStep = 1D * im.getWidth() / tableSize; // Sample pixels
        // evenly
        byte[][] colorTable = new byte[4][tableSize];
        int[] pixel = new int[1]; // Sample pixel
        Color c;

        for (int i = 0; i < tableSize; ++i) {
            imageRaster.getDataElements((int) (i * sampleStep), 0, pixel);

            c = new Color(pixel[0]);

            colorTable[0][i] = (byte) c.getRed();
            colorTable[1][i] = (byte) c.getGreen();
            colorTable[2][i] = (byte) c.getBlue();
            colorTable[3][i] = (byte) (alpha * 0xff);
        }

        LookupTable lookupTable = new ByteLookupTable(0, colorTable);

        return lookupTable;
    }

    public static BufferedImage createEvenlyDistributedGradientImage(
            Dimension size, Color... colors) {
        BufferedImage im = createCompatibleTranslucentImage(
                size.width, size.height);
        Graphics2D g = im.createGraphics();

        float[] fractions = new float[colors.length];
        float step = 1f / colors.length;

        for (int i = 0; i < colors.length; ++i) {
            fractions[i] = i * step;
        }

        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0, size.width, 1, fractions, colors,
                MultipleGradientPaint.CycleMethod.REPEAT);

        g.setPaint(gradient);
        g.fillRect(0, 0, size.width, size.height);

        g.dispose();

        return im;
    }

    public static BufferedImage createCompatibleTranslucentImage(int width,
            int height) {       

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        int[] image_bytes;

        image_bytes = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
                null, 0, image.getWidth());

        /* try transparency as missing value */
        for (int i = 0; i < image_bytes.length; i++) {
            image_bytes[i] = 0x00000000;
        }

        /* write bytes to image */
        image.setRGB(0, 0, image.getWidth(), image.getHeight(),
                image_bytes, 0, image.getWidth());

        return image;

    }

    public BufferedImage doColorize() {
        int[] image_bytes, image_bytes2;

        image_bytes = colorImage.getRGB(0, 0, colorImage.getWidth(), colorImage.getHeight(),
                null, 0, colorImage.getWidth());
        image_bytes2 = monochromeImage.getRGB(0, 0, monochromeImage.getWidth(), monochromeImage.getHeight(),
                null, 0, monochromeImage.getWidth());

        for (int i = 0; i < image_bytes2.length; i++) {
            int pos = image_bytes2[i] & 0x000000ff;
            image_bytes2[i] = image_bytes[pos * 2] & 0x99ffffff;
        }

        /* write bytes to image */
        BufferedImage biColorized = new BufferedImage(monochromeImage.getWidth(), monochromeImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        biColorized.setRGB(0, 0, biColorized.getWidth(), biColorized.getHeight(),
                image_bytes2, 0, biColorized.getWidth());

        return biColorized;
    }

    public static Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                    //return (rgb) | (rgb << 8) | (rgb << 16) | 0xff000000;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public BufferedImage colorize(LookupOp colorOp) {
        return colorOp.filter(monochromeImage, null);
    }

    public BufferedImage colorize(LookupTable colorTable) {
        return colorize(new LookupOp(colorTable, null));
    }

    public static BufferedImage createFadedCircleImage(int size) {
        BufferedImage im = createCompatibleTranslucentImage(size, size);
        float radius = size / 2f;

        RadialGradientPaint gradient = new RadialGradientPaint(
                radius, radius, radius, new float[]{0f, 1f}, new Color[]{
                    Color.BLACK, new Color(0xffffffff, true)});

        Graphics2D g = (Graphics2D) im.getGraphics();

        g.setPaint(gradient);
        g.fillRect(0, 0, size, size);

        g.dispose();

        return im;
    }

    private void addDotImage(Point p, float alpha) {
        int circleRadius = dotImage.getWidth() / 2;
        Graphics2D g = (Graphics2D) monochromeImage.getGraphics();
        //g.setComposite(BlendComposite.Multiply.derive(alpha));
        g.drawImage(dotImage, null, p.x - circleRadius, p.y - circleRadius);
    }

    private void addDotImage(Point p) {
        Graphics2D g = (Graphics2D) monochromeImage.getGraphics();
        float radius = 10f;
        Shape circle = new Ellipse2D.Float(p.x - (radius / 2), p.y - (radius / 2), radius, radius);
        g.draw(circle);
        g.setPaint(Color.blue);
        g.fill(circle);
    }

    private BufferedImage getDotImageFile() {       
        return createFadedCircleImage(radius);
    }   

    private Point translate(double x, double y) {
        try {
        	/*
            x = (x - minX) / (maxX - minX);
            y = (y - minY) / (maxY - minY);
            
            x = (x * backgroundImage.getWidth());
            y = ((1 - y) * backgroundImage.getHeight());
            System.out.println("pixeled: " + x + ", " + y);
            */
            
            return new Point(new Double(x).intValue(), new Double(y).intValue());
        } catch (Exception e) {
            //System.out.println("Exception with translating:");
            e.printStackTrace(System.out);
        }
        return null;
    }  

    private void generateLogScaleCircle(int dPoints[][]) {
        try {

            int maxValue = 0;
            int width = monochromeImage.getWidth();
            int height = monochromeImage.getHeight();

            for (int mi = 0; mi < width; mi++) {
                for (int mj = 0; mj < height; mj++) {
                    if (maxValue < dPoints[mi][mj]) {
                        maxValue = dPoints[mi][mj];
                    }
                }
            }

            if (maxValue > 0) {
                int roundFactor = 1;

                for (int mi = 0; mi < width; mi++) {
                    for (int mj = 0; mj < height; mj++) {
                        int rgba = (int) (255 - Math.log(dPoints[mi][mj]) * 255 / Math.log((double) maxValue));
                        if (rgba < 255 && rgba > 255 - (255 / numColours) - roundFactor) {
                            rgba = 255 - (255 / numColours) - roundFactor;
                        }
                       
                        rgba = (rgba) | (rgba << 8) | (rgba << 16) | 0xff000000;

                        monochromeImage.setRGB(mi, mj, rgba);
                    }
                }

                //generateLegend(maxValue);
            }
        } catch (Exception e) {
            System.out.println("Error generating log scale circle:");
            e.printStackTrace(System.out);
        }
    }
  
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void loadData_list(List<?> list){
    	Vector v = new Vector();
    	if(list.size() > 0){
    		for(int i=0; i<list.size(); i++){
    			EgovMap map = (EgovMap)list.get(i);
    			double x = (double)map.get("stx"); //double형이라 에러
				double y = (double)map.get("sty");
				//double val = ((BigDecimal)map.get("hco")).doubleValue();				
				String line = x +","+y;
				v.add(line);
    		}
            generateClasses(v);
    	}
    }

    private void generateClasses(Vector<String> v) {        
        int width = backgroundImage.getWidth();
        int height = backgroundImage.getHeight();
       
        int dPoints[][] = new int[width][height];
        for (int i = 0; i < v.size(); i++) {
            String strpts[] = (v.get(i)).split(",");
            double cx = Double.parseDouble(strpts[0]);
            double cy = Double.parseDouble(strpts[1]);
            Point p = translate(cx, cy);

            int pradius = radius * radius;
            if(p != null){
            	 for (int ci = (int) (p.x - radius); ci <= (p.x + radius); ci++) {
                     for (int cj = (int) (p.y - radius); cj <= (p.y + radius); cj++) {
                         if (ci >= 0 && ci < width && cj >= 0 && cj < height) {
                             double d = Math.pow((p.x - ci), 2) + Math.pow((p.y - cj), 2);
                             if ((int) d <= pradius) {                           
                                 dPoints[ci][cj] += numColours - ((d * numColours) / pradius);
                             }
                         }
                     }
                 }            	 
            	 //addDotImage(p, .75f);
            	 addDotImage(p);
            }
        }

        generateLogScaleCircle(dPoints);
    }

    public void generatePoints(double[] v) {        

        int width = backgroundImage.getWidth();
        int height = backgroundImage.getHeight();
       
        int dPoints[][] = new int[width][height];
        for (int i = 0; i < v.length; i += 2) {
            double cx = v[i];
            double cy = v[i + 1];

            Point p = translate(cx, cy);

            addDotImage(p);
        }
    }
    
    public void drawOuput_list(boolean colorize) {
        try {
            if (colorize) {
                heatmapImage = doColorize();
            } else {
                heatmapImage = monochromeImage;
            }
            Graphics2D g = (Graphics2D) backgroundImage.getGraphics();
            g.drawImage(makeColorTransparent(heatmapImage, Color.WHITE), 0, 0, null);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static String toRgbText(int rgb) {
        // clip input value.

        if (rgb > 0xFFFFFF) {
            rgb = 0xFFFFFF;
        }
        if (rgb < 0) {
            rgb = 0;
        }

        String str = "000000" + Integer.toHexString(rgb); //$NON-NLS-1$
        return "#" + str.substring(str.length() - 6); //$NON-NLS-1$
    }
    
    //색상 지정
    private void setHeatColor(String chk, int alpha){
    	if(chk.trim().equals("blue")){
    		colorImage = createEvenlyDistributedGradientImage(new Dimension(512, 20), 
             		new Color(0, 0, 63, alpha), 
                     new Color(0, 0, 81, alpha),
                     new Color(0, 0, 99, alpha),
                     new Color(0, 33, 135, alpha),
                     new Color(18, 69, 171, alpha),
                     new Color(54, 105, 207, alpha),
                     new Color(90, 141, 243, alpha),
                     new Color(126, 177, 255, alpha),
                     new Color(162, 213, 255, alpha),
                     new Color(198, 249, 255, alpha),
                     new Color(255, 255, 255, 0));
    	}else if(chk.trim().equals("green")){
    		colorImage = createEvenlyDistributedGradientImage(new Dimension(512, 20),
    				new Color(0, 8, 0, alpha),
    				new Color(0, 26, 0, alpha),
    				new Color(0, 62, 0, alpha),
    				new Color(16, 98, 10, alpha),
    				new Color(52, 134, 46, alpha),
    				new Color(88, 170, 82, alpha),
    				new Color(124, 206, 118, alpha),
    				new Color(160, 242, 154, alpha),
    				new Color(196, 255, 190, alpha),
    				new Color(232, 255, 226, alpha),
                    new Color(255, 255, 255, 0));
    	}else{
    		colorImage = createEvenlyDistributedGradientImage(new Dimension(512, 20),
    				new Color(129, 0, 0, alpha),
    				new Color(165, 0, 0, alpha),
    				new Color(201, 0, 0, alpha),
    				new Color(237, 0, 0, alpha),
    				new Color(255, 18, 18, alpha),
    				new Color(255, 54, 54, alpha),
    				new Color(255, 90, 90, alpha),
    				new Color(255, 126, 126, alpha),
    				new Color(255, 162, 162, alpha),
    				new Color(255, 198, 198, alpha),
                    new Color(255, 255, 255, 0));
    	}
    }
}