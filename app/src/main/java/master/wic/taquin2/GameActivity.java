package master.wic.taquin2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TaquinAdapter adapter = null;
    GridView gridview;
    LinearLayout playableZone;
    TextView commentary;

    Uri imageUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // On choisi la layout correspondant selon l'orientation du terminal
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getOrientation() == Surface.ROTATION_90) {
            setContentView(R.layout.land_activity_game);
        }
        else{
            setContentView(R.layout.activity_game);
        }

        // Récupération des informations transmises par l'autre activité
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        imageUri= Uri.parse(extras.getString("selectedPicture"));

        // Récupération d'élements de la vue
        playableZone = (LinearLayout) findViewById(R.id.playableZone);
        gridview = (GridView) findViewById(R.id.grid);
        commentary = (TextView) findViewById(R.id.commentary);


        gridview.setNumColumns(3);

        Toast.makeText(GameActivity.this, "Passage sur l'activité de jeu", Toast.LENGTH_LONG).show();





        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ///Toast.makeText(GameActivity.this, "" + position,         Toast.LENGTH_SHORT).show();

                Log.d("Pos chunk empty",""+pos_chunk_empty);
                Log.d("Pos(point) chunk empty",""+posToPoint(pos_chunk_empty));
                if(!move(position)){
                    Toast.makeText(GameActivity.this, "Mouvement impossible(" + position+")-"+getPossibilities(pos_chunk_empty).toString(),
                            Toast.LENGTH_SHORT).show();
                }

                if(checkWin()){
                    showWin();
                }

            }
        });


        // Attendre que la layout soit bien chargé
        gridview.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                       init();
                        gridview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
        );


    }

    protected void init() {
        int gridDim = playableZone.getWidth() < playableZone.getHeight() ? playableZone.getWidth() : playableZone.getHeight();
        Log.d("dimension",""+playableZone.getHeight());
        gridview.getLayoutParams().width = gridDim;
        gridview.getLayoutParams().height = gridDim;

        LoadPicture lp = new LoadPicture(GameActivity.this,imageUri,gridDim,gridDim);
        Bitmap img = lp.getPicture();

        ImageView iv2 = (ImageView)findViewById(R.id.iv2);
        iv2.setImageBitmap(img);


        Log.d("cols",gridview.getNumColumns()+"");
        adapter = new TaquinAdapter(GameActivity.this, img, gridDim,gridview.getNumColumns());
        gridview.setAdapter(adapter);

        this.shuffle();

    }

    int pos_chunk_empty = 0;
    public void shuffle() {




        for(int i=0; i<2; i++) {
            List<Integer> possibilities= getPossibilities(pos_chunk_empty);
            Random r = new Random();
            move(   possibilities.get(r.nextInt(possibilities.size()))   );
        }

    }

    private boolean move(int pos){
        if(!getPossibilities(pos_chunk_empty).contains(pos)){
            return false;
        }

        adapter.switchChunk(pos_chunk_empty,pos);
        pos_chunk_empty = pos;
        return true;
    }

    private boolean checkWin(){
        return adapter.orderIsOk();
    }

    private void showWin(){
        commentary.setText("Gagné !!!!");
    }

    private List<Integer> getPossibilities(int caseId){
        List<Integer> possibilities = new ArrayList<Integer>();


        Point p = posToPoint(caseId);
        possibilities.clear();

        if (p.x > 0)
            possibilities.add(caseId-1);
        if (p.x < gridview.getNumColumns()-1)
            possibilities.add(caseId+1);
        if (p.y > 0)
            possibilities.add(   (p.y-1)*gridview.getNumColumns()+p.x   );
        if (p.y < gridview.getNumColumns()-1)
            possibilities.add(   (p.y+1)*gridview.getNumColumns()+p.x   );

        return possibilities;
    }

    private Point posToPoint(int pos){
        int line = pos/gridview.getNumColumns();
        int col = pos - line*gridview.getNumColumns();

        return new Point(col,line);
    }


}
