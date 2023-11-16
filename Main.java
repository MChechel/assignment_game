import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        MainProcessHandler MainProcessHandler = new MainProcessHandler();

        Map<String, MatchObject> matches = null;
        Map<String, PlayerObject> players = null;
        Map<Integer, ActionObject> actions = null;

        int indicator = 0;
        while(indicator != 999){

            System.out.println(
                "MENU"
                +"\nMain purpose of current menu is provide information and ability to change file names that are used in the programm. "
                +"\nBy default, the names are `match_data.txt` and `player_data.txt`. "
                +"\nCURRENT names are `"+ MainProcessHandler.getMatchesDataFileName() + "` and `" + MainProcessHandler.getPlayersDataFileName() + "`. "
                + "\nExpected that filenames are placed in the same folder as the Main.class"
                    + "\nOPTIONS:"
                    + "\n# 100 - Process DATA;"
                    + "\n# 200 - Update filenames that contain data DATA;"
                    + "\n# 300 - Reset fileName to original values;"
                    + "\n# 999 - LEAVE; ");
            indicator = scan.nextInt();
            System.out.println("############################################");

            switch (indicator){

                case 100:
                    System.out.println("Start processing data.");
                    String reportData = MainProcessHandler.processData();
                    
                    System.out.println("Process finished. Generating the report.");
                    
                    try{

                        generateReport(reportData);
                        System.out.println("Report was succesfully generated - return to main menu.");
                        
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 200:

                    scan.nextLine();
                    try{

                        updateFileNames(MainProcessHandler);    
                        System.out.println("File names are succesfully updated.");
                        
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    break;    
                case 300:

                    MainProcessHandler = new MainProcessHandler();
                    System.out.println("File names are changed back to original ones.");

                    break;
                case 999:

                    System.out.println("Done.");

                    break;                
                default:
                
                    System.out.println("Provided code is unknown!");

                    break;
            }
        }
    }

    private static void updateFileNames(MainProcessHandler MainProcessHandler) throws IOException {
        Scanner scan = new Scanner(System.in);
        int numberOfTriesFOrInput = 5;
        String matchesFileName;
        File matchesFile;
        String playersFileName;
        File playersFile;

        int i = numberOfTriesFOrInput;

        do {

            if (i < numberOfTriesFOrInput) {
                System.out.println("something went wring, probably there is no file with such name."
                        + "\nyou have  " + i + " tries left");
            }
            System.out.println("Please provide filename with data about matches: ");
            matchesFileName = scan.nextLine();
            matchesFile = new File(matchesFileName);
            i--;
        } while (i > 0 && !matchesFile.exists());

        if (!matchesFile.exists()) {
            throw new IOException(
                    "File with provided name could not be found in the folder with main class - process stopped.");
        }

        i = numberOfTriesFOrInput;

        do {
            if (i < numberOfTriesFOrInput) {
                System.out.println("something went wring, probably there is no file with such name."
                        + "\nyou have  " + i + " tries left");
            }

            System.out.println("Please provide filename with data about players: ");
            playersFileName = scan.nextLine();
            playersFile = new File(playersFileName);
            i--;
        } while (i > 0 && !playersFile.exists());

        if (!playersFile.exists()) {
            throw new IOException(
                    "File with provided name could not be found in the folder with main main class - process stopped.");
        }

        MainProcessHandler.setPlayersDataFileName(playersFileName);
        MainProcessHandler.setMatchesDataFileName(matchesFileName);
    }

    private static void generateReport(String reportData) throws IOException {
        String fileName = "result.txt";
        File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File " + fileName + " - already exists.");
        }
        FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(reportData);
        myWriter.close();
    } 
    
}
