package mrothwell4.uclan.ac.uk.myweatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mrothwell4.uclan.ac.uk.myweatherapp.db.DatabaseOpenHelper;

public class AddFavourite extends AppCompatActivity {

    private EditText titleEditText;
    private EditText bodyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favourite);

        titleEditText = (EditText) findViewById(R.id.editTextCity);

        Button addButton = (Button) findViewById(R.id.entryCity);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        final Button homebutton = (Button) findViewById(R.id.homeAButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddFavourite.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final Button button = (Button) findViewById(R.id.favAButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddFavourite.this, Favourites.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void add() {
        String title = titleEditText.getText().toString().trim();

        if(title.isEmpty()) {
            Toast.makeText(AddFavourite.this, "City must be entered", Toast.LENGTH_SHORT).show();
        } else {
            // store in DB
            DatabaseOpenHelper doh = new DatabaseOpenHelper(this);
            SQLiteDatabase db = doh.getWritableDatabase(); // note we get a ‘writable’ DB
            ContentValues contentValues = new ContentValues(); // a map data structure
            contentValues.put("title", title);
            db.insert("entries", null, contentValues);

            finish(); // causes this activity to terminate, and return to the parent one
        }

    }

    //final EditText edit = (EditText) findViewById(R.id.editTextCity);

}
