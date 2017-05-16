package com.example.unaizrehmani.finalcookbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Recipe implements Serializable{

    private ArrayList<Ingredient> _recipeIngredients = new ArrayList<Ingredient>();
    private ArrayList<String> _recipeDirections = new ArrayList<String>();
    private String _recipeName;
    private String _recipeCategory;
    private String _recipeType;
    private int _prepTime;
    private int _cookTime;
    private int _calories;

    public Recipe(String newName){
        _recipeName = newName.toUpperCase();
    }

    public Recipe(String newName, String newCategory, String newType){
        _recipeName = newName.toUpperCase();
        _recipeType = newType.toUpperCase();
        _recipeCategory = newCategory.toUpperCase();
    }


    public Recipe(String newName, String newCategory, String newType,
                  ArrayList<Ingredient> newIngredients, ArrayList<String> newDirections,
                  int newPrepTime, int newCookTime, int newCalories){

        _recipeName = newName.toUpperCase();
        _recipeIngredients = newIngredients;

        _recipeType = newType.toUpperCase();
        _recipeCategory = newCategory.toUpperCase();
        _prepTime = newPrepTime;
        _cookTime = newCookTime;
        _calories = newCalories;

        for(int i = 0; i<newDirections.size(); i++){

            try {
                int a = Integer.valueOf(String.valueOf(newDirections.get(i).charAt(0)));
                _recipeDirections.add(newDirections.get(i));

            } catch (Exception e){
                _recipeDirections.add((i+1) + ") " + newDirections.get(i));
            }
        }


        //Collections.sort(_recipeIngredients);
        //Collections.sort(_recipeDirections);
        Collections.sort(_recipeIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });
    }

    public String getRecipeName(){
        return _recipeName;
    }

    public void setRecipeName(String newName){ //to be used when editing recipe name
        _recipeName = newName.toUpperCase();
    }

    public ArrayList<Ingredient> getRecipeIngredients(){
        return _recipeIngredients;
    }

    public void setRecipeIngredients(ArrayList<Ingredient> newIngredients){ //to be used when editing recipe ingredients
        _recipeIngredients = newIngredients;
        Collections.sort(_recipeIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });    }

    public ArrayList<String> getRecipeDirections(){
        return _recipeDirections;
    }

    public void setRecipeDirections(int position, String newDirection){ //to be used when editing recipe directions
        _recipeDirections.remove(position);
        _recipeDirections.add(position,newDirection);
    }

    public void addRecipeIngredient(Ingredient newIngredient){
        _recipeIngredients.add(newIngredient);
        Collections.sort(_recipeIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });    }

    public void addRecipeDirection(String newDirection){
        _recipeDirections.add(newDirection);
        Collections.sort(_recipeDirections);
    }

    public void removeRecipeIngredient(int index){
        _recipeIngredients.remove(index);
    }

    public void removeRecipeIngredient(String ingredient){
        _recipeIngredients.remove(new Ingredient(ingredient));
    }

    public void removeDirectionIngredient(int index){
        _recipeDirections.remove(index);
    }

    public String getRecipeCategory(){
        return _recipeCategory;
    }

    public String getRecipeType(){
        return _recipeType;
    }

    public void setRecipeCategory(String newCategory){
        _recipeCategory = newCategory.toUpperCase();
    }

    public void setRecipeType(String newRecipeType){
        _recipeType = newRecipeType.toUpperCase();
    }

    public int get_prepTime() {
        return _prepTime;
    }

    public void set_prepTime(int _prepTime) {
        this._prepTime = _prepTime;
    }

    public int get_cookTime() {
        return _cookTime;
    }

    public void set_cookTime(int _cookTime) {
        this._cookTime = _cookTime;
    }

    public int get_calories() {
        return _calories;
    }

    public void set_calories(int _calories) {
        this._calories = _calories;
    }

    public boolean equals(Object object){
        return _recipeName.equals(((Recipe)object).getRecipeName());
    }

    public String toString(){
        return _recipeName;
    }
}
