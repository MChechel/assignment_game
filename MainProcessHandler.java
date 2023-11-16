
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class MainProcessHandler {

    private String matchesDataFileName;
    private String playersDataFileName;

    public Map<String, MatchObject> matches;
    public Map<String, PlayerObject> players;
    public Map<Integer, ActionObject> actions;
    public String playersData;
    public String illigalActionsData;
    public int hostBalance;
    Map<String, Integer> hostData;

    public MainProcessHandler(){
        this.hostData = new HashMap<>();
        this.matchesDataFileName = "match_data.txt";
        this.playersDataFileName = "player_data.txt";
    }

    public void setMatchesDataFileName(String fileName){
        this.matchesDataFileName = fileName;
    }

    public String getMatchesDataFileName(){
        return this.matchesDataFileName;
    }

    public void setPlayersDataFileName(String fileName){
        this.playersDataFileName = fileName;
    }

    public String getPlayersDataFileName(){
        return this.playersDataFileName;
    }

    public void addMatches() throws IOException {
        FileReadHelper FileReadHelper = new FileReadHelper(this.matchesDataFileName);

        this.matches =  FileReadHelper.getMatchedData();
    }

    public void addPlayers() throws IOException {
        FileReadHelper FileReadHelper = new FileReadHelper(this.playersDataFileName);

        this.players =  FileReadHelper.addPlayers();

        this.players.forEach((key, value) -> {
            this.hostData.put(key, 0);
        });
    }

    public void addPlayersActions() throws IOException {

        FileReadHelper FileReadHelper = new FileReadHelper(this.playersDataFileName);

        this.actions =  FileReadHelper.addPlayersActions();

    }

    public String processData() throws IOException {
        playersData = "";
        illigalActionsData = "";
        hostBalance = 0;
        
        this.addMatches();
        this.addPlayers();
        this.addPlayersActions();
        this.actions.forEach((key, action) -> {
            this.processAction(action);
        });

        this.players.forEach((key, value) -> {
            if( ! value.getHasIllegalTransactionFlag() ){
                this.addPlayerData(value);
            }
        });
        this.hostData.forEach((key, value) -> this.hostBalance += value);

        return this.playersData + "\n" +  this.illigalActionsData + "\n" + this.hostBalance;
    }

    private void processAction(ActionObject action){

        PlayerObject player = this.players.get(action.getPlayerUid());
        
        if(player.getHasIllegalTransactionFlag()){
            return;
        }

        switch (action.getActionType()){
            case "DEPOSIT":
                player.depositAmount(action.getAmount());
                break;

            case "WITHDRAW":
                player.withdrawAmount(action.getAmount());
                if( player.getBalance() < 0 ){
                    player.setHasIllegalTransactionFlag(true);
                    this.addIllegalAction(action);
                    this.hostData.remove(action.getPlayerUid());
                }
                break;

            case "BET":
                if( action.getMatchUid().isEmpty() || action.getMatchUid() == null){
                    break;
                }
                MatchObject match = this.matches.get(action.getMatchUid());

                int betAmount = action.getAmount();
                String betSide = action.getMatchSide();
                String outcome = match.getOutcome(); 
                float coeficient = 0;

                player.placeBet(betAmount);

                if( player.getBalance() < 0 ){
                    player.setHasIllegalTransactionFlag(true);
                    this.addIllegalAction(action);
                    this.hostData.remove(action.getPlayerUid());
                    break;
                }

                if(outcome.equals("DRAW")){

                    player.returnBet(betAmount);
                    break;
                }

                if( outcome.equals( betSide ) ){
                
                    if( betSide.equals("A")){
                        coeficient = match.getSideValueA();
                    }else{
                        coeficient = match.getSideValueB();
                    }

                    this.hostData.put(
                        action.getPlayerUid(), 
                        this.hostData.get(action.getPlayerUid()) - (int)(betAmount * coeficient)
                    );
                    player.winBet(betAmount, coeficient);
                }else{
                    this.hostData.put(action.getPlayerUid(), this.hostData.get(action.getPlayerUid()) + betAmount);
                }
                break;
            default:
                System.out.println("Wrong action was provided!");
                break;
            }

    }

    private void addIllegalAction(ActionObject action){
        this.illigalActionsData += action.dataForReport()+"\n";
    }

    private void addPlayerData(PlayerObject player){
        this.playersData += player.dataForReport()+"\n";
    }
}
