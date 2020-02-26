package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    public static final String RESULT_KEY = "result";
    public static final String SCORER_KEY = "scorer";

    private TextView resultText, scorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.textView3);
        scorer = findViewById(R.id.scorerOutput);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resultText.setText(bundle.getString(RESULT_KEY));
            scorer.setText(bundle.getString(SCORER_KEY));
        }
    }
}
