
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;


public class FileReadHelper {
    String filePathName;

    public FileReadHelper(String filePathAndName){
        this.filePathName = filePathAndName;
    }

    public  Map<String, MatchObject> getMatchedData() {

        try {

            Map<String, MatchObject> matchesList= new TreeMap<>();
            List<String> productLines = Files.readAllLines(Path.of("./" + this.filePathName), StandardCharsets.UTF_8);

            for (String line: productLines){
                MatchObject matchObject = new MatchObject();
                String[] tokens = line.split(",");
                matchObject.setUid(tokens[0]);
                matchObject.setSideValueA(Float.valueOf(tokens[1]));
                matchObject.setSideValueB(Float.valueOf(tokens[2]));
                matchObject.setOutcome(tokens[3]);

                matchesList.put(tokens[0], matchObject);
            }
            return matchesList;
        } catch (
        IOException e) {
            e.printStackTrace();
        }
            return null;
    }

  
    public Map<String, PlayerObject> addPlayers() {

        try {

            Map<String, PlayerObject> players= new TreeMap<>();
            List<String> actionsData = Files.readAllLines(Path.of("./" + this.filePathName), StandardCharsets.UTF_8);

            String prevPlayerUid = "";
            for (String line: actionsData){
                String[] tokens = line.split(",");
                String playerUid = tokens[0];
                if( ! playerUid.equals(prevPlayerUid) ) {
                    players.put(playerUid, new PlayerObject(playerUid));
                    prevPlayerUid = playerUid;
                }
            }
            return players;
        } catch (
        IOException e) {
            e.printStackTrace();
        }
            return null;
    }
  
    public Map<Integer, ActionObject> addPlayersActions() {

        try {

            Map<Integer, ActionObject> actions= new TreeMap<>();
            List<String> actionsData = Files.readAllLines(Path.of("./" + this.filePathName), StandardCharsets.UTF_8);
            int lineIndex = 1;
            for (String line: actionsData){

                String[] tokens = line.split(",");

                if( tokens[1].equals("BET") ) {
                    actions.put(lineIndex, new ActionObject(
                        lineIndex, 
                        tokens[1],
                        tokens[0],
                        tokens[2],
                        Integer.valueOf(tokens[3]),
                        tokens[4]
                        ));
                }else{
                    actions.put(lineIndex, new ActionObject(
                        lineIndex, 
                        tokens[1],
                        tokens[0],
                        Integer.valueOf(tokens[3])
                        ));
                }
                lineIndex++;
            }
            return actions;
        } catch (
        IOException e) {
            e.printStackTrace();
        }
            return null;
    }
}
