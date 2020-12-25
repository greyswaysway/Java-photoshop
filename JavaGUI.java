import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class summativeGUI {
	
	private static JFrame frame = new JFrame("summativeGUI");
	private static JPanel panel = new JPanel();
	private static JLabel picture = new JLabel();
	private static File image;
	private static BufferedImage originalImage;
	private static BufferedImage changeImage;
   
    public static void main(String[] args) {
    	optionsBar();
    	
    	panel.setPreferredSize(new Dimension(1000, 1000));
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	panel.add(picture);
    	
    	frame.setVisible(true);
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
    }
    
    
    public static void optionsBar() {
    	JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
    	ActionListener fileOpen = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				BufferedImage pic = null;
				int outcome = chooser.showOpenDialog(null);
  	  			if (outcome == JFileChooser.APPROVE_OPTION) {
  	  				image = chooser.getSelectedFile();
  	  				try {
                        picture.setIcon(new ImageIcon(ImageIO.read(image)));
                        originalImage = ImageIO.read(image);
                        changeImage = ImageIO.read(image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
  	  		   }
    	   }
		};
		ActionListener realSave = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
                    ImageIO.write(changeImage, "PNG", image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
			}
		};
		JFileChooser saver = new JFileChooser();
		saver.setSelectedFile(new File("save.png"));
		ActionListener saveAs = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
  	  			if (saver.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
  	  				File pathFinder = saver.getSelectedFile();
  	  				try {
                        ImageIO.write(changeImage, "PNG", pathFinder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
  	  			}
				
			}
		};
		ActionListener depart = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
    		System.exit(0);
			}
		};
		ActionListener renew = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
                picture.setIcon(new ImageIcon(originalImage));
                Graphics g = changeImage.getGraphics();
                g.drawImage(originalImage, 0, 0, null);
                g.dispose();
			}
		};
		ActionListener horizonFlip = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int height = picture.getHeight();
				int width = picture.getWidth();
				BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int r = 0; r < height; r++){
					 for (int c = 0; c < width; c++){
						 replica.setRGB((width - 1) - c, r, changeImage.getRGB(c, r));
					 }
				}
                Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
                picture.setIcon(new ImageIcon(changeImage));
			}
		};
		ActionListener vertFlip = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int height = picture.getHeight();
				int width = picture.getWidth();
				BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				for (int r = 0; r < height; r++){
					for (int c = 0; c < width; c++){
						replica.setRGB(c, (height - 1) - r, changeImage.getRGB(c, r));
					}
				}
				Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
				picture.setIcon(new ImageIcon(changeImage));
			}
		};
		ActionListener greyOut = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int height = picture.getHeight();
				int width = picture.getWidth();
                BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics g2 = replica.getGraphics();
                g2.drawImage(changeImage, 0, 0, null);
                g2.dispose();
                for (int r = 0; r < height; r++){
					for (int c = 0; c < width; c++){
					    int pixel = replica.getRGB(c,r);
					    int alpha = (pixel>>24)&0xff;
					    int green = (pixel>>8)&0xff;
					    int red = (pixel>>16)&0xff;
					    int blue = pixel&0xff;
					    int average = (red + blue + green) / 3;
					    pixel = (alpha<<24) | (average<<16) | (average<<8) | average;
					    replica.setRGB(c, r, pixel);
					}
                }
                Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
				picture.setIcon(new ImageIcon(changeImage));
			}
		};
		ActionListener redPaint = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
  	  			int height = picture.getHeight();
				int width = picture.getWidth();
                BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics g2 = replica.getGraphics();
                g2.drawImage(changeImage, 0, 0, null);
                g2.dispose();
                for (int r = 0; r < height; r++){
					for (int c = 0; c < width; c++){
					    int pixel = replica.getRGB(c,r);
					    int alpha = (pixel>>24)&0xff;
					    int green = (pixel>>8)&0xff;
					    int red = (pixel>>16)&0xff;
					    int blue = pixel&0xff;
					    int redValue = (int)(0.393*red + 0.769*green + 0.189*blue);
                        int greenValue = (int)(0.349*red + 0.686*green + 0.168*blue);
                        int blueValue = (int)(0.272*red + 0.534*green + 0.131*blue);
                        if(redValue > 255){
                            red = 255;
                        }else{
                            red = redValue;
                        }
                        if(blueValue > 255){
                            blue = 255;
                        }else{
                            blue = blueValue;
                        }
                        if(greenValue > 255){
                            green = 255;
                        }else{
                            green = greenValue;
                        }
                        pixel = (alpha<<24) | (red<<16) | (green<<8) | blue;
                        replica.setRGB(c, r, pixel);
					}
                }
                Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
				picture.setIcon(new ImageIcon(changeImage));
			}
		};
		ActionListener flipColour = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int height = picture.getHeight();
				int width = picture.getWidth();
                BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics g2 = replica.getGraphics();
                g2.drawImage(changeImage, 0, 0, null);
                g2.dispose();
                for (int r = 0; r < height; r++){
					for (int c = 0; c < width; c++){
					    int pixel = replica.getRGB(c,r);
					    int alpha = (pixel>>24)&0xff;
					    int green = (pixel>>8)&0xff;
					    int red = (pixel>>16)&0xff;
					    int blue = pixel&0xff;
					    green = 255 - green;
					    red = 255 - red;
					    blue = 255 - blue;
					    pixel = (alpha<<24) | (red<<16) | (green<<8) | blue;
                        replica.setRGB(c, r, pixel);
					}
                }
                Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
				picture.setIcon(new ImageIcon(changeImage));
			}
		};
		ActionListener blurry = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int height = picture.getHeight();
				int width = picture.getWidth();
				int radius = 5;
				int h = radius * 2 + 1;
				int w = h;
                BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                double sigma = 3;
                float[] weights = new float[w * h];
                double sum = 0;
                for (int y = -radius, i = 0; y <= radius; y++){
					for (int x = -radius; x <= radius; x++){
					    double weight = (1 / (2 * Math.PI * Math.pow(sigma, 2))) * Math.exp(-((Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(sigma, 2))));
					    sum += weight;
					    weights [i++] = (float)weight;
					}
				}
				for (int i = 0; i < weights.length; i++) {
			        weights[i] = (float)((double)weights[i] / sum); 
		        }
                for (int r = 0; r < height; r++){
					for (int c = 0; c < width; c++){
					    float greenSum = 0;
					    float redSum = 0;
					    float blueSum = 0;
					    float alphaSum = 0;
					    for (int y = r - radius, i = 0; y < r + radius; y++) {
					        for (int x = c - radius; x < c + radius; x++) {
					    		if ((y >= 0) && (x >= 0) && (y < height) && (x < width)){
					    			int pixelTemp = changeImage.getRGB(x,y);
					    			int alphaTemp = (pixelTemp>>24)&0xff;
					    	        int greenTemp = (pixelTemp>>8)&0xff;
					    	        int redTemp = (pixelTemp>>16)&0xff;
					    	        int blueTemp = pixelTemp&0xff;
					    	        alphaSum += alphaTemp * weights [i];
					    	        greenSum += greenTemp * weights [i];
					    	        redSum += redTemp * weights [i];
					    	        blueSum += blueTemp * weights [i];
					    	        i++;
					    	    }else{
					    			i++;	
					    		}
					    	}
					    }
					    int pixel = changeImage.getRGB(c,r);
					    int alphaValue = (int)Math.round(alphaSum);
					    int greenValue = (int)Math.round(greenSum);
					    int redValue = (int)Math.round(redSum);
					    int blueValue = (int)Math.round(blueSum);
					    pixel = (alphaValue<<24) | (redValue<<16) | (greenValue<<8) | blueValue;
                        replica.setRGB(c, r, pixel);
					}
                }
                Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
				picture.setIcon(new ImageIcon(changeImage));
			}
		};
		ActionListener stickOut = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int ny = picture.getHeight();
				int nx = picture.getWidth();
				int height = ny;
				int width =nx;
				BufferedImage replica = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				int antialias = 1;
				double scale = 1;
				double power = 1.5;
				boolean outofrange = false;
				for (int j = 0; j < height; j++){
					for (int i = 0; i < width; i++){
         				double found = 0;
         				double rsum = 0;
         				double gsum = 0;
         				double bsum = 0;
         				double asum = 0;
						double newX = 0;
				      	double newY = 0;
				       	int alpha = 0;
						int green = 0;
						int red = 0;
						int blue = 0;
       					for (int ai=0;ai<antialias;ai++) {
           					 double x = 2 * (i + ai/(double)antialias) / width - 1;
           					 x /= scale;
          				  	for (int aj=0;aj<antialias;aj++) {
               					double y = 2 * (j + aj/(double)antialias) / height - 1;
               					y /= scale;
               					
				       			double radi = Math.sqrt(x * x + y * y);
				        		double ang = Math.atan2(y, x);
								
			        			radi = Math.pow (radi, power);
			            		newY = radi*Math.sin(ang);
			            		newX = radi*Math.cos(ang);

					            int i2 = (int) (0.5 * nx * (newX + 1));
				           		int j2 = (int) (0.5 * ny * (newY + 1));
            					if (i2 < 0 || i2 >= nx || j2 < 0 || j2 >= ny) {
                  					outofrange = true;
                  					continue;
            				  }
            				   	
				        	    int pixelNewPos = changeImage.getRGB(i2,j2);
				        	    alpha = (pixelNewPos>>24)&0xff;
					        	green = (pixelNewPos>>8)&0xff;
					        	red = (pixelNewPos>>16)&0xff;
					        	blue = pixelNewPos&0xff;
               					rsum += red;
               					gsum += green;
               					bsum += blue;
               					asum += alpha;
               					found++;
          				  	}
       					}
       					  
       					if (found > 0) {
            				red = (int)(rsum / (antialias*antialias));
            				green = (int)(gsum / (antialias*antialias));
            				blue = (int)(bsum / (antialias*antialias));
            				alpha = (int)(asum / (antialias*antialias));
        				}else{
            				red = 255;
            				green = 255;
            				blue = 255;
            				alpha = 255;
        				}
						int pixel = (alpha<<24) | (red<<16) | (green<<8) | blue;
						replica.setRGB(i, j, pixel);

					}
				}
				Graphics g = changeImage.getGraphics();
                g.drawImage(replica, 0, 0, null);
                g.dispose();
				picture.setIcon(new ImageIcon(changeImage));
			}
		};
    	JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenuItem open, save, saveIn, exit;
		open = new JMenuItem("Open File");
		file.add(open);
		open.addActionListener(fileOpen);
		save = new JMenuItem("Save");
		file.add(save);
		save.addActionListener(realSave);
		saveIn = new JMenuItem("Save As");
		file.add(saveIn);
		saveIn.addActionListener(saveAs);
		exit = new JMenuItem("Exit");
		file.add(exit);
		exit.addActionListener(depart);	
		
		JMenu option = new JMenu("Options");
		menuBar.add(option);
		JMenuItem restore, hFlip, vFlip, gScale, sepia, invert, blur, bulge;
		restore = new JMenuItem("Restore To Original");
		option.add(restore);
		restore.addActionListener(renew);
		hFlip = new JMenuItem("Horizontal Flip");
		option.add(hFlip);
		hFlip.addActionListener(horizonFlip);
		vFlip = new JMenuItem("Vertiacl Flip");
		option.add(vFlip);
		vFlip.addActionListener(vertFlip);
		gScale = new JMenuItem("Grey Scale");
		option.add(gScale);
		gScale.addActionListener(greyOut);
		sepia = new JMenuItem("Sepia Tone");
		option.add(sepia);
		sepia.addActionListener(redPaint);
		invert = new JMenuItem("Invert Colour");
		option.add(invert);
		invert.addActionListener(flipColour);
		blur = new JMenuItem("Gaussian Blur");
		option.add(blur);
		blur.addActionListener(blurry);
		bulge = new JMenuItem("Bulge Effect");
		option.add(bulge);
		bulge.addActionListener(stickOut);
    }
}