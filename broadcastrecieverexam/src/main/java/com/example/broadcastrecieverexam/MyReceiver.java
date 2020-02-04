package com.example.broadcastrecieverexam;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String MY_ACTION = "com.example.broadcastreceiverexam.action.ACTION_BATTERY_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        if("koon".equals(intent.getAction())) {
            Toast.makeText(context, "받은 액션***** : " + actionName, Toast.LENGTH_SHORT).show();
        }
    }
}
