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

public class SearchedThesis extends SecondActivity {
    private Structlist<String> listDataHeader;
    private Structlist<MyThesis> allthesis = new Structlist<MyThesis>();
    private ExpandableListView Explistview;
    Cursor cursor;
    MyDatabase db;
    SharedPreferences EDIT_DELETE;
    private int maxthesis;
    private HashMap<String, Structlist<String>> listDataChild;
    private ExpandableListAdapter listadapter;
    private int THESIS_DETAIL_SIZE=10;
    private TextView TITLE;
    private int A=0;
    private int ONCLICKPOSTION;
    private String SEARCH_FOR= "RESULTS FOR ";
    private boolean isEditing, isDeleting;
    private String toDelete;
    private int x,y;
    private boolean firstIteration;
    private boolean temp=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EDIT_DELETE = getSharedPreferences("data",
                Context.MODE_PRIVATE);
        Intent intent=getIntent();
        x= intent.getIntExtra("condtion",A);
        y=intent.getIntExtra("extra",A);
        if(x<=3&&x>=1 || y==1 ) {
            saveData3();
        }
        firstIteration=intent.getBooleanExtra("first",Boolean.parseBoolean(null));
        if(firstIteration) {
            isEditing = intent.getBooleanExtra("editing", Boolean.parseBoolean(null));
            isDeleting = intent.getBooleanExtra("deleting", Boolean.parseBoolean(null));
              }
        else{
            loadData();

        }

        db = new MyDatabase(this);


                if(x==1){
                 setContentView(R.layout.category_1);
                 cursor=db.setCursor();

         }
              else if(x==2){
             setContentView(R.layout.category_1);
             ONCLICKPOSTION = intent.getIntExtra("Position", A);
             TITLE = (TextView) findViewById(R.id.textView);
             TITLE.setText(Category(ONCLICKPOSTION));
             cursor = db.setSearchCursor(Category(ONCLICKPOSTION));

         }
                else if(x==3){
                    setContentView(R.layout.category_1);
                    ONCLICKPOSTION = intent.getIntExtra("Position", A);
                    TITLE = (TextView) findViewById(R.id.textView);
                    TITLE.setText("("+Year(ONCLICKPOSTION)+")"+" PROPOSED "+"THESES");
                    cursor = db.setSearchCursor(Year(ONCLICKPOSTION));

                }
        else {
            setContentView(R.layout.category_2);
                if(y==1){

                    handleIntent(getIntent());
                }
                    else {
                    handleIntent1(getIntent());
                }

            ImageButton search = (ImageButton) findViewById(R.id.imageButton);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSound("Music1.wav");
                    handleIntent(getIntent());

                }
            });
        }


        acquireThesis();
        prepareList();


        listadapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        Explistview = (ExpandableListView) findViewById(R.id.expandableListView);
        Explistview.setAdapter(listadapter);
        Explistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                if(isDeleting){
                    deleteThesis(groupPosition);
                    playSound("Music1.wav");
                    return true;
                }
                if(isEditing){
                    editThesis(groupPosition);
                    playSound("Music1.wav");
                    return true;

                }
                else {
                    playSound("Music1.wav");
                    return false;
                     }
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void acquireThesis() {

            allthesis.initializelist(cursor.getCount());



        if (cursor.getCount() > 0) {


            do {
                MyThesis all = new MyThesis(); // create a new Question object
                all.setID(cursor.getInt(0));//get the question id for the cursor and set it to the object
                all.setTITLE(cursor.getString(1));//same here for the question and the answers
                all.setAUTHOR(cursor.getString(2));
                all.setADVISER(cursor.getString(3));
                all.setCHAIROFPANEL(cursor.getString(4));
                all.setPANELIST1(cursor.getString(5));
                all.setPANELIST2(cursor.getString(6));
                all.setAREA(cursor.getString(7));
                all.setTERM(cursor.getString(8));
                all.setAY(cursor.getString(9));
                all.setCOURSE(cursor.getString(10));
                all.setSTATUS(cursor.getString(11));
                allthesis.add(maxthesis+1, all);//finally add the question to the list
                maxthesis++;
            } while (cursor.moveToNext()); //move to next question until you finish with all of them
            cursor.close(); /// closing the cursor
            db.close(); /// closing the database


        }
        else{
            showToast("NO RESULTS FOUND!");
        }
    }

    private void prepareList() {
        listDataHeader = new Structlist<>();
        listDataChild = new HashMap<>();
        Structlist[] q = new Structlist[maxthesis];
        listDataHeader.initializelist(cursor.getCount());

        for (int i = 0; i < maxthesis; i++) {

            listDataHeader.add(i+1,allthesis.getitem(i + 1).getTITLE());


            q[i]= new Structlist<>();
            q[i].initializelist(THESIS_DETAIL_SIZE);
            q[i].add(1,"Authors: "+allthesis.getitem(i + 1).getAUTHOR());
            q[i].add(2,"Adviser: "+allthesis.getitem(i + 1).getADVISER());
            q[i].add(3,"Chair of Panelists:  "+allthesis.getitem(i + 1).getCHAIROFPANEL());
            q[i].add(4,"Panelist 1: "+allthesis.getitem(i + 1).getPANELIST1());
            q[i].add(5,"Panelist 2: "+allthesis.getitem(i+1).getPANELIST2());
            q[i].add(6,"Category: "+allthesis.getitem(i+1).getAREA());
            q[i].add(7,"Term: "+allthesis.getitem(i+1).getTERM());
            q[i].add(8,"Academic Year: "+allthesis.getitem(i+1).getAY());
            q[i].add(9,"Course: "+allthesis.getitem(i+ 1).getCOURSE());
            q[i].add(10,"Status: "+allthesis.getitem(i+ 1).getSTATUS());



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
        allthesis.clear();
        listDataChild.clear();
    }

    private void clearData1(){
        listDataHeader.clear();
        allthesis.clear();
    }

    private void handleIntent(Intent intent) {
        onSearchRequested();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {


            String query = intent.getStringExtra(SearchManager.QUERY);
            cursor = db.setSearchCursor(query);
            TITLE = (TextView) findViewById(R.id.textView2);
            TITLE.setText(SEARCH_FOR+"'"+query+"'");

        } else {
            cursor = db.setCursor();
        }
    }

    private void handleIntent1(Intent intent) {


        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {


            String query = intent.getStringExtra(SearchManager.QUERY);
            cursor = db.setSearchCursor(query);
            TITLE = (TextView) findViewById(R.id.textView2);
            TITLE.setText(SEARCH_FOR+"'"+query+"'");

        } else {
            cursor = db.setCursor();
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
        if(isEditing) {
            saveData1();
        }
        else if(isDeleting){
            saveData2();
        }
        if(x==1||y==1){
            clearData1();
        }
        else{
            clearData();
        }
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

    public void editThesis(final int index){

        final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(SearchedThesis.this);
        alertDialog3.setTitle("Edit Thesis");
        Context context = SearchedThesis.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ScrollView scrollPane = new ScrollView(this);
        scrollPane.addView(layout);
        TextView reqs[] = new TextView[cursor.getColumnCount()-1];
        final EditText inputs[]= new EditText[cursor.getColumnCount()-1];
        int i;
        String p[]={listDataHeader.getitem(index + 1),allthesis.getitem(index + 1).getAUTHOR(),
                allthesis.getitem(index + 1).getADVISER(),allthesis.getitem(index + 1).getCHAIROFPANEL(),allthesis.getitem(index + 1).getPANELIST1(),
                allthesis.getitem(index + 1).getPANELIST2(),allthesis.getitem(index + 1).getAREA(),allthesis.getitem(index + 1).getTERM(),
                allthesis.getitem(index + 1).getAY(),allthesis.getitem(index + 1).getCOURSE(),allthesis.getitem(index + 1).getSTATUS()};
        for(i=0;i<cursor.getColumnCount()-1;i++){
            reqs[i]=new TextView(this);
            inputs[i]=new EditText(this);
            inputs[i].setText(p[i]);
            if(i==0){
                toDelete= inputs[i].getText().toString();
            }

            if(cursor.getColumnName(i+1).equals("CHAIR_OF_PANEL")){
                reqs[i].setText("CHAIR OF PANEL");
            }
            else if(cursor.getColumnName(i+1).equals("PANELIST_1")){
                reqs[i].setText("PANELSIT 1");
            }
            else if(cursor.getColumnName(i+1).equals("PANELIST_2")){
                reqs[i].setText("PANELSIT 2");

            }
            else if(cursor.getColumnName(i+1).equals("ACADEMIC_YEAR")){
                reqs[i].setText("ACADEMIC YEAR");
            }
            else {
                reqs[i].setText(cursor.getColumnName(i + 1));
            }
            if(i==10) {
                inputs[i].setInputType(InputType.TYPE_CLASS_TEXT);
            }
            else {
                inputs[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
            reqs[i].setTextColor(Color.parseColor("#0099cc"));
            layout.addView(reqs[i]);

            layout.addView(inputs[i]);
        }
        alertDialog3.setView(scrollPane);
        //.setView(layout);



        alertDialog3.setPositiveButton("UPDATE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MyThesis e = new MyThesis();
                        e.setTITLE(inputs[0].getText().toString());
                        e.setAUTHOR(inputs[1].getText().toString());
                        e.setADVISER(inputs[2].getText().toString());
                        e.setCHAIROFPANEL(inputs[3].getText().toString());
                        e.setPANELIST1(inputs[4].getText().toString());
                        e.setPANELIST2(inputs[5].getText().toString());
                        e.setAREA(inputs[6].getText().toString());
                        e.setTERM(inputs[7].getText().toString());
                        e.setAY(inputs[8].getText().toString());
                        e.setCOURSE(inputs[9].getText().toString());
                        e.setSTATUS(inputs[10].getText().toString());
                         db.editThesis(toDelete, e);
                        Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                        intent.putExtra("editing", isEditing);
                        intent.putExtra("first", temp);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),
                                "DATABASE UPDATED", Toast.LENGTH_SHORT)
                                .show();


                    }
                });

        alertDialog3.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog3.show();


    }

    private void deleteThesis(final int index){
        final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(SearchedThesis.this);
        alertDialog3.setTitle("Delete Thesis");
        alertDialog3.setMessage("Delete Selected Thesis?");
        Context context = SearchedThesis.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        //alertDialog3.setView(layout);



        alertDialog3.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        db.deleteThesis(allthesis.getitem(index + 1).getTITLE());
                        Intent intent = new Intent(getBaseContext(),SearchedThesis.class);
                        intent.putExtra("deleting", isDeleting);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),
                                "THESIS DELETED", Toast.LENGTH_SHORT)
                                .show();


                    }
                });

        alertDialog3.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog3.show();

    }

    private void saveData1(){
        SharedPreferences.Editor editor = EDIT_DELETE.edit();
        editor.putBoolean("a", isEditing=true);
        editor.commit();
    }

    private void saveData2(){

        SharedPreferences.Editor editor = EDIT_DELETE.edit();
        editor.putBoolean("b", isDeleting=true);
        editor.commit();
    }

    private void loadData(){
        isEditing=EDIT_DELETE.getBoolean("a", Boolean.parseBoolean("can't load"));
        isDeleting=EDIT_DELETE.getBoolean("b", Boolean.parseBoolean("can't load"));
    }

    private void saveData3(){

        SharedPreferences.Editor editor = EDIT_DELETE.edit();
        editor.putBoolean("a", isDeleting=false);
        editor.putBoolean("b", isEditing=false);
        editor.commit();
    }

}
