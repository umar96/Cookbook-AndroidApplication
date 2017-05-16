package com.example.unaizrehmani.finalcookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class AboutScreen extends AppCompatActivity {

    private MainScreen cookBook;
    private ArrayList<String> typeList;
    private ArrayList<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        Bundle bundle = getIntent().getExtras();
        cookBook = (MainScreen) bundle.getSerializable("cookBook");
        typeList = (ArrayList<String>) getIntent().getExtras().getSerializable("typeList");
        categoryList = (ArrayList<String>) getIntent().getExtras().getSerializable("categoryList");

    }
}