package myPackage.hellokevin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class OtherFeatures extends SearchedThesis{

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.finals);
       Button a = (Button)  findViewById(R.id.stats);
        Button b= (Button) findViewById(R.id.word);
        Button c=(Button) findViewById(R.id.faculty);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound("Music1.wav");
                Intent intent = new Intent(getBaseContext(), Statistics.class);
                startActivity(intent);


            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound("Music1.wav");
                Intent intent = new Intent(getBaseContext(), CommonWord.class);
                startActivity(intent);

            }
        });

        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                playSound("Music1.wav");
                Intent intent = new Intent(getBaseContext(), Faculty.class);
                startActivity(intent);


            }
        });


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

        return super.onOptionsItemSelected(item);
    }


}
