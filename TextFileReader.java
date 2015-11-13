import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TextFileReader extends Reader {

	
	public ArrayList<Tweet> readTweets() {
		
		String t = Main.tweets_file_name;
		File tweets_file = new File(t);

		if (!tweets_file.exists() || !tweets_file.canRead()) {
			Controller.display("Tweets TEXT file does not exist or cannot be read");
			System.exit(0);
		}
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		String  line = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(tweets_file));
			while ((line = br.readLine()) != null) {
				
				int comma_index = line.indexOf(',');
				int bracket_index = line.indexOf(']');
				double la = Double.parseDouble(line.substring(1, comma_index)); 
				double lo = Double.parseDouble(line.substring(comma_index + 1, bracket_index));
				
				int date_index = line.indexOf('-', bracket_index) - 4;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				Date d = null;
				try {
					d = format.parse(line.substring(date_index, date_index + 19));
				} catch (java.text.ParseException pe) {
					Controller.display("Could not parse date.");
					Controller.display(line.substring(date_index, date_index + 19));
			     	System.exit(1); 
				}

				int tweet_index = line.indexOf('\t', date_index);
				String tweet = line.substring(tweet_index + 1, line.length());
				
				Tweet tw = new Tweet(la, lo, d, tweet);
				tweets.add(tw);
			}
			br.close();
		}
		catch (IOException ioe) {
			Controller.display("IO Error reading tweets TEXT file.");
	     	System.exit(1); 
		}
		return tweets;
	
	}
		
	
}
