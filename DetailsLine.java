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
		this.prfNr = prfNr.trim();
		this.pruefung = pruefung.trim();
		this.prfArt = prfArt.trim();
		this.status = status.trim();
		this.note = note.trim();
		this.lP = lP.trim();
		this.aL = aL.trim();
		this.versuch = versuch.trim();
		this.vermerk = vermerk.trim();
		this.datum = datum.trim();
		this.semester = semester.trim();
	}

	@Override
	public String toString() {
		return prfNr + " " + pruefung + " " + prfArt + " " + status + " " + note  + " " + lP + " " + aL + " " + versuch + " " + vermerk + " " + datum + " " + semester;
	}

}
