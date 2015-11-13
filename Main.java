
public class Main {

	public static String tweets_file_name;
	public static String states_file_name;
	public static String output_file_name;

	
	public static void main(String[] args) {
		
		// handle command-line arguments
		if (args == null || args.length < 4) {
			Controller.display("Please specify the required four arguments");
			System.exit(0);
		}
		String file_type = args[0];
		tweets_file_name = args[1];
		states_file_name = args[2];
		output_file_name = args[3];
	
		if (!(file_type.equals("JSON") || file_type.equals("TEXT"))) {
			Controller.display("Please specify a correct format: JSON or TEXT");
			System.exit(0);
		}
		
		// create Data Processor, User Interface, and Controller	
		DataProcessor dp;
		if (file_type.equals("JSON")) 
			dp = new JSONDataProcessor();
		else 
			dp = new TextFileDataProcessor();

		UserInterface ui = new UserInterface();
		Controller cont = new Controller(dp, ui);
		
		// read files
		dp.readFiles();

		// add Observers to Subjects
		Logger fl = FileLogger.getInstance();
		Logger sl = ScreenLogger.getInstance();
		dp.attach(fl);
		dp.attach(sl);
		cont.attach(fl);
		cont.attach(sl);
		
		// log the first line
		String s = tweets_file_name + " " + states_file_name + " " + output_file_name;
		fl.log(s);
		sl.log(s);
		
		// begin program by prompting user
		cont.displayMainMenu();
		
	}

}
