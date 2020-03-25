package com.example.edurado.MyFilms;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class AddActivity extends AppCompatActivity {
    String name;
    String year;
    String ratio;
    String ballActors;
    String ballPlot;
    String ballInstallation;
    String review;
    String url;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    TextView nameView, yearView, ballKinopoisk;
    TextView ballActorsView, ballPlotView, ballInstallationView;
    EditText reviewView;
    Button addToMyFilms;
    Button addToWantedFilms;
    Button goKinopoisk;

    ImageView buttonStarActors1, buttonStarActors2, buttonStarActors3, buttonStarActors4, buttonStarActors5, buttonStarActors6, buttonStarActors7, buttonStarActors8, buttonStarActors9;
    ImageView buttonStarPlot1, buttonStarPlot2, buttonStarPlot3, buttonStarPlot4, buttonStarPlot5, buttonStarPlot6, buttonStarPlot7, buttonStarPlot8, buttonStarPlot9;
    ImageView buttonStarInstallation1, buttonStarInstallation2, buttonStarInstallation3, buttonStarInstallation4, buttonStarInstallation5, buttonStarInstallation6, buttonStarInstallation7, buttonStarInstallation8, buttonStarInstallation9;

    LinearLayout layout_actors_stars,layout_plot_stars,layout_installation_stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Добавление фильма в коллекцию");

        buttonStarActors1 = findViewById(R.id.ActorBall1);
        buttonStarActors2 = findViewById(R.id.ActorBall2);
        buttonStarActors3 = findViewById(R.id.ActorBall3);
        buttonStarActors4 = findViewById(R.id.ActorBall4);
        buttonStarActors5 = findViewById(R.id.ActorBall5);
        buttonStarActors6 = findViewById(R.id.ActorBall6);
        buttonStarActors7 = findViewById(R.id.ActorBall7);
        buttonStarActors8 = findViewById(R.id.ActorBall8);
        buttonStarActors9 = findViewById(R.id.ActorBall9);

        buttonStarPlot1 = findViewById(R.id.PlotBall1);
        buttonStarPlot2 = findViewById(R.id.PlotBall2);
        buttonStarPlot3 = findViewById(R.id.PlotBall3);
        buttonStarPlot4 = findViewById(R.id.PlotBall4);
        buttonStarPlot5 = findViewById(R.id.PlotBall5);
        buttonStarPlot6 = findViewById(R.id.PlotBall6);
        buttonStarPlot7 = findViewById(R.id.PlotBall7);
        buttonStarPlot8 = findViewById(R.id.PlotBall8);
        buttonStarPlot9 = findViewById(R.id.PlotBall9);

        buttonStarInstallation1 = findViewById(R.id.InstallationBall1);
        buttonStarInstallation2 = findViewById(R.id.InstallationBall2);
        buttonStarInstallation3 = findViewById(R.id.InstallationBall3);
        buttonStarInstallation4 = findViewById(R.id.InstallationBall4);
        buttonStarInstallation5 = findViewById(R.id.InstallationBall5);
        buttonStarInstallation6 = findViewById(R.id.InstallationBall6);
        buttonStarInstallation7 = findViewById(R.id.InstallationBall7);
        buttonStarInstallation8 = findViewById(R.id.InstallationBall8);
        buttonStarInstallation9 = findViewById(R.id.InstallationBall9);

        layout_actors_stars = findViewById(R.id.LinearLayoutActorsStars);
        layout_installation_stars = findViewById(R.id.LinearLayoutInstallationStars);
        layout_plot_stars = findViewById(R.id.LinearLayoutPlotStars);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayShowHomeEnabled(true);

        name = getIntent().getExtras().getString("name");
        name.substring(0, name.length() - 7);
        year = getIntent().getExtras().getString("year");
        ratio = getIntent().getExtras().getString("ratio");
        url = getIntent().getExtras().getString("url");

        addToWantedFilms = findViewById(R.id.addButtonSecond);
        addToMyFilms = findViewById(R.id.addButtonFirst);
        nameView = findViewById(R.id.addName);
        yearView = findViewById(R.id.addYear);
        ballKinopoisk = findViewById(R.id.addBallKinopoisk);
        ballActorsView = findViewById(R.id.addBallActors);
        ballPlotView = findViewById(R.id.addBallPlot);
        ballInstallationView = findViewById(R.id.addBallInstallation);
        reviewView = findViewById(R.id.review);
        goKinopoisk = findViewById(R.id.kinopoiskButtonInAddActivity);

        String name_without_year = name.substring(0,name.length()-6);
        nameView.setText(name_without_year);
        yearView.setText(year);
        ballKinopoisk.setText(ratio);

        databaseHelper = new DataBaseHelper(getApplicationContext());


        //Проверяем есть ли фильм в нашей коллекции
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from [table]", null);
        userCursor.moveToFirst();
        while (!userCursor.isAfterLast()) {
            if (userCursor.getString(1).equals(name) && userCursor.getString(10).equals("1")) {
                addToMyFilms.setText("ОБНОВИТЬ РЕЙТИНГ");
                break;

            }
            userCursor.moveToNext();
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void onClickAdd(View v) {

        if (v.getId() == R.id.addButtonFirst) {
            db = databaseHelper.getReadableDatabase();
            userCursor = db.rawQuery("select * from [table]", null);
            userCursor.moveToFirst();
            while (!userCursor.isAfterLast()) {
                if (userCursor.getString(1).equals(name)) {
                    String[] m = new String[]{userCursor.getString(0)};
                    db.delete("[table]", "_id = ?", m);
                    addToMyFilms.setText("ДОБАВИТЬ К ПРОСМОТРЕННЫМ");
                    break;

                }
                userCursor.moveToNext();
            }
            userCursor.moveToFirst();

            if (ballActorsView.getText().toString().equals("") || ballInstallationView.getText().toString().equals("")
                    || ballPlotView.getText().toString().equals("") || reviewView.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getApplicationContext(), "НЕ ВСЕ ОЦЕНКИ ЗАПОЛНЕНЫ", Toast.LENGTH_LONG);
                toast.show();
            } else {
                int ballUser = (Integer.parseInt(ballActorsView.getText().toString()) + Integer.parseInt(ballPlotView.getText().toString())
                        + Integer.parseInt(ballInstallationView.getText().toString())) / 3;
                ContentValues cv = new ContentValues();
                cv.put(DataBaseHelper.COLUMN_NAME, name);
                cv.put(DataBaseHelper.COLUMN_YEAR, Integer.parseInt(year));
                cv.put(DataBaseHelper.COLUMN_BALLACTORS, Integer.parseInt(ballActorsView.getText().toString()));
                cv.put(DataBaseHelper.COLUMN_BALLKINOPOISK, Double.parseDouble(ballKinopoisk.getText().toString()));
                cv.put(DataBaseHelper.COLUMN_BALLPLOT, Integer.parseInt(ballPlotView.getText().toString()));
                cv.put(DataBaseHelper.COLUMN_BALLUSER, ballUser);
                cv.put(DataBaseHelper.COLUMN_INSTALLATION, Integer.parseInt(ballInstallationView.getText().toString()));
                cv.put(DataBaseHelper.COLUMN_REVIEW, reviewView.getText().toString());
                cv.put(DataBaseHelper.COLUMN_URL, url);
                cv.put(DataBaseHelper.COLUMN_VIEWED, 1);
                db.insert("[table]", null, cv);
                db.close();
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                AddActivity.this.startActivity(i);
            }
        } else if (v.getId() == R.id.addButtonSecond) {
            userCursor = db.rawQuery("select * from [table]", null);
            userCursor.moveToFirst();
            while (!userCursor.isAfterLast()) {
                if (userCursor.getString(1).equals(name)) {
                    String[] m = new String[]{userCursor.getString(0)};
                    db.delete("[table]", "_id = ?", m);
                    break;

                }
                userCursor.moveToNext();
            }
            db = databaseHelper.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DataBaseHelper.COLUMN_NAME, name);
            cv.put(DataBaseHelper.COLUMN_YEAR, Integer.parseInt(year));
            cv.put(DataBaseHelper.COLUMN_BALLKINOPOISK, Double.parseDouble(ballKinopoisk.getText().toString()));
            cv.put(DataBaseHelper.COLUMN_BALLPLOT, 1);
            cv.put(DataBaseHelper.COLUMN_BALLUSER, 1);
            cv.put(DataBaseHelper.COLUMN_INSTALLATION, 1);
            cv.put(DataBaseHelper.COLUMN_REVIEW, "1");
            cv.put(DataBaseHelper.COLUMN_BALLACTORS, 1);
            cv.put(DataBaseHelper.COLUMN_URL, url);
            cv.put(DataBaseHelper.COLUMN_VIEWED, 0);
            db.insert("[table]", null, cv);
            db.close();
            Intent i = new Intent(AddActivity.this, MainActivity.class);
            AddActivity.this.startActivity(i);
        }
    }


    public void onClickKinoPoiskGo(View v) {
        Intent browserIntent = new
                Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void setStarsActors(Integer count) {
        if(count <=3) {
            layout_actors_stars.setBackgroundColor(Color.parseColor("#FFA68F"));
        }
        else if(count > 3 && count <= 6){
            layout_actors_stars.setBackgroundColor(Color.parseColor("#E8D790"));
        }
        else{
            layout_actors_stars.setBackgroundColor(Color.parseColor("#AAFFAA"));
        }

        for (int i = 1; i <= count; i++) {
            if (i == 1) {
                buttonStarActors1.setImageResource(R.drawable.star);
            } else if (i == 2) {
                buttonStarActors2.setImageResource(R.drawable.star);
            } else if (i == 3) {
                buttonStarActors3.setImageResource(R.drawable.star);
            } else if (i == 4) {
                buttonStarActors4.setImageResource(R.drawable.star);
            } else if (i == 5) {
                buttonStarActors5.setImageResource(R.drawable.star);
            } else if (i == 6) {
                buttonStarActors6.setImageResource(R.drawable.star);
            } else if (i == 7) {
                buttonStarActors7.setImageResource(R.drawable.star);
            } else if (i == 8) {
                buttonStarActors8.setImageResource(R.drawable.star);
            } else if (i == 9) {
                buttonStarActors9.setImageResource(R.drawable.star);
            }
        }
        for (int i = count + 1; i <= 9; i++) {
            if (i == 1) {
                buttonStarActors1.setImageResource(R.drawable.emptystar);
            } else if (i == 2) {
                buttonStarActors2.setImageResource(R.drawable.emptystar);
            } else if (i == 3) {
                buttonStarActors3.setImageResource(R.drawable.emptystar);
            } else if (i == 4) {
                buttonStarActors4.setImageResource(R.drawable.emptystar);
            } else if (i == 5) {
                buttonStarActors5.setImageResource(R.drawable.emptystar);
            } else if (i == 6) {
                buttonStarActors6.setImageResource(R.drawable.emptystar);
            } else if (i == 7) {
                buttonStarActors7.setImageResource(R.drawable.emptystar);
            } else if (i == 8) {
                buttonStarActors8.setImageResource(R.drawable.emptystar);
            } else if (i == 9) {
                buttonStarActors9.setImageResource(R.drawable.emptystar);
            }
        }
    }

    public void onClickStarsActors(View v) {
        switch (v.getId()) {
            case R.id.ActorBall1:
                ballActorsView.setText("1");
                setStarsActors(1);
                break;
            case R.id.ActorBall2:
                ballActorsView.setText("2");
                setStarsActors(2);
                break;
            case R.id.ActorBall3:
                ballActorsView.setText("3");
                setStarsActors(3);
                break;
            case R.id.ActorBall4:
                ballActorsView.setText("4");
                setStarsActors(4);
                break;
            case R.id.ActorBall5:
                ballActorsView.setText("5");
                setStarsActors(5);
                break;
            case R.id.ActorBall6:
                ballActorsView.setText("6");
                setStarsActors(6);
                break;
            case R.id.ActorBall7:
                ballActorsView.setText("7");
                setStarsActors(7);
                break;
            case R.id.ActorBall8:
                ballActorsView.setText("8");
                setStarsActors(8);
                break;
            case R.id.ActorBall9:
                ballActorsView.setText("9");
                setStarsActors(9);
                break;


        }
    }



    public void onClickStarsPlot(View v) {
        switch (v.getId()) {
            case R.id.PlotBall1:
                ballPlotView.setText("1");
                setStarsPlot(1);
                break;
            case R.id.PlotBall2:
                ballPlotView.setText("2");
                setStarsPlot(2);
                break;
            case R.id.PlotBall3:
                ballPlotView.setText("3");
                setStarsPlot(3);
                break;
            case R.id.PlotBall4:
                ballPlotView.setText("4");
                setStarsPlot(4);
                break;
            case R.id.PlotBall5:
                ballPlotView.setText("5");
                setStarsPlot(5);
                break;
            case R.id.PlotBall6:
                ballPlotView.setText("6");
                setStarsPlot(6);
                break;
            case R.id.PlotBall7:
                ballPlotView.setText("7");
                setStarsPlot(7);
                break;
            case R.id.PlotBall8:
                ballPlotView.setText("8");
                setStarsPlot(8);
                break;
            case R.id.PlotBall9:
                ballPlotView.setText("9");
                setStarsPlot(9);
                break;

        }
    }

    public void setStarsPlot(Integer count) {
        if(count <=3) {
            layout_plot_stars.setBackgroundColor(Color.parseColor("#FFA68F"));
        }
        else if(count > 3 && count <= 6){
            layout_plot_stars.setBackgroundColor(Color.parseColor("#E8D790"));
        }
        else{
            layout_plot_stars.setBackgroundColor(Color.parseColor("#AAFFAA"));
        }
        for (int i = 1; i <= count; i++) {
            if (i == 1) {
                buttonStarPlot1.setImageResource(R.drawable.star);
            } else if (i == 2) {
                buttonStarPlot2.setImageResource(R.drawable.star);
            } else if (i == 3) {
                buttonStarPlot3.setImageResource(R.drawable.star);
            } else if (i == 4) {
                buttonStarPlot4.setImageResource(R.drawable.star);
            } else if (i == 5) {
                buttonStarPlot5.setImageResource(R.drawable.star);
            } else if (i == 6) {
                buttonStarPlot6.setImageResource(R.drawable.star);
            } else if (i == 7) {
                buttonStarPlot7.setImageResource(R.drawable.star);
            } else if (i == 8) {
                buttonStarPlot8.setImageResource(R.drawable.star);
            } else if (i == 9) {
                buttonStarPlot9.setImageResource(R.drawable.star);
            }
        }
        for (int i = count + 1; i <= 9; i++) {
            if (i == 1) {
                buttonStarPlot1.setImageResource(R.drawable.emptystar);
            } else if (i == 2) {
                buttonStarPlot2.setImageResource(R.drawable.emptystar);
            } else if (i == 3) {
                buttonStarPlot3.setImageResource(R.drawable.emptystar);
            } else if (i == 4) {
                buttonStarPlot4.setImageResource(R.drawable.emptystar);
            } else if (i == 5) {
                buttonStarPlot5.setImageResource(R.drawable.emptystar);
            } else if (i == 6) {
                buttonStarPlot6.setImageResource(R.drawable.emptystar);
            } else if (i == 7) {
                buttonStarPlot7.setImageResource(R.drawable.emptystar);
            } else if (i == 8) {
                buttonStarPlot8.setImageResource(R.drawable.emptystar);
            } else if (i == 9) {
                buttonStarPlot9.setImageResource(R.drawable.emptystar);
            }
        }
    }

    public void onClickStarsInstallation(View v){
        switch (v.getId()) {
            case R.id.InstallationBall1:
                ballInstallationView.setText("1");
                setStarsInstallation(1);
                break;
            case R.id.InstallationBall2:
                ballInstallationView.setText("2");
                setStarsInstallation(2);
                break;
            case R.id.InstallationBall3:
                ballInstallationView.setText("3");
                setStarsInstallation(3);
                break;
            case R.id.InstallationBall4:
                ballInstallationView.setText("4");
                setStarsInstallation(4);
                break;
            case R.id.InstallationBall5:
                ballInstallationView.setText("5");
                setStarsInstallation(5);
                break;
            case R.id.InstallationBall6:
                ballInstallationView.setText("6");
                setStarsInstallation(6);
                break;
            case R.id.InstallationBall7:
                ballInstallationView.setText("7");
                setStarsInstallation(7);
                break;
            case R.id.InstallationBall8:
                ballInstallationView.setText("8");
                setStarsInstallation(8);
                break;
            case R.id.InstallationBall9:
                ballInstallationView.setText("9");
                setStarsInstallation(9);
                break;
        }
    }
    public void setStarsInstallation(Integer count) {

        if(count <=3) {
            layout_installation_stars.setBackgroundColor(Color.parseColor("#FFA68F"));
        }
        else if(count > 3 && count <= 6){
            layout_installation_stars.setBackgroundColor(Color.parseColor("#E8D790"));
        }
        else{
            layout_installation_stars.setBackgroundColor(Color.parseColor("#AAFFAA"));
        }

        for (int i = 1; i <= count; i++) {
            if (i == 1) {
                buttonStarInstallation1.setImageResource(R.drawable.star);
            } else if (i == 2) {
                buttonStarInstallation2.setImageResource(R.drawable.star);
            } else if (i == 3) {
                buttonStarInstallation3.setImageResource(R.drawable.star);
            } else if (i == 4) {
                buttonStarInstallation4.setImageResource(R.drawable.star);
            } else if (i == 5) {
                buttonStarInstallation5.setImageResource(R.drawable.star);
            } else if (i == 6) {
                buttonStarInstallation6.setImageResource(R.drawable.star);
            } else if (i == 7) {
                buttonStarInstallation7.setImageResource(R.drawable.star);
            } else if (i == 8) {
                buttonStarInstallation8.setImageResource(R.drawable.star);
            } else if (i == 9) {
                buttonStarInstallation9.setImageResource(R.drawable.star);
            }
        }
        for (int i = count + 1; i <= 9; i++) {
            if (i == 1) {
                buttonStarInstallation1.setImageResource(R.drawable.emptystar);
            } else if (i == 2) {
                buttonStarInstallation2.setImageResource(R.drawable.emptystar);
            } else if (i == 3) {
                buttonStarInstallation3.setImageResource(R.drawable.emptystar);
            } else if (i == 4) {
                buttonStarInstallation4.setImageResource(R.drawable.emptystar);
            } else if (i == 5) {
                buttonStarInstallation5.setImageResource(R.drawable.emptystar);
            } else if (i == 6) {
                buttonStarInstallation6.setImageResource(R.drawable.emptystar);
            } else if (i == 7) {
                buttonStarInstallation7.setImageResource(R.drawable.emptystar);
            } else if (i == 8) {
                buttonStarInstallation8.setImageResource(R.drawable.emptystar);
            } else if (i == 9) {
                buttonStarInstallation9.setImageResource(R.drawable.emptystar);
            }
        }
    }
}
