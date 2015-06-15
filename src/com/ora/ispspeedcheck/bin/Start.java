
package com.ora.ispspeedcheck.bin;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;

import com.ora.ispspeedcheck.log.ISPDataLogger;
import com.ora.ispspeedcheck.log.ISPLogResults;
import com.ora.ispspeedcheck.net.SpeedTest;

/**
 * A small brutally simple application to speed test your Internet provider's
 * bandwidth.  You can use the application from your favorite IDE or
 * from the command line.  Following is an example.<br/>
 * <code>
 * java -Dfile=./ispspeedtest.csv -jar ./ispspeedcheck.jar 
 * </code>
 * if your having troubles you can turn on debug.
 * <code>
 * java -Dfile=./ispspeedtest.csv -Ddebug=true -jar ./ispspeedcheck.jar 
 * </code>
 * Of course, how you execute on your system may be different depending
 * on your needs and system type.
 * <p/>
 * Once you do get some results you can graph them easy
 * on Excel.  You can also go to the following web site and 
 * compare speed you are getting at your ISP with your ideal
 * bandwidth.<br/>
 * <code>
 * http://www.numion.com/calculators/time.html
 * </code>
 * <p/>
 * Be careful about jumping to any conclusions.  Measuring your available
 * bandwidth is easy but understanding the results is more work.  This application
 * is not multi-process, thread safe, or even secure.  If you need any of these
 * or your considering use of the code for broader applications your on your
 * own.  Thar be dragons -- you are warned.
 * @author Milton Smith
 * @version 0.1
 */
public class Start {
	
	/** Take a new bandwidth measurement each hour. */
	private static final int DELAY_INTERVAL = 60 * 60 * 1000; /* delay 1hr */
	
	/** Repeat the bandwidth measure for N intervals (N=1000 or 1000hrs) */
	private static final int MAX_INTERVALS = 1000;
	
	/**
	 * Java application entry point.
	 * @param args Command line arguments.  Not used.
	 */
	public static void main(String[] args) {
	
		try {

// Uncomment and recompile if you need to debug and see property values.
//	        Properties props = System.getProperties();
//	        for (Object envName : props.keySet() ) {
//	            System.out.format("%s=%s%n",
//	                              envName,
//	                              props.get(envName));
//	        }
			
			// Uncomment your for tests in different regions.  These work for North America only.
			HashMap<String,String> smap = new HashMap<String,String>();
			//smap.put("sea01-10", "http://speedtest.sea01.softlayer.com/downloads/test10.zip");
			smap.put("sjc01-10", "http://speedtest.sjc01.softlayer.com/downloads/test10.zip");
			smap.put("dal01-10", "http://speedtest.dal01.softlayer.com/downloads/test10.zip");
			//smap.put("dal05-10", "http://speedtest.dal05.softlayer.com/downloads/test10.zip");
			//smap.put("dal07-10", "http://speedtest.dal07.softlayer.com/downloads/test10.zip");
			//smap.put("hou01-10", "http://speedtest.hou01.softlayer.com/downloads/test10.zip");
			smap.put("wdc01-10", "http://speedtest.wdc01.softlayer.com/downloads/test10.zip");
			
			//smap.put("sea01-100", "http://speedtest.sea01.softlayer.com/downloads/test100.zip");
			smap.put("sjc01-100", "http://speedtest.sjc01.softlayer.com/downloads/test100.zip");
			//smap.put("dal01-100", "http://speedtest.dal01.softlayer.com/downloads/test100.zip");
			//smap.put("dal05-100", "http://speedtest.dal05.softlayer.com/downloads/test100.zip");
			//smap.put("dal07-100", "http://speedtest.dal07.softlayer.com/downloads/test100.zip");
			//smap.put("hou01-100", "http://speedtest.hou01.softlayer.com/downloads/test100.zip");
			//smap.put("wdc01-100", "http://speedtest.wdc01.softlayer.com/downloads/test100.zip");
			
			ISPDataLogger isplog = ISPDataLogger.getInstance();
	        
	        System.out.println("Writing log. file="+isplog.getFQFile());
	       
	        System.out.flush();
	        
	        // If the log already exists.  Don't write a new header.
	        if ( !isplog.headerExists() ) {
	        
	        	isplog.print("DATE,TIME");
	        	
		        for (String keyName : smap.keySet() ) {
		        	
		        	isplog.print(","+keyName+",PAYLOADSZ-"+keyName+",ELAPSED-"+keyName);
		        	
		        }
	        	
	        	isplog.println("");
	        
	        }
	        	
	        int intervals  = 0;
 			
			while (intervals < MAX_INTERVALS ) {
			
				URL url = null;
				
		        ISPLogResults r = new ISPLogResults();
		        
		        for (String keyName : smap.keySet() ) {
		        	
		        	url = new URL(smap.get(keyName));
		        	
			        SpeedTest st = new SpeedTest( url );
			        
			        st.startDownload();
			        
			        r.addTestResult(keyName, st.getBytesRead(), st.getElapsedTime() );
			       
		        }
		    
		        isplog.println( r.getResults() );
		        
		        r.reset();
		        
		        Thread.sleep(DELAY_INTERVAL);
		        
		        intervals++;
			} 
	     
		 } catch (IOException e) {
			 
			 System.out.println( "Download failed. msg="+e.getMessage() );
	     
		 } catch (Throwable t ) {
			 
			 t.printStackTrace();
			 
		 }
	 
	  }


	
}
