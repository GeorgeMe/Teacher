package com.dmd.zsb.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.utils.BusHelper;

/**
 *
 */
public class PhoneCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent) {
            return;
        }

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            BusHelper.post(new EventCenter(Constants.EVENT_STOP_PLAY_MUSIC));
        } else {
            final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(new PhoneStateListener() {
                @Override
                public void onCallStateChanged(int state, String incomingNumber) {
                    super.onCallStateChanged(state, incomingNumber);
                    switch (state) {

                        case TelephonyManager.CALL_STATE_OFFHOOK:
                            break;

                        case TelephonyManager.CALL_STATE_RINGING:
                            BusHelper.post(new EventCenter(Constants.EVENT_STOP_PLAY_MUSIC));
                            break;

                        case TelephonyManager.CALL_STATE_IDLE:
                            BusHelper.post(new EventCenter(Constants.EVENT_START_PLAY_MUSIC));
                            break;
                    }
                }
            }, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}
