package com.uottawa.alisanaknaki.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by AliSanaknaki on 2016-11-16.
 */
public class CTSelect extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findrecipe_ct);

        Button advanceToFindRecipe = (Button) findViewById(R.id.finishCategoryType);
        advanceToFindRecipe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(CTSelect.this,FindRecipe.class);
                startActivity(intent);
            }
        });
    }
}
