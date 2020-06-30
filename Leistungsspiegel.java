import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;


public class Leistungsspiegel {
    private static final Pattern ABSCHLUSS_LINE = Pattern.compile(
            "Abschluss: \\[(\\d+)\\] ([a-zA-Z ]+)"
    );

    private List<Abschluss> abschlüsse;

    Leistungsspiegel(String rawLeistungsspiegelHtml) {
        abschlüsse = new ArrayList<>();

        String preparedLS = prepareLeistungsspiegel(rawLeistungsspiegelHtml);
        System.out.println(preparedLS); //PRINT leistungsspiegel

        //Parse(preparedLS);
    }

    private void Parse(String preparedLS) {
        String[] lines = preparedLS.split("\n");

        int parserAtLineIndex = 0;

        while (parserAtLineIndex < lines.length) {
            if (lines[parserAtLineIndex].startsWith("Abschluss")) {
                Abschluss abschluss = new Abschluss();

                abschlüsse.add(new Abschluss());
            }
        }
    }

    private String prepareLeistungsspiegel(String rawLS) {
        String[] leistungsspiegelTable =
                rawLS.substring(
                        rawLS.indexOf("<table"),
                        rawLS.indexOf("</table>") + 8
                ).split("\n");

        StringBuilder nicerLS = new StringBuilder();

        for (int i = 0; i < leistungsspiegelTable.length; ++i) {
            // get current line, but without unneccassary whitspace
            String newLine = leistungsspiegelTable[i].trim();

            // remove tablerow start tags
            newLine = newLine.replace("<tr>", "");
            // add new lines instead of tablerow end tags
            newLine = newLine.replace("</tr>", "\n");

            // remove blank lines
            if (!newLine.isEmpty()) {
                nicerLS.append(newLine);
            }
        }

        String leistungspiegel = nicerLS.toString();

        // remove html table tags, insert custom seperators (--|--) instead if in middle of row
        leistungspiegel = leistungspiegel.replaceAll("<\\/td><td[a-zA-Z0-9 _=:;\\\"\\-]*>", " --|-- ");
        leistungspiegel = leistungspiegel.replaceAll("<\\/th><th[a-zA-Z0-9 _=:;\\\"\\-]*>", " --|-- ");

        // remove them from start..
        leistungspiegel = leistungspiegel.replaceAll("<td[a-zA-Z0-9 _=:;\\\"\\-]*>", "");
        leistungspiegel = leistungspiegel.replaceAll("<th[a-zA-Z0-9 _=:;\\\"\\-]*>", "");
        // ..and end of line
        leistungspiegel = leistungspiegel.replaceAll("<\\/td>", "");
        leistungspiegel = leistungspiegel.replaceAll("<\\/th>", "");

        // remove italics tags in header line
        leistungspiegel = leistungspiegel.replaceAll("<i>Abschluss", "Abschluss");
        leistungspiegel = leistungspiegel.replaceAll("<i>", "--|-- ");
        leistungspiegel = leistungspiegel.replaceAll("<\\/i>", "");

        // replace Agnes own placeholders with ours
        //leistungspiegel = leistungspiegel.replaceAll("&nbsp;\\|&nbsp;", " --|-- ");

        // all that replacing leaves some strange and useless patterns, remove those too
        leistungspiegel = leistungspiegel.replaceAll("\\-\\-\\|\\-\\- \\| \\-\\-\\|\\-\\-", "--|--");
        leistungspiegel = leistungspiegel.replaceAll("\\-\\-\\|\\-\\-  \\-\\-\\|\\-\\-  \\-\\-\\|\\-\\-", "--|--");

        // add line breaks after each table row
        leistungspiegel = leistungspiegel.replaceAll("</tr>", "\n");

        // at this point, only our delimiters should be left - let's replace them with something a little shorter/nicer
        leistungspiegel = leistungspiegel.replaceAll("\\-\\-\\|\\-\\-", "\t");

        // Lukas Aenderungen
        leistungspiegel = leistungspiegel.replaceAll("&nbsp;", " ");

        // now, re-trim and also remove the first and the last two lines since they are irrelevant to parsing
        nicerLS = new StringBuilder();
        leistungsspiegelTable = leistungspiegel.split("\n");

        Line[] lines = new Line[leistungsspiegelTable.length]; //because of html stuff
        for (int i = 2; i < lines.length - 2; ++i) {
            if (leistungsspiegelTable[i].contains("bereich") && leistungsspiegelTable[i].contains("|")) {
		String[] content = leistungsspiegelTable[i].split("\t", 2);
                String[] cont2 = content[1].replace("\n", "").split("\\|");
                lines[i] = new HeaderLine(content[0], cont2[0], cont2[1]);
            } else {
                int count = leistungsspiegelTable[i].length() - leistungsspiegelTable[i].replaceAll("\t", "").length();
                if (count == 10) {
                    String[] content = leistungsspiegelTable[i].split("\t");
                    lines[i] = new DetailsLine(content[0], content[1], content[2], content[3], content[4], content[5], content[6], content[7], content[8], content[9], content[10]);
                } else {
                    String[] content = leistungsspiegelTable[i].replace("\n", "").split("\t", 2);
                    lines[i] = new CourseLine(content[0], content[1]);
                }
            }
        }

        for (int i = 2; i < leistungsspiegelTable.length - 2; ++i) {
            nicerLS.append(lines[i].toString() + "\n");
        }

        return nicerLS.toString();
    }
}
