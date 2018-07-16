package jaked.battleritechampionstatistics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class BattleriteAppFragment extends Fragment {

    public EditText etPlayerName;
    public Button btSearch;
    public String mPlayerName;
    public ChampionLab championLab;

    public static BattleriteAppFragment newInstance() {
        return new BattleriteAppFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();
    }

    // Saves the name that the user entered
    public void saveName(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("PlayerName", mPlayerName);
        editor.commit();
    }

    // Gets the name that the user previously entered
    public void getName(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        mPlayerName = sharedPreferences.getString("PlayerName","");
        etPlayerName.setText(mPlayerName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_name_search, container, false);
        championLab =  ChampionLab.get(getActivity());
        etPlayerName = v.findViewById(R.id.etPlayerName);
        getName();

        btSearch = v.findViewById(R.id.btnSearch);

        // Get the JSON for the given playername
        btSearch.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             mPlayerName = etPlayerName.getText().toString();
             mPlayerName = mPlayerName.substring(0,1).toUpperCase() + mPlayerName.substring(1).toLowerCase();
             if(!mPlayerName.equals("")){
                 new FetchItemTasks().execute();
                 // Prevents the user from sendinf multiple requests
                 btSearch.setClickable(false);
             }
         }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        btSearch.setClickable(true);
        ChampionLab championLab = ChampionLab.get(getActivity());
        championLab.clear();
    }

    private class FetchItemTasks extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params){

            new JsonFetcher().fetchItems(mPlayerName);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            List<Champion> champions = championLab.getChampionsByName();
            if(champions.size() > 0){
                saveName();
                Intent i = new Intent(getActivity(), UserOverviewActivity.class);
                startActivity(i);
            }
            // If the player doesn't exist, allow the user to search again.
            else{
                Toast.makeText(getActivity(), "Player not found!", Toast.LENGTH_SHORT).show();
                btSearch.setClickable(true);
            }

        }
    }



}
