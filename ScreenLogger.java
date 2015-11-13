
public class ScreenLogger extends Logger {

	private static Logger instance = new ScreenLogger();
	
	private ScreenLogger() {
	}
	
	public static Logger getInstance() { 
		return instance;
	}
	
	public void log(String event) {
		
		System.out.println("\n" + System.currentTimeMillis() + ": " + event +"\n");		
	}
	
	
}
