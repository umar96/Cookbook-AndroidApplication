package com.example.unaizrehmani.finalcookbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SingleRecipeDirections extends AppCompatActivity {

    private ArrayList<String> directions = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe_directions);

        //Directions instantiated from previous activity.
        directions = (ArrayList<String>) getIntent().getExtras().getSerializable("directions");

        //Populates listview.
        ListView myListView = (ListView) findViewById(R.id.getDirectionsListView);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SingleRecipeDirections.this,android.R.layout.simple_list_item_1,directions);
        myListView.setAdapter(myAdapter);
    }
}
