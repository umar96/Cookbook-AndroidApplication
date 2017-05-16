package com.example.unaizrehmani.finalcookbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.unaizrehmani.finalcookbook.R.layout.single_ingredient_black;

public class findRecipe extends AppCompatActivity {

    //Reference to original MainScreen instantiation
    private MainScreen cookBook;

    //Used to keep track of Included Ingredients desired by the user.
    private ArrayList<Ingredient> chosenIngredients = new ArrayList<Ingredient>();

    //Stores 'Type' selection from user.
    private String chosenType;

    //Stores 'Category' selection from user.
    private String chosenCategory;

    private ArrayList<String> categoryList = new ArrayList<String>();
    private ArrayList<String> typeList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_recipe);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");


        String[] tempCategoryArray = categoryList.toArray(new String[categoryList.size()]);
        String[] tempTypeArray = typeList.toArray(new String[typeList.size()]);

        //CREATE AND SET SPINNER FOR CATEGORY.
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(findRecipe.this,android.R.layout.simple_list_item_1,
                tempCategoryArray);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(findRecipe.this,android.R.layout.simple_list_item_1,
                tempTypeArray);

        Spinner categorySpinner = (Spinner) findViewById(R.id.spinnerCategory);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //CREATE AND SET SPINNER FOR TYPE.
        Spinner typeSpinner = (Spinner) findViewById(R.id.spinnerType);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        //SAVING CHOICES FOR SELECTED TYPE AND CATEGORY
        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        //SET TEXTVIEWS FOR SPINNERS
        TextView categoryTextView = (TextView) findViewById(R.id.categoryTextView);
        categoryTextView.setText("CATEGORY");

        TextView typeTextView = (TextView) findViewById(R.id.typeTextView);
        typeTextView.setText("TYPE");

        populateIngredientListView(); //Create list View
    }

    public void populateIngredientListView(){

        /*
        Creates custom ListView by creating a Custom Adapter of type ingredients which utilizes a custom layout single_ingredient
        to allow users to 'select' their preferred ingredients using textboxes.
        */

        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(findRecipe.this,cookBook.get_cookBookIngredients());
        ListView ingredientList = (ListView)findViewById(R.id.includeIngredientListView);
        ingredientList.setAdapter(ingredientListAdapter);
    }

    private class IngredientAdapter extends ArrayAdapter<Ingredient>{

        public IngredientAdapter(Context context, ArrayList<Ingredient> objects) {
            super(context, single_ingredient_black, objects);
        }

        //override this method.
        public View getView(int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(single_ingredient_black,parent,false);

            //Retrieves ingredient selected by user.
            final Ingredient curIngredient = getItem(position);

            //Uses info of ingredient selected to set custom layout.
            String currentIngredient = curIngredient.get_IngredientName();

            TextView ingredientText = (TextView) customView.findViewById(R.id.singleIngredientName_black);
            ingredientText.setText(currentIngredient);

            CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection_black);

            ingredientSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                    //If an ingredient is selected, this is reflected in the list view and populates array of desired
                    //ingredients.

                    curIngredient.set_selected(isChecked);

                    if(isChecked){
                        chosenIngredients.add(curIngredient);
                    } else {
                        chosenIngredients.remove(curIngredient);
                    }

                }
            });

            //keeps track of selected ingredients.
            ingredientSelected.setChecked(curIngredient.is_selected());
            ingredientSelected.setTag(curIngredient);

            return customView;
        }

    }

    //User click activates this method which sends user to Exclude Ingredients screen.
    public void clickNextExcludeIngredients(View view){
        Intent intent = new Intent(this, excludeRecipe.class);

        //Retrieves choices.
        chosenType = ((Spinner) findViewById(R.id.spinnerType)).getSelectedItem().toString();
        chosenCategory = ((Spinner) findViewById(R.id.spinnerCategory)).getSelectedItem().toString();

        Bundle bundle = new Bundle();

        //Unselects select attribute for CookBook Ingredients
        for(int i =0 ; i<cookBook.get_cookBookIngredients().size(); i++){
            cookBook.get_cookBookIngredients().get(i).set_selected(false);
        }

        typeList.remove("Any");
        categoryList.remove("Any");

        //Sends Cook Book reference, chosen ingredients, chosen category and chosen Type to inform possible recipes
        //in the next activity.
        bundle.putSerializable("cookBook",cookBook);
        bundle.putSerializable("chosenIngredients", chosenIngredients);
        bundle.putSerializable("chosenType", chosenType);
        bundle.putSerializable("chosenCategory", chosenCategory);
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }

}
