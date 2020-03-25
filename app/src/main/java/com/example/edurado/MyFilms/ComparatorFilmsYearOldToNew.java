package com.example.edurado.MyFilms;

import java.util.Comparator;

public class ComparatorFilmsYearOldToNew implements Comparator<Film> {
    @Override
    public int compare(Film obj1, Film obj2) {

        if (Integer.parseInt(obj1.getYear()) > Integer.parseInt(obj2.getYear()))
            return 1;
        else
            return -1;
    }
}
