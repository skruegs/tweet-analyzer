
public class NumPerStateComparator implements Comparator {

	@Override
	public boolean equals(Hashtag h1, Hashtag h2) {
		return h1.getNumPerState() == h2.getNumPerState();
	}

	public int compare(Hashtag h1, Hashtag h2) {
		return h2.getNumPerState() - h1.getNumPerState(); // descending
	}
	
	
}
