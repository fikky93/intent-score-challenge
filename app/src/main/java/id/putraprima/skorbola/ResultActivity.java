package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView messageText, scorerText;
    private String message, scorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        messageText =findViewById(R.id.textView3);
        scorerText = findViewById(R.id.scorertext);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            message = bundle.getString("MESSAGE_KEY");
            scorer = bundle.getString("SCORER_KEY");
            messageText.setText(message);
            scorerText.setText(scorer);
        }
    }

}
