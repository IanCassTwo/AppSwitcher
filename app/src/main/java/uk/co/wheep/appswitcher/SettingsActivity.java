package uk.co.wheep.appswitcher;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import uk.co.wheep.appswitcher.util.AppSettings;


/**
 * This is the activity that is shown when the user launches
 * the app.
 *
 * @author icass
 */
public class SettingsActivity extends PreferenceActivity {
    private static final String TAG = "SettingsActivity";

    int REQUEST_READ_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean maps = Boolean.FALSE;

        // Get intent
        Intent intent = getIntent();

        // See if we've received a MAP intent
        Intent sel = intent.getSelector();
        if (sel != null) {
            if (sel.getCategories().contains("android.intent.category.APP_MAPS")) {
                maps = Boolean.TRUE;
            }
        }

        if (maps) {

            // Do app switcher
            AppSettings settings = new AppSettings(this);
            String pkg = settings.getNextPackage();
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pkg);
            if (launchIntent != null) {
                startActivity(launchIntent);
            }
            finish();
        } else {

            // Do settings screen
            addPreferencesFromResource(R.xml.settings_prefs);
        }

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen prefScreen,
                                         Preference pref) {
        return super.onPreferenceTreeClick(prefScreen, pref);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                new AboutDialog(this).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}