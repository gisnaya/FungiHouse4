package com.example.fungihouse;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataInterface {

    final public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    // Init format
    final public static SimpleDateFormat myDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("id","ID"));
    final public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm", Locale.getDefault());
    // Init format
    final public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd, MMM yyyy", new Locale("id","ID"));
    final public static SimpleDateFormat simpleDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    final public static SimpleDateFormat formatwaktu = new SimpleDateFormat("HH:mm", Locale.getDefault());

    final public static SimpleDateFormat englishDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

    final public static SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id","ID"));
    final public static SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
    public static String grafikurl, tanggal;

    public static String formateDateFromstring(SimpleDateFormat inputFormat, SimpleDateFormat outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        try {
            parsed = inputFormat.parse(inputDate);
            outputDate = outputFormat.format(parsed);

        } catch (ParseException e) {
        }

        return outputDate;

    }
}
