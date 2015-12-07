package myPackage.hellokevin;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;


/**
 * Created by Kevin Cepria on 7/28/2015.
 */
public class CommonWord extends Faculty{
    String a[];
    String x[];
    Cursor cursor,cursortitle;
    Cursor cursor1;
    MyDatabase db;
    private ListView categories;
    private ArrayAdapter<String> adapter;
    String word[];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new MyDatabase(this);

        setContentView(R.layout.category);
        TextView title=(TextView)findViewById(R.id.textView);
        title.setText("MOST USED WORDS (TOP 10)");
        cursor=db.setCursorWord();
        cursortitle=db.setCursor();
        if(cursor.getCount()>0){
            searchA();
        }
        else{
            searchB();
        }
        acquireWords();

        adapter = new ArrayAdapter<>(this, R.layout.category_layout, x);
        categories = (ListView) findViewById(R.id.listView);
        categories.setAdapter(adapter);

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
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        clearData();
        startActivity(intent);
    }

    private void clearData() {
       // listDataHeader.clear();
      //  allFaculty.clear();
        //listDataChild.clear();
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
        cursor.close(); /// closing the cursor
        db.close(); /// closing the database
    }


    private void showToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        // Inflate the Layout
        View layout = inflater.inflate(R.layout.my_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout));

        TextView text = (TextView) layout.findViewById(R.id.textToShow);



        // Set the Text to show in TextView
        text.setText(message);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);





        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
  public void searchA() {
      String b=cursortitle.getString(1);
      a=b.split(" ");
      x= new String[a.length];
      for(int i=0;i<a.length;i++){
          x[i]=a[i];
      }
      db.deleteAllWords();
      for (int i = 0; i < 100; i++) {
          String title=cursortitle.getString(1);
          String[] words=title.split(" ");
          db.addWords1(words,words.length);
          cursortitle.moveToNext();

      }
      cursor1 = db.setCursorWord();
  }
    public void searchB(){
        String b=cursortitle.getString(1);
        a=b.split(" ");
        x= new String[a.length];
        for(int o=0;o<a.length;o++) {
            x[o] = a[o];
        }
        for(int i=0;i<100;i++){
            String title=cursortitle.getString(1);
            String[] words=title.split(" ");
            db.addWords1(words, words.length);
            cursortitle.moveToNext();
        }
        cursor1=db.setCursorWord();
  }

    public void acquireWords(){
        int i=0;
        word=new String[cursor1.getCount()];
       do{
           word[i]=(i+1)+") "+cursor1.getString(0);
           i++;
       }while(cursor.moveToNext());
        db.close();
        cursor.close();
        cursor1.close();

    }
}
