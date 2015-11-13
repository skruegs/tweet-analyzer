
import java.util.Date;
import java.text.SimpleDateFormat;


public class Tweet {
	
	private double latitude;
	private double longitude;
	Date date;
	private String tweet;
	
	public Tweet(double la, double lo, Date d, String tw) {
		latitude = la;
		longitude = lo;
		date = d;		
		tweet = tw;
	}
	
	public double getLatitude()  { return latitude;  }
	public double getLongitude() { return longitude; }
	public Date getDate()   	 { return date;      }
	public String getTweet()     { return tweet;     }
	
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		return "[" + latitude + ", " + longitude + "]\t" + 
				format.format(date) + "\t" +
				tweet;
	}
	

}
