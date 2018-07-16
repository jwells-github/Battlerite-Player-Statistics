package jaked.battleritechampionstatistics;

public class Champion {

    private String mName;
    private String mXP;
    private String mWins;
    private String mLosses;
    private String mTimePlayed;
    private String mLevel;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getXP() {
        return mXP;
    }

    public void setXP(String XP) {
        mXP = XP;
    }

    public String getWins() {
        return mWins;
    }

    public void setWins(String wins) {
        mWins = wins;
    }

    public String getLosses() {
        return mLosses;
    }

    public void setLoses(String loses) {
        mLosses = loses;
    }

    // TimePlayed is given in seconds, we convert it to hours.
    public String getTimePlayed() {
        return  String.valueOf((Integer.parseInt(mTimePlayed) / 60) / 60);
    }

    public void setTimePlayeD(String TimePlayed) {
        mTimePlayed = TimePlayed;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }
}
