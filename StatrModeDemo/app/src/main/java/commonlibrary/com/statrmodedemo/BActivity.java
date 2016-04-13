package commonlibrary.com.statrmodedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * @author: vision
 * @function:
 * @date: 16/3/30
 */
public class BActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        button = (Button) findViewById(R.id.start_button);
        button.setText("跳转到CActivity");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BActivity.this, CActivity.class);
                startActivity(intent);
            }
        });
    }
}
