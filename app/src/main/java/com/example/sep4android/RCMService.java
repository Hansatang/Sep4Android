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

import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.example.sep4android.Repositories.RoomRepository;
import com.example.sep4android.Repositories.TokenRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    Log.d(TAG, "From: " + remoteMessage.getFrom());
    if (remoteMessage.getData().size() > 0) {
      if (AppStatusChecker.isActivityVisible()) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
          RoomRepository repository = RoomRepository.getInstance(this.getApplication());
          repository.getDatabaseRooms(FirebaseAuth.getInstance().getCurrentUser().getUid());
          if (remoteMessage.getData().get("exceeded").contains("true")) {
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("exceeded"), false);
          }
        }
      } else {
        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("exceeded"), true);
      }
    }
  }


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
    sendRegistrationToServer(token);
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
    TokenRepository repository = TokenRepository.getInstance(this.getApplication());
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
      user.getIdToken(false).addOnSuccessListener(result -> {
        repository.setNewToken(user.getUid(), token);
      });
    }

  }

  /**
   * Create and show a simple notification containing the received FCM message.
   *
   * @param title FCM message body received.
   */
  private void sendNotification(String title, String content, boolean openOnCLick) {
    String channelId = getString(R.string.default_notification_channel_id);
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder;
    notificationBuilder = new NotificationCompat.Builder(
        this, channelId).
        setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(content).setAutoCancel(true)
        .setSound(defaultSoundUri);
    if (openOnCLick) {
      Intent intent = new Intent(this, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
      notificationBuilder.setContentIntent(pendingIntent);
    }

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    NotificationChannel channel = new NotificationChannel
        (channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
    notificationManager.createNotificationChannel(channel);

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
  }

}
