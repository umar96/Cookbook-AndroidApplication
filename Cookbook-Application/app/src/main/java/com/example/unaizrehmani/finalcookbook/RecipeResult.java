package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeResult extends AppCompatActivity {

    //Reference of original instantiation and recipe results from previous activities to inform ths one.
    private MainScreen cookBook;
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> typeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_result);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        recipes = (ArrayList<Recipe>) getIntent().getExtras().getSerializable("result");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");

        populateListView();

        //Set button to allow user to return to the home screen once satisfied with the choices.
        Button goHome = (Button) findViewById(R.id.homeScreenButton);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeResult.this,MainScreen.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("cookBook",cookBook);
                bundle.putSerializable("typeList",typeList);
                bundle.putSerializable("categoryList",categoryList);

                intent.putExtras(bundle);

                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        });
    }

    //This custom listview displays recipes using buttons.
    public void populateListView(){
        ListView listView = (ListView) findViewById(R.id.finalRecipeResult);
        ArrayAdapter<Recipe> recipeArrayAdapter = new RecipeAdapter(getApplicationContext(),recipes);
        listView.setAdapter(recipeArrayAdapter);
    }

    //Used to instantiate Adapter in populateListView method
    private class RecipeAdapter extends ArrayAdapter<Recipe>{


        private Context context;

        public RecipeAdapter(Context context, ArrayList<Recipe> objects) {
            super(context, R.layout.single_recipe_black, objects);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe_black,parent,false);

            final Recipe curRecipe = getItem(position);

            String currentIngredient = curRecipe.getRecipeName();

            final int pos = position;

            //Sets the text of the button to the name of the recipe.
            Button recipeButton = (Button) customView.findViewById(R.id.button_designBlack);
            recipeButton.setText(currentIngredient);

            //Sets a click listener which links information of that recipe to the next activity.
            recipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe currentRecipe = recipes.get(pos);

                    Intent intent = new Intent(RecipeResult.this,singleRecipeScreen.class);

                    //Recipe info is passed based on button clicked.
                    Bundle b = new Bundle();
                    b.putSerializable("currentRecipe",currentRecipe);

                    intent.putExtras(b);

                    startActivity(intent);
                }
            });

            return customView;
        }

    }
}

