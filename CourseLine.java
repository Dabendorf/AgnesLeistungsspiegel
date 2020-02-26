public class CourseLine extends Line {

	public String prfNr;
	public String restString;

	public CourseLine(String prfNr, String restString) {
		this.prfNr = prfNr;
		this.restString = restString.replace("\n", "");
	}

	@Override
	public String toString() {
		this.restString = restString.replace("\n", "");
		this.restString = restString.replace("\r", "");
		String cleanString = restString.replaceAll("\r", "").replaceAll("\n", "");
		String[] cont2 = cleanString.split("\\|");
		String str = "";
		if(cont2.length==2) {
			str = String.format("%-6s %-50s %s\n", prfNr, cont2[0], cont2[1]);
		} else if(cont2.length==3) {
			str = String.format("%-6s %-50s %-20s %s\n", prfNr, cont2[0], cont2[1], cont2[2]);
		} else if (cont2.length==1){
			str = String.format("%-6s %s\n", prfNr, cont2[0]);
		} else {
			str = String.format("%-6s %-50s %-20s %-20s %s\n", prfNr, cont2[0], cont2[1], cont2[2], cont2[3]);
		}
		
		return str;
	}

}