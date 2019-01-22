package com.serviceprovider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.serviceprovider.LogsUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button buttonStartService, buttonStopService;
    private Context mContext;

    private Intent serviceIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        buttonStartService = (Button) findViewById(R.id.buttonStartService);
        buttonStopService = (Button) findViewById(R.id.buttonStopService);

        buttonStopService.setOnClickListener(this);
        buttonStartService.setOnClickListener(this);

        serviceIntent = new Intent(getApplicationContext(), MyServiceProvider.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStartService:
                startService(serviceIntent);
                LogsUtils.printLog(TAG, "Service Started");
                break;
            case R.id.buttonStopService:
                stopService(serviceIntent);
                break;
            default:
                break;
        }
    }
}