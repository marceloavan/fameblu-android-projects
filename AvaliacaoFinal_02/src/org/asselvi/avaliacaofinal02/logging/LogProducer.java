package org.asselvi.avaliacaofinal02.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

/**
 * 
 * @author marcelo
 */
public class LogProducer {
	
	public static void log(Class<?> clazz, Level level, String message) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		StringBuilder sb = new StringBuilder("\n");
		sb.append(" [");
		sb.append(dateFormatter.format(new Date()));
		sb.append("] [");
		sb.append(clazz.getName());
		sb.append("] [");
		sb.append(level);
		sb.append("]: ");
		sb.append(message);
		
		message = sb.toString();
		
		logCat(level, message);
		write(message);
	}
	
	private static void write(String message) {
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/OFICIAL_02_LOGGING/";
		
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH));
		if (month.length() < 2) {
			month = "0" + month;
		}
		String fileName =  "appLog_" + cal.get(Calendar.YEAR) + month + cal.get(Calendar.DAY_OF_MONTH) + ".txt";
		
		File fileExt = new File(path, fileName);
		fileExt.getParentFile().mkdirs();
		
	    FileOutputStream fosExt = null ;
	    try {
			fosExt = new FileOutputStream(fileExt, true);
	    	fosExt.write(message.getBytes());
		} 
	    catch (IOException e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
				fosExt.close();
			} catch (IOException e) {}
	    }
	}

	private static void logCat(Level level, String message) {
		Log.d(level.toString(), message);
	}
}
