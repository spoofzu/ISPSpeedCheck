package com.ora.ispspeedcheck.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Light weight utility class to do some logging.  There are more
 * powerful logging options feel free to improve.
 * @author Milton Smith
 */
public class ISPDataLogger {
	

private String FQFILE = null;

private static ISPDataLogger instance = null;

	/**
	 * Private CTOR
	 * @param file Fully qualified CSV log file.
	 */
	private ISPDataLogger( String file ) {
		
		FQFILE = file;
		
	}
		
	/**
	 * Get an instance of the logger.
	 * @return Logger instance
	 * @throws IOException Thrown on problems.
	 */
	public static synchronized final ISPDataLogger getInstance() throws IOException {
		
		if ( instance == null ) {
		
			String file = System.getProperty("file");
			
			if( file == null || file.length() < 1 )
				throw new IOException( "No file specified.  Detail: -Dfile=<filename>");
			
			instance = new ISPDataLogger(file);
		
		}
		
		return instance;
		
	}

	/**
	 * Name of the fully qualified CSV file.
	 * @return File name.
	 */
	public final String getFQFile() {
		
		return FQFILE;
	
	}

	// not a general write method, not thread or multi-process safe.
	// flush after each write in event ctrl-c is pressed.  not most efficient but safe.
	/**
	 * Print a log entry to the CSV log file. Note: not process or thread safe.  Also not
	 * optimized.  We open, flush buffers, and close the file stream after every message
	 * is logged so you can CTRL-C or kill the process at anytime without corrupting file.  
	 * @param line Message to log.
	 * @throws IOException Thrown on problems.
	 */
	public final void println( String line ) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(FQFILE,true));
		out.write(line);
		out.write("\n"); // need to fix for any os
		out.flush(); // close so we don't lock
		out.close();
	
	}
	
	// not a general write method, not thread or multi-process safe.
	// flush after each write in event ctrl-c is pressed.  not most efficient but safe.
	/**
	 * Print a log entry to the CSV log file. Note: not process or thread safe.  Also not
	 * optimized.  We open, flush buffers, and close the file stream after every message
	 * is logged so you can CTRL-C or kill the process at anytime without corrupting file.  
	 * @param line Message to log.
	 * @throws IOException Thrown on problems.
	 */
	public final void print( String line ) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(FQFILE,true));
		out.write(line);
		out.flush(); // close so we don't lock
		out.close();
	
	}

	/**
	 * If the file exists we assume it has a CSV header.
	 * @return true, file and header exists.  false, file does not exist.
	 */
	public final boolean headerExists() {
		
		File f = new File(FQFILE);
		
		return f.exists();
		
	}
	

	
}
