package com.ora.ispspeedcheck.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Read some bytes from an ISP.  Collect basic metrics like
 * bytes read and elapsed time.
 * @author Milton Smith
 */
public class SpeedTest {

	private int totalBytesRead = 0;;
	
	private int elapsedTime = 0;
	
	private URL file;
	
	private int BUFF_SZ = 9000; // Download buffer size.
	
	/**
	 * CTOR
	 * @param file Fully qualified URL to target file.
	 */
	public SpeedTest( URL file ) { 
		
		this.file = file;
		
	}
	
	/**
	 * File size.
	 * @return  Size of the file downloaded in bytes.  Valid after file is downloaded.
	 */
	public final int getBytesRead() {
		
		return totalBytesRead;
	}
	
	/**
	 * Elapsed time of download.
	 * @return Elapsed download time in milliseconds.
	 */
	public final int getElapsedTime() {
		
		return elapsedTime;
	}
	
	/**
	 * Download a target from host.
	 * @throws IOException Thrown on trouble.
	 */
	public final void startDownload()  throws IOException {
	
		InputStream reader = null;
        
		try {
		
	        file.openConnection();
	        reader = file.openStream();
	        byte[] buffer = new byte[BUFF_SZ];
	        int b = 0;
	 
	        long startTime = System.currentTimeMillis();
	        while (( b = reader.read(buffer)) > 0)	      
	        	totalBytesRead +=  b;
	        long endTime = System.currentTimeMillis();
			
	        elapsedTime = (int)(endTime - startTime);
	        
		} finally {
			
	        if ( reader != null ) reader.close();
			
		}
	        
	}
	
}
