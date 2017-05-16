package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddRecipeDirections extends AppCompatActivity {

    //These are the comments in AddRecipeDirections

    private MainScreen cookBook;
    private String recipeName;
    private String chosenCategory;
    private String chosenType;
    private ArrayList<Ingredient> chosenAddIngredients;
    private int prepIntTime;
    private int cookIntTime;
    private int caloriesIntTime;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    private ArrayList<String> directions = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_directions);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        recipeName = (String) getIntent().getExtras().getSerializable("recipeName");
        chosenCategory = (String)getIntent().getExtras().getSerializable("chosenCategory");
        chosenType = (String)getIntent().getExtras().getSerializable("chosenType");
        chosenAddIngredients = (ArrayList<Ingredient>) getIntent().getExtras().getSerializable("chosenAddIngredients");
        prepIntTime = (Integer) getIntent().getExtras().getSerializable("prepIntTime");
        cookIntTime = (Integer) getIntent().getExtras().getSerializable("cookIntTime");
        caloriesIntTime = (Integer) getIntent().getExtras().getSerializable("caloriesIntTime");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");


        populateListView();

        Button add = (Button) findViewById(R.id.addRecipeDirectionStepButton);
        Button finish = (Button) findViewById(R.id.createRecipeFinalButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addRecipeDirectionEditText);
                String userStringInput = userInput.getText().toString();
                userInput.setText("");

                if(userStringInput!=null && !userStringInput.equals("")){
                    directions.add((directions.size()+1) + ") " + userStringInput);
                    populateListView();
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(directions.size() == 0){
                    Toast.makeText(getApplicationContext(), "Need to Add at Least One Step.", Toast.LENGTH_SHORT).show();
                } else {
                    Recipe recipe = new Recipe(recipeName,chosenCategory,chosenType,chosenAddIngredients,directions,prepIntTime,cookIntTime,caloriesIntTime);
                    Toast.makeText(getApplicationContext(), ("Added " + recipe + " to Cook Book."), Toast.LENGTH_SHORT).show();

                    cookBook.add_cookBookRecipe(recipe);

                    Intent intent = new Intent(AddRecipeDirections.this,MainScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cookBook", cookBook);
                    bundle.putSerializable("typeList",typeList);
                    bundle.putSerializable("categoryList",categoryList);
                    intent.putExtras(bundle);

                    intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                }

            }
        });

    }

    public void populateListView(){
        ArrayAdapter<String> stringArrayAdapter = new CategoryAdapter(getApplicationContext(),directions);
        final ListView ingredientListView = (ListView) findViewById(R.id.addRecipeDirectionListView);
        ingredientListView.setAdapter(stringArrayAdapter);

    }

    private class CategoryAdapter extends ArrayAdapter<String>{


        private Context context;

        public CategoryAdapter(Context context, ArrayList<String> objects) {
            super(context, R.layout.single_recipe_white, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe_white,parent,false);

            EditText userInput = (EditText) findViewById(R.id.addRecipeDirectionEditText);
            userInput.setTextColor(Color.WHITE);

            final String curCategory = getItem(position);

            //boolean selection = curIngredient.is_selected();

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button_designWhite);
            ingredientText.setText(curCategory);

            ingredientText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    EditText userInput = (EditText) findViewById(R.id.addRecipeDirectionEditText);
                    userInput.setTextColor(Color.WHITE);
                    userInput.setText(curCategory);

                    //Toast.makeText(getApplicationContext(), "Clicked: " + currentRecipe.get_IngredientName(), Toast.LENGTH_SHORT).show();
                    //populateListView();
                }
            });

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }


}
