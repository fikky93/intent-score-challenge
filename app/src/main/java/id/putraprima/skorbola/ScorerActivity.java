package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScorerActivity extends AppCompatActivity {

    public static final String SCORER_KEY = "scorer";
    public static final String EXTRA_REPLY = "com.example.android.twoactivities.extra.REPLY";

    private EditText scorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        scorer = findViewById(R.id.pencetakGol);
    }

    public void match(View view) {
        String scorerr = scorer.getText().toString();
        Intent i = new Intent();
        i.putExtra(SCORER_KEY, scorerr);
        setResult(RESULT_OK,i);
        finish();
    }
}
