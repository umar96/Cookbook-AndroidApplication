package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class EditRecipes extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipes);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

    }

    public void clickAddRecipe(View view){

        Intent intent = new Intent(EditRecipes.this,AddRecipe.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        b.putSerializable("typeList",typeList);
        b.putSerializable("categoryList",categoryList);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void clickEditRecipe(View view){

        Intent intent = new Intent(EditRecipes.this,EditAllRecipes.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        b.putSerializable("typeList",typeList);
        b.putSerializable("categoryList",categoryList);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void clickDeleteRecipe(View view){
        Intent intent = new Intent(EditRecipes.this,DeleteRecipe.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        b.putSerializable("typeList",typeList);
        b.putSerializable("categoryList",categoryList);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void clickEditCategoryType(View view){
        Intent intent = new Intent(EditRecipes.this,EditCategoryType.class);
        Bundle b = new Bundle();
        b.putSerializable("cookBook",cookBook);
        b.putSerializable("typeList",typeList);
        b.putSerializable("categoryList",categoryList);
        intent.putExtras(b);
        startActivity(intent);
    }
}
