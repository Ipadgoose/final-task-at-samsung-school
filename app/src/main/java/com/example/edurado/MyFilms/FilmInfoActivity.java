package com.example.edurado.MyFilms;

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
import android.widget.ImageView;
import android.widget.TextView;


public class FilmInfoActivity extends AppCompatActivity {
    String name;
    String year;
    String userRatio;
    String kinopoiskRatio;
    String ballActors;
    String ballPlot;
    String ballInstallation;
    String review;
    String id;
    TextView nameView, yearView, ballKinopoisk, topView;
    TextView ballActorsView, ballPlotView, ballInstallationView, reviewView;
    Button delete;
    String url;

    ImageView buttonStarActors1, buttonStarActors2, buttonStarActors3, buttonStarActors4, buttonStarActors5, buttonStarActors6, buttonStarActors7, buttonStarActors8, buttonStarActors9;
    ImageView buttonStarPlot1, buttonStarPlot2, buttonStarPlot3, buttonStarPlot4, buttonStarPlot5, buttonStarPlot6, buttonStarPlot7, buttonStarPlot8, buttonStarPlot9;
    ImageView buttonStarInstallation1, buttonStarInstallation2, buttonStarInstallation3, buttonStarInstallation4, buttonStarInstallation5, buttonStarInstallation6, buttonStarInstallation7, buttonStarInstallation8, buttonStarInstallation9;


    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        setTitle("Просмотр фильма из коллекции");
        id = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        year = name.substring(name.length()-5, name.length()-1);
        name = name.substring(0, name.length()-7);
        ballActors = getIntent().getExtras().getString("actorsBall");
        ballPlot = getIntent().getExtras().getString("plotBall");
        ballInstallation = getIntent().getExtras().getString("installationBall");
        review = getIntent().getExtras().getString("review");
        userRatio = getIntent().getExtras().getString("userRatio");
        kinopoiskRatio = getIntent().getExtras().getString("kinopoiskRatio");
        url = getIntent().getExtras().getString("Url");

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


        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayShowHomeEnabled(true);


        nameView = findViewById(R.id.MyName);
        yearView = findViewById(R.id.MyYear);
        ballKinopoisk = findViewById(R.id.MyBallKinopoisk);
        reviewView = findViewById(R.id.Myreview);
        delete = findViewById(R.id.delete);



        nameView.setText(name);
        yearView.setText(year);
        ballKinopoisk.setText(kinopoiskRatio);



        setStarsActorsView(Integer.parseInt(ballActors));
        setStarsPlotView(Integer.parseInt(ballPlot));
        setStarsInstallationView(Integer.parseInt(ballInstallation));


        reviewView.setText(review);

        databaseHelper = new DataBaseHelper(getApplicationContext());

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
    public void onClickDelete(View v){
        db = databaseHelper.getReadableDatabase();
        String[] m = new String[]{id};
        db.delete("[table]","_id = ?", m);
        db.close();

        Intent i = new Intent(FilmInfoActivity.this, MainActivity.class);
        FilmInfoActivity.this.startActivity(i);
    }
    public void onClickKinoPoiskGoFilmInfo(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    public void setStarsActorsView(Integer countStars){
        for (int i = 1; i <= countStars; i++) {
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
        for (int i = countStars + 1; i <= 9; i++) {
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

    public void setStarsPlotView(Integer countStars){
        for (int i = 1; i <= countStars; i++) {
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
        for (int i = countStars + 1; i <= 9; i++) {
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
    public void setStarsInstallationView(Integer countStars){
        for (int i = 1; i <= countStars; i++) {
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
        for (int i = countStars + 1; i <= 9; i++) {
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

