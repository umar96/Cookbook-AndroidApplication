package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditTextIngredient extends AppCompatActivity {

    private MainScreen cookBook;
    private String oldIngredient;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_ingredient);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        oldIngredient = (String) getIntent().getExtras().getSerializable("ingredientToEdit");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        TextView myText = (TextView) findViewById(R.id.ingreidentToEditTextView);
        myText.setText(oldIngredient);


        Button save = (Button) findViewById(R.id.editSingleIngredientNameButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (EditTextIngredient.this, EditIngredients.class);

                Bundle b = new Bundle();

                String newIngredient = ((EditText) findViewById(R.id.newIngredientNameEditText)).getText().toString().toUpperCase();

                //If the new name is not equal to null and is not blank.
                if(newIngredient!= null && !newIngredient.equals("")){

                    //if the cookbook doesn't already contain an ingredient with that name.
                    if(!cookBook.get_cookBookIngredients().contains(new Ingredient(newIngredient))){

                        //Loop through all the recipes
                        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){

                            //For each recipe, check if that recipes ingredients contains the old ingredient name.
                            if(cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().contains(new Ingredient(oldIngredient))){

                                //if such a recipe does contain that ingredient, remove that ingredient and add the new one.
                                cookBook.get_cookBookRecipes().get(i).getRecipeIngredients().remove(new Ingredient(oldIngredient));
                                cookBook.get_cookBookRecipes().get(i).addRecipeIngredient(new Ingredient(newIngredient));
                            }

                        }

                        //Now that all the recipes are 'cleansed' remove that old ingredient from cookbook.
                        cookBook.get_cookBookIngredients().remove(new Ingredient(oldIngredient));

                        //And add the new ingredient
                        cookBook.add_cookBookIngredient(newIngredient);

                        Toast.makeText(getApplicationContext(), ("Changed all Names of "+oldIngredient+" to " + newIngredient), Toast.LENGTH_SHORT).show();

                        b.putSerializable("cookBook",cookBook);
                        b.putSerializable("typeList",typeList);
                        b.putSerializable("categoryList",categoryList);

                        intent.putExtras(b);

                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "This Ingredient Already Exists", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), ("Must Enter Ingredient to Edit"), Toast.LENGTH_SHORT).show();

                }


                //Toast.makeText(getApplicationContext(), ingredientChange, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
