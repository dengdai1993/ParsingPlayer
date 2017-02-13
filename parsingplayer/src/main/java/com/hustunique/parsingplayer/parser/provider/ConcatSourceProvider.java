package com.hustunique.parsingplayer.parser.provider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.hustunique.parsingplayer.parser.entity.VideoInfo;

import static com.hustunique.parsingplayer.parser.entity.VideoInfo.HD_0;
import static com.hustunique.parsingplayer.parser.entity.VideoInfo.HD_1;
import static com.hustunique.parsingplayer.parser.entity.VideoInfo.HD_2;
import static com.hustunique.parsingplayer.parser.entity.VideoInfo.HD_3;

/**
 * Created by JianGuo on 2/10/17.
 * Implementation for concat protocol
 */

public class ConcatSourceProvider extends VideoInfoSourceProvider {
    private NetworkInfo mNetworkInfo;
    @Override
    public String provideSource(VideoInfo videoInfo) {
        return ProtocolHelper.concat(videoInfo.getSegs(getHdByNetwork()));
    }

    public ConcatSourceProvider(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = cm.getActiveNetworkInfo();
    }

    private @Quality int getHdByNetwork() {
        switch (mNetworkInfo.getType()) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_WIMAX:
            case ConnectivityManager.TYPE_ETHERNET:
                return HD_3;
            case ConnectivityManager.TYPE_MOBILE:
                switch (mNetworkInfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_LTE:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        return HD_2;
                    case TelephonyManager.NETWORK_TYPE_UMTS: // 3G
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        return HD_1;
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 2G
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return HD_0;
                    default:
                        return HD_0;
                }
                default:
                    return HD_0;
        }
    }
}
