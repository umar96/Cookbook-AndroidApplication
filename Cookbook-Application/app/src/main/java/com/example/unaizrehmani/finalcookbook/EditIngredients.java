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

import static com.example.unaizrehmani.finalcookbook.R.id.finishedAddingButton;

public class EditIngredients extends AppCompatActivity {

    private MainScreen cookBook;

    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        EditText userInput = (EditText) findViewById(R.id.addIngredientInput);
        userInput.setTextColor(Color.WHITE);

        Bundle bundle = getIntent().getExtras();

        cookBook = (MainScreen) bundle.getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        populateListView();

        Button addButton = (Button)findViewById(R.id.addIngredientButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addIngredientInput);

                String userInputString = userInput.getText().toString();
                userInput.setText("");

                if(!cookBook.get_cookBookIngredients().contains(new Ingredient(userInputString))) {

                    if(userInputString != null && !userInputString.equals("")){
                        cookBook.add_cookBookIngredient(userInputString);
                        populateListView();

                        Toast toast = Toast.makeText(getApplicationContext(), ("Ingredient Added: " + userInputString), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Must Enter an Ingredient to Add", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ingredient Already stored", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button deleteButton = (Button)findViewById(R.id.button_deleteSingleIngredient);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addIngredientInput);

                String userInputString = userInput.getText().toString();
                userInput.setText("");

                if(userInputString!= null || !userInputString.equals("")){
                    if(cookBook.get_cookBookIngredients().contains(new Ingredient(userInputString))){
                        cookBook.delete_cookBookIngredient(userInputString);

                        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
                            if(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().contains(new Ingredient(userInputString))){
                                cookBook.get_cookBookRecipes().remove(i);
                            }
                        }

                        populateListView();

                        Toast.makeText(getApplicationContext(),("Ingredient deleted: " + userInputString),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),("No such Ingredient: " + userInputString),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),("Must Enter Ingredient to Delete"),Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button editButton = (Button)findViewById(R.id.button_editSingleIngredient);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientToEdit = ((EditText)findViewById(R.id.addIngredientInput)).getText().toString();

                if(!ingredientToEdit.equals("") && ingredientToEdit!=null){


                    if(cookBook.get_cookBookIngredients().contains(new Ingredient(ingredientToEdit))){
                        Intent intent = new Intent(EditIngredients.this,EditTextIngredient.class);

                        Bundle b = new Bundle();
                        b.putSerializable("ingredientToEdit",ingredientToEdit);
                        b.putSerializable("cookBook",cookBook);
                        b.putSerializable("typeList",typeList);
                        b.putSerializable("categoryList",categoryList);

                        intent.putExtras(b);
                        startActivity(intent);

                    }else{
                        EditText userInput = (EditText) findViewById(R.id.addIngredientInput);
                        userInput.setText("");
                        Toast.makeText(getApplicationContext(), "No such Ingredient: " + ingredientToEdit, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Type or click an Ingredient to Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button finished = (Button)findViewById(finishedAddingButton);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditIngredients.this,MainScreen.class);

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

    public void populateListView(){
        ArrayAdapter<Ingredient> stringArrayAdapter = new IngredientAdapter(getApplicationContext(),cookBook.get_cookBookIngredients());
        final ListView ingredientListView = (ListView) findViewById(R.id.currentIngredientListView);
        ingredientListView.setAdapter(stringArrayAdapter);

    }

    private class IngredientAdapter extends ArrayAdapter<Ingredient>{


        private Context context;

        public IngredientAdapter(Context context, ArrayList<Ingredient> objects) {
            super(context, R.layout.single_recipe_white, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe_white,parent,false);

            EditText userInput = (EditText) findViewById(R.id.addIngredientInput);
            userInput.setTextColor(Color.WHITE);

            final Ingredient curIngredient = getItem(position);

            String currentIngredient = curIngredient.get_IngredientName();
            //boolean selection = curIngredient.is_selected();

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button_designWhite);
            ingredientText.setText(currentIngredient);

            ingredientText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ingredient currentRecipe = cookBook.get_cookBookIngredients().get(pos);

                    EditText userInput = (EditText) findViewById(R.id.addIngredientInput);
                    userInput.setTextColor(Color.WHITE);
                    userInput.setText(currentRecipe.get_IngredientName());

                    //Toast.makeText(getApplicationContext(), "Clicked: " + currentRecipe.get_IngredientName(), Toast.LENGTH_SHORT).show();
                    //populateListView();
                }
            });

            //comment

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }


}
