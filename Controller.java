import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Controller {

	private DataProcessor dp;
	private UserInterface ui;
	private ArrayList<Logger> loggers = new ArrayList<Logger>();
	
	public void attach(Logger l) {
		loggers.add(l);
	}
	public void detach(Logger l) {
		for (Logger lg : loggers) 
			if (lg == l) loggers.remove(lg);
	}
	public void log(String event) {
		for (Logger lg : loggers) 
			lg.log(event);
	}
	
	public Controller(DataProcessor d, UserInterface u) {
		dp = d;
		ui = u;
	}
	
	// calls on the View to print to the console
	public static void display(String s) {
		UserInterface.display(s);
	}
	
	public void displayMainMenu() {
		ui.displayMainMenu();
		determineAction();
	}
	
	private void determineAction() {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String input = null;
	    try {
	    	input = br.readLine();
	    } catch (IOException ioe) {
	    	display("IO error reading your selection");
	     	System.exit(1);
	    }
	 
	    log(input);
	    
	    if (input.equals("1")) {
	    	dp.rankNumberPerState();
	    }
	    else if (input.equals("2")) {
	    	dp.showMostPopularPerState();
	    }
	    else if (input.equals("3")) {
	    	dp.showNumberPerHour();
	    }
	    else if (input.equals("4")) {
	    	log("Program ended.");
			display("==========================");
	    	display("System exited.\n");
	    	System.exit(0);
	    }
		else {
			display("Error reading your selection. Please try again.");
			displayMainMenu();
		}
	    
	    displayMainMenu();
	}
	
	public static String readUserInput() {
		
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String input = null;
	    try {
	    	input = br.readLine();
	    } catch (IOException ioe) {
	    	display("Error reading your input");
	     	System.exit(1);
	    }
	    return input;
	}
	
}
