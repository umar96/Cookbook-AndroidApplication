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

public class EditTypeName extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();
    private String oldType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_type_name);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        oldType = (String) getIntent().getExtras().getSerializable("oldType");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        TextView myText = (TextView) findViewById(R.id.typeToEditTextView);
        myText.setText(oldType);

        Button save = (Button) findViewById(R.id.editSingleTypeNameButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (EditTypeName.this, EditType.class);

                Bundle b = new Bundle();

                String newCategory = ((EditText) findViewById(R.id.newTypeNameEditText)).getText().toString().toUpperCase();

                //If the new name is not equal to null and is not blank.
                if(newCategory!= null && !newCategory.equals("")){

                    //if the cookbook doesn't already contain an ingredient with that name.
                    if(!typeList.contains(newCategory)){

                        //Loop through all the recipes
                        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){

                            //For each recipe, check if that recipes ingredients contains the old ingredient name.
                            if(cookBook.get_cookBookRecipes().get(i).getRecipeType().equals(oldType)){

                               // Toast.makeText(getApplicationContext(), cookBook.get_cookBookRecipes().get(i).getRecipeName() + " changed from " + cookBook.get_cookBookRecipes().get(i).getRecipeType(), Toast.LENGTH_SHORT).show();

                                cookBook.get_cookBookRecipes().get(i).setRecipeType(newCategory);

                                //Toast.makeText(getApplicationContext(), " to: " + cookBook.get_cookBookRecipes().get(i).getRecipeType(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        typeList.remove(oldType);
                        typeList.add(newCategory);

                        try{
                            Collections.sort(typeList);
                        }catch(Exception e){

                        }


                        Toast.makeText(getApplicationContext(), ("Changed all Names of "+oldType+" to " + newCategory), Toast.LENGTH_SHORT).show();

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
