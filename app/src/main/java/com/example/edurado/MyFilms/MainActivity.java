package com.example.edurado.MyFilms;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Film> KinopoiskFilms = new ArrayList<Film>();
    public List<Film> ViewedFilms = new ArrayList<Film>();
    public List<Film> WantedFilms = new ArrayList<Film>();

    ListView listView;

    Elements rating;
    Film film0;
    DataBaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;

    Integer numberOfList;
    Integer numberOfFilter = 1;
    FilmAdapter userAdapterForAddList;
    MyFilmAdapter userAdapterForViewedList;
    WantedFilmAdapter userAdapterForWantedList;
    TextView textInternet;
    TextView textNewFilm;




    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    numberOfList = 1;
                    ViewedFilms.clear();
                    setHomeLayout();
                    return true;
                case R.id.navigation_wanted_films:
                    numberOfList = 2;
                    WantedFilms.clear();
                    setWantedLayout();
                    return true;
                case R.id.navigation_kinopoisk_films:
                    numberOfList = 3;
                    setAddLayout();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.filters, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(numberOfList==1) userAdapterForViewedList.getFilter().filter(s);
                if(numberOfList==2) userAdapterForWantedList.getFilter().filter(s);
                if(numberOfList==3){ userAdapterForAddList.getFilter().filter(s); }
                return false;
            }

        });
        textNewFilm.setVisibility(View.GONE);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rating:
                numberOfFilter = 1;
                switch (numberOfList){
                    case 1:setHomeLayout();break;
                    case 2:setWantedLayout();break;
                    case 3:setAddLayout();break;
                }
                return true;
            case R.id.action_year_new:
                numberOfFilter = 2;
                switch (numberOfList){
                    case 1:setHomeLayout();break;
                    case 2:setWantedLayout();break;
                    case 3:setAddLayout();break;
                }
                return true;
            case R.id.action_year_old:
                numberOfFilter = 3;
                switch (numberOfList){
                    case 1:setHomeLayout();break;
                    case 2:setWantedLayout();break;
                    case 3:setAddLayout();break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberOfList = 1;
        textInternet = findViewById(R.id.internet);
        textInternet.setVisibility(View.GONE);
        listView = findViewById(R.id.info);
        textNewFilm = findViewById(R.id.newFilm);
        textNewFilm.setVisibility(View.GONE);

            //При запуске приложение парсится список фильмов
            class MyTask extends AsyncTask<String, Void, String> {
                Elements name;
                String info;
                String year;
                int temp;
                String ratio;
                @Override
                protected String doInBackground(String... params) {
                    Document doc = null;
                    try {

                        //определеляем откуда будем парсить
                        doc = Jsoup.connect("https://www.kinopoisk.ru/top/").get();
                        int place = 1;
                        int temp;
                        //Парсим все 250 фильмов в список.
                        for (; place <= 250; place++) {
                            name = doc.select("#top250_place_" + place + " td a.all");
                            ratio = doc.select("#top250_place_" + place + " td div a.continue").text();
                            temp = name.text().length();
                            year = name.text().substring(temp - 5, temp - 1);
                            KinopoiskFilms.add(new Film(name.text(),year,info,ratio,Integer.toString(place),
                                    "https://www.kinopoisk.ru" + name.attr("href")));
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    return "1";
                }
                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                }
            }
            MyTask mt = new MyTask();
            mt.execute();

            mTextMessage = (TextView) findViewById(R.id.message);
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            listView = (ListView)findViewById(R.id.info);
            setHomeLayout();


    }





    //Метод для установления активности для просмотренных фильмов
    public void setHomeLayout(){
        textInternet.setVisibility(View.GONE);
        ViewedFilms.clear();
        databaseHelper = new DataBaseHelper(this);
        userAdapterForViewedList = new MyFilmAdapter(this, R.layout.list_item_collections, ViewedFilms);
        listView.setAdapter(userAdapterForViewedList);
        try {
            databaseHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from [table]", null);
        userCursor.moveToFirst();

        while (!userCursor.isAfterLast()) {
            int check = userCursor.getInt(10);

            if(check==1){

                ViewedFilms.add(new Film(userCursor.getString(0),userCursor.getString(1),userCursor.getString(2),userCursor.getString(3),userCursor.getString(4),
                        userCursor.getString(5),userCursor.getString(6),userCursor.getString(7),userCursor.getString(8), userCursor.getString(9), userCursor.getString(10)));
            }userCursor.moveToNext();
        }
        if(ViewedFilms.size()==0){
            ViewedFilms.add(new Film("Нажмите \"Добавить\" для добавления фильмов"));

            userAdapterForViewedList.notifyDataSetChanged();
        }
        else{
            if(numberOfFilter==1) Collections.sort(ViewedFilms,new ComparatorFilms());
            else if(numberOfFilter==2) Collections.sort(ViewedFilms,new ComparatorFilmsYearNewToOld());
            else Collections.sort(ViewedFilms,new ComparatorFilmsYearOldToNew());
            userAdapterForViewedList.notifyDataSetChanged();
            db.close();
        }


    }

    //Метод для установления активности для желаемых к просмотру фильмов
    public void setWantedLayout(){
        textInternet.setVisibility(View.GONE);
        WantedFilms.clear();
        databaseHelper = new DataBaseHelper(this);
        userAdapterForWantedList = new WantedFilmAdapter(this, R.layout.list_item_wanted, WantedFilms);
        listView.setAdapter(userAdapterForWantedList);
        try {
            databaseHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            db = databaseHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from [table]", null);
        userCursor.moveToFirst();

        while (!userCursor.isAfterLast()) {
            int check = userCursor.getInt(10);

            if(check==0){
                WantedFilms.add(new Film(userCursor.getString(0),userCursor.getString(1),userCursor.getString(2),userCursor.getString(3),userCursor.getString(9)));
            }
            userCursor.moveToNext();
        }
        if(WantedFilms.size()==0){
            WantedFilms.add(new Film("Нажмите \"Добавить\" для добавления фильмов"));
            userAdapterForWantedList.notifyDataSetChanged();
        }
        else{
            if(numberOfFilter==1) Collections.sort(WantedFilms,new ComparatorBallKinopoisk());
            else if(numberOfFilter==2) Collections.sort(WantedFilms,new ComparatorFilmsYearNewToOld());
            else Collections.sort(WantedFilms,new ComparatorFilmsYearOldToNew());
            userAdapterForWantedList.notifyDataSetChanged();
        }
        db.close();
    }



    //Метод для установления активности для добавления фильмов
    public void setAddLayout(){
        WantedFilms.clear();
        ViewedFilms.clear();
        textInternet = findViewById(R.id.internet);
        if(!isOnline()){
            textInternet.setVisibility(View.VISIBLE);
            textInternet.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
            textInternet.setText("НЕТ ПОДКЛЮЧЕНИЯ К ИНТЕРНЕТУ");
        }
        else {
            while(KinopoiskFilms.size()!=250){
                textInternet.setVisibility(View.VISIBLE);
                textInternet.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                textInternet.setText("НЕТ ПОДКЛЮЧЕНИЯ К ИНТЕРНЕТУ");
            }
            textInternet.setVisibility(View.GONE);
            userAdapterForAddList = new FilmAdapter(this, R.layout.list_item, KinopoiskFilms);
            if(numberOfFilter==1) Collections.sort(KinopoiskFilms,new ComparatorBallKinopoisk());
            else if(numberOfFilter==2) Collections.sort(KinopoiskFilms,new ComparatorFilmsYearNewToOld());
            else Collections.sort(KinopoiskFilms,new ComparatorFilmsYearOldToNew());
            listView.setAdapter(userAdapterForAddList);
            userAdapterForAddList.notifyDataSetChanged();

        }

    }

   ////Создаем слушатели для списков////

    //Для просмотренных фильмов
    public void onClickViewedFilms(View view){
        Integer id_=-1;
        TextView sView = view.findViewById(R.id.MynameFilm);
        String s = sView.getText().toString();
        for (int i = 0; i < ViewedFilms.size() ; i++) {
            if(ViewedFilms.get(i).getName().equals(s)) {
                id_ = i;
                break;
            }
        }
        Intent intent = new Intent(MainActivity.this, FilmInfoActivity.class);
        TextView name = view.findViewById(R.id.MynameFilm);
        String name_str = name.getText().toString();
        if(!name_str.equals("Нажмите \"Добавить\" для добавления фильмов")){
            TextView kinopoisk = view.findViewById(R.id.ratioKinopoisk);
            TextView user = view.findViewById(R.id.ratioUser);
            TextView top = view.findViewById(R.id.MyplaceList);
            intent.putExtra("name",name.getText().toString());
            intent.putExtra("userRatio",user.getText().toString());
            intent.putExtra("plotBall", ViewedFilms.get(id_).getPlotBall());
            intent.putExtra("actorsBall", ViewedFilms.get(id_).getActorBall());
            intent.putExtra("installationBall", ViewedFilms.get(id_).getInstallationBall());
            intent.putExtra("review", ViewedFilms.get(id_).getInfo());
            intent.putExtra("id", ViewedFilms.get(id_).getId());
            intent.putExtra("kinopoiskRatio",kinopoisk.getText().toString());
            intent.putExtra("Url",ViewedFilms.get(id_).getUrl());
            Integer integer = id_+1;
            intent.putExtra("top", integer.toString());
            MainActivity.this.startActivity(intent);
        }
    }

    //Для желаемых фильмов
    public void onClickWantedFilms(View view){
        TextView name_view = view.findViewById(R.id.nameFilm);
        String name_str = name_view.getText().toString();
        for (int i = 0; i < WantedFilms.size(); i++) {
            if(name_str.equals(WantedFilms.get(i).getName())){
                Context context = getApplicationContext();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("name",WantedFilms.get(i).getName());
                if(!WantedFilms.get(i).getName().equals("Нажмите \"Добавить\" для добавления фильмов")){
                    intent.putExtra("year",WantedFilms.get(i).getYear());
                    intent.putExtra("ratio",WantedFilms.get(i).getRatio());
                    intent.putExtra("url",WantedFilms.get(i).getUrl());
                    MainActivity.this.startActivity(intent);
                    break;
                }
            }
        }
    }

    //Для фильмов с Кинопоиска
    public void onClickAddFilms(View view){
        Context context = getApplicationContext();
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        TextView place_text_view = view.findViewById(R.id.placeList);
        int place_str=1;
        TextView name_view = view.findViewById(R.id.nameFilm);
        String name = name_view.getText().toString();
        for (int j = 0; j < KinopoiskFilms.size() ; j++) {
            if(name.equals(KinopoiskFilms.get(j).getName())){
                place_str = j;
                break;
            }
        }
        i.putExtra("name",KinopoiskFilms.get(place_str).getName());
        i.putExtra("ratio",KinopoiskFilms.get(place_str).getRatio());
        i.putExtra("year", KinopoiskFilms.get(place_str).getYear());
        i.putExtra("url",KinopoiskFilms.get(place_str).getUrl());
        MainActivity.this.startActivity(i);
    }

    //Метод для проверки соединения устройства с сетью
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    //СОЗДАЕМ АДАПТЕРЫ//


    //Создаем адаптер для списка фильмов с Кинопоиска
    public class FilmAdapter extends ArrayAdapter<Film> implements Filterable {
        private LayoutInflater inflater;
        private int layout;
        List<Film> films;
        List<Film> filmsOriginal;
        public FilmAdapter(Context context, int resource, List<Film> films) {
            super(context, resource, films);
            this.films = films;
            this.layout = resource;
            this.inflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount()
        {
            return films.size();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    films = (ArrayList<Film>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }


                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<Film> FilteredArrList = new ArrayList<Film>();

                    if (filmsOriginal == null) {
                        filmsOriginal = new ArrayList<Film>(films);
                    }
                    if (constraint == null || constraint.length() == 0) {
                        // set the Original result to return
                        results.count = filmsOriginal.size();
                        results.values = filmsOriginal;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < filmsOriginal.size(); i++) {
                            String data = filmsOriginal.get(i).getName();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new Film(filmsOriginal.get(i).getName(), filmsOriginal.get(i).getYear(), filmsOriginal.get(i).getInfo(), filmsOriginal.get(i).getRatio(), filmsOriginal.get(i).getPlace(), filmsOriginal.get(i).getUrl()));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }


        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            @SuppressLint("ViewHolder") View view = inflater.inflate(this.layout, parent, false);

            TextView nameView = (TextView) view.findViewById(R.id.nameFilm);
            TextView ratioView = view.findViewById(R.id.ratio);
            TextView placeView = view.findViewById(R.id.placeList);

            film0 = films.get(position);
            nameView.setText(film0.getName());
            ratioView.setText(film0.getRatio());
            if(Integer.valueOf(film0.getPlace())<=3){
                placeView.setTextColor(Color.parseColor("#006400"));
                placeView.setText(film0.getPlace());}
            else{
                placeView.setTextColor(Color.parseColor("#000000"));
                placeView.setText(film0.getPlace());}
            return view;

        }

    }

    //Создаем адаптер для списка просмотренных фильмов
    public class MyFilmAdapter extends ArrayAdapter<Film> {
        private LayoutInflater inflater;
        private int layout;
        List<Film> films;
        List<Film> filmsOriginal;
        public MyFilmAdapter(Context context, int resource, List<Film> films) {
            super(context, resource, films);
            try {
                this.films = films;
                this.layout = resource;
                this.inflater = LayoutInflater.from(context);
            } catch (Exception e) {
                System.out.print(e);
            }
        }



        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(this.layout, parent, false);
            TextView nameView = (TextView) view.findViewById(R.id.MynameFilm);
            TextView ratioUserView = view.findViewById(R.id.ratioUser);
            TextView ratioKinopoiskView = view.findViewById(R.id.ratioKinopoisk);
            TextView placeView = view.findViewById(R.id.MyplaceList);
            film0 = films.get(position);

            nameView.setText(film0.getName());
            ratioUserView.setText(film0.getUserRatio());
            ratioKinopoiskView.setText(film0.getRatio());
            Integer place = position+1;
            placeView.setText(place.toString());

            return view;

        }
        @Override
        public int getCount()
        {
            return films.size();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    films = (ArrayList<Film>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }


                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Film> FilteredArrList = new ArrayList<Film>();

                    if (filmsOriginal == null) {
                        filmsOriginal = new ArrayList<Film>(films); // saves the original data in mOriginalValues
                    }
                    if (constraint == null || constraint.length() == 0) {
                        // set the Original result to return
                        results.count = filmsOriginal.size();
                        results.values = filmsOriginal;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < filmsOriginal.size(); i++) {
                            String data = filmsOriginal.get(i).getName();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new Film(filmsOriginal.get(i).getName(), filmsOriginal.get(i).getYear(), filmsOriginal.get(i).getInfo(), filmsOriginal.get(i).getRatio(), filmsOriginal.get(i).getPlace(), filmsOriginal.get(i).getUrl()));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }

    }

    public class WantedFilmAdapter extends ArrayAdapter<Film> {
        private LayoutInflater inflater;
        private int layout;
        List<Film> films;
        List<Film> filmsOriginal;
        public WantedFilmAdapter(Context context, int resource, List<Film> films) {
            super(context, resource, films);
            try {
                this.films = films;
                this.layout = resource;
                this.inflater = LayoutInflater.from(context);
            } catch (Exception e) {
                System.out.print(e);
            }
        }

        @Override
        public int getCount()
        {
            return films.size();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    films = (ArrayList<Film>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }


                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Film> FilteredArrList = new ArrayList<Film>();

                    if (filmsOriginal == null) {
                        filmsOriginal = new ArrayList<Film>(films); // saves the original data in mOriginalValues
                    }

                    if (constraint == null || constraint.length() == 0) {
                        // set the Original result to return
                        results.count = filmsOriginal.size();
                        results.values = filmsOriginal;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < filmsOriginal.size(); i++) {
                            String data = filmsOriginal.get(i).getName();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrList.add(new Film(filmsOriginal.get(i).getName(), filmsOriginal.get(i).getYear(), filmsOriginal.get(i).getInfo(), filmsOriginal.get(i).getRatio(), filmsOriginal.get(i).getPlace(), filmsOriginal.get(i).getUrl()));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(this.layout, parent, false);
            TextView nameView = (TextView) view.findViewById(R.id.nameFilm);
            TextView ratioKinopoiskView = view.findViewById(R.id.ratio);
            TextView placeView = view.findViewById(R.id.placeList);
            film0 = films.get(position);

            nameView.setText(film0.getName());
            ratioKinopoiskView.setText(film0.getRatio());
            Integer place = position+1;
            placeView.setText(place.toString());

            return view;

        }

    }
}
