public class DetailsLine extends Line {

	public String prfNr;
	public String pruefung;
	public String prfArt;
	public String status;
	public String note;
	public String lP;
	public String aL;
	public String versuch;
	public String vermerk;
	public String datum;
	public String semester;

	public DetailsLine(String prfNr, String pruefung, String prfArt, String status, String note, String lP, String aL, String versuch, String vermerk, String datum, String semester) {
		this.prfNr = prfNr;
		if(pruefung.startsWith(" Norwegian Language and Culture for Foreign Language Students, Level 2")) {
			this.pruefung = " Norwegisch";
		} else {
			this.pruefung = pruefung;
		}
		
		this.prfArt = prfArt;
		this.status = status;
		this.note = note;
		this.lP = lP;
		this.aL = aL;
		this.versuch = versuch;
		this.vermerk = vermerk;
		this.datum = datum;
		this.semester = semester;
	}

	@Override
	public String toString() {
		//String sf1 = String.format("%-40s%s%n",name);
		String str = String.format("%-6s %-50s %-5s %-20s %-5s %-5s %-5s %-3s %-3s %-12s %s", prfNr, pruefung, prfArt, status, note, lP, aL, versuch, vermerk, datum, semester);
		//String str = String.format("%-6s %-60s %s", prfNr, pruefung, prfArt);
		return str;
		//return prfNr + "\t" + pruefung + "\t" + prfArt + "\t" + status + "\t" + note + "\t" + lP + "\t" + aL + "\t" + versuch + "\t" + vermerk + "\t" + datum + "\t" + semester;
	}

}