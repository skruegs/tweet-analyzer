import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public abstract class DataProcessor {

	private ArrayList<Tweet> tweets;
	private ArrayList<State> states;	
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
	
	public abstract Reader createReader();
	
	public void readFiles() {
		Reader r = createReader();
		tweets = r.readTweets();
		states = r.readStates();
	}
	
	public void rankNumberPerState() {
		
		// identify the location of each tweet using Euclidean distance
		// find the number of tweets per state
		for (int i = 0; i < tweets.size(); i++) {
			Tweet tweet = tweets.get(i);
			double dist = Math.sqrt(Math.pow(tweet.getLatitude() - states.get(0).getLatitude(), 2) +
									Math.pow(tweet.getLongitude() - states.get(0).getLongitude(), 2));
			State st = states.get(0);
			
			for (int j = 0; j < states.size(); j++) {
				double temp_dist = Math.sqrt(Math.pow(tweet.getLatitude() - states.get(j).getLatitude(), 2) +
								   			 Math.pow(tweet.getLongitude() - states.get(j).getLongitude(), 2));
				if (temp_dist <= dist) {
					dist = temp_dist;
					st = states.get(j);
				}
			}
			st.setNumTweets(st.getNumTweets() + 1);
		}
		
		// list the states and the number of tweets in descending order
		Collections.sort(states);
		for (int i = 0; i < states.size(); i ++) {
			if (states.get(i).getNumTweets() > 0) 
				Controller.display(states.get(i).getName() + " " + states.get(i).getNumTweets());
		}

	}
	
	
	
	public void showMostPopularPerState() {
		
		// prompt user to enter state name (prompt again if no match)
		Controller.display("Enter a state name: ");
	    String input = Controller.readUserInput();

	    // log
	    log(input);
	    
	    State state = null;
	    for (int i = 0; i < states.size(); i++) {
	    	if (states.get(i).getName().toLowerCase().equals(input.toLowerCase()))
	    		state = states.get(i);
	    }
	    	
		if (state == null) {
			Controller.display("State name incorrect... Try again. ");
			showMostPopularPerState();
		}
		
		ArrayList<Tweet> tweetsFromState = new ArrayList<Tweet>();
		ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();
		
		// find all the tweets originating in that state using Euclidean distance
		for (int i = 0; i < tweets.size(); i++) {
			Tweet tweet = tweets.get(i);
			double dist = Math.sqrt(Math.pow(tweet.getLatitude() - states.get(0).getLatitude(), 2) +
									Math.pow(tweet.getLongitude() - states.get(0).getLongitude(), 2));
			State st = states.get(0);
			
			for (int j = 0; j < states.size(); j++) {
				double temp_dist = Math.sqrt(Math.pow(tweet.getLatitude() - states.get(j).getLatitude(), 2) +
								   			 Math.pow(tweet.getLongitude() - states.get(j).getLongitude(), 2));
				if (temp_dist <= dist) {
					dist = temp_dist;
					st = states.get(j);
				}
			}
			if (st != null && state != null && st.getName() == state.getName()) {
				tweetsFromState.add(tweet);
			}
		}
		
		// extract hashtags
		for (int i = 0; i < tweetsFromState.size(); i++) {
			String str = tweetsFromState.get(i).getTweet().toLowerCase();
			
			for (int j = 0; j < str.length(); j++) {
				if (str.charAt(j) == '#') {
					// read hashtag, add to AL, advance j
					int k = j;
					String ht = "";
					while (k < str.length() && !Character.isWhitespace(str.charAt(k))) {
						ht += str.charAt(k);
						k++;
					}
					
					boolean already_contains = search(hashtags, new Hashtag(ht), new TextComparator());
					
					if (already_contains) {
						for (Hashtag l : hashtags) 
							if (l.getText().equals(ht)) l.setNumPerState(l.getNumPerState() + 1);
					}		
					else {
						hashtags.add(new Hashtag(ht));
					}
					
					j = k;
				}
			}
		}
		

		// output (to the console) the top 10 hashtags, descending order
		Collections.sort(hashtags);
		Hashtag last = null;
		for (int i = 0; i < hashtags.size() && i < 10; i ++) {
			Controller.display(hashtags.get(i).getText() + " " + hashtags.get(i).getNumPerState());
			last = hashtags.get(i);
		}
		
		hashtags.remove(last);
		boolean contains_more = search(hashtags, last, new NumPerStateComparator());
		if (contains_more)
			Controller.display("(Other hashtags with count " + last.getNumPerState() + " also exist, but omitted for brevity)");
	}
	
	
	
	public void showNumberPerHour() {
		
		// prompt user to enter state name (prompt again if no match)
		Controller.display("Enter a phrase to search for: ");
		String phrase = Controller.readUserInput();

		if (phrase == null) {
			Controller.display("Couldn't read that correctly... Try again. ");
	    	showNumberPerHour();
		}
		
	    // log
		log(phrase);
		
		// find all the tweets containing the phrase
		ArrayList<Tweet> tweetsWithPhrase = new ArrayList<Tweet>();

		for (int i = 0; i < tweets.size(); i++) {
			String tweet = tweets.get(i).getTweet();
			if (tweet.contains(phrase)) {
				tweetsWithPhrase.add(tweets.get(i));
			}
		}
		
		// truncate time to be per hour
		ArrayList<Date> timesOfPhrase = new ArrayList<Date>();
		for (int i = 0; i < tweetsWithPhrase.size(); i++) {
			Date d = tweetsWithPhrase.get(i).getDate();

			Calendar d2 = new GregorianCalendar();
		    d2.setTime(d);
		    d2.set(Calendar.MINUTE, 0);
		    d2.set(Calendar.SECOND, 0);
		    
		    d = d2.getTime();
		    timesOfPhrase.add(d);
		}
		
		// put in hashmap, sort, then print
		HashMap<Date, Integer> hm = new HashMap<Date, Integer>();
		for (int i = 0; i < timesOfPhrase.size(); i++) {
			Date d = timesOfPhrase.get(i);	
			
			if (hm.containsKey(d))
				hm.put(d, hm.get(d) + 1);
			else
				hm.put (d, 1);
			
		}
		
		timesOfPhrase.clear();  // (reuse array list)
		for (Date d : hm.keySet()) {
			timesOfPhrase.add(d);
		}
		
		Collections.sort(timesOfPhrase);
		
		for (Date d : timesOfPhrase) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm");
			Controller.display(format.format(d) + " " + hm.get(d));
		}
		
	}
	
	
	// (Strategy Pattern)
	public boolean search(ArrayList<Hashtag> list, Hashtag target, Comparator comp) {
		for (Hashtag t : list) {
			if (comp.equals(t, target))
				return true;
		} 
		return false;
	}
	
}
