package com.uottawa.alisanaknaki.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by AliSanaknaki on 2016-11-16.
 */

public class RecipeInfo extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinfo);

        Button advanceToDirections = (Button) findViewById(R.id.goToDirections);
        advanceToDirections.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(RecipeInfo.this,Directions.class);
                startActivity(intent);
            }
        });

    }
}
