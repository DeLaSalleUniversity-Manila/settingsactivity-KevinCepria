package myPackage.hellokevin;


import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.HashMap;

public class Faculty extends SecondActivity {
    private Structlist<String> listDataHeader;
    private Structlist<Professor> allFaculty = new Structlist<Professor>();
    private ExpandableListView Explistview;
    Cursor cursor;
    MyDatabase db;
    private int maxthesis;
    private HashMap<String, Structlist<String>> listDataChild;
    private ExpandableListAdapterImage listadapter;
    private int THESIS_DETAIL_SIZE=1;
    private TextView TITLE;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new MyDatabase(this);

            setContentView(R.layout.category_1);
            TextView title=(TextView)findViewById(R.id.textView);
            title.setText("DLSU ECE/CPE DEPARTMENT");
            cursor=db.setCursorFaculty();

        acquireFaculty();
        prepareFaculty();


        listadapter = new ExpandableListAdapterImage(this, listDataHeader, listDataChild);
        Explistview = (ExpandableListView) findViewById(R.id.expandableListView);
        Explistview.setAdapter(listadapter);
        Explistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                    playSound("Music1.wav");
                    return false;
                }
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();


        });
    }

    private void acquireFaculty() {

        allFaculty.initializelist(cursor.getCount());



        if (cursor.getCount() > 0) {


            do {
                Professor all = new Professor(); // create a new Question object
                all.setName(cursor.getString(0));//same here for the question and the answers
                all.setPosition(cursor.getString(1));

                allFaculty.add(maxthesis + 1, all);//finally add the question to the list
                maxthesis++;
            } while (cursor.moveToNext()); //move to next question until you finish with all of them
            cursor.close(); /// closing the cursor
            db.close(); /// closing the database


        }
        else{
            showToast("NO RESULTS FOUND!");
        }
    }

    private void prepareFaculty() {
        listDataHeader = new Structlist<>();
        listDataChild = new HashMap<>();
        Structlist[] q = new Structlist[maxthesis];
        listDataHeader.initializelist(cursor.getCount());



        for (int i = 0; i < maxthesis; i++) {

            listDataHeader.add(i+1,allFaculty.getitem(i + 1).getName());


            q[i]= new Structlist<>();
            q[i].initializelist(THESIS_DETAIL_SIZE);
            q[i].add(1,  allFaculty.getitem(i + 1).getPosition());


            listDataChild.put(listDataHeader.getitem(i+1),q[i]); // Header, Child data


        }
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
        listDataHeader.clear();
        allFaculty.clear();
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



}
