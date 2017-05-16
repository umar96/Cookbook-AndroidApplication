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

public class EditAllRecipes extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_recipes);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        populateListView();

    }

    public void populateListView(){
        ListView listView = (ListView) findViewById(R.id.getEditRecipeListView);
        ArrayAdapter<Recipe> recipeArrayAdapter = new RecipeAdapter(EditAllRecipes.this,cookBook.get_cookBookRecipes());
        listView.setAdapter(recipeArrayAdapter);
    }

    private class RecipeAdapter extends ArrayAdapter<Recipe>{


        private Context context;

        public RecipeAdapter(Context context, ArrayList<Recipe> objects) {
            super(context, R.layout.single_recipe_white, objects);
            this.context = context;
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe_white,parent,false);

            final Recipe curIngredient = getItem(position);

            String currentIngredient = curIngredient.getRecipeName();
            //boolean selection = curIngredient.is_selected();
            final int positionOfRecipe = position;

            Button buttonText = (Button) customView.findViewById(R.id.button_designWhite);
            buttonText.setText(currentIngredient);

            buttonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Recipe currentRecipe = cookBook.get_cookBookRecipes().get(positionOfRecipe);

                    Intent intent = new Intent(EditAllRecipes.this,SingleEditRecipeClass.class);

                    Bundle b = new Bundle();

                    b.putSerializable("currentRecipe",currentRecipe);
                    b.putSerializable("cookBook",cookBook);
                    b.putSerializable("typeList",typeList);
                    b.putSerializable("categoryList",categoryList);
                    intent.putExtras(b);

                    startActivity(intent);
                }
            });

            return customView;
        }

    }
}
