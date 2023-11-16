public class ActionObject {
    private int id;
    private String actionType;
    private String playerUid;
    private int amount;
    private String matchUid;
    private String matchSide;


    public ActionObject(int id, String action, String playerUid, String matchUid, int amount, String side) { 
        this.id = id;
        this.actionType = action;
        this.playerUid = playerUid;
        this.matchUid = matchUid;
        this.amount = amount;
        this.matchSide = side;
    }

    public ActionObject(int id, String action, String playerUid, int amount) { 
        this.id = id;
        this.actionType = action;
        this.playerUid = playerUid;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getActionType() {
        return actionType;
    }

    public String getPlayerUid() {
        return playerUid;
    }
    
    public String getMatchUid() {
        return matchUid;
    }

    public String getMatchSide() {
        return matchSide;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ActionObject{" +
                "id='" + id + '\'' +
                "actionType='" + actionType + '\'' +
                "playerUid='" + playerUid + '\'' +
                "amount='" + amount + '\'' +
                "matchUid='" + matchUid + '\'' +
                "matchSide='" + matchSide + '\'' +
                '}';
    }


    public String dataForReport() {
        return 
                playerUid + ' ' +
                actionType + ' ' +
                matchUid + ' ' +
                amount + ' ' +
                matchSide
                ;
    }
}