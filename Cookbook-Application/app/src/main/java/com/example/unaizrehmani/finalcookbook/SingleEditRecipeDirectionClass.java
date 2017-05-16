package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class SingleEditRecipeDirectionClass extends AppCompatActivity {

    private MainScreen cookBook;
    private Recipe currentRecipe;
    private String oldName;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_edit_recipe_direction_class);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        currentRecipe = (Recipe) getIntent().getExtras().getSerializable("currentRecipe");
        oldName = (String) getIntent().getExtras().getSerializable("oldName");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        populateListView();

        Button finished = (Button)findViewById(R.id.changeSingleRecipeDirectionButton);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to mainscreen with changes.
                Intent intent = new Intent(SingleEditRecipeDirectionClass.this,MainScreen.class);

                Bundle b = new Bundle();

                cookBook.get_cookBookRecipes().remove(new Recipe(oldName));

                cookBook.add_cookBookRecipe(currentRecipe);

                b.putSerializable("cookBook",cookBook);
                b.putSerializable("typeList",typeList);
                b.putSerializable("categoryList",categoryList);

                intent.putExtras(b);

                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
        });
    }

    /*
    public void populateListView(){
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(
                SingleEditRecipeDirectionClass.this,
                android.R.layout.simple_list_item_1,
                currentRecipe.getRecipeDirections());

        ListView myListView = (ListView) findViewById(R.id.getEditDirectionListView);

        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String oldDirectionName = (String)adapterView.getItemAtPosition(i);

                Intent intent= new Intent(SingleEditRecipeDirectionClass.this,ChangeSingleRecipeDirection.class);
                Bundle b = new Bundle();
                b.putSerializable("oldDirectionName",oldDirectionName);
                b.putSerializable("cookBook",cookBook);
                b.putSerializable("direction position",i);
                b.putSerializable("currentRecipe",currentRecipe);
                b.putSerializable("oldName",oldName);
                b.putSerializable("typeList",typeList);
                b.putSerializable("categoryList",categoryList);

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }*/


    public void populateListView(){
        ArrayAdapter<String> stringArrayAdapter = new CategoryAdapter(SingleEditRecipeDirectionClass.this,currentRecipe.getRecipeDirections());
        final ListView ingredientListView = (ListView) findViewById(R.id.getEditDirectionListView);
        ingredientListView.setAdapter(stringArrayAdapter);

        ingredientListView.setAdapter(stringArrayAdapter);

    }

    private class CategoryAdapter extends ArrayAdapter<String>{


        private Context context;

        public CategoryAdapter(Context context, ArrayList<String> objects) {
            super(context, R.layout.single_recipe_white, objects);
            this.context = context;
        }

        //override this method.
        public View getView(final int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_recipe_white,parent,false);

            final String curCategory = getItem(position);

            //boolean selection = curIngredient.is_selected();

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button_designWhite);
            ingredientText.setText(curCategory);

            ingredientText.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //EditText userInput = (EditText) findViewById(R.id.addCategoryInput);
                    //userInput.setTextColor(Color.WHITE);
                    //userInput.setText(curCategory);

                    String oldDirectionName = currentRecipe.getRecipeDirections().get(position);

                    Intent intent= new Intent(SingleEditRecipeDirectionClass.this,ChangeSingleRecipeDirection.class);
                    Bundle b = new Bundle();
                    b.putSerializable("oldDirectionName",oldDirectionName);
                    b.putSerializable("cookBook",cookBook);
                    b.putSerializable("direction position",position);
                    b.putSerializable("currentRecipe",currentRecipe);
                    b.putSerializable("oldName",oldName);
                    b.putSerializable("typeList",typeList);
                    b.putSerializable("categoryList",categoryList);

                    intent.putExtras(b);
                    startActivity(intent);
                }
            });

            //CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection);
            //ingredientSelected.setText("Include");
            //Need to double check to ensure that boxes stay ticked.

            return customView;
        }

    }

}
