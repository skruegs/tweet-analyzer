
public class State implements Comparable<State>{

	private String name;
	private double latitude;
	private double longitude;
	private int numTweets;
	
	public State(String n, double la, double lo) {
		name = n;
		latitude = la;
		longitude = lo;
		
		numTweets = 0;
	}
	
	public String getName()      { return name;      }
	public double getLatitude()  { return latitude;  }
	public double getLongitude() { return longitude; }
	public int getNumTweets()    { return numTweets; }

	public void setNumTweets(int n) { numTweets = n; }
	
	public String toString() {
		return name + ", " + latitude + ", " + longitude;
	}

	@Override
	public int compareTo(State state2) {
		return state2.getNumTweets() - this.getNumTweets(); // descending
	}
	
}
