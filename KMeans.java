import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
 

public class KMeans {
    public static void main(String [] args){
	if (args.length < 3){
	    System.out.println("Usage: Kmeans <input-image> <k> <output-image>");
	    return;
	}
	try{
		
	    BufferedImage originalImage = ImageIO.read(new File(args[0]));
	    int k=Integer.parseInt(args[1]);
	    BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
	    ImageIO.write(kmeansJpg, "jpg", new File(args[2])); 
	    
	}catch(IOException e){
	    System.out.println(e.getMessage());
	}	
    }
    
    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k){
	int w=originalImage.getWidth();
	int h=originalImage.getHeight();
	BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
	Graphics2D g = kmeansImage.createGraphics();
	g.drawImage(originalImage, 0, 0, w,h , null);
	// Read rgb values from the image
	int[] rgb=new int[w*h];
	int count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		rgb[count]=kmeansImage.getRGB(i,j);
		count++;
	    }
	}
	
	
	// Call kmeans algorithm: update the rgb values
	kmeans(rgb,k);

	// Write the new rgb values to the image
	count=0;
	for(int i=0;i<w;i++){
	    for(int j=0;j<h;j++){
		kmeansImage.setRGB(i,j,rgb[count++]);
	    }
	}
	return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private static void kmeans(int[] rgb, int k){
    	int length=rgb.length;
    	int[] r=new int[length];
    	int[] g=new int[length];
    	int[] b=new int[length];
    	for(int i=0;i<length;i++)
    	{
    		r[i]=(rgb[i]>>16) & 0xff;
    		g[i]=(rgb[i]>>8) & 0xff;
    		b[i]=(rgb[i]) & 0xff;
    		
    	}
    	
    	
    	int min=0;
    	int max=length-1;
    	
    	int iterations=100;
    	
    	int[] centers_r=new int[k];
    	int[] centers_g=new int[k];
    	int[] centers_b=new int[k];
    	int[] cluster_of_instance=new int[length];
    	
    	
    	for(int i=0;i<k;i++)
    	{
    		Random rand=new Random();
        	centers_r[i]=r[rand.nextInt(max-min)+min];
        	centers_g[i]=g[rand.nextInt(max-min)+min];
        	centers_b[i]=b[rand.nextInt(max-min)+min];
        	
    	}
    	while(iterations!=0)
    	{
    		for(int i=0;i<length;i++)
    		{
    			double min_dist = Integer.MAX_VALUE;
				int min_k=-1;
				double distance;
    			for(int j=0;j<k;j++)
    			{
    				distance=Math.sqrt(Math.abs((centers_r[j]-r[i])^2+(centers_g[j]-g[i])^2+(centers_b[j]-b[i])^2));
    				if(distance<min_dist)
    				{
    					min_dist=distance;
    					min_k=j;
    				}
    			}
    			cluster_of_instance[i]=min_k;
    			
    		}
    		
    		for(int j=0;j<k;j++)
    		{
    			int sum1=0;
        		int sum2=0;
        		int sum3=0;
    			int c=0;
    			for(int i=0;i<length;i++)
    			{
    				if(cluster_of_instance[i]==j)
    				{
    					sum1=sum1+r[i];
    					sum2=sum2+g[i];
    					sum3=sum3+b[i];
    					c++;
    					
    				}
    			}
    			
    			centers_r[j]=(int)((double)sum1/(double)c);
    			centers_g[j]=(int)((double)sum2/(double)c);
    			centers_b[j]=(int)((double)sum3/(double)c);
    		}
    		iterations--;
    		
    	}
    	
    	for(int i=0;i<length;i++)
    	{
    		
    		rgb[i] = ((centers_r[cluster_of_instance[i]]&0x0ff)<<16)|((centers_g[cluster_of_instance[i]]&0x0ff)<<8)|(centers_b[cluster_of_instance[i]]&0x0ff);
    		
    	}
    	
    	
    	   
    	
    }

}
