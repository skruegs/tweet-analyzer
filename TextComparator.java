
public class TextComparator implements Comparator {

	public boolean equals(Hashtag h1, Hashtag h2) {
		return h1.getText().equals(h2.getText());
	}

}
