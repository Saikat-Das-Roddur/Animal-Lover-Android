package org.richit.animal.Others;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GlobaMethods {
    public static void sendEmailFeedback(Context context, String feedback, String feedbackEmail) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "" + feedbackEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "" + context.getApplicationInfo().loadLabel(context.getPackageManager()).toString() + " feedback");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "" + feedback);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
