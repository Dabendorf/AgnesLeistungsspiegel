import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

public class Main {
    public static void main(String[] args) {
        String link = "";
        UI ui = new UI();

        ui.Log("Welcome to AgnesLeistungsspiegel!");

        ui.Log("Initializing Agnes-adapter..");
        AgnesAdapter agnes = new AgnesAdapter();
        ui.Log("Agnes-adapter initialized");

        String user = null;
        String pass = null;

        //file containts two lines seperated by newline, first username, second password
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("login.txt"));
            String line = reader.readLine();
            if(line != null) {
                user = line;
            }
            line = reader.readLine();
            if(line != null) {
                pass = line;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO Hier PW und Nutzername eingeben
        //String user = ui.getLine("Please enter your Agnes user name");
        // TODO: validate user, eg only lowercase, max 8 char, text only
        //String pass = ui.getLine("Please enter your Agnes password", true);

        ui.Log("Contacting Agnes..");
        AgnesAdapter.AgnesLogonResult result = agnes.signIn(user, pass);

        if (result.loggedOn) {
            ui.Log("Successfully signed into Agnes");

            ui.Log("Getting start page to parse for session specific link to the Leistungsspiegel-page..");
            link = agnes.getLeistungsspiegelLinkFromStartPage(result.cookie);

            ui.Log("Getting Leistungsspiegel-page to parser for detail-page link..");
            link = agnes.getLeistungspiegelDetailLinkFromLeistungspiegelPage(link, result.cookie);

            ui.Log("Getting (raw) Leistungsspiegel from detail-page..");
            String rawLeistungsspiegel = agnes.getRawLeistungsspiegel(link, result.cookie);

            ui.Log("Parsing raw Leistungsspiegel..");
            Leistungsspiegel ls = new Leistungsspiegel(rawLeistungsspiegel);

            ui.Log("Done");
        } else {
            ui.Log("Failed to sign into Agnes: " + result.responseMessage + "(" + result.responseCode + ")");
        }
    }
}
