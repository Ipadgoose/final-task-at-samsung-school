package com.example.edurado.MyFilms;
import java.util.Comparator;

public class ComparatorBallKinopoisk implements Comparator<Film> {
    @Override
    public int compare(Film obj1, Film obj2) {
        if (Double.parseDouble(obj1.getRatio()) < Double.parseDouble(obj2.getRatio()))
            return 1;
        else
            return -1;
    }}
