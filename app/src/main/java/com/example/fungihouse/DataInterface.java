package com.example.fungihouse;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataInterface {

    final public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    final public static DatabaseReference myRefGrafik = database.getReference("monitoring/grafik");
    final public static DatabaseReference myRefMonitor = database.getReference("monitoring");
    final public static DatabaseReference myRefFeeder = database.getReference("feeder");
    final public static DatabaseReference myRefPanduan = database.getReference("panduan");
    // Init format
    final public static SimpleDateFormat myDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", new Locale("id","ID"));
    final public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm", Locale.getDefault());
    // Init format
    final public static SimpleDateFormat DateFormat = new SimpleDateFormat("dd MMM yyyy", new Locale("id","ID"));
    final public static SimpleDateFormat simpleDate = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
    final public static SimpleDateFormat formatwaktu = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static String grafikurl, tanggal;

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        try {
            parsed = simpleDate.parse(inputDate);
            outputDate = DateFormat.format(parsed);

        } catch (ParseException e) {
        }

        return outputDate;

    }
}
