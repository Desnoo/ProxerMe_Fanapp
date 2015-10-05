package www.luneyco.com.proxertestapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Utils used to prepare the ui for special things like loading,etc.
 * Created by tinos_000 on 04.10.2015.
 */
public class UiUtils {

    private static ProgressDialog mProgressDialog;
    private static int mNumOfRequests = 0;

    /**
     * Used to display the loading dialog.
     *
     * @param _Context the context of the app.
     */
    public static void ShowLoadingDialog(Context _Context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(_Context);
        }
        mNumOfRequests ++;
        mProgressDialog.show();
    }

    /**
     * Hides the loading dialog.
     */
    public static void HideLoadingDialog() {
        mNumOfRequests--;
        if(mNumOfRequests == 0){
            mProgressDialog.hide();
        }
    }
}
