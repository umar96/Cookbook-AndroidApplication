package com.uottawa.alisanaknaki.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by unaizrehmani on 2016-11-14.
 */

public class FindRecipe extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findrecipe);

        Button advanceToExcludeRecipe = (Button) findViewById(R.id.finishIncludeIngredients);
        advanceToExcludeRecipe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(FindRecipe.this,ExcludeIngredients.class);
                startActivity(intent);
            }
        });

    }
}
