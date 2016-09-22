package mrothwell4.uclan.ac.uk.myweatherapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import mrothwell4.uclan.ac.uk.myweatherapp.db.DatabaseOpenHelper;

public class Favourites extends AppCompatActivity {

    private ListView listView;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        final Button hbutton = (Button) findViewById(R.id.homefButton);
        hbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Favourites.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.listview);


        final Button addbutton = (Button) findViewById(R.id.addnew);
        addbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Favourites.this, AddFavourite.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // we first need a database open helper to even touch the DB...
        DatabaseOpenHelper doh = new DatabaseOpenHelper(this);
        // we then get a readable handler to the DB...
        SQLiteDatabase db = doh.getReadableDatabase();
        // and then we run a raw SQL query which returns a cursor pointing to the results
        Cursor cursor = db.rawQuery("SELECT * FROM entries", null);
        // number of rows in the result set
        int numOfRows = cursor.getCount();
        final String [] titles = new String[numOfRows]; // the titles of the blog entries
        cursor.moveToFirst();
        int columnTitleIndex = cursor.getColumnIndex("title");
        for(int i = 0; i < numOfRows; i++) {
            titles[i] = cursor.getString(columnTitleIndex);
            cursor.moveToNext();
        }
        cursor.close();

        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, titles);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
                String city = ((TextView)v).getText().toString();
                Log.e("c\\", city);
                Intent intent1 = new Intent(Favourites.this, DisplayCity.class);
                intent1.putExtra("city", city);
                startActivity(intent1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int pos, long id) {
                // delete entry
                deleteEntry(titles[pos]);
                // explicitly update UI after modifying the list view
                arrayAdapter.notifyDataSetChanged();

                Toast.makeText(Favourites.this, "Delete entry with title: " + titles[pos], Toast.LENGTH_SHORT).show();
                //Intent intentDelete = new Intent(Favourites.this, MainActivity.class);
                //startActivity(intentDelete);
                return false;

            }
        });
    }

    public void deleteEntry(String title) {
        DatabaseOpenHelper doh = new DatabaseOpenHelper(this);
        SQLiteDatabase db = doh.getWritableDatabase();
        db.delete("entries", "title=" + DatabaseUtils.sqlEscapeString(title) + "", null);
        Intent intentDelete = new Intent(Favourites.this, Favourites.class);
        startActivity(intentDelete);
    }


}
