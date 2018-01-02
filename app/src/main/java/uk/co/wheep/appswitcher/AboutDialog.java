package uk.co.wheep.appswitcher;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import uk.co.wheep.appswitcher.util.Util;

public class AboutDialog {
    @SuppressWarnings("unused")
    private static final String TAG = "AboutDialog";
    private final Context mCtx;

    public AboutDialog(Context ctx) {
        super();
        this.mCtx = ctx;
    }

    public void show() {
        final LayoutInflater factory = LayoutInflater.from(mCtx);

        View dialogView = factory.inflate(R.layout.about, null);

        innerUpdate(dialogView);

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(mCtx).setTitle(
                R.string.about).setIcon(android.R.drawable.ic_dialog_info).setView(
                dialogView).setNegativeButton(R.string.close,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        adBuilder.show();
    }

    private void innerUpdate(View dialogView) {
        TextView appName = dialogView.findViewById(R.id.app_name);
        TextView author = dialogView.findViewById(R.id.author);
        TextView license = dialogView.findViewById(R.id.license);

        // app name & version
        String appText = Util.getAppName(mCtx, mCtx.getPackageName()) + " v"
                + Util.getAppVersionName(mCtx, mCtx.getPackageName());
        appName.setText(appText);

        // author
        author.setText(R.string.by_adam);

        // license
        license.setText(R.string.license);

    }
}
