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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleEditRecipeClass extends AppCompatActivity {

    private Recipe currentRecipe;
    private MainScreen cookBook;
    private ArrayList<Ingredient> chosenAddIngredients = new ArrayList<Ingredient>();
    private String chosenType;
    private String chosenCategory;
    private Spinner typeSpinner;
    private Spinner categorySpinner;
    private String oldName;
    private ArrayList<String> typeList = new ArrayList<String>();
    private ArrayList<String> categoryList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_edit_recipe_class);

        cookBook = (MainScreen) getIntent().getExtras().getSerializable("cookBook");
        currentRecipe = (Recipe)getIntent().getExtras().getSerializable("currentRecipe");
        oldName = currentRecipe.getRecipeName();
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

        for(int i = 0; i<cookBook.get_cookBookIngredients().size(); i++){
            if(currentRecipe.getRecipeIngredients().contains(cookBook.get_cookBookIngredients().get(i))){
                cookBook.get_cookBookIngredients().get(i).set_selected(true);
            }

        }

        createAndSetEditViews();
        createAndSetSpinners();

        populateIngredientListView();
    }

    public void populateIngredientListView(){

        /*
        for(int i = 0; i< cookBook.get_cookBookIngredients().size(); i++){

            if(currentRecipe.getRecipeIngredients().contains(cookBook.get_cookBookIngredients().get(i))){
                chosenAddIngredients.add(cookBook.get_cookBookIngredients().get(i));
                cookBook.get_cookBookIngredients().get(i).set_selected(true);

            }
        }*/

        final ArrayAdapter<Ingredient> ingredientListAdapter = new IngredientAdapter(SingleEditRecipeClass.this,cookBook.get_cookBookIngredients());
        ListView ingredientList = (ListView)findViewById(R.id.getEditIngredientsToAddListView);
        ingredientList.setAdapter(ingredientListAdapter);
    }

    private class IngredientAdapter extends ArrayAdapter<Ingredient>{

        private Context context;

        public IngredientAdapter(Context context, ArrayList<Ingredient> objects) {
            super(context, R.layout.single_ingredient_white, objects);
            this.context = context;
        }

        //override this method.
        public View getView(final int position, View convertView, ViewGroup parent){
            View customView = (LayoutInflater.from(getContext())).inflate(R.layout.single_ingredient_white,parent,false);

            final Ingredient curIngredient = getItem(position);

            final String currentIngredient = curIngredient.get_IngredientName();
            boolean selection = curIngredient.is_selected();

            TextView ingredientText = (TextView) customView.findViewById(R.id.singleIngredientName_white);
            ingredientText.setText(currentIngredient);

            CheckBox ingredientSelected = (CheckBox) customView.findViewById(R.id.singleIngredientSelection_white);

            ingredientSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

                    curIngredient.set_selected(isChecked);

                }
            });

            ingredientSelected.setChecked(curIngredient.is_selected());
            ingredientSelected.setTag(curIngredient);

            return customView;
        }

    }

    public void clickEditRecipeSave(View view){
        String recipeName = ((EditText)findViewById(R.id.getEditRecipeNameEditView)).getText().toString();
        String cookTime = ((EditText)findViewById(R.id.getEditCookEditView)).getText().toString();
        String prepTime = ((EditText)findViewById(R.id.getEditPrepEditView)).getText().toString();
        String caloriesTime = ((EditText)findViewById(R.id.getEditCaloriesEditView)).getText().toString();


        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        //all fields must be satisfied;
        if(recipeName!=null && !recipeName.equals("") && cookTime!=null
                && prepTime!=null && caloriesTime!=null
                && chosenType!=null && chosenCategory!=null
                ){

            try{
                int prepIntTime = Integer.parseInt(prepTime);
                int caloriesIntTime = Integer.parseInt(caloriesTime);
                int cookIntTime = Integer.parseInt(cookTime);

                for(int i = 0; i<cookBook.get_cookBookIngredients().size(); i++){
                    if(cookBook.get_cookBookIngredients().get(i).is_selected()){
                        chosenAddIngredients.add(cookBook.get_cookBookIngredients().get(i));
                    }
                }

                if(chosenAddIngredients.size() > 0){
                    for (int i = 0; i<cookBook.get_cookBookIngredients().size(); i++){

                        cookBook.get_cookBookIngredients().get(i).set_selected(false);

                    } //just in case.

                    Intent intent = new Intent(SingleEditRecipeClass.this,SingleEditRecipeDirectionClass.class);
                    Bundle bundle = new Bundle();

                    currentRecipe.setRecipeIngredients(chosenAddIngredients);
                    currentRecipe.set_calories(caloriesIntTime);
                    currentRecipe.setRecipeName(recipeName);
                    currentRecipe.set_cookTime(cookIntTime);
                    currentRecipe.set_prepTime(prepIntTime);
                    currentRecipe.setRecipeType(chosenType);
                    currentRecipe.setRecipeCategory(chosenCategory);

                    bundle.putSerializable("currentRecipe",currentRecipe);
                    bundle.putSerializable("cookBook",cookBook);
                    bundle.putSerializable("oldName",oldName);
                    bundle.putSerializable("typeList",typeList);
                    bundle.putSerializable("categoryList",categoryList);

                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Must choose at least one ingredient.", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e){
                Toast.makeText(getApplicationContext(), "Prep Time, Cook Time and Calories must be Numbers.", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "All Fields Must be Satisfied.", Toast.LENGTH_SHORT).show();

        }
    }

    public void createAndSetEditViews(){
        //Set the names to what Recipe originally had.
        ((EditText)findViewById(R.id.getEditRecipeNameEditView)).setText(currentRecipe.getRecipeName());
        ((EditText)findViewById(R.id.getEditPrepEditView)).setText(String.valueOf(currentRecipe.get_prepTime()));
        ((EditText)findViewById(R.id.getEditCookEditView)).setText(String.valueOf(currentRecipe.get_cookTime()));
        ((EditText)findViewById(R.id.getEditCaloriesEditView)).setText(String.valueOf(currentRecipe.get_calories()));
    }

    public void createAndSetSpinners(){

        String[] tempCategoryArray = categoryList.toArray(new String[categoryList.size()]);
        String[] tempTypeArray = typeList.toArray(new String[typeList.size()]);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(SingleEditRecipeClass.this,android.R.layout.simple_list_item_1,
                tempCategoryArray);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(SingleEditRecipeClass.this,android.R.layout.simple_list_item_1,
                tempTypeArray);

        categorySpinner = (Spinner) findViewById(R.id.getEditRecipeCategorySpinner);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //CREATE AND SET SPINNER FOR TYPE.
        typeSpinner = (Spinner) findViewById(R.id.getEditRecipeTypeSpinner);

        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        chosenType = typeSpinner.getSelectedItem().toString();
        chosenCategory = categorySpinner.getSelectedItem().toString();

        //Set both Spinners to what Recipe initially had
        String[] categoryArray  = tempCategoryArray;
        int indexCategory = 0;

        for(int i = 0; i<categoryArray.length; i++ ){
            if(currentRecipe.getRecipeCategory().equals(categoryArray[i])){
                indexCategory = i;
                break;
            }
        }

        ((Spinner)findViewById(R.id.getEditRecipeCategorySpinner)).setSelection(indexCategory);

        String[] typesArray  = tempTypeArray;
        int indexType = 0;

        for(int i = 0; i<typesArray.length; i++ ){
            if(currentRecipe.getRecipeType().equals(typesArray[i])){
                indexType = i;
                break;
            }
        }

        ((Spinner)findViewById(R.id.getEditRecipeTypeSpinner)).setSelection(indexType);
    }

}
