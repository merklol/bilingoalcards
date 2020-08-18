package com.bilingoal.bilingoalcards.settings;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.bilingoal.bilingoalcards.R;

public class AppSettings extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        Preference privacyPolicy = findPreference("privacy");
        privacyPolicy.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("https://bilingoal-privacy-policy.netlify.app/")));
            return true;
        });

        Preference feedback = findPreference("feedback");
        feedback.setOnPreferenceClickListener(preference -> {
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","sm.maksim@icloud.com", null))
                            .putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject))
                            .putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.email_body)),
                    getResources().getString(R.string.email_title)));
            return true;
        });
    }
}
