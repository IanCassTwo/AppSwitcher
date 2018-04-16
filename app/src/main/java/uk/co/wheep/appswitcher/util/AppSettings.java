/**
 * This file is part of Simple Last.fm Scrobbler.
 * <p/>
 * https://github.com/tgwizard/sls
 * <p/>
 * Copyright 2011 Simple Last.fm Scrobbler Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package uk.co.wheep.appswitcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author tgwizard
 * @author icass
 */
public class AppSettings {

    private static final String TAG = "AppSettings";
    private static final String SETTINGS_NAME = "settings";


    private final Context mCtx;
    private final SharedPreferences prefs;
    private static final String KEY_APP_PKGS = "app_packages";
    private static final String KEY_CURRENT = "app_current";


    public Set<String> getPackages() {
        return prefs.getStringSet(KEY_APP_PKGS, null);
    }

     public String getNextPackage(String currentPackage) {
         Log.d(TAG, "Looking for " + currentPackage);

        Set<String> set = prefs.getStringSet(KEY_APP_PKGS, null);
        if (set == null)
            return null;

        if (set.isEmpty()) {
            return null;
        }

        String[] packages = set.toArray(new String[0]);
        Integer current = prefs.getInt(KEY_CURRENT, 0);

         if (set.contains(currentPackage)) {
             Log.d(TAG, "It's here somewhere");
            // find it
            for(int i = 0; i < packages.length; i++) {
                if (packages[i].equals(currentPackage)) {
                    Log.d(TAG, "Found it!");
                    if (i < packages.length - 1) {
                        current = i + 1;
                    } else {
                        current = 0;
                    }
                }
            }
         } else if (current < packages.length - 1) {
            current++;
        } else {
            current = 0;
        }

        SharedPreferences.Editor e = prefs.edit();
        e.putInt(KEY_CURRENT , current);
        e.commit();

        return packages[current];
    }

    public void setPackages(Set<String> packages) {
        SharedPreferences.Editor e = prefs.edit();
        e.putStringSet(KEY_APP_PKGS , packages);
        e.commit();

        // Also reset the index
        e.putInt(KEY_CURRENT , 0);
        e.commit();
    }

    public AppSettings(Context ctx) {
        mCtx = ctx;
        prefs = ctx.getSharedPreferences(SETTINGS_NAME, 0);
    }

}