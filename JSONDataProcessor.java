
public class JSONDataProcessor extends DataProcessor {

	@Override
	public Reader createReader() {
		return new JSONReader();
	}

}
