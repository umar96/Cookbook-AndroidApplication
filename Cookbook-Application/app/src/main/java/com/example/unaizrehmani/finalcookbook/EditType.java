package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EditType extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_type);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        EditText userInput = (EditText) findViewById(R.id.addTypesInput);
        userInput.setTextColor(Color.WHITE);


        populateListView();

        Button addCategory = (Button)findViewById(R.id.addTypesButton);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addTypesInput);

                String userInputString = userInput.getText().toString().toUpperCase();
                userInput.setText("");

                if(userInputString != null && !userInputString.equals("")){

                    if(!typeList.contains(userInputString)) {
                        typeList.add(userInputString);
                        Collections.sort(typeList);
                        populateListView();

                        Toast toast = Toast.makeText(getApplicationContext(), ("Type Added: " + userInputString), Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Type Already stored", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Must Enter a Type to Add", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button editCategory = (Button)findViewById(R.id.editTypesButton);
        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientToEdit = ((EditText)findViewById(R.id.addTypesInput)).getText().toString().toUpperCase();

                if(!ingredientToEdit.equals("") && ingredientToEdit!=null){


                    if(typeList.contains(ingredientToEdit)){
                        Intent intent = new Intent(EditType.this,EditTypeName.class);

                        Bundle b = new Bundle();
                        b.putSerializable("oldType",ingredientToEdit);
                        b.putSerializable("cookBook",cookBook);
                        b.putSerializable("typeList",typeList);
                        b.putSerializable("categoryList",categoryList);

                        intent.putExtras(b);
                        startActivity(intent);

                    }else{
                        EditText userInput = (EditText) findViewById(R.id.addTypesInput);
                        userInput.setText("");
                        Toast.makeText(getApplicationContext(), "No such Type: " + ingredientToEdit, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Type or click a Type to Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button deleteCategory = (Button)findViewById(R.id.deleteTypesButton);
        deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userInput = (EditText) findViewById(R.id.addTypesInput);

                String userInputString = userInput.getText().toString().toUpperCase();
                userInput.setText("");

                if(userInputString!= null && !userInputString.equals("")){
                    if(typeList.contains(userInputString)){
                        typeList.remove(userInputString);

                        //Should delete all recipes with this category.
                        for(int i = 0; i<cookBook.get_cookBookRecipes().size(); i++){
                            if(cookBook.get_cookBookRecipes().get(i).getRecipeType().equals(userInputString)){
                                //Toast.makeText(getApplicationContext(),"Deleted Recipe: "+ cookBook.get_cookBookRecipes().get(i).getRecipeName(), Toast.LENGTH_SHORT).show();

                                cookBook.get_cookBookRecipes().remove(i);
                                i = i - 1;
                            }
                        }

                        populateListView();

                        Toast.makeText(getApplicationContext(),("Type deleted: " + userInputString),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),("No Such Type: " + userInputString),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),("Enter a Type to Delete"),Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button goToMain = (Button)findViewById(R.id.finishEditTypes);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditType.this,MainScreen.class);

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
        ArrayAdapter<String> stringArrayAdapter = new CategoryAdapter(getApplicationContext(),typeList);
        final ListView ingredientListView = (ListView) findViewById(R.id.currentTypeListView);
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

            EditText userInput = (EditText) findViewById(R.id.addTypesInput);
            userInput.setTextColor(Color.WHITE);

            final String curCategory = getItem(position);

            //boolean selection = curIngredient.is_selected();

            final int pos = position;

            Button ingredientText = (Button) customView.findViewById(R.id.button_designWhite);
            ingredientText.setText(curCategory);

            ingredientText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    EditText userInput = (EditText) findViewById(R.id.addTypesInput);
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
