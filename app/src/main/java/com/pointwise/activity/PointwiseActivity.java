package com.pointwise.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;
import android.widget.Toast;

import com.pointwise.R;
import com.pointwise.entity.UserData;
import com.pointwise.manager.SimulateUnreliableNetworkManager;
import com.pointwise.util.Constants;

public class PointwiseActivity extends BaseActivity {

    private UIRefreshReceiver mUIRefreshReceiver;

    private TextView mTextViewData;

    private TextView mTextQueueSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointwise);

        this.mTextViewData = (TextView) findViewById(R.id.text_view_label_data);
        this.mTextQueueSize = (TextView) findViewById(R.id.text_view_queue_size);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mUIRefreshReceiver == null) {
            this.mUIRefreshReceiver = new UIRefreshReceiver();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mUIRefreshReceiver,
                new IntentFilter(Constants.BroadcastEvents.USER_DATA_PICKED));
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mUIRefreshReceiver,
                new IntentFilter(Constants.BroadcastEvents.UNRELIABLE_NETWORK));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mUIRefreshReceiver);
    }

    private class UIRefreshReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final UserData userData = (UserData) intent.getExtras().get(Constants.BUNDLE_USER_DATA);
                final ResultReceiver resultReceiver = (ResultReceiver) intent.getExtras().get(Constants.Service.RESULT_RECEIVER_KEY);
                if(resultReceiver != null && userData != null) {
                    if (intent.getAction() != null &&
                            intent.getAction().equals(Constants.BroadcastEvents.USER_DATA_PICKED)) {
                        mTextViewData.setText(String.format("[%s] %s", String.valueOf(userData.getWeight()), userData.getData()));
                        final int queueSize = intent.getExtras().getInt(Constants.BUNDLE_QUEUE_SIZE, 0);
                        mTextQueueSize.setText(String.valueOf(queueSize));
                        resultReceiver.send(Constants.Service.ResultReceiverCode.SUCCESS, null);
                    } else {
                        try {
                            new SimulateUnreliableNetworkManager().unreliableNetwork();
                        } catch (Exception e) {
                            final Bundle bundleResult = new Bundle();
                            bundleResult.putParcelable(Constants.BUNDLE_USER_DATA, userData);
                            resultReceiver.send(Constants.Service.ResultReceiverCode.FAILURE, bundleResult);
                            Toast.makeText(PointwiseActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
    }
}
