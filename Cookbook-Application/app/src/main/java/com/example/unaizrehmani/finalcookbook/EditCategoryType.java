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
import java.util.Collections;

public class EditCategoryType extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category_type);

        EditText userInput = (EditText) findViewById(R.id.addCategoryInput);
        userInput.setTextColor(Color.WHITE);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        populateListView();

        Button addCategory = (Button)findViewById(R.id.addCategoryButton);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addCategoryInput);

                String userInputString = userInput.getText().toString().toUpperCase();
                userInput.setText("");

                if(userInputString != null && !userInputString.equals("")){

                    if(!categoryList.contains(userInputString)) {
                        categoryList.add(userInputString);
                        Collections.sort(categoryList);
                        populateListView();

                        Toast toast = Toast.makeText(getApplicationContext(), ("Category Added: " + userInputString), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Category Already stored", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Must Enter a Category to Add", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button editCategory = (Button)findViewById(R.id.editCategoryButton);
        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientToEdit = ((EditText)findViewById(R.id.addCategoryInput)).getText().toString().toUpperCase();

                if(!ingredientToEdit.equals("") && ingredientToEdit!=null){


                    if(categoryList.contains(ingredientToEdit)){
                        Intent intent = new Intent(EditCategoryType.this,EditCategoryName.class);

                        Bundle b = new Bundle();
                        b.putSerializable("ingredientToEdit",ingredientToEdit);
                        b.putSerializable("cookBook",cookBook);
                        b.putSerializable("typeList",typeList);
                        b.putSerializable("categoryList",categoryList);

                        intent.putExtras(b);
                        startActivity(intent);

                    }else{
                        EditText userInput = (EditText) findViewById(R.id.addCategoryInput);
                        userInput.setText("");
                        Toast.makeText(getApplicationContext(), "No such Category: " + ingredientToEdit, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Type or click a Category to Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteCategory = (Button)findViewById(R.id.deleteCategoryButton);
        deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addCategoryInput);

                String userInputString = userInput.getText().toString().toUpperCase();
                userInput.setText("");

                if(userInputString!= null && !userInputString.equals("")){
                    if(categoryList.contains(userInputString)){
                        categoryList.remove(userInputString);

                        //Should delete all recipes with this category.
                        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
                            if(cookBook.get_cookBookRecipes().get(i).getRecipeCategory().toUpperCase().equals(userInputString)){
                                //Toast.makeText(getApplicationContext(),"Deleted Recipe: "+ cookBook.get_cookBookRecipes().get(i).getRecipeName(), Toast.LENGTH_SHORT).show();

                                cookBook.get_cookBookRecipes().remove(i);
                                i=i-1;
                            }
                        }

                        populateListView();

                        Toast.makeText(getApplicationContext(),("Category deleted: " + userInputString),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),("No Such Category: " + userInputString),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),("Click or Type a Category to Delete"),Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button goToEditType = (Button)findViewById(R.id.finishEditCategories);
        goToEditType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditCategoryType.this,EditType.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("cookBook",cookBook);
                bundle.putSerializable("typeList",typeList);
                bundle.putSerializable("categoryList",categoryList);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public void populateListView(){
        ArrayAdapter<String> stringArrayAdapter = new CategoryAdapter(getApplicationContext(),categoryList);
        final ListView ingredientListView = (ListView) findViewById(R.id.currentCategoryListView);
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

            EditText userInput = (EditText) findViewById(R.id.addCategoryInput);
            userInput.setTextColor(Color.WHITE);

            final String curCategory = getItem(position);

            //boolean selection = curIngredient.is_selected();

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button_designWhite);
            ingredientText.setText(curCategory);

            ingredientText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    EditText userInput = (EditText) findViewById(R.id.addCategoryInput);
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

