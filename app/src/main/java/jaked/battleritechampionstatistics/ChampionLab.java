package jaked.battleritechampionstatistics;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChampionLab {

    private List<Champion> mChampions;
    private static ChampionLab sChampionLab;
    private Context mAppContext;
    private String mTotalWins;
    private String mTotalLosses;
    private String mAccountLevel;

    private ChampionLab(Context appContext){
        mAppContext = appContext;
        mChampions  = new ArrayList<Champion>();
    }

    // Returns an alphebtically sorted list of champions
    public List<Champion> getChampionsByName(){

        Collections.sort(mChampions, new Comparator<Champion>() {
            @Override
            public int compare(Champion champion, Champion championTwo) {
                return champion.getName().compareTo(championTwo.getName());
            }
        });
        

        return mChampions;
    }

    // Returns a list of chapmions sorted by wins (highest to lowest)
    public List<Champion> getChampionsByWins(){
        Collections.sort(mChampions, new Comparator<Champion>() {
            @Override
            public int compare(Champion champion, Champion championTwo) {
                return Integer.parseInt(championTwo.getWins()) - Integer.parseInt(champion.getWins());
            }
        });
        return mChampions;
    }

    // Returns a list of chapmions sorted by losses (highest to lowest)
    public List<Champion> getChampionsByLosses(){
        Collections.sort(mChampions, new Comparator<Champion>() {
            @Override
            public int compare(Champion champion, Champion championTwo) {
                return Integer.parseInt(championTwo.getLosses()) - Integer.parseInt(champion.getLosses());
            }
        });
        return mChampions;
    }

    // Returns a list of chapmions sorted by play time (highest to lowest)
    public List<Champion> getChampionsByTimePlayed(){
        Collections.sort(mChampions, new Comparator<Champion>() {
            @Override
            public int compare(Champion champion, Champion championTwo) {
                return Integer.parseInt(championTwo.getTimePlayed()) - Integer.parseInt(champion.getTimePlayed());
            }
        });
        return mChampions;
    }



    public void addChampion(Champion c)
    {
        mChampions.add(c);
    }

    public void clear(){
        mChampions.clear();
    }

    // Creates a new ChampionLab singleton if one doesn't exist already
    public static ChampionLab get (Context context){
        if(sChampionLab == null){
            sChampionLab = new ChampionLab(context.getApplicationContext());
        }
        return sChampionLab;
    }


    public String getTotalWins() {
        return mTotalWins;
    }

    public void setTotalWins(String totalWins) {
        mTotalWins = totalWins;
    }

    public String getTotalLosses() {
        return mTotalLosses;
    }

    public void setTotalLosses(String totalLosses) {
        mTotalLosses = totalLosses;
    }

    public String getAccountLevel() {
        return mAccountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        mAccountLevel = accountLevel;
    }
}
