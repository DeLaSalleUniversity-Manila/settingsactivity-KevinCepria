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

public class Statistics extends Activity {

    MyDatabase db;
    Cursor cursor;
    private Structlist CAT = new Structlist();
    private String [] AY;
    private String []YEAR;
    private ListView categories;
    private ArrayAdapter<String> adapter;
    private int index,array[],h;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
            TextView title = (TextView)findViewById(R.id.textView);
            title.setText("STATISTICS");
            statistics();

        adapter = new ArrayAdapter<>(this, R.layout.statistics, AY);
        categories = (ListView) findViewById(R.id.listView);
        categories.setAdapter(adapter);
        categories.setOnItemClickListener(new
                                                  AdapterView.OnItemClickListener() {
                                                      @Override
                                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                          index = position;
                                                          int CONDITION=3;
                                                          playSound("Music1.wav");
                                                          Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                                                          intent.putExtra("condtion", CONDITION);
                                                          intent.putExtra("Position", index);
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
    private void statistics(){
        setYear();
        storeStat();
        //adaptStats();

    }
    private void setYear(){
        db = new MyDatabase(this);
        cursor=db.setCursorYear();
        CAT= new Structlist();
        CAT.initializelist(10);
        Cursor cursor1;
        if (cursor.getCount() > 0) {
            array=new int [cursor.getCount()];
            int val=0;

            do {

                if(val==0){
                    CAT.add(1, cursor.getString(9));
                    cursor1=db.setSearchCursor(cursor.getString(9));
                    array[val]=cursor1.getCount();
                    val++;
                }
                else {
                    cursor.moveToPrevious();
                    String temp2 = cursor.getString(9);
                    cursor.moveToNext();
                    if (!cursor.getString(9).equals(temp2)) {
                        CAT.add(1, cursor.getString(9));
                        cursor1=db.setSearchCursor(cursor.getString(9));
                        array[val]=cursor1.getCount();
                        val++;
                    }
                }
            } while (cursor.moveToNext()); //move to next question until you finish with all of them
            cursor.close();/// closing the cursor
            db.close(); /// closing the database
        }
    }
    private void storeStat(){
        int o=CAT.listsize()-1;
        AY=new String[CAT.listsize()];
        YEAR=new String[CAT.listsize()];
        for(int i=0;i<CAT.listsize();i++){
            YEAR[i]= (String) CAT.getitem(i+1);
            AY[i]=  "("+CAT.getitem(i+1)+")"+ " Theses Proposed -"+ array[o];
            o--;
        }
    }
    String Year(int index) {
            return YEAR[index];
    }

//    private void adaptStats(){
//        adapter = new ArrayAdapter<>(this, R.layout.category_layout, ITEMS2);
//        theses = (ListView) findViewById(R.id.listView);
//        theses.setAdapter(adapter);
//
//    }
}