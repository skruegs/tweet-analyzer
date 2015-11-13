import java.io.File;
import java.io.FileWriter;

public class FileLogger extends Logger {

	private static Logger instance = new FileLogger();
	private FileWriter writer = null;
	
	private FileLogger() {
		try {
			File log_file = new File(Main.output_file_name);
			if (log_file.exists()) {
				if (!log_file.canWrite()) {
					Controller.display("Access not given to write to log file.");
			     	System.exit(1); 
				}
				writer = new FileWriter(log_file, true);
			}
			else {
				writer = new FileWriter(log_file);	
			}			
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static Logger getInstance() { 
		return instance;
	}
	
	public void log(String event) {
		try {
			writer.write(System.currentTimeMillis() + "\n" + event +"\n");		
			writer.flush();
		}
		catch (Exception e) { 
			Controller.display("Error writing to file.");
			System.exit(1);
		}
	}
	
	public void close() {
		try { writer.close(); } catch (Exception e) { }
	}
	
	
}
