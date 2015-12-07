package myPackage.hellokevin;


import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



import java.io.IOException;

public class MainActivity extends AppCompatActivity {
public int CONDITION;
public Button a, b, c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        a = (Button)  findViewById(R.id.button1);
        b= (Button) findViewById(R.id.button2);
        c=(Button) findViewById(R.id.button3);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound("Music1.wav");
                int extra =1;
                Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                intent.putExtra("extra", extra);
                startActivity(intent);

              }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound("Music1.wav");
                CONDITION=2;
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                intent.putExtra("condtion",CONDITION);
                startActivity(intent);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CONDITION=1;
                playSound("Music1.wav");
                Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                intent.putExtra("condtion",CONDITION);
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
            startActivity(new Intent(this,
                    About.class));

            return true;
        }
       else if (id == R.id.action_administrator) {

            startActivity(new Intent(MainActivity.this,
                    Administrator.class));

            return true;
        }
        else if (id == R.id.action_others) {

            startActivity(new Intent(MainActivity.this,
                    OtherFeatures.class));

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
