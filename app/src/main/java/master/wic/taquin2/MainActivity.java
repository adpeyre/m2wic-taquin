package master.wic.taquin2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PICTURE = 0;

    Uri selectedPicture=null;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView)findViewById(R.id.image);


        final Button launchButton = (Button) findViewById(R.id.launch);
        launchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchGame();


            }
        });




        // Récupération du chemin du fichier que l'on veut charger comme image
        Uri pUri = this.getIntent().getData();

        // Si null : rentrée dans l'app en mode "normal"
        if(pUri == null){
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            this.startActivityForResult(i, PICK_PICTURE);
        }
        // Autrement, on a demandé à ouvrir une image avec l'app Taquin
        else{
            loadPicture(pUri);
        }





    }

    public void launchGame(){
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra("selectedPicture",selectedPicture.toString());
        Toast.makeText(MainActivity.this, "Demande activité de jeu", Toast.LENGTH_LONG).show();
        startActivity(intent);

    }




    // Méthode appelée quand on revient dans l'application qui était restée ouverte ou qu'on change l'orientation de l'écran
    protected void onResume(){
        super.onResume();
        Uri pUri = this.getIntent().getData();

    }

    // Une fois qu'on a le chemin vers l'image, on peut la charger en mémoire dans l'app
    private void loadPicture(Uri pUri) {

        LoadPicture lp = new LoadPicture(this,pUri);
        Bitmap img = lp.getPicture();
        selectedPicture = pUri;
        ImageView iv = (ImageView)findViewById(R.id.image);

        TextView tv = (TextView)findViewById(R.id.textView);
        tv.setText("Image chargée");

        iv.setImageBitmap(img);
        Toast.makeText(this, img.toString(), Toast.LENGTH_LONG).show();


    }

    // Dans le cas où on soit rentrée dans l'appli en mode "normal", Callback après que l'utilisateur a choisi une image
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri pUri = data.getData();
        if(pUri != null){
            loadPicture(pUri);
        }
    }
}
