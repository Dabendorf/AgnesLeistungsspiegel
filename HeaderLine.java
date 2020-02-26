public class HeaderLine extends Line {

	public String prfNr;
	public String name;
	public String punkte;

	public HeaderLine(String prfNr, String name, String punkte) {
		this.prfNr = prfNr;
		this.name = name;
		this.punkte = punkte;
	}

	@Override
	public String toString() {
		String str = String.format("%-6s %-50s %s\n", prfNr, name, punkte);
		return str;
	}

}