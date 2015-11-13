import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONReader extends Reader {
	
	public ArrayList<Tweet> readTweets() {

		String t = Main.tweets_file_name;
		File tweets_file = new File(t);

		if (!tweets_file.exists() || !tweets_file.canRead()) {
			System.out.println("Tweets JSON file does not exist or cannot be read");
			System.exit(0);
		}
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		String  line = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(tweets_file));
			JSONParser parser = new JSONParser();

			while ((line = br.readLine()) != null) {
		
				try {
					
					JSONObject obj = (JSONObject)parser.parse(line);
					String tweet = (String)obj.get("text"); 
					String time = (String)obj.get("time");
					JSONArray location = (JSONArray)obj.get("location");
					Object[] locationArray = location.toArray();
					Double lat = (Double)locationArray[0];
					Double lon = (Double)locationArray[1];	
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
					Date d = null;
					try {
						d = format.parse(time);
					} catch (java.text.ParseException pe) {
						System.out.println("Could not parse date in JSON file.");
				     	System.exit(1); 
					}
					Tweet tw = new Tweet(lat, lon, d, tweet);
					tweets.add(tw);
					
				} catch (ParseException e) {
					System.out.println("Could not parse line of JSON file.");
			     	System.exit(1); 
				}
				
			}
			br.close();
		}
		catch (IOException ioe) {
			System.out.println("IO Error reading tweets JSON file.");
			System.exit(1); 
		}
		return tweets;
	}
	
	
}