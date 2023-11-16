public class PlayerObject {
    private String uid;
    private int balance;
    private Boolean hasIllegalTransactionFlag;
    private int totalBetsCount;
    private int wonBetsCount;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public Boolean getHasIllegalTransactionFlag() {
        return hasIllegalTransactionFlag;
    }

    public void setHasIllegalTransactionFlag(Boolean hasIllegalTransactionFlag) {
        this.hasIllegalTransactionFlag = hasIllegalTransactionFlag;
    }

    public void depositAmount(int amount) {
        this.balance += amount;
    }

    public void withdrawAmount(int amount) {
        this.balance -= amount;
    }

    public PlayerObject (String uid) {
        this.uid = uid;
        this.hasIllegalTransactionFlag = false;
        this.balance = 0;
        this.totalBetsCount = 0;
        this.wonBetsCount = 0;
    } 

    @Override
    public String toString() {
        return "PlayerObject{" +
                "uid='" + uid + '\'' +
                "balance='" + balance + '\'' +
                "winningRate='" + this.getWinningRate() + '\'' +
                "totalBetsCount='" + totalBetsCount + '\'' +
                "wonBetsCount='" + wonBetsCount + '\'' +
                "hasIllegalTransactionFlag='" + hasIllegalTransactionFlag + '\'' +
                '}';
    }
    
    public float getWinningRate() {
        if( this.totalBetsCount > 0 ) {

            return Float.valueOf(this.wonBetsCount)/Float.valueOf(this.totalBetsCount);
        }else{

            return 0;
        }
    }
    
    public void increaseTotalBetCount() {
        this.totalBetsCount ++;
    }

    public void increaseWonBetCount() {
        this.wonBetsCount ++;
    }

    public void winBet(int amount, float coeficient){
        this.increaseWonBetCount();
        this.balance += Math.floor(amount * ( 1 + coeficient ));
    }

    public void returnBet(int amount){
        this.balance += amount;
    }

    public void placeBet(int amount){
        this.increaseTotalBetCount();
        this.balance -= amount;
    }

    public String dataForReport() {
        return 
                uid + ' ' +
                balance + ' ' +
                String.format("%.2f", getWinningRate())
                ;
    }

}

