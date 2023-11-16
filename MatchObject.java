public class MatchObject {
    private String uid;
    private float sideValueA;
    private float sideValueB;
    private String outcome;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getSideValueA() {
        return sideValueA;
    }

    public void setSideValueA(float sideValueA) {
        this.sideValueA = sideValueA;
    }

    public float getSideValueB() {
        return sideValueB;
    }

    public void setSideValueB(float sideValueB) {
        this.sideValueB = sideValueB;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }


    @Override
    public String toString() {
        return "MatchObject{" +
                "uid='" + uid + '\'' +
                "sideValueA='" + sideValueA + '\'' +
                "sideValueB='" + sideValueB + '\'' +
                "outcome='" + outcome + '\'' +
                '}';
    }
}
