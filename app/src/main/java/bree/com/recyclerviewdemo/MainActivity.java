package bree.com.recyclerviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        intent = new Intent(MainActivity.this, JustLikeListView.class);
       String type="";

        switch (v.getId()) {
            case R.id.btn1:
                intent.putExtra("layout", "1");
                type="1";
                break;
            case R.id.btn2:
                intent.putExtra("layout", "2");
                type="2";
                break;
            case R.id.btn3:
                intent.putExtra("layout", "3");
                type="3";
                break;
        }
        BaseActivity.type=type;
        startActivity(intent);
    }
}
