package uk.co.wheep.appswitcher;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import uk.co.wheep.appswitcher.util.AppSettings;

public class OptionsActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "OptionsGeneralScreen";
    private AppSettings settings;

    // Class to store the packages installed on this system
    class PInfo {
        private HashMap<CharSequence,CharSequence> packages = new HashMap<CharSequence,CharSequence>();

        void addEntry(CharSequence k, CharSequence v) {
            Log.d(TAG, "K: " + k + ", V: " + v);
            packages.put(k, v);
        }

        CharSequence[] getNames() {
            return packages.keySet().toArray(new String[0]);
        }

        CharSequence[] getPackages() {
            return packages.values().toArray(new String[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.options);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        settings = new AppSettings(this);

        PInfo installedApps = getInstalledApps();

        MultiSelectListPreference  packagesDialog = (MultiSelectListPreference)findPreference("packages");
        packagesDialog.setEntries(installedApps.getNames());
        packagesDialog.setEntryValues(installedApps.getPackages());
        packagesDialog.setDefaultValue(settings.getPackages());

        packagesDialog.setOnPreferenceChangeListener(this);

    }

    private PInfo getInstalledApps() {
        PInfo pInfo = new PInfo();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            if (p.versionName == null) {
                continue ;
            }
            pInfo.addEntry(p.applicationInfo.loadLabel(getPackageManager()).toString(), p.packageName);
        }
        return pInfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onPreferenceChange(Preference preference, Object o) {
        if(preference.getKey().equals("packages")) {
            // Save our selection
            HashSet<String> packages = (HashSet)o;
            settings.setPackages(packages);
            return true;
        }

        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen prefScreen,
                                         Preference pref) {
        return super.onPreferenceTreeClick(prefScreen, pref);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}