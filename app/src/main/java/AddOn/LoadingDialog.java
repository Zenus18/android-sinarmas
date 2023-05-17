package AddOn;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.letsmovie.R;

public class LoadingDialog {
    private Activity activity;
    private Dialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }
    public void startLoadingDialog() {
        if (dialog == null) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.custom_loading);
        }
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        TextView textView = dialog.findViewById(R.id.textView);
        textView.setText("Loading...");

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}