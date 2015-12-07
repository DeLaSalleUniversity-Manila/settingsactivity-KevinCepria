package myPackage.hellokevin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.IOException;

public class SecondActivity extends Statistics {

    MyDatabase db;
    Cursor cursor;
    private Structlist CAT = new Structlist();
    private String [] ITEMS2;
    private ListView categories;
    private ArrayAdapter<String> adapter;
    private int index, CONDITION,array[],h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        Intent intent=getIntent();
            setCategories();
            storeCategories();

        adapter = new ArrayAdapter<>(this, R.layout.category_layout, ITEMS2);
        categories = (ListView) findViewById(R.id.listView);
        categories.setAdapter(adapter);
        CONDITION = intent.getIntExtra("condtion",0);
        categories.setOnItemClickListener(new
                                                  AdapterView.OnItemClickListener() {
                                                      @Override
                                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                          index = position;
                                                          playSound("Music1.wav");
                                                          Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                                                          intent.putExtra("Position", index);
                                                          intent.putExtra("condtion", CONDITION);
                                                          startActivity(intent);

                                                      }
                                                  });


    }

    public void playSound(String fileName) {
        final MediaPlayer mp = new MediaPlayer();

        if (mp.isPlaying()) {
            mp.stop();
            mp.reset();
        }
        try {

            AssetFileDescriptor afd;
            afd = getAssets().openFd(fileName);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }
    private void setCategories(){
        db = new MyDatabase(this);
        cursor=db.setCursorCAT();
        CAT.initializelist(60);
        if (cursor.getCount() > 0) {

            int val=0;
            do {

                if(val==0){
                    CAT.add(1, cursor.getString(7));
                    val=1;
                }
                else {
                    cursor.moveToPrevious();
                    String temp2 = cursor.getString(7);
                    cursor.moveToNext();
                    if (!cursor.getString(7).equals(temp2)) CAT.add(1, cursor.getString(7));
                }
            } while (cursor.moveToNext()); //move to next question until you finish with all of them
            cursor.close(); /// closing the cursor
            db.close(); /// closing the database
        }
    }

    private void storeCategories(){
            ITEMS2=new String[CAT.listsize()];
        for(int i=0;i<CAT.listsize();i++){

            ITEMS2[i]= (String) CAT.getitem(i+1);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close(); /// closing the cursor
        db.close(); /// closing the database
    }

    @Override
    protected void onPause() {
        super.onPause();


        clearData();
        cursor.close(); /// closing the cursor
        db.close(); /// closing the database
    }

    @Override
    protected void onStop() {
        super.onStop();
        clearData();
        cursor.close(); /// closing the cursor
        db.close(); /// closing the database
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        clearData();
        startActivity(intent);
    }

    private void clearData() {
       CAT.clear();

    }

    String Category(int index){
            return ITEMS2[index];
    }
//    private void adaptStats(){
//        adapter = new ArrayAdapter<>(this, R.layout.category_layout, ITEMS2);
//        theses = (ListView) findViewById(R.id.listView);
//        theses.setAdapter(adapter);
//
//    }
}