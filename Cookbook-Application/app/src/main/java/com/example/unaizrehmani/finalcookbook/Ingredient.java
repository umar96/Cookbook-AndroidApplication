package com.example.unaizrehmani.finalcookbook;

import java.io.Serializable;

public class Ingredient implements Serializable{
    private String _IngredientName;
    private boolean _selected;

    public Ingredient(String _IngredientName) {
        this._IngredientName = _IngredientName.toUpperCase();
        _selected = false;
    }

    public String get_IngredientName() {
        return _IngredientName;
    }

    public void set_IngredientName(String _IngredientName) {
        this._IngredientName = _IngredientName.toUpperCase();
    }

    public boolean is_selected() {
        return _selected;
    }

    public void set_selected(boolean _selected) {
        this._selected = _selected;
    }

    public boolean equals(Object object){
        boolean result= false;

        if(object != null && object instanceof Ingredient){
            result = (get_IngredientName().toUpperCase()).equals(((Ingredient)object).get_IngredientName().toUpperCase());
        }

        return result;
    }
}
