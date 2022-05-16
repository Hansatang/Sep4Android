/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sep4android;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.example.sep4android.Repositories.RoomRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class RCMService extends FirebaseMessagingService {

  private static final String TAG = "RCMService";

  public RCMService() {
    System.out.println("Created firebase");
  }

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    // [START_EXCLUDE]
    // There are two types of messages data messages and notification messages. Data messages
    // are handled
    // here in onMessageReceived whether the app is in the foreground or background. Data
    // messages are the type
    // traditionally used with GCM. Notification messages are only received here in
    // onMessageReceived when the app
    // is in the foreground. When the app is in the background an automatically generated
    // notification is displayed.
    // When the user taps on the notification they are returned to the app. Messages
    // containing both notification
    // and data payloads are treated as notification messages. The Firebase console always
    // sends notification
    // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
    // [END_EXCLUDE]

    if (AppStatusChecker.isActivityVisible()) {
      System.out.println("foreground");
      if (FirebaseAuth.getInstance().getCurrentUser() != null) {
        RoomRepository repository = RoomRepository.getInstance(this.getApplication());
        repository.getDatabaseRooms(FirebaseAuth.getInstance().getCurrentUser().getUid());
        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("key_1"), false);
      }
    } else {
      sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("key_1"), true);
      System.out.println("Back");

    }

    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d(TAG, "From: " + remoteMessage.getFrom());


    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
      System.out.println("DataLoad");
      Log.d(TAG, "Message data payload: " + remoteMessage.getData());
      System.out.println(remoteMessage.getData().get("title"));


    }

  }


  // [START on_new_token]

  /**
   * There are two scenarios when onNewToken is called:
   * 1) When a new token is generated on initial app startup
   * 2) Whenever an existing token is changed
   * Under #2, there are three scenarios when the existing token is changed:
   * A) App is restored to a new device
   * B) User uninstalls/reinstalls the app
   * C) User clears app data
   */
  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);

    // If you want to send messages to this application instance or
    // manage this apps subscriptions on the server side, send the
    // FCM registration token to your app server.
    sendRegistrationToServer(token);
  }
  // [END on_new_token]

  /**
   * Schedule async work using WorkManager.
   */
  private void scheduleJob() {
    // [START dispatch_job]
    OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
        .build();
    WorkManager.getInstance(this).beginWith(work).enqueue();
    // [END dispatch_job]
  }

  /**
   * Handle time allotted to BroadcastReceivers.
   */
  private void handleNow() {
    Log.d(TAG, "Short lived task is done.");
  }

  /**
   * Persist token to third-party servers.
   * <p>
   * Modify this method to associate the user's FCM registration token with any
   * server-side account maintained by your application.
   *
   * @param token The new token.
   */
  private void sendRegistrationToServer(String token) {
    RoomRepository repository = RoomRepository.getInstance(this.getApplication());
    repository.setNewToken(FirebaseAuth.getInstance().getCurrentUser().getUid(),token);
    // TODO: Implement this method to send token to your app server.
  }

  /**
   * Create and show a simple notification containing the received FCM message.
   *
   * @param messageBody FCM message body received.
   */
  private void sendNotification(String messageBody, String content, boolean openOnCLick) {
    String channelId = getString(R.string.default_notification_channel_id);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder;
    if (openOnCLick) {
      Intent intent = new Intent(this, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
          PendingIntent.FLAG_ONE_SHOT);

      notificationBuilder = new NotificationCompat.Builder(this, channelId)
          .setSmallIcon(R.drawable.ic_launcher_foreground)
          .setContentTitle(messageBody)
          .setContentText(content)
          .setAutoCancel(true)
          .setSound(defaultSoundUri)
          .setContentIntent(pendingIntent);
    } else {
      notificationBuilder = new NotificationCompat.Builder(this, channelId)
          .setSmallIcon(R.drawable.ic_launcher_foreground)
          .setContentTitle(messageBody)
          .setContentText(content)
          .setAutoCancel(true)
          .setSound(defaultSoundUri);
    }


    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationChannel channel = new NotificationChannel(channelId,
        "Channel human readable title",
        NotificationManager.IMPORTANCE_HIGH);
    notificationManager.createNotificationChannel(channel);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

}
