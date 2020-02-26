package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String HOME_TEXT_KEY = "homeText";
    public static final String AWAY_TEXT_KEY = "awayText";
    public static final String URI_HOME_KEY = "uriHome";
    public static final String URI_AWAY_KEY = "uriAway";

    private ImageView imageHome, imageAway;
    private EditText home, away;
    private boolean isHome = true;
    private Uri uriHome, uriAway;
    private Bitmap homeBitmap, awayBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        home = findViewById(R.id.home_team);
        //2. Validasi Input Away Team
        away = findViewById(R.id.away_team);
        //3. Ganti Logo Home Team
        imageHome = findViewById(R.id.home_logo);
        //4. Ganti Logo Away Team
        imageAway = findViewById(R.id.away_logo);
        //5. Next Button Pindah Ke MatchActivity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (isHome){
                if (data != null){
                    try {
                        uriHome = data.getData();
                        homeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriHome);
                        imageHome.setImageBitmap(homeBitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }else{
                if (data != null){
                    try {
                        uriAway = data.getData();
                        awayBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriAway);
                        imageAway.setImageBitmap(awayBitmap);
                    }catch (IOException e){
                        Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
    }

    private void selectImage(Context context) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , GALLERY_REQUEST_CODE);
    }

    public void handleHomeLogo(View view) {
        isHome = true;
        selectImage(this);
    }

    public void handleAwayLogo(View view){
        isHome = false;
        selectImage(this);
    }

    public void handle(View view) {
        String home_text = home.getText().toString();
        String away_text = away.getText().toString();

        boolean validate = home_text.isEmpty()&&away_text.isEmpty();

        if(home_text.isEmpty()){
            Toast.makeText(this, "Entry home team name!", Toast.LENGTH_SHORT).show();
        }
        else if(away_text.isEmpty()){
            Toast.makeText(this, "Entry away Team name!", Toast.LENGTH_SHORT).show();
        }
        else if(homeBitmap==null){
            Toast.makeText(this, "Attach Home Team logo!", Toast.LENGTH_SHORT).show();
        }
        else if(awayBitmap==null){
            Toast.makeText(this, "Attach Away Team logo!", Toast.LENGTH_SHORT).show();
        }
        else if(validate){
            Toast.makeText(this, "Fill all Team name!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(this, MatchActivity.class);

            i.putExtra(HOME_TEXT_KEY, home_text);
            i.putExtra(AWAY_TEXT_KEY, away_text);
            i.putExtra(URI_HOME_KEY, uriHome.toString());
            i.putExtra(URI_AWAY_KEY, uriAway.toString());
            startActivity(i);
        }
    }
}
