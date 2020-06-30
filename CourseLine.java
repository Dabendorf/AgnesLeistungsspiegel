public class CourseLine extends Line {

	public String prfNr;
	public String restString;

	public CourseLine(String prfNr, String restString) {
		this.prfNr = prfNr.trim();
		this.restString = restString.replace("\n", "").trim();
	}

	@Override
	public String toString() {
		String cleanString = restString.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "  ");
		String[] cont2 = cleanString.split("\\|");
		switch (cont2.length) {
			case 1:
				return prfNr + " " + cont2[0].trim();
			case 2:
				return prfNr + " " + cont2[0].trim() + " " + cont2[1].trim();
			case 3:
				return prfNr + " " + cont2[0].trim() + " " + cont2[1].trim() + " " + cont2[2].trim();
			default:
				return prfNr + " " + cont2[0].trim() + " " + cont2[1].trim() + " " + cont2[2].trim() + " " + cont2[3].trim();
		}
	}

}
