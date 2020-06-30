public class HeaderLine extends Line {

	public String prfNr;
	public String name;
	public String punkte;

	public HeaderLine(String prfNr, String name, String punkte) {
		this.prfNr = prfNr.trim();
		this.name = name.trim();
		this.punkte = punkte.trim();
	}

	@Override
	public String toString() {
		return prfNr + " " + name + " " + punkte;
	}

}
