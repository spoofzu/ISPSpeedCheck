package com.ora.ispspeedcheck.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ISPLogResults {

	String[] s = new String[10];
	int[] pb = new int[10];
	int[] et = new int[10];
	int idx = 0;
	
	public void reset(){
		s = new String[10];
		pb = new int[10];
		et = new int[10];
		idx=0;
	}
	
	public void addTestResult( String servercode, int payloadbytes, int elapsedtime ) {
		
		s[idx] = servercode;
		pb[idx] = payloadbytes;
		et[idx] = elapsedtime;
		
		idx++;		
	}
	
	/**
	 * Create a log message suitable for logging to the CSV file.
	 * @param server Name of server file was downloaded from.
	 * @param payloadbytes Size of the payload in bytes.
	 * @param elapsed Elapsed time of the download in milliseconds.
	 * @return Formatted CSV log message.
	 */
	public final String getResults()  {
		
		StringBuffer buff = new StringBuffer();
		
		DateFormat time = new SimpleDateFormat("h:mm:ss a");
		
		DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
		
		Date now = Calendar.getInstance().getTime();
		
		// datetime stamp, time msg was created/logged.
		buff.append( date.format( now ) );    
		buff.append(',');
		buff.append( time.format( now ) );    
		buff.append(',');
		
		for( int i=0; i < idx; i++ ) {
			
			// server code
			buff.append( s[i] );    
			buff.append(',');
			
			// bytes downloaded
			buff.append( pb[i] );    
			buff.append(',');
			
			// elapsed download time in ms for N bytes
			buff.append(  et[i] );	
			if( i+1 < idx) buff.append(',');
			
		}
		
		return buff.toString();
	
	}
	
}
