
public class TextFileDataProcessor extends DataProcessor {

	@Override
	public Reader createReader() {
		return new TextFileReader();
	}

}
