
public class Hashtag implements Comparable<Hashtag> {

	private String text;
	private int numPerState;
	
	public Hashtag (String s) {
		text = s;
		numPerState = 1;
	}
	
	public String getText()     { return text;        }
	public int getNumPerState() { return numPerState; }
	
	public void setNumPerState(int n) { numPerState = n; } 
	
	@Override
	public int compareTo(Hashtag ht2) {
		return ht2.getNumPerState() - this.getNumPerState(); // descending
	}
	
	
}
