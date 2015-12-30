package siesgst.edu.in.tml16.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vishal on 30/12/15.
 */
public class ConnectionUtils {

    Context context;

    public ConnectionUtils(Context context) {
        this.context = context;
    }

    public boolean checkConnection() {
        final ConnectivityManager ComMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo nwInfo = ComMgr.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnected())
            return true;
        else
            return false;
    }
}
