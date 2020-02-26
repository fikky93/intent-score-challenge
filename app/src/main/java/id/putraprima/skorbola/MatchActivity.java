package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    public static final String HOME_TEXT_KEY = "homeText";
    public static final String AWAY_TEXT_KEY = "awayText";
    public static final String URI_HOME_KEY = "uriHome";
    public static final String URI_AWAY_KEY = "uriAway";
    public static final String SCORE_HOME_KEY = "scoreHome";
    public static final String SCORE_AWAY_KEY = "scoreAway";
    public static final String SCORER_KEY = "scorer";
    public static final int TEXT_REQUEST = 1;

    private TextView home, away, scorer, score;
    private int homeScore,awayScore;
    private ImageView uriHome, uriAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        scorer = findViewById(R.id.scorerrr);
        home = findViewById(R.id.txt_home);
        away = findViewById(R.id.txt_away);
        uriHome = findViewById(R.id.home_logo);
        uriAway = findViewById(R.id.away_logo);
        Bundle extras = getIntent().getExtras();
        Uri homeLogoUri = Uri.parse(extras.getString(URI_HOME_KEY));
        Uri awalLogoUri = Uri.parse(extras.getString(URI_AWAY_KEY));
        if(extras != null){
            home.setText(extras.getString(HOME_TEXT_KEY));
            away.setText(extras.getString(AWAY_TEXT_KEY));
            try{
                Bitmap homeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeLogoUri);
                Bitmap awayBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awalLogoUri);
                uriHome.setImageBitmap(homeBitmap);
                uriAway.setImageBitmap(awayBitmap);
            }
            catch (IOException e){
                Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            }
        }
    }
    //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
    //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
    public void handle(View view) {
        score = findViewById(R.id.score_home);
        homeScore = Integer.valueOf(score.getText().toString());
        homeScore += 1;
        score.setText(String.valueOf(homeScore));
        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, TEXT_REQUEST);
    }

    public void handle2(View view) {
        score = findViewById(R.id.score_away);
        awayScore = Integer.valueOf(score.getText().toString());
        awayScore += 1;
        score.setText(String.valueOf(awayScore));
        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, TEXT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                String reply = data.getStringExtra(ScorerActivity.EXTRA_REPLY);
                scorer.setText(reply);
                scorer.setVisibility(View.VISIBLE);
            }
        }
    }

    //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    public void result(View view) {
        String scorerrrrrr = scorer.getText().toString();
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle extras = getIntent().getExtras();
        if (homeScore>awayScore){
            scorer.setText(extras.getString(SCORER_KEY));
            intent.putExtra("result", "Pemenangnya "+home.getText().toString());
            intent.putExtra(SCORER_KEY, scorerrrrrr);
        }
        else if(awayScore>homeScore){
            scorer.setText(extras.getString(SCORER_KEY));
            intent.putExtra("result", "Pemenangnya "+away.getText().toString());
            intent.putExtra(SCORER_KEY, scorerrrrrr);
        }
        else{
            intent.putExtra("result", "Seri");
        }
        startActivity(intent);
    }
}
