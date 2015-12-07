package myPackage.hellokevin;



import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class Administrator extends SearchedThesis {
 private String ACCOUNT_ID, ACCOUNT_PASSWORD;
 private boolean accExists, isLoggedin, isDeleting;
     boolean isEditing;
    private String SAVED_VALUES="values";
    private String SAVED_USERNAME = "username";
    private String SAVED_PASSWORD="password";
    private String SAVED_ACC="accexists";
    private String SAVED_LOG="isloggedin";
 SharedPreferences ADMINISTRATOR;
    private boolean fristIteration=true;



    private String [] replace = {"OLD PASSWORD","NEW USERNAME", "NEW PASSWORD","REPEAT NEW PASSWORD"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ADMINISTRATOR = getSharedPreferences(SAVED_VALUES,
                Context.MODE_PRIVATE);
        loadData();
        if(isLoggedin){
             setContentView(R.layout.login2);
            TextView admin=(TextView) findViewById(R.id.textViewAd);
            admin.setText("WELCOME "+ACCOUNT_ID);
             login2();
        }
        else {
            setContentView(R.layout.login);
            login();
         }


    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = ADMINISTRATOR.edit();
        editor.putString(SAVED_USERNAME, ACCOUNT_ID);
        editor.putString(SAVED_PASSWORD, ACCOUNT_PASSWORD);
        editor.putBoolean(SAVED_ACC, accExists);
        editor.putBoolean(SAVED_LOG,isLoggedin);
        editor.commit();

    }

       private void errorNoAcc(){
           AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(Administrator.this);
           alertDialog1.setTitle("Login Error");
           alertDialog1.setMessage("No existing account. Please create an account to login");
           alertDialog1.setNeutralButton("OK",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   });

           alertDialog1.show();
       }
      private void accRep(){
          final AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Administrator.this);
          alertDialog2.setTitle("Account Replacement");
          Context context = Administrator.this;
          LinearLayout layout = new LinearLayout(context);
          layout.setOrientation(LinearLayout.VERTICAL);

          final TextView [] q = new TextView[4];
          final EditText [] k= new EditText[4];
          final TextView checker = new TextView(this);
          for(int i=0; i<4;i++){
              q[i]=new TextView(this);
              k[i]=new EditText(this);
              q[i].setText(replace[i]);
              q[i].setTextColor(Color.parseColor("#0099cc"));
              if(i==0||i==2){
                  k[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
              }
              if(i==3) {
                  k[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

              }
              layout.addView(q[i]);

              layout.addView(k[i]);
          }

          checker.setTextSize(20);
          layout.addView(checker);

          k[3].addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {

              }

              @Override
              public void afterTextChanged(Editable s) {
                  String strPass1 = k[2].getText().toString();
                  String strPass2 = k[3].getText().toString();
                  if (strPass1.equals(strPass2)) {
                      checker.setText("New Passwords Match");
                      checker.setTextColor(Color.parseColor("#FF1C7003"));
                      checker.setGravity(Gravity.CENTER);
                  } else {
                      checker.setText("New Passwords Do Not Match. Try Again");
                      checker.setTextColor(Color.RED);
                      checker.setGravity(Gravity.CENTER);
                  }
              }

          });
          alertDialog2.setView(layout);

          alertDialog2.setPositiveButton("REPLACE",
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {

                          if ((k[0].getText().toString().equals(ACCOUNT_PASSWORD))&& (k[2].getText().toString().equals(k[3].getText().toString())))
                          {
                              ACCOUNT_ID=k[1].getText().toString();
                              ACCOUNT_PASSWORD=k[2].getText().toString();
                              Toast.makeText(getApplicationContext(),
                                      "ACCOUNT HAS BEEN REPLACED", Toast.LENGTH_SHORT)
                                      .show();
                          }
                           else if (!(k[2].getText().toString().equals(k[3].getText().toString()))&& k[0].getText().toString().equals(ACCOUNT_PASSWORD)){
                                accRep();
                              playSound("Error.mp3");
                              Toast.makeText(getApplicationContext(),
                                      "NEW PASSWORD DOES NOT MATCH!", Toast.LENGTH_SHORT)
                                      .show();

                          }
                           else if(!k[0].getText().toString().equals(ACCOUNT_PASSWORD)&&(k[2].getText().toString().equals(k[3].getText().toString()))){
                              accRep();
                              playSound("Error.mp3");
                              Toast.makeText(getApplicationContext(),
                                      "OLD PASSWORD DOES NOT MATCH!", Toast.LENGTH_SHORT)
                                      .show();
                          }

                          else if(!k[0].getText().toString().equals(ACCOUNT_PASSWORD)&& (!k[2].getText().toString().equals(k[3].getText().toString()))){
                              accRep();
                              playSound("Error.mp3");
                              Toast.makeText(getApplicationContext(),
                                      "INVALID OLD PASSWORD AND NEW PASSWORD DOES NOT MATCH!", Toast.LENGTH_SHORT)
                                      .show();
                          }

                      }
                  });

          alertDialog2.setNegativeButton("CANCEL",
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {
                          dialog.cancel();
                      }
                  });
          alertDialog2.show();


      }
      private void accReg(){
          final AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Administrator.this);
          alertDialog2.setTitle("Account Registration");
          Context context = Administrator.this;
          LinearLayout layout = new LinearLayout(context);
          layout.setOrientation(LinearLayout.VERTICAL);


          final TextView Username = new TextView(context);
          Username.setText(" USER ID");
          Username.setTextColor(Color.parseColor("#0099cc"));
          layout.addView(Username);

          final EditText setusername = new EditText(context);
          setusername.setHint("Username");
          layout.addView(setusername);

          final TextView Password = new TextView(context);
         Password.setText(" PASSWORD");
          Password.setTextColor(Color.parseColor("#0099cc"));
          layout.addView(Password);

          final EditText setpassword = new EditText(context);
          setpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          setpassword.setHint("Password");
          layout.addView(setpassword);

          final TextView Password2 = new TextView(context);
          Password2.setText(" REPEAT PASSWORD");
          Password2.setTextColor(Color.parseColor("#0099cc"));
          layout.addView(Password2);

          final EditText repassword = new EditText(context);
          repassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
          repassword.setHint("Password");
          layout.addView(repassword);

          final TextView checker = new TextView(context);
          checker.setTextSize(20);
          layout.addView(checker);
          repassword.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {

              }

              @Override
              public void afterTextChanged(Editable s) {
                  String strPass1 = setpassword.getText().toString();
                  String strPass2 = repassword.getText().toString();
                  if (strPass1.equals(strPass2)) {
                      checker.setText("Passwords Match");
                      checker.setTextColor(Color.parseColor("#FF1C7003"));
                      checker.setGravity(Gravity.CENTER);
                  } else {
                      checker.setText("Passwords Do Not Match. Try Again");
                      checker.setTextColor(Color.RED);
                      checker.setGravity(Gravity.CENTER);
                  }
              }

          });

          alertDialog2.setView(layout);



                      alertDialog2.setPositiveButton("REGISTER",
                              new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {

                              if((setpassword.getText().toString().equals(repassword.getText().toString()))){
                                  ACCOUNT_ID = setusername.getText().toString();
                                  ACCOUNT_PASSWORD=setpassword.getText().toString();
                                  accExists=true;
                                  Toast.makeText(getApplicationContext(),
                                          "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT)
                                          .show();
                              }
                              else {

                                  accExists=false;
                                  playSound("Error.mp3");
                                  accReg();
                                  Toast.makeText(getApplicationContext(),
                                          "PASSWORD NOT THE SAME", Toast.LENGTH_SHORT)
                                          .show();

                              }

                          }
                  });

                      alertDialog2.setNegativeButton("CANCEL",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      dialog.cancel();
                                  }
                              });

          alertDialog2.show();
      }

      private void addThesis(){
          final AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(Administrator.this);
          alertDialog3.setTitle("Add Thesis");
          Context context =Administrator.this;
          LinearLayout layout = new LinearLayout(context);
          layout.setOrientation(LinearLayout.VERTICAL);
          ScrollView scrollPane = new ScrollView(this);
          scrollPane.addView(layout);
          TextView reqs[] = new TextView[cursor.getColumnCount()-1];
          final EditText inputs[]= new EditText[cursor.getColumnCount()-1];
          int i;
          for(i=0;i<cursor.getColumnCount()-1;i++){
              reqs[i]=new TextView(this);
              inputs[i]=new EditText(this);
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



          alertDialog3.setPositiveButton("ADD",
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
                          db.addThesis(e);
                          db.close();
                          Toast.makeText(getApplicationContext(),
                                  "NEW THESIS ADDED", Toast.LENGTH_SHORT)
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
      private void wrongUserPass(){
          AlertDialog.Builder alertDialog3 = new AlertDialog.Builder(Administrator.this);
          alertDialog3.setTitle("Login Error");
          alertDialog3.setMessage("Invalid Username or Password");
          alertDialog3.setNeutralButton("OK",
                  new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int which) {

                      }
                  });

          alertDialog3.show();

      }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    private void login2(){
        Button add=(Button)findViewById(R.id.addthesis);
        Button edit=(Button) findViewById(R.id.editthesis);
        Button delete=(Button) findViewById(R.id.deletethesis);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound("Music1.wav");
                addThesis();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing=true;
                isDeleting=false;
                playSound("Music1.wav");
                Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                intent.putExtra("editing", isEditing);
                intent.putExtra("first", fristIteration);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeleting=true;
                isEditing=false;
                playSound("Music1.wav");
                Intent intent = new Intent(getBaseContext(), SearchedThesis.class);
                intent.putExtra("deleting", isDeleting);
                intent.putExtra("first", fristIteration);
                startActivity(intent);
            }
        });

        Button logout=(Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Account has been logged out", Toast.LENGTH_SHORT)
                        .show();
                isLoggedin=false;
                playSound("Music1.wav");
                setContentView(R.layout.login);
                login();
//                Intent intent = new Intent(getBaseContext(), Administrator.class);
//                startActivity(intent);
            }
        });

    }
    private void login(){
        Button login = (Button) findViewById(R.id.login);
        Button register = (Button)findViewById(R.id.register);
        final EditText userid=(EditText)findViewById(R.id.username);
        final EditText passw=(EditText) findViewById(R.id.password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accExists&&(userid.getText().toString().equals(ACCOUNT_ID))&&(passw.getText().toString().equals(ACCOUNT_PASSWORD))){
                    Toast.makeText(getApplicationContext(),
                            "login success", Toast.LENGTH_SHORT)
                            .show();
                    playSound("Music1.wav");
                    isLoggedin=true;
                    setContentView(R.layout.login2);
                    TextView admin=(TextView) findViewById(R.id.textViewAd);
                    admin.setText("WELCOME "+ACCOUNT_ID);
                    login2();

                }
                else if(accExists&&(!userid.getText().toString().equals(ACCOUNT_ID))&&(!passw.getText().toString().equals(ACCOUNT_PASSWORD))){
                    wrongUserPass();
                    playSound("Error.mp3");

                }
                else if(accExists&&((!userid.getText().toString().equals(ACCOUNT_ID))||(!passw.getText().toString().equals(ACCOUNT_PASSWORD)))){
                    wrongUserPass();
                    playSound("Error.mp3");

                }
                else if(!accExists){
                    errorNoAcc();
                    playSound("Error.mp3");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accExists){
                    playSound("Music1.wav");
                    accRep();
                }
                else {
                    playSound("Music1.wav");
                    accReg();
                }


            }
        });
    }
    private void loadData(){
        ACCOUNT_ID = ADMINISTRATOR.getString(SAVED_USERNAME, "can't load");
        ACCOUNT_PASSWORD=ADMINISTRATOR.getString(SAVED_PASSWORD, "can't load");
        isLoggedin=ADMINISTRATOR.getBoolean(SAVED_LOG, Boolean.parseBoolean("can't load"));
        accExists=ADMINISTRATOR.getBoolean(SAVED_ACC, Boolean.parseBoolean("can't load"));
    }



    }


