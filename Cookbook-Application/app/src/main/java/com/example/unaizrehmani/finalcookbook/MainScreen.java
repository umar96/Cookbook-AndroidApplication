package com.example.unaizrehmani.finalcookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainScreen extends AppCompatActivity implements Serializable{


    //Singleton Class Design. Modifies and passes reference to MainScreen called CookBook between Each Activity.
    //comment


    //@param _cookBookIngredients : Contains all the Ingredients in the Cook Book Database.
    //@param _cookBookRecipes : Contains all the Recipes in the Cook Book Database.
    private ArrayList<Ingredient> _cookBookIngredients = new ArrayList<Ingredient>();
    private ArrayList<Recipe> _cookBookRecipes = new ArrayList<Recipe>();
    private ArrayList<String> typeList;
    private ArrayList<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        try {
            //Toast.makeText(getApplicationContext(), "Load saved types and categories", Toast.LENGTH_SHORT).show();

            /*
                If instantiates the first time, serializable keyword will instantiate CookBook and
                it will catch the exception and populate the Cook Book Database with default pre-set
                ingredients and recipes.

                Otherwise, altered instances of CookBook in later activities returning to this class
                will inform altered recipes and ingredients.
             */
            Bundle bundle = getIntent().getExtras();
            MainScreen mainScreen = (MainScreen) bundle.getSerializable("cookBook");
            _cookBookRecipes = mainScreen.get_cookBookRecipes();
            _cookBookIngredients = mainScreen.get_cookBookIngredients();

            //Toast.makeText(getApplicationContext(), "Successfully loaded cookBook reference", Toast.LENGTH_SHORT).show();


            typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
            categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");
            Collections.sort(categoryList);
            Collections.sort(typeList);

            if(typeList.contains("Any")) {
                typeList.remove("Any");
            }


            if(categoryList.contains("Any")) {
                categoryList.remove("Any");
            }

            //Toast.makeText(getApplicationContext(), "Successfully loaded typeList and categoryList", Toast.LENGTH_SHORT).show();


        } catch(Exception e){

            //Toast.makeText(getApplicationContext(), "Load default types and categories", Toast.LENGTH_SHORT).show();

            typeList = new ArrayList<String>();
            categoryList = new ArrayList<String>();

            categoryList.add("BREAKFAST");
            categoryList.add("DINNER");
            categoryList.add("LUNCH");
            categoryList.add("SNACK");

            typeList.add("AMERICAN");
            typeList.add("ASIAN");
            typeList.add("ITALIAN");
            typeList.add("MEXICAN");

            //@method populateCookBookIngredients() : adds default ingredients to _cookBookIngredients
            populateCookBookIngredients();

            //@method populateCookBookRecipes() : adds default recipes to _cookBookIngredients
            populateCookBookRecipes();
        }

        //if previously saved, populate category list with this.

    }

    public void clickAbout(View view){

        if(typeList.contains("Any")) {
            typeList.remove("Any");
        }


        if(categoryList.contains("Any")) {
            categoryList.remove("Any");
        }

        Intent intent = new Intent (MainScreen.this,AboutScreen.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable("cookBook",this);
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void clickHelp(View view){
        Intent intent = new Intent (MainScreen.this,HelpScreen.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable("cookBook",this);
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }


    //Method used to direct user to findRecipe activity.
    public void clickFindRecipes(View view){
        Intent intent = new Intent(this,findRecipe.class);

        Bundle bundle = new Bundle();

        if(typeList.contains("Any")) {
            typeList.remove("Any");
        }


        if(categoryList.contains("Any")) {
            categoryList.remove("Any");
        }

        if(!typeList.contains("Any")) {
            typeList.add(0,"Any");
        }


        if(!categoryList.contains("Any")) {
            categoryList.add(0, "Any");
        }

        bundle.putSerializable("cookBook",this);
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    //Method used to direct user to EditIngredients Activity
    public void clickEditIngredients(View view){
        Intent intent = new Intent(this,EditIngredients.class);

        if(typeList.contains("Any")) {
            typeList.remove("Any");
        }


        if(categoryList.contains("Any")) {
            categoryList.remove("Any");
        }

        Bundle bundle = new Bundle();

        bundle.putSerializable("cookBook",this);
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    //Method used to direct user to EditRecipes Activity
    public void clickEditRecipes(View view){
        Intent intent = new Intent(this,EditRecipes.class);

        Bundle bundle = new Bundle();

        if(typeList.contains("Any")) {
            typeList.remove("Any");
        }


        if(categoryList.contains("Any")) {
            categoryList.remove("Any");
        }

        bundle.putSerializable("cookBook",this);
        bundle.putSerializable("typeList",typeList);
        bundle.putSerializable("categoryList",categoryList);

        intent.putExtras(bundle);

        startActivity(intent);
    }

    //Returns Array of Cook Book Ingredients
    public ArrayList<Ingredient> get_cookBookIngredients() {
        return _cookBookIngredients;
    }

    //Returns Array of Cook Book Recipes
    public ArrayList<Recipe> get_cookBookRecipes() {
        return _cookBookRecipes;
    }

    //Adds a Cook Book Ingredient using string argument then sorts by name.
    public void add_cookBookIngredient(String ingredient){

        if(!_cookBookIngredients.contains(new Ingredient(ingredient))) {
            _cookBookIngredients.add(new Ingredient(ingredient));

            Collections.sort(_cookBookIngredients, new Comparator<Ingredient>() {
                @Override
                public int compare(Ingredient s1, Ingredient s2) {
                    return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
                }
            });
        }
    }

    //Deletes a Cook Book Ingredient using string argument
    public void delete_cookBookIngredient(String ingredient){
        if(_cookBookIngredients.contains(new Ingredient(ingredient))){
            _cookBookIngredients.remove(new Ingredient(ingredient));


        }
    }

    //Adds a Cook Book recipe with Recipe object argument then sorts by name.
    public void add_cookBookRecipe(Recipe recipe){
        _cookBookRecipes.add(recipe);

        Collections.sort(_cookBookRecipes, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe s1, Recipe s2) {
                return s1.getRecipeName().compareToIgnoreCase(s2.getRecipeName());
            }
        });
    }

    //Deletes a Recipe in Cook Book Recipe array given a recipe name
    public void delete_cookBookRecipe(String recipeName){

        _cookBookRecipes.remove(new Recipe(recipeName));
    }

    //Populate's some default ingredients for cookbook.
    private void populateCookBookIngredients(){

        //Populates Ingredients objects.
        _cookBookIngredients.add(new Ingredient ("Chicken"));
        _cookBookIngredients.add(new Ingredient ("Water"));
        _cookBookIngredients.add(new Ingredient ("Salt"));
        _cookBookIngredients.add(new Ingredient ("Beef"));
        _cookBookIngredients.add(new Ingredient ("Lettuce"));
        _cookBookIngredients.add(new Ingredient ("Tomatoes"));
        _cookBookIngredients.add(new Ingredient ("Ketchup"));
        _cookBookIngredients.add(new Ingredient ("Mayonnaise"));
        _cookBookIngredients.add(new Ingredient ("Pepper"));
        _cookBookIngredients.add(new Ingredient ("Eggs"));
        _cookBookIngredients.add(new Ingredient ("Cheese"));
        _cookBookIngredients.add(new Ingredient ("Milk"));
        _cookBookIngredients.add(new Ingredient ("Pasta"));
        _cookBookIngredients.add(new Ingredient ("Salsa"));
        _cookBookIngredients.add(new Ingredient ("Walnuts"));
        _cookBookIngredients.add(new Ingredient ("Almonds"));
        _cookBookIngredients.add(new Ingredient ("Rice"));
        _cookBookIngredients.add(new Ingredient ("Potatoes"));
        _cookBookIngredients.add(new Ingredient ("Butter"));
        _cookBookIngredients.add(new Ingredient ("Oil"));
        _cookBookIngredients.add(new Ingredient ("Orange"));
        _cookBookIngredients.add(new Ingredient ("Banana"));
        _cookBookIngredients.add(new Ingredient ("Grapes"));
        _cookBookIngredients.add(new Ingredient ("Strawberry"));
        _cookBookIngredients.add(new Ingredient ("Cranberry"));
        _cookBookIngredients.add(new Ingredient ("Cucumber"));
        _cookBookIngredients.add(new Ingredient ("Radish"));
        _cookBookIngredients.add(new Ingredient ("Olives"));
        _cookBookIngredients.add(new Ingredient ("Bread"));
        _cookBookIngredients.add(new Ingredient ("Sugar"));
        _cookBookIngredients.add(new Ingredient ("Lemon"));
        _cookBookIngredients.add(new Ingredient ("Parsley"));
        _cookBookIngredients.add(new Ingredient ("Pasta Sauce"));
        _cookBookIngredients.add(new Ingredient ("Apple"));
        _cookBookIngredients.add(new Ingredient ("Pear"));
        _cookBookIngredients.add(new Ingredient ("Avocado"));
        _cookBookIngredients.add(new Ingredient ("Oats"));
        _cookBookIngredients.add(new Ingredient ("Honey"));
        _cookBookIngredients.add(new Ingredient ("Onion"));
        _cookBookIngredients.add(new Ingredient ("Hotdog"));
        _cookBookIngredients.add(new Ingredient ("Hotdog bun"));
        _cookBookIngredients.add(new Ingredient ("Bacon"));
        _cookBookIngredients.add(new Ingredient ("Flour"));
        _cookBookIngredients.add(new Ingredient ("Beans"));
        _cookBookIngredients.add(new Ingredient ("Mustard"));
        _cookBookIngredients.add(new Ingredient ("Relish"));
        _cookBookIngredients.add(new Ingredient ("Chips"));
        _cookBookIngredients.add(new Ingredient ("Squash"));
        _cookBookIngredients.add(new Ingredient ("Watermelon"));
        _cookBookIngredients.add(new Ingredient ("Eggplant"));
        _cookBookIngredients.add(new Ingredient ("Corn"));
        _cookBookIngredients.add(new Ingredient ("Cilantro"));
        _cookBookIngredients.add(new Ingredient ("Goat"));
        _cookBookIngredients.add(new Ingredient ("Lamb"));
        _cookBookIngredients.add(new Ingredient ("Carmel"));
        _cookBookIngredients.add(new Ingredient ("Bay Leaf"));
        _cookBookIngredients.add(new Ingredient ("Brown Sugar"));
        _cookBookIngredients.add(new Ingredient ("Buttermilk"));
        _cookBookIngredients.add(new Ingredient ("Breadcrumbs"));
        _cookBookIngredients.add(new Ingredient ("Tortilla"));
        _cookBookIngredients.add(new Ingredient ("Garlic"));
        _cookBookIngredients.add(new Ingredient ("Dough"));
        _cookBookIngredients.add(new Ingredient ("Cereal"));
        _cookBookIngredients.add(new Ingredient ("Tuna"));
        _cookBookIngredients.add(new Ingredient ("Peanut Butter"));
        _cookBookIngredients.add(new Ingredient ("Jam"));
        _cookBookIngredients.add(new Ingredient ("Dragon Fruit"));
        _cookBookIngredients.add(new Ingredient ("Chocolate Chips"));
        _cookBookIngredients.add(new Ingredient ("Pancake Batter"));
        _cookBookIngredients.add(new Ingredient ("Pickles"));
        _cookBookIngredients.add(new Ingredient ("Chicken Breast"));
        _cookBookIngredients.add(new Ingredient ("Fish"));
        _cookBookIngredients.add(new Ingredient ("Lime"));
        _cookBookIngredients.add(new Ingredient ("Oregano"));
        _cookBookIngredients.add(new Ingredient ("Thimes"));
        _cookBookIngredients.add(new Ingredient ("Sausage"));
        _cookBookIngredients.add(new Ingredient ("Ground Meat"));
        _cookBookIngredients.add(new Ingredient ("Herbs"));
        _cookBookIngredients.add(new Ingredient ("Siracha"));
        _cookBookIngredients.add(new Ingredient ("Paprika"));
        _cookBookIngredients.add(new Ingredient ("Red Peppers"));
        _cookBookIngredients.add(new Ingredient ("Yellow Peppers"));
        _cookBookIngredients.add(new Ingredient ("Green Peppers"));
        _cookBookIngredients.add(new Ingredient ("Ginger"));
        _cookBookIngredients.add(new Ingredient ("Heavy Cream"));
        _cookBookIngredients.add(new Ingredient ("Bagel"));
        _cookBookIngredients.add(new Ingredient ("Cheese Slice"));
        _cookBookIngredients.add(new Ingredient ("Burger Bun"));
        _cookBookIngredients.add(new Ingredient ("Sesame Seeds"));
        _cookBookIngredients.add(new Ingredient ("Pesto"));
        _cookBookIngredients.add(new Ingredient ("Steak"));
        _cookBookIngredients.add(new Ingredient ("Seasoning"));
        _cookBookIngredients.add(new Ingredient ("Cream Cheese"));
        _cookBookIngredients.add(new Ingredient ("Marshmallow"));
        _cookBookIngredients.add(new Ingredient ("Gram Crackers"));
        _cookBookIngredients.add(new Ingredient ("Digestive Cookies"));
        _cookBookIngredients.add(new Ingredient ("Cookies"));
        _cookBookIngredients.add(new Ingredient ("Cinnamon"));
        _cookBookIngredients.add(new Ingredient ("Pie Crust"));

        Collections.sort(_cookBookIngredients,new Comparator<Ingredient>() {
            @Override
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.get_IngredientName().compareToIgnoreCase(s2.get_IngredientName());
            }
        });
    }

    //Default Recipes entered here and sorted.
    private void populateCookBookRecipes(){
        //********************CHICKEN NOODLE SOUP
        ArrayList<Ingredient> chickenNoodleSoupIngredients = new ArrayList<Ingredient>();
        chickenNoodleSoupIngredients.add(new Ingredient("Chicken"));
        chickenNoodleSoupIngredients.add(new Ingredient("Water"));
        chickenNoodleSoupIngredients.add(new Ingredient("Salt"));
        chickenNoodleSoupIngredients.add(new Ingredient("Pepper"));
        chickenNoodleSoupIngredients.add(new Ingredient("Noodles"));
        chickenNoodleSoupIngredients.add(new Ingredient("Soy sauce"));

        ArrayList<String> chickenNoodleSoupDirections = new ArrayList<String>();
        chickenNoodleSoupDirections.add("Add Chicken to boiling hot water");
        chickenNoodleSoupDirections.add("Add a table spoon of salt and pepper each.");
        chickenNoodleSoupDirections.add("Add a cup of noodles to the boiling hot water.");
        chickenNoodleSoupDirections.add("After 10 minutes, remove and serve with soy sauce.");

        Recipe chickenNoodleSoup = new Recipe("Chicken Noodle Soup", "Lunch", "American",
                chickenNoodleSoupIngredients, chickenNoodleSoupDirections, 5, 30, 150);

        _cookBookRecipes.add(chickenNoodleSoup);

        //*********************BLT SAMMICH
        ArrayList<Ingredient> bltIngredients = new ArrayList<Ingredient>();
        bltIngredients.add(new Ingredient("Bread"));
        bltIngredients.add(new Ingredient("Mayonnaise"));
        bltIngredients.add(new Ingredient("Salt"));
        bltIngredients.add(new Ingredient("Lettuce"));
        bltIngredients.add(new Ingredient("Beef"));
        bltIngredients.add(new Ingredient("Tomatoes"));

        ArrayList<String> bltDirections = new ArrayList<String>();
        bltDirections.add("Toast Bread");
        bltDirections.add("Add mayonnaise on each side of the toasts");
        bltDirections.add("Add lettuce on each side");
        bltDirections.add("Add beef, salt, tomatoes and eat it.");

        Recipe blt = new Recipe("BLT", "Lunch", "American",
                bltIngredients, bltDirections,5, 30, 150);

        _cookBookRecipes.add(blt);

        ArrayList<Ingredient> swedishMeatballPastaIngredients = new ArrayList<Ingredient>();
        swedishMeatballPastaIngredients.add(new Ingredient("Beef"));
        swedishMeatballPastaIngredients.add(new Ingredient("Eggs"));
        swedishMeatballPastaIngredients.add(new Ingredient("Salt"));
        swedishMeatballPastaIngredients.add(new Ingredient("Pepper"));
        swedishMeatballPastaIngredients.add(new Ingredient("Pasta"));
        swedishMeatballPastaIngredients.add(new Ingredient("Onion"));
        swedishMeatballPastaIngredients.add(new Ingredient("Pasta Sauce"));
        ArrayList<String> swedishMeatballPastaDirections = new ArrayList<String>();
        swedishMeatballPastaDirections.add("Combine Beef, Onion, Salt, Pepper, Eggs. Mix well");
        swedishMeatballPastaDirections.add("Cook pasta and beef");
        swedishMeatballPastaDirections.add("Add together with pasta sauce and simmer");
        swedishMeatballPastaDirections.add("Eat it.");
        Recipe swedishMeatball = new Recipe("Meatball Pasta", "Dinner", "Italian",
                swedishMeatballPastaIngredients, swedishMeatballPastaDirections,5, 30, 150);
        _cookBookRecipes.add(swedishMeatball);

        ArrayList<Ingredient> grilledCheeseIngredients = new ArrayList<Ingredient>();
        grilledCheeseIngredients.add(new Ingredient("Cheese"));
        grilledCheeseIngredients.add(new Ingredient("Bread"));
        grilledCheeseIngredients.add(new Ingredient("Butter"));
        ArrayList<String> grilledCheeseDirections = new ArrayList<String>();
        grilledCheeseDirections.add("Add butter to a pan and turn on stove");
        grilledCheeseDirections.add("Make sandwich");
        grilledCheeseDirections.add("Add sandwich to pan");
        grilledCheeseDirections.add("Eat it.");
        Recipe grilledCheese = new Recipe("Grilled Cheese", "Lunch", "American",
                grilledCheeseIngredients, grilledCheeseDirections,5, 30, 150);
        _cookBookRecipes.add(grilledCheese);

        ArrayList<Ingredient> pastaIngredients = new ArrayList<Ingredient>();
        pastaIngredients.add(new Ingredient("Pasta"));
        pastaIngredients.add(new Ingredient("Pasta Sauce"));
        pastaIngredients.add(new Ingredient("Cheese"));
        ArrayList<String> pastaDirections = new ArrayList<String>();
        pastaDirections.add("Add water to pot and turn stove on");
        pastaDirections.add("Add pasta; make sure its' cooked");
        pastaDirections.add("Remove water and add Pasta Sauce and Cheese; simmer");
        pastaDirections.add("Eat it.");
        Recipe pasta = new Recipe("Pasta", "Lunch", "Italian",
                pastaIngredients, pastaDirections,5, 30, 150);
        _cookBookRecipes.add(pasta);

        ArrayList<Ingredient> saladIngredients = new ArrayList<Ingredient>();
        saladIngredients.add(new Ingredient("Lettuce"));
        saladIngredients.add(new Ingredient("Tomatoes"));
        saladIngredients.add(new Ingredient("Salt"));
        saladIngredients.add(new Ingredient("Pepper"));
        ArrayList<String> saladDirections = new ArrayList<String>();
        saladDirections.add("Cut tomatoes and lettuce");
        saladDirections.add("Add salt and pepper to lettuce and tomatoes");
        saladDirections.add("Eat it.");
        Recipe salad = new Recipe("Salad", "Lunch", "American",
                saladIngredients, saladDirections,5, 30, 150);
        _cookBookRecipes.add(salad);

        ArrayList<Ingredient> potatoSaladIngredients = new ArrayList<Ingredient>();
        potatoSaladIngredients.add(new Ingredient("Lettuce"));
        potatoSaladIngredients.add(new Ingredient("Tomatoes"));
        potatoSaladIngredients.add(new Ingredient("Potato"));
        potatoSaladIngredients.add(new Ingredient("Salt"));
        potatoSaladIngredients.add(new Ingredient("Pepper"));
        ArrayList<String> potatoSaladDirections = new ArrayList<String>();
        potatoSaladDirections.add("Cut tomatoes, lettuce and potato");
        potatoSaladDirections.add("Add salt and pepper to lettuce, tomatoes and potato");
        potatoSaladDirections.add("Eat it.");
        Recipe potatoSalad = new Recipe("Potato Salad", "Lunch", "American",
                potatoSaladIngredients, potatoSaladDirections,5, 30, 150);
        _cookBookRecipes.add(potatoSalad);

        ArrayList<Ingredient> grilledChickenIngredients = new ArrayList<Ingredient>();
        grilledChickenIngredients.add(new Ingredient("Chicken"));
        grilledChickenIngredients.add(new Ingredient("Oil"));
        grilledChickenIngredients.add(new Ingredient("Salt"));
        grilledChickenIngredients.add(new Ingredient("Pepper"));
        ArrayList<String> grilledChickenDirections = new ArrayList<String>();
        grilledChickenDirections.add("Cut tomatoes, lettuce and potato");
        grilledChickenDirections.add("Add salt and pepper to lettuce, tomatoes and potato");
        grilledChickenDirections.add("Eat it.");
        Recipe grilledChicken = new Recipe("Grilled Chicken", "Dinner", "American",
                grilledChickenIngredients, grilledChickenDirections,5, 30, 150);
        _cookBookRecipes.add(grilledChicken);

        ArrayList<Ingredient> burritoIngredients = new ArrayList<Ingredient>();
        burritoIngredients.add(new Ingredient("Chicken"));
        burritoIngredients.add(new Ingredient("Salsa"));
        burritoIngredients.add(new Ingredient("Salt"));
        burritoIngredients.add(new Ingredient("Pepper"));
        burritoIngredients.add(new Ingredient("Tortilla"));
        burritoIngredients.add(new Ingredient("Cheese"));
        burritoIngredients.add(new Ingredient("Lettuce"));
        burritoIngredients.add(new Ingredient("Avocado"));
        ArrayList<String> burritoDirections = new ArrayList<String>();
        burritoDirections.add("Lay out Tortilla and add all ingredients");
        burritoDirections.add("Roll it.");
        burritoDirections.add("Eat it.");
        Recipe burrito = new Recipe("Burrito", "Dinner", "Mexican",
                burritoIngredients, burritoDirections,5, 30, 150);
        _cookBookRecipes.add(burrito);

        ArrayList<Ingredient> chickenBakeIngredients = new ArrayList<Ingredient>();
        chickenBakeIngredients.add(new Ingredient("Chicken"));
        chickenBakeIngredients.add(new Ingredient("Cheese"));
        chickenBakeIngredients.add(new Ingredient("Salt"));
        chickenBakeIngredients.add(new Ingredient("Pepper"));
        chickenBakeIngredients.add(new Ingredient("Oil"));
        ArrayList<String> chickenBakeDirections = new ArrayList<String>();
        chickenBakeDirections.add("Spread salt and pepper over chicken");
        chickenBakeDirections.add("Put oil in pan and put chicken there; add cheese on top");
        chickenBakeDirections.add("Cook it.");
        chickenBakeDirections.add("Eat it.");
        Recipe chickenBake = new Recipe("Chicken Bake", "Dinner", "American",
                chickenBakeIngredients, chickenBakeDirections,5, 30, 150);
        _cookBookRecipes.add(chickenBake);

        ArrayList<Ingredient> omletteIngredients = new ArrayList<Ingredient>();
        omletteIngredients.add(new Ingredient("Eggs"));
        omletteIngredients.add(new Ingredient("Milk"));
        omletteIngredients.add(new Ingredient("Butter"));
        omletteIngredients.add(new Ingredient("Cheese"));
        ArrayList<String> omletteDirections = new ArrayList<String>();
        omletteDirections.add("Whisk eggs and milk");
        omletteDirections.add("Add butter to a pan and turn on stove");
        omletteDirections.add("Add eggs to pan and add cheese");
        omletteDirections.add("Eat it.");
        Recipe omlette = new Recipe("Omlette", "Breakfast", "American",
                omletteIngredients, omletteDirections,5, 30, 150);
        _cookBookRecipes.add(omlette);

        ArrayList<Ingredient> nachosIngredients = new ArrayList<Ingredient>();
        nachosIngredients.add(new Ingredient("Salsa"));
        nachosIngredients.add(new Ingredient("Chips"));
        nachosIngredients.add(new Ingredient("Cheese"));
        ArrayList<String> nachosDirections = new ArrayList<String>();
        nachosDirections.add("Add cheese on top of chips and put in microwave; warm up");
        nachosDirections.add("Take out and add salsa");
        nachosDirections.add("Eat it.");
        Recipe nachos = new Recipe("Nachos", "Snack", "Mexican",
                nachosIngredients, nachosDirections,5, 30, 150);
        _cookBookRecipes.add(nachos);

        ArrayList<Ingredient> fruitSaladIngredients = new ArrayList<Ingredient>();
        fruitSaladIngredients.add(new Ingredient("Apple"));
        fruitSaladIngredients.add(new Ingredient("Grape"));
        fruitSaladIngredients.add(new Ingredient("Banana"));
        fruitSaladIngredients.add(new Ingredient("Orange"));
        fruitSaladIngredients.add(new Ingredient("Pear"));
        ArrayList<String> fruitSaladDirections = new ArrayList<String>();
        fruitSaladDirections.add("Cut all fruits; mix them");
        fruitSaladDirections.add("Eat it.");
        Recipe fruitSalad = new Recipe("Fruit Salad", "Breakfast", "American",
                fruitSaladIngredients, fruitSaladDirections,5, 30, 150);
        _cookBookRecipes.add(fruitSalad);

        ArrayList<Ingredient> smoothieIngredients = new ArrayList<Ingredient>();
        smoothieIngredients.add(new Ingredient("Apple"));
        smoothieIngredients.add(new Ingredient("Grape"));
        smoothieIngredients.add(new Ingredient("Banana"));
        smoothieIngredients.add(new Ingredient("Orange"));
        smoothieIngredients.add(new Ingredient("Pear"));
        smoothieIngredients.add(new Ingredient("Milk"));
        ArrayList<String> smoothieDirections = new ArrayList<String>();
        smoothieDirections.add("Cut all fruits; mix them");
        smoothieDirections.add("Add to blender with milk");
        smoothieDirections.add("Eat it.");
        Recipe smoothie = new Recipe("Smoothie", "Breakfast", "American",
                smoothieIngredients, smoothieDirections,5, 30, 150);
        _cookBookRecipes.add(smoothie);

        ArrayList<Ingredient> wedgesIngredients = new ArrayList<Ingredient>();
        wedgesIngredients.add(new Ingredient("Potato"));
        wedgesIngredients.add(new Ingredient("Pepper"));
        wedgesIngredients.add(new Ingredient("Salt"));
        wedgesIngredients.add(new Ingredient("Oil"));
        ArrayList<String> wedgesDirections = new ArrayList<String>();
        wedgesDirections.add("Cut potoates and mix with salt and pepper");
        wedgesDirections.add("Add oil into pan and add potatoes; cook.");
        wedgesDirections.add("Eat it.");
        Recipe wedges = new Recipe("Wedges", "Snack", "American",
                wedgesIngredients, wedgesDirections,5, 30, 150);
        _cookBookRecipes.add(wedges);

        ArrayList<Ingredient> garlicBreadIngredients = new ArrayList<Ingredient>();
        garlicBreadIngredients.add(new Ingredient("Bread"));
        garlicBreadIngredients.add(new Ingredient("Parsley"));
        garlicBreadIngredients.add(new Ingredient("Garlic"));
        garlicBreadIngredients.add(new Ingredient("Butter"));
        ArrayList<String> garlicBreadDirections = new ArrayList<String>();
        garlicBreadDirections.add("Melt butter and add parley, garlic and mix");
        garlicBreadDirections.add("Cut slices in the bread; apply garlic spread");
        garlicBreadDirections.add("Cook it.");
        garlicBreadDirections.add("Eat it.");
        Recipe garlicBread = new Recipe("Garlic Bread", "Snack", "American",
                garlicBreadIngredients, garlicBreadDirections,5, 30, 150);
        _cookBookRecipes.add(garlicBread);

        ArrayList<Ingredient> pizzaIngredients = new ArrayList<Ingredient>();
        pizzaIngredients.add(new Ingredient("Tomatoes"));
        pizzaIngredients.add(new Ingredient("Dough"));
        pizzaIngredients.add(new Ingredient("Sauce"));
        pizzaIngredients.add(new Ingredient("Cheese"));
        pizzaIngredients.add(new Ingredient("Onion"));
        pizzaIngredients.add(new Ingredient("Oil"));
        ArrayList<String> pizzaDirections = new ArrayList<String>();
        pizzaDirections.add("Roll dough and add to pan; apply oil");
        pizzaDirections.add("Spread cheese, sauce, tomatoes and onions");
        pizzaDirections.add("Cook it.");
        pizzaDirections.add("Eat it.");
        Recipe pizza = new Recipe("Pizza", "Dinner", "American",
                pizzaIngredients, pizzaDirections,5, 30, 150);
        _cookBookRecipes.add(pizza);

        ArrayList<Ingredient> cerealIngredients = new ArrayList<Ingredient>();
        cerealIngredients.add(new Ingredient("Cereal"));
        cerealIngredients.add(new Ingredient("Milk"));
        ArrayList<String> cerealDirections = new ArrayList<String>();
        cerealDirections.add("Choose preferred cereal and bowl");
        cerealDirections.add("Add milk");
        cerealDirections.add("Eat it.");
        Recipe cereal = new Recipe("Cereal", "Breakfast", "American",
                cerealIngredients, cerealDirections,5, 30, 150);
        _cookBookRecipes.add(cereal);

        ArrayList<Ingredient> smoresIngredients = new ArrayList<Ingredient>();
        smoresIngredients.add(new Ingredient("Gram Crackers"));
        smoresIngredients.add(new Ingredient("Digestive Cookies"));
        smoresIngredients.add(new Ingredient("Chocolate Chips"));
        smoresIngredients.add(new Ingredient("Marshmallow"));
        ArrayList<String> smoresDirections = new ArrayList<String>();
        smoresDirections.add("Melt chocolate and apply to preferred cookie");
        smoresDirections.add("Add Marshmallow");
        smoresDirections.add("Eat it.");
        Recipe smores = new Recipe("Smores", "Snack", "American",
                smoresIngredients, smoresDirections,5, 30, 150);
        _cookBookRecipes.add(smores);

        ArrayList<Ingredient> pestoChickenIngredients = new ArrayList<Ingredient>();
        pestoChickenIngredients.add(new Ingredient("Chicken"));
        pestoChickenIngredients.add(new Ingredient("Pesto"));
        pestoChickenIngredients.add(new Ingredient("Oil"));
        pestoChickenIngredients.add(new Ingredient("Italian Seasoning"));
        ArrayList<String> pestoChickenDirections = new ArrayList<String>();
        pestoChickenDirections.add("Apply olive oil, Italian seasoning and Pesto to chicken; place in a pan");
        pestoChickenDirections.add("Cook it.");
        pestoChickenDirections.add("Eat it.");
        Recipe pestoChicken = new Recipe("Chicken", "Lunch", "Italian",
                pestoChickenIngredients, pestoChickenDirections,5, 30, 150);
        _cookBookRecipes.add(pestoChicken);

        ArrayList<Ingredient> steakIngredients = new ArrayList<Ingredient>();
        steakIngredients.add(new Ingredient("Steak"));
        steakIngredients.add(new Ingredient("Garlic"));
        steakIngredients.add(new Ingredient("Oil"));
        steakIngredients.add(new Ingredient("Butter"));
        steakIngredients.add(new Ingredient("Herbs"));
        steakIngredients.add(new Ingredient("Thimes"));
        ArrayList<String> steakDirections = new ArrayList<String>();
        steakDirections.add("Heat pan and apply oil");
        steakDirections.add("Add steak and cook on each side.");
        steakDirections.add("Add butter, herbs, garlic and thimes; apply over steak");
        steakDirections.add("Eat it.");
        Recipe steak = new Recipe("Steak", "Dinner", "American",
                steakIngredients, steakDirections,5, 30, 150);
        _cookBookRecipes.add(steak);

        ArrayList<Ingredient> cakeIngredients = new ArrayList<Ingredient>();
        cakeIngredients.add(new Ingredient("Flour"));
        cakeIngredients.add(new Ingredient("Eggs"));
        cakeIngredients.add(new Ingredient("Butter"));
        cakeIngredients.add(new Ingredient("Chocolate Chips"));
        cakeIngredients.add(new Ingredient("Milk"));
        ArrayList<String> cakeDirections = new ArrayList<String>();
        cakeDirections.add("Combine all ingredients");
        cakeDirections.add("Cook in an oven");
        cakeDirections.add("Eat it.");
        Recipe cake = new Recipe("Cake", "Snack", "American",
                cakeIngredients, cakeDirections,5, 30, 150);
        _cookBookRecipes.add(cake);

        ArrayList<Ingredient> tunaSandwichIngredients = new ArrayList<Ingredient>();
        tunaSandwichIngredients.add(new Ingredient("Bread"));
        tunaSandwichIngredients.add(new Ingredient("Tuna"));
        tunaSandwichIngredients.add(new Ingredient("Mayonnaise"));
        ArrayList<String> tunaSandwichDirections = new ArrayList<String>();
        tunaSandwichDirections.add("Apply mayonnaise to bread");
        tunaSandwichDirections.add("Drain tuna and add to bread");
        tunaSandwichDirections.add("Eat it.");
        Recipe tunaSandwich = new Recipe("Tuna sandwich", "Lunch", "American",
                tunaSandwichIngredients, tunaSandwichDirections,5, 30, 150);
        _cookBookRecipes.add(tunaSandwich);

        ArrayList<Ingredient> waffleIngredients = new ArrayList<Ingredient>();
        waffleIngredients.add(new Ingredient("Pancake Batter"));
        waffleIngredients.add(new Ingredient("Milk"));
        waffleIngredients.add(new Ingredient("Eggs"));
        waffleIngredients.add(new Ingredient("Oil"));
        ArrayList<String> waffleDirections = new ArrayList<String>();
        waffleDirections.add("Combine batter, milk and eggs");
        waffleDirections.add("Apply oil to waffle maker");
        waffleDirections.add("Add wet batter");
        waffleDirections.add("Eat it.");
        Recipe waffle = new Recipe("Waffles", "Breakfast", "American",
                waffleIngredients, waffleDirections,5, 30, 150);
        _cookBookRecipes.add(waffle);

        ArrayList<Ingredient> potatoBakeIngredients = new ArrayList<Ingredient>();
        potatoBakeIngredients.add(new Ingredient("Potato"));
        potatoBakeIngredients.add(new Ingredient("Salt"));
        potatoBakeIngredients.add(new Ingredient("Pepper"));
        potatoBakeIngredients.add(new Ingredient("Oil"));
        ArrayList<String> potatoBakeDirections = new ArrayList<String>();
        potatoBakeDirections.add("Poke holes in potato with fork and rub with salt, pepper and oil");
        potatoBakeDirections.add("microwave");
        potatoBakeDirections.add("Eat it.");
        Recipe potatoBake = new Recipe("Potato Bake", "Snack", "American",
                potatoBakeIngredients, potatoBakeDirections,5, 30, 150);
        _cookBookRecipes.add(potatoBake);

        ArrayList<Ingredient> quesadillaIngredients = new ArrayList<Ingredient>();
        quesadillaIngredients.add(new Ingredient("Tortilla"));
        quesadillaIngredients.add(new Ingredient("Ground Meat"));
        quesadillaIngredients.add(new Ingredient("Sauce"));
        quesadillaIngredients.add(new Ingredient("Cheese"));
        quesadillaIngredients.add(new Ingredient("Paprika"));
        ArrayList<String> quesadillaDirections = new ArrayList<String>();
        quesadillaDirections.add("Cook meat");
        quesadillaDirections.add("Add meat to Tortilla with sauce and cheese; cook.");
        quesadillaDirections.add("Eat it.");
        Recipe quesadilla = new Recipe("Quesadilla", "Lunch", "American",
                quesadillaIngredients, quesadillaDirections,5, 30, 150);
        _cookBookRecipes.add(quesadilla);

        ArrayList<Ingredient> fishBakeIngredients = new ArrayList<Ingredient>();
        fishBakeIngredients.add(new Ingredient("Fish"));
        fishBakeIngredients.add(new Ingredient("Oil"));
        fishBakeIngredients.add(new Ingredient("Salt"));
        fishBakeIngredients.add(new Ingredient("Pepper"));
        fishBakeIngredients.add(new Ingredient("Lime"));
        ArrayList<String> fishBakeDirections = new ArrayList<String>();
        fishBakeDirections.add("Apply oil, salt, pepper and lime juice to fish");
        fishBakeDirections.add("Bake in oven");
        fishBakeDirections.add("Eat it.");
        Recipe fishBake = new Recipe("Fish Bake", "Dinner", "American",
                fishBakeIngredients, fishBakeDirections,5, 30, 150);
        _cookBookRecipes.add(fishBake);

        ArrayList<Ingredient> riceBeansIngredients = new ArrayList<Ingredient>();
        riceBeansIngredients.add(new Ingredient("Rice"));
        riceBeansIngredients.add(new Ingredient("Beans"));
        riceBeansIngredients.add(new Ingredient("Salt"));
        riceBeansIngredients.add(new Ingredient("Pepper"));
        riceBeansIngredients.add(new Ingredient("Lime"));
        ArrayList<String> riceBeansDirections = new ArrayList<String>();
        riceBeansDirections.add("Cook beans.");
        riceBeansDirections.add("Apply salt, pepper and lime juice to beans");
        riceBeansDirections.add("Cook rice; combine with beans.");
        riceBeansDirections.add("Eat it.");
        Recipe riceBeans = new Recipe("Rice and Beans", "Dinner", "American",
                riceBeansIngredients, riceBeansDirections,5, 30, 150);
        _cookBookRecipes.add(riceBeans);

        ArrayList<Ingredient> breakBurrIngredients = new ArrayList<Ingredient>();
        breakBurrIngredients.add(new Ingredient("Eggs"));
        breakBurrIngredients.add(new Ingredient("Sausage"));
        breakBurrIngredients.add(new Ingredient("Bacon"));
        breakBurrIngredients.add(new Ingredient("Siracha"));
        breakBurrIngredients.add(new Ingredient("Lime"));
        ArrayList<String> breakBurrDirections = new ArrayList<String>();
        breakBurrDirections.add("Cook eggs, sausage and bacon");
        breakBurrDirections.add("Apply lime and siracha to Tortilla");
        breakBurrDirections.add("Cook add cooked ingredients.");
        breakBurrDirections.add("Eat it.");
        Recipe breakBurr = new Recipe("Breakfast Burritos", "Breakfast", "Mexican",
                breakBurrIngredients, breakBurrDirections,5, 30, 150);
        _cookBookRecipes.add(breakBurr);

        ArrayList<Ingredient> burritoBowlIngredients = new ArrayList<Ingredient>();
        burritoBowlIngredients.add(new Ingredient("Red Peppers"));
        burritoBowlIngredients.add(new Ingredient("Yellow Peppers"));
        burritoBowlIngredients.add(new Ingredient("Green Peppers"));
        burritoBowlIngredients.add(new Ingredient("Chicken"));
        burritoBowlIngredients.add(new Ingredient("Onion"));
        burritoBowlIngredients.add(new Ingredient("Sesame Seeds"));
        burritoBowlIngredients.add(new Ingredient("Sauce"));
        burritoBowlIngredients.add(new Ingredient("Lime"));
        burritoBowlIngredients.add(new Ingredient("Rice"));
        ArrayList<String> burritoBowlDirections = new ArrayList<String>();
        burritoBowlDirections.add("Cook peppers and onions; add chicken");
        burritoBowlDirections.add("Cook rice and apply lime juice and sesame seeds");
        burritoBowlDirections.add("Cover base with rice, add sauce and cooked ingredients");
        burritoBowlDirections.add("Eat it.");
        Recipe burritoBowl = new Recipe("Burrito Bowl", "Dinner", "Mexican",
                burritoBowlIngredients, burritoBowlDirections,5, 30, 150);
        _cookBookRecipes.add(burritoBowl);

        ArrayList<Ingredient> stirFryIngredients = new ArrayList<Ingredient>();
        stirFryIngredients.add(new Ingredient("Red Peppers"));
        stirFryIngredients.add(new Ingredient("Yellow Peppers"));
        stirFryIngredients.add(new Ingredient("Green Peppers"));
        stirFryIngredients.add(new Ingredient("Chicken"));
        stirFryIngredients.add(new Ingredient("Onion"));
        stirFryIngredients.add(new Ingredient("Sesame Seeds"));
        ArrayList<String> stirFryDirections = new ArrayList<String>();
        stirFryDirections.add("Cook peppers and onions; add chicken");
        stirFryDirections.add("Apply sesame seeds");
        stirFryDirections.add("Eat it.");
        Recipe stirFry = new Recipe("Stir Fry", "Dinner", "Asian",
                stirFryIngredients, stirFryDirections,5, 30, 150);
        _cookBookRecipes.add(stirFry);

        ArrayList<Ingredient> gingerTeaIngredients = new ArrayList<Ingredient>();
        gingerTeaIngredients.add(new Ingredient("Water"));
        gingerTeaIngredients.add(new Ingredient("Ginger"));
        ArrayList<String> gingerTeaDirections = new ArrayList<String>();
        gingerTeaDirections.add("Boil water");
        gingerTeaDirections.add("Add ginger");
        gingerTeaDirections.add("Eat it.");
        Recipe gingerTea = new Recipe("Ginger Tea", "Snack", "Asian",
                gingerTeaIngredients, gingerTeaDirections,5, 30, 150);
        _cookBookRecipes.add(gingerTea);

        ArrayList<Ingredient> chipsIngredients = new ArrayList<Ingredient>();
        gingerTeaIngredients.add(new Ingredient("Chips"));
        ArrayList<String> chipsDirections = new ArrayList<String>();
        gingerTeaDirections.add("Eat it.");
        Recipe chips = new Recipe("Chips", "Snack", "American",
                chipsIngredients, chipsDirections,5, 30, 150);
        _cookBookRecipes.add(chips);

        ArrayList<Ingredient> cookiesIngredients = new ArrayList<Ingredient>();
        cookiesIngredients.add(new Ingredient("Chips"));
        ArrayList<String> cookiesDirections = new ArrayList<String>();
        cookiesDirections.add("Eat it.");
        Recipe cookies = new Recipe("Chips", "Snack", "American",
                cookiesIngredients, cookiesDirections,5, 30, 150);
        _cookBookRecipes.add(cookies);

        ArrayList<Ingredient> macCheeseIngredients = new ArrayList<Ingredient>();
        macCheeseIngredients.add(new Ingredient("Pasta"));
        macCheeseIngredients.add(new Ingredient("Cheese"));
        macCheeseIngredients.add(new Ingredient("Milk"));
        macCheeseIngredients.add(new Ingredient("Breadcrumbs"));
        ArrayList<String> macCheeseDirections = new ArrayList<String>();
        macCheeseDirections.add("Add milk to pan; add pasta");
        macCheeseDirections.add("Add cheese to pasta when cooked.");
        macCheeseDirections.add("Add to pan and spread breadcumbs ontop; cook.");
        macCheeseDirections.add("Eat it.");
        Recipe macCheese = new Recipe("Macaroni and Cheese", "Lunch", "American",
                macCheeseIngredients, macCheeseDirections,5, 30, 150);
        _cookBookRecipes.add(macCheese);

        ArrayList<Ingredient> hotDogIngredients = new ArrayList<Ingredient>();
        hotDogIngredients.add(new Ingredient("Hotdog"));
        hotDogIngredients.add(new Ingredient("Hotdog bun"));
        hotDogIngredients.add(new Ingredient("Ketchup"));
        hotDogIngredients.add(new Ingredient("Mustard"));
        hotDogIngredients.add(new Ingredient("Relish"));
        ArrayList<String> hotDogDirections = new ArrayList<String>();
        hotDogDirections.add("Cook hotdog and hotdog bun");
        hotDogDirections.add("Add preferred condiments.");
        macCheeseDirections.add("Eat it.");
        Recipe hotDog = new Recipe("Hotdog", "Lunch", "American",
                hotDogIngredients, hotDogDirections,5, 30, 150);
        _cookBookRecipes.add(hotDog);

        ArrayList<Ingredient> beefSoupIngredients = new ArrayList<Ingredient>();
        beefSoupIngredients.add(new Ingredient("Beef"));
        beefSoupIngredients.add(new Ingredient("Water"));
        beefSoupIngredients.add(new Ingredient("Salt"));
        beefSoupIngredients.add(new Ingredient("Pepper"));
        beefSoupIngredients.add(new Ingredient("Onion"));
        ArrayList<String> beefSoupDirections = new ArrayList<String>();
        beefSoupDirections.add("Add all ingredients to a pot and cook until done.");
        beefSoupDirections.add("Eat it.");
        Recipe beefSoup = new Recipe("Beef Soup", "Dinner", "American",
                beefSoupIngredients, beefSoupDirections,5, 30, 150);
        _cookBookRecipes.add(beefSoup);

        ArrayList<Ingredient> beltIngredients = new ArrayList<Ingredient>();
        beltIngredients.add(new Ingredient("Bagel"));
        beltIngredients.add(new Ingredient("Egg"));
        beltIngredients.add(new Ingredient("Sausage"));
        beltIngredients.add(new Ingredient("Lettuce"));
        beltIngredients.add(new Ingredient("Tomato"));
        beltIngredients.add(new Ingredient("Cream Cheese"));
        ArrayList<String> beltDirections = new ArrayList<String>();
        beltDirections.add("Cook eggs, sausage and bagel");
        beltDirections.add("Add all ingredients to bagel.");
        beltDirections.add("Eat it.");
        Recipe belt = new Recipe("Bagel Belt", "Breakfast", "American",
                beltIngredients, beltDirections,5, 30, 150);
        _cookBookRecipes.add(belt);

        ArrayList<Ingredient> riceBeefIngredients = new ArrayList<Ingredient>();
        riceBeefIngredients.add(new Ingredient("Rice"));
        riceBeefIngredients.add(new Ingredient("Beef"));
        riceBeefIngredients.add(new Ingredient("Salt"));
        riceBeefIngredients.add(new Ingredient("Pepper"));
        ArrayList<String> riceBeefDirections = new ArrayList<String>();
        riceBeefDirections.add("Cook rice and beef; add salt and pepper and combine");
        riceBeefDirections.add("Eat it.");
        Recipe riceBeef = new Recipe("Rice and Beef", "Breakfast", "American",
                riceBeefIngredients, riceBeefDirections,5, 30, 150);
        _cookBookRecipes.add(riceBeef);

        ArrayList<Ingredient> eggplantChipsIngredients = new ArrayList<Ingredient>();
        eggplantChipsIngredients.add(new Ingredient("Eggplant"));
        eggplantChipsIngredients.add(new Ingredient("Paprika"));
        eggplantChipsIngredients.add(new Ingredient("Salt"));
        eggplantChipsIngredients.add(new Ingredient("Pepper"));
        ArrayList<String> eggplantChipsDirections = new ArrayList<String>();
        eggplantChipsDirections.add("Cut eggplant and apply seasoning; bake.");
        eggplantChipsDirections.add("Eat it.");
        Recipe eggplantChips = new Recipe("Eggplant Chips", "Snack", "American",
                eggplantChipsIngredients, eggplantChipsDirections,5, 30, 150);
        _cookBookRecipes.add(eggplantChips);

        ArrayList<Ingredient> turkeyChickenIngredients = new ArrayList<Ingredient>();
        turkeyChickenIngredients.add(new Ingredient("Chicken"));
        turkeyChickenIngredients.add(new Ingredient("Paprika"));
        turkeyChickenIngredients.add(new Ingredient("Salt"));
        turkeyChickenIngredients.add(new Ingredient("Pepper"));
        turkeyChickenIngredients.add(new Ingredient("Lemon"));
        ArrayList<String> turkeyChickenDirections = new ArrayList<String>();
        turkeyChickenDirections.add("Take whole chicken and apply seasoning; cook.");
        turkeyChickenDirections.add("Eat it.");
        Recipe turkeyChicken = new Recipe("Whole Chicken", "Dinner", "American",
                turkeyChickenIngredients, turkeyChickenDirections,5, 30, 150);
        _cookBookRecipes.add(turkeyChicken);

        ArrayList<Ingredient> coffeeIngredients = new ArrayList<Ingredient>();
        turkeyChickenIngredients.add(new Ingredient("Beans"));
        turkeyChickenIngredients.add(new Ingredient("Milk"));
        turkeyChickenIngredients.add(new Ingredient("Heavy Cream"));
        turkeyChickenIngredients.add(new Ingredient("Sugar"));
        ArrayList<String> coffeeDirections = new ArrayList<String>();
        turkeyChickenDirections.add("Roast coffee; apply necessary extras.");
        turkeyChickenDirections.add("Eat it.");
        Recipe coffee = new Recipe("Coffee", "Snack", "American",
                coffeeIngredients, coffeeDirections,5, 30, 150);
        _cookBookRecipes.add(coffee);

        ArrayList<Ingredient> burgerIngredients = new ArrayList<Ingredient>();
        burgerIngredients.add(new Ingredient("Ground Meat"));
        burgerIngredients.add(new Ingredient("Eggs"));
        burgerIngredients.add(new Ingredient("Burger Bun"));
        burgerIngredients.add(new Ingredient("Lettuce"));
        ArrayList<String> burgerDirections = new ArrayList<String>();
        burgerDirections.add("Mix meat and eggs together; add to pan.");
        burgerDirections.add("Warm the bun and add burger and lettuce.");
        burgerDirections.add("Eat it.");
        Recipe burger = new Recipe("Burger", "Lunch", "American",
                burgerIngredients, burgerDirections,5, 30, 150);
        _cookBookRecipes.add(burger);

        ArrayList<Ingredient> meatStewIngredients = new ArrayList<Ingredient>();
        meatStewIngredients.add(new Ingredient("Lamb"));
        meatStewIngredients.add(new Ingredient("Goat"));
        meatStewIngredients.add(new Ingredient("Salt"));
        meatStewIngredients.add(new Ingredient("Pepper"));
        meatStewIngredients.add(new Ingredient("Water"));
        meatStewIngredients.add(new Ingredient("Cilantro"));
        meatStewIngredients.add(new Ingredient("Bay Leaf"));
        meatStewIngredients.add(new Ingredient("Oats"));
        meatStewIngredients.add(new Ingredient("Radish"));
        meatStewIngredients.add(new Ingredient("Cucumber"));
        meatStewIngredients.add(new Ingredient("Squash"));
        meatStewIngredients.add(new Ingredient("Brown Sugar"));
        ArrayList<String> meatStewDirections = new ArrayList<String>();
        meatStewDirections.add("Add all ingredients into a pan and boil.");
        meatStewDirections.add("Eat it.");
        Recipe meatStew = new Recipe("Meat Stew", "Lunch", "American",
                meatStewIngredients, meatStewDirections,5, 30, 150);
        _cookBookRecipes.add(meatStew);

        ArrayList<Ingredient> nutsMixIngredients = new ArrayList<Ingredient>();
        nutsMixIngredients.add(new Ingredient("Oats"));
        nutsMixIngredients.add(new Ingredient("Almonds"));
        nutsMixIngredients.add(new Ingredient("Walnuts"));
        nutsMixIngredients.add(new Ingredient("Brown Sugar"));
        nutsMixIngredients.add(new Ingredient("Honey"));
        ArrayList<String> nutsMixDirections = new ArrayList<String>();
        nutsMixDirections.add("Mix all ingredients");
        nutsMixDirections.add("Eat it.");
        Recipe nutsMix = new Recipe("Trail Mix", "Snack", "American",
                nutsMixIngredients, nutsMixDirections,5, 30, 150);
        _cookBookRecipes.add(nutsMix);

        ArrayList<Ingredient> hangoverCureIngredients = new ArrayList<Ingredient>();
        hangoverCureIngredients.add(new Ingredient("Milk"));
        hangoverCureIngredients.add(new Ingredient("Strawberry"));
        hangoverCureIngredients.add(new Ingredient("Banana"));
        hangoverCureIngredients.add(new Ingredient("Brown Sugar"));
        hangoverCureIngredients.add(new Ingredient("Honey"));
        ArrayList<String> hangoverCureDirections = new ArrayList<String>();
        hangoverCureDirections.add("Mix all ingredients and blend");
        hangoverCureDirections.add("Eat it.");
        Recipe hangoverCure = new Recipe("Hangover Cure", "Breakfast", "American",
                hangoverCureIngredients, hangoverCureDirections,5, 30, 150);
        _cookBookRecipes.add(hangoverCure);

        Collections.sort(_cookBookRecipes,new Comparator<Recipe>() {
            @Override
            public int compare(Recipe s1, Recipe s2) {
                return s1.getRecipeName().compareToIgnoreCase(s2.getRecipeName());
            }
        });
    }

}
