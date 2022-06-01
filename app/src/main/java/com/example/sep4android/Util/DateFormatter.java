package com.example.sep4android.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

  /**
   * @param currentItem Date in String in "yyyy-MM-dd'T'HH:mm:ss" format
   * @return modified Date in String in "E hh:mm:ss" format
   */
  public static String getFormattedDateForChildAdapter(String currentItem) {
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      Date date1 = dateFormat.parse(currentItem);
      DateFormat dateFormat2 = new SimpleDateFormat("E hh:mm:ss");
      strDate = dateFormat2.format(date1);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strDate;
  }

  public static String getFormattedDateForRoom(String date) {
    String strDate = null;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
      Date date1 = dateFormat.parse(date);
      System.out.println("Gut"+date1);
      DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd E hh:mm:ss");
      strDate = dateFormat2.format(date1);
      System.out.println("Tag"+strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return strDate;
  }

  public static String getFormattedDateForParent(LocalDateTime date) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd E");
    return dtf.format(date);
  }


  public static String getFormattedDateForArchive(LocalDateTime date) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return dtf.format(date);
  }
}
