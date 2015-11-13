
public class UserInterface {

	
	public void displayMainMenu() {
		System.out.println("\n==========================");
		System.out.println("M  A  I  N      M  E  N  U");
		System.out.println("==========================");
		System.out.println("Press (1) to rank the number of tweets per state.");
		System.out.println("Press (2) to show the most popular hashtags in a given state.");
		System.out.println("Press (3) to show the number of tweets per hour containing a given term.");
		System.out.println("Press (4) to quit.");
	}

	public static void display(String s) {
		System.out.println(s);
	}
	
}
