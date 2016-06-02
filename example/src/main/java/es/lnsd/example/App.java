package es.lnsd.example;

import android.app.Application;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import es.lnsd.example.models.City;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * RealmSearchView
 * Copyright (C) 2016 Lorenzo Delgado.
 * http://LNSD.es
 * <p/>
 * Licensed under The MIT License (MIT). See LICENSE file for more information.
 */
public class App extends Application {
    private static final String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        initializeDB();
    }

    private void initializeDB() {

        long initTime = System.currentTimeMillis();

        // Realm DB configuration object
        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name(getResources().getString(R.string.realm_db_filename))
                .schemaVersion(0)
                .build();

        // Clear Realm DB
        Realm.deleteRealm(config);

        Realm realm = Realm.getInstance(config);

        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.beginTransaction();

        Map<Class, Integer> JSONres = new HashMap<Class, Integer>();
        JSONres.put(City.class, R.raw.cities);

        Resources res = getResources();

        try {
            for (Map.Entry<Class, Integer> resource : JSONres.entrySet())
            {
                InputStream stream = res.openRawResource(resource.getValue());
                realm.createAllFromJson(resource.getKey(), stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // When the transaction is committed, all changes a synced to disk.
        realm.commitTransaction();

        long duration = (System.currentTimeMillis() - initTime);
        long sleepTime = TimeUnit.SECONDS.toMillis(getResources().getInteger(R.integer.splash_max_delay_sec)) - duration;
        Log.d(TAG, "DB insert time: " + duration + "ms");

        if(sleepTime > 0) {
            SystemClock.sleep(sleepTime);
        }

        Log.d(TAG, "Cities in DB: " + realm.where(City.class).findAll().size());

        realm.close();
    }
}
