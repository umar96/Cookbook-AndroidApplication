package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class singleRecipeScreen extends AppCompatActivity {

    //Retrieves the recipe informing the layout from previous activity. Other variables are auxillary.
    private Recipe recipe;
    private ArrayList<Ingredient> recipeIngredients = new ArrayList<Ingredient>();
    private ArrayList<String> recipeStringIngredients = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe_screen);

        recipe  = (Recipe) getIntent().getExtras().getSerializable("currentRecipe");
        recipeIngredients = recipe.getRecipeIngredients();

        for(int i = 0; i<recipeIngredients.size(); i++){
            recipeStringIngredients.add(recipeIngredients.get(i).get_IngredientName());
        }

        //Sets the text of recipe info for all options.
        TextView recipeName = (TextView) findViewById(R.id.recipeNamePopUpTextView);
        recipeName.setText(recipe.getRecipeName());

        TextView recipeCookTime = (TextView) findViewById(R.id.getCookTextView);
        recipeCookTime.setText(Integer.toString(recipe.get_cookTime()));

        TextView prepTime = (TextView) findViewById(R.id.getPrepTextView);
        prepTime.setText(Integer.toString(recipe.get_prepTime()));

        TextView calories = (TextView) findViewById(R.id.getCaloriesTextView);
        calories.setText(Integer.toString(recipe.get_calories()));

        TextView category = (TextView) findViewById(R.id.getCategoryTextView);
        category.setText(recipe.getRecipeCategory());

        TextView type = (TextView) findViewById(R.id.getTypeTextView);
        type.setText(recipe.getRecipeType());

        //Populates listview with the ingredients used in the respective recipe.
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(singleRecipeScreen.this,android.R.layout.simple_list_item_1,recipeStringIngredients);
        ListView myList = (ListView) findViewById(R.id.recipePopUpListView);
        myList.setAdapter(myAdapter);

    }

    //Used to view the directions of the recipe.
    public void clickDirectionsButton(View view){

        Intent intent = new Intent(singleRecipeScreen.this,SingleRecipeDirections.class);

        Bundle b = new Bundle();
        b.putSerializable("directions",recipe.getRecipeDirections());

        intent.putExtras(b);
        startActivity(intent);
    }
}
