package com.example.sep4android;

import android.app.Application;

public class AppStatusChecker extends Application {

  public static boolean isActivityVisible() {
    return activityVisible;
  }

  public static void activityResumed() {
    activityVisible = true;
  }

  public static void activityPaused() {
    activityVisible = false;
  }

  private static boolean activityVisible;
}