package br.edu.ifspsaocarlos.sdm.firebasenotifications.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifspsaocarlos.sdm.firebasenotifications.NewNotificationActivity;
import br.edu.ifspsaocarlos.sdm.firebasenotifications.R;

/**
 * Created by agomes on 10/07/16.
 */
public class BackgroundService extends Service implements Runnable{
    public static final String TO = "to";     //TODO - Make it configurable
    public static final String USER_ID = "1"; //TODO - Make it dynamic
    public static final String FROM = "from"; //TODO - Make it configurable

    private boolean isOpen;
    private SharedPreferences localControl;

    @Override
    public void onCreate() {
        super.onCreate();
        isOpen = true;
        localControl = getSharedPreferences("Notification_Shared_Prefs",Context.MODE_PRIVATE);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(TO).child(USER_ID).child(FROM);

        //Read Value
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("NOTI-COUNT", "There are " + dataSnapshot.getChildrenCount() + " values.");
                Log.d("LOCAL-PREFERENCES", localControl.getAll().toString());
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    final String key = data.getKey();
                    final Long value = Long.parseLong(data.getValue().toString());
                    //Log.d("LOG", "Key :" + key + " Value :" + value);
                    localControl.edit().putLong(key, value).commit();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("NOTI", "Failed to read value.", error.toException());
            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                Log.d("CHILD", "ADDED");
                Map<String, Long> local = (HashMap<String, Long>) localControl.getAll();
                final String key = snapshot.getKey();
                if (!local.containsKey(key))
                    notification(key);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("CHILD", "CHANGED");
                Map<String, Long> local = (HashMap<String, Long>) localControl.getAll();
                final Long value = (Long) dataSnapshot.getValue();
                final String key = dataSnapshot.getKey();
                final Long localValue = local.get(key).longValue();
                if (value > localValue)
                    notification(key);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("CHILD", "REMOVED");
                //Do nothing
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("CHILD", "MOVED");
                //Do nothing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        new Thread(this).start();
    }

    private void notification(String userId) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NewNotificationActivity.class);
        intent.putExtra("user_id",userId);

        PendingIntent p = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_contato);
        builder.setTicker(getString(R.string.new_ticker));
        builder.setContentTitle(getString(R.string.new_title));
        builder.setContentText("Notification by user ID : " + userId);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(p);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_mensageiro));
        Notification notification = builder.build();
        notification.vibrate = new long[] {100, 250};
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        nm.notify(R.mipmap.ic_launcher, notification);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {

        while (isOpen) {
            try {
                Thread.sleep(getResources().getInteger(R.integer.tempo_inatividade_servico));
            }
            catch (InterruptedException ie) {
                Log.e("NOTI-ERROR", "Erro na thread de recuperação notificações");
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isOpen = false;
        stopSelf();
    }
}
