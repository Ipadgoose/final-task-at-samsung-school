package com.example.edurado.MyFilms;

import java.util.Comparator;

class ComparatorFilms implements Comparator<Film> {
    @Override
    public int compare(Film obj1, Film obj2) {

        if (Integer.parseInt(obj1.getUserRatio()) < Integer.parseInt(obj2.getUserRatio()))
            return 1;
        else
            return -1;
    }}