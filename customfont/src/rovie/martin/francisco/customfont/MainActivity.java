package rovie.martin.francisco.customfont;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GhostTheory.ttf");
        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Fantastic4.ttf");
        final Typeface tf2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/EvilGenius.ttf");

        final TextView txt = (TextView) findViewById(R.id.txt);
        txt.setTypeface(tf);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setTypeface(tf1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("Thanks for Watching");
                txt.setTypeface(tf2);
                txt.setTextColor(Color.BLUE);
            }
        });
    }
}