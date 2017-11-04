package com.gadgetcreek.barcode;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button bt;
    String contents="", format="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.contents);
        bt = (Button)findViewById(R.id.search);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent in) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, in);

        if (scanningResult != null) {
            try {
                contents = in.getStringExtra("SCAN_RESULT");
                format = in.getStringExtra("SCAN_RESULT_FORMAT");
                tv.setText("Content-" + contents + "\n" + " Format-" + format);
            }
            catch (NullPointerException e){}
        }
    }

    public void search(View v){
        if(contents.equals("")){
            Toast.makeText(getApplication(),"Click Camera icon to Scan BarCode",Toast.LENGTH_LONG).show();
        }
        else{
            //search scan result on web
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(contents)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                IntentIntegrator scanIntegrator = new IntentIntegrator(MainActivity.this);
                scanIntegrator.initiateScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

