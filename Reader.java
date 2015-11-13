import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Reader {

	public abstract ArrayList<Tweet> readTweets();
	
	public ArrayList<State> readStates() {
		
		String s = Main.states_file_name;
		File states_file = new File(s);
		
		if (!states_file.exists() || !states_file.canRead()) {
			Controller.display("States file does not exist or cannot be read");
			System.exit(0);
		}
		
		ArrayList<State> states = new ArrayList<State>();
		String  line = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(states_file));
			while ((line = br.readLine()) != null) {
				
				int comma_index = line.indexOf(',');
				String name = line.substring(0, comma_index); 
				
				int second_comma_index = line.indexOf(',', comma_index + 1);
				double la = Double.parseDouble(line.substring(comma_index + 1, second_comma_index)); 
				double lo = Double.parseDouble(line.substring(second_comma_index + 1, line.length()));
				
				State st = new State(name, la, lo);
				states.add(st);
			}
			br.close();
		}
		catch (IOException ioe) {
			Controller.display("IO Error reading states file.");
	     	System.exit(1); 
		}
		
		return states;
	}

}
