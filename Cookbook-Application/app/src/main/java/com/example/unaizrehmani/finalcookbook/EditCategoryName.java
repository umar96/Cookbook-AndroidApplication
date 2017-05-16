package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class EditCategoryName extends AppCompatActivity {

    private MainScreen cookBook;
    private String oldCategory;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category_name);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        oldCategory = (String) getIntent().getExtras().getSerializable("ingredientToEdit");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        TextView myText = (TextView) findViewById(R.id.categoryToEditTextView);
        myText.setText(oldCategory);

        Button save = (Button) findViewById(R.id.editSingleCategoryNameButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (EditCategoryName.this, EditCategoryType.class);

                Bundle b = new Bundle();

                String newCategory = ((EditText) findViewById(R.id.newCategoryNameEditText)).getText().toString().toUpperCase();

                //If the new name is not equal to null and is not blank.
                if(newCategory!= null && !newCategory.equals("")){

                    //if the cookbook doesn't already contain an ingredient with that name.
                    if(!categoryList.contains(newCategory)){

                        //Loop through all the recipes
                        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){

                            //For each recipe, check if that recipes ingredients contains the old ingredient name.
                            if(cookBook.get_cookBookRecipes().get(i).getRecipeCategory().equals(oldCategory)){

                                //Toast.makeText(getApplicationContext(), cookBook.get_cookBookRecipes().get(i).getRecipeName() + " changed from " + cookBook.get_cookBookRecipes().get(i).getRecipeCategory(), Toast.LENGTH_SHORT).show();

                                cookBook.get_cookBookRecipes().get(i).setRecipeCategory(newCategory);

                                //Toast.makeText(getApplicationContext(), " to: " + cookBook.get_cookBookRecipes().get(i).getRecipeCategory(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        categoryList.remove(oldCategory);
                        categoryList.add(newCategory);

                        try{
                            Collections.sort(categoryList);
                        }catch(Exception e){

                        }


                        Toast.makeText(getApplicationContext(), ("Changed all Names of "+oldCategory+" to " + newCategory), Toast.LENGTH_SHORT).show();

                        b.putSerializable("cookBook",cookBook);
                        b.putSerializable("typeList",typeList);
                        b.putSerializable("categoryList",categoryList);

                        intent.putExtras(b);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "This Category Already Exists", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(getApplicationContext(), ("Must Enter a Category to Edit"), Toast.LENGTH_SHORT).show();

                }


                //Toast.makeText(getApplicationContext(), ingredientChange, Toast.LENGTH_SHORT).show();
            }
        });

    }



}


