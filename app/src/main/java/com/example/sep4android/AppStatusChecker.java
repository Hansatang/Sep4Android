package com.example.sep4android;

import android.app.Application;

/**
 * Class with static method to get current status(foreground or background) of application
 */
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