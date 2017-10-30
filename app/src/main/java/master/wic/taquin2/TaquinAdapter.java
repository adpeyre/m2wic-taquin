package master.wic.taquin2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by adrien on 27/10/2017.
 */

public class TaquinAdapter extends BaseAdapter {

    private Context context;


    // Original order of chunks
    private Bitmap[] chunks_img;
    private int[] chunks_placed;

    public TaquinAdapter(Context context, Bitmap picture, int dimGrid, int gridWidth){
        this.context = context;
        this.chunks_img = new Bitmap[gridWidth*gridWidth];
        this.chunks_placed = new int[gridWidth * gridWidth];

        //Log.d("perso","grille largeur : "+this.grid.getLayoutParams().width);

        //GridView grid = this.context.getGridView();

        // Case vide
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.chunk_empty);
        //Bitmap largeIcon = BitmapFactory.decodeResource(ContextCompat.getDrawable( context,R.drawable.chunk_empty ));
        Bitmap chunkEmpty = Bitmap.createScaledBitmap(largeIcon, dimGrid/gridWidth, dimGrid/gridWidth, true);


        int chunks_w =  picture.getWidth()/gridWidth;

        this.chunks_img[0] = chunkEmpty;




        for(int line=0; line<gridWidth; line++){
            for(int col=0; col<gridWidth; col++) {
                if(line == 0 && col == 0)
                    continue;
                //this.chunks[i] = Bitmap.createBitmap(..); // Find solution
                this.chunks_img[line*gridWidth+col] = Bitmap.createBitmap(picture, chunks_w * col , chunks_w * line, chunks_w, chunks_w);
                this.chunks_placed[line*gridWidth+col] = line*gridWidth+col;


            }
        }



    }

    public void shuffle(){


        // Avec une boucle : switch plusieurs fois (nombre pair de permutation)
    }

    public void switchChunk(int pos1, int pos2){
        int tmp = this.chunks_placed[pos2];
        this.chunks_placed[pos2] = this.chunks_placed[pos1];
        this.chunks_placed[pos1] = tmp;
        this.notifyDataSetChanged();
    }

    public boolean orderIsOk(){
        for(int i=0; i< this.chunks_placed.length; i++){
            if(this.chunks_placed[i] != i)
                return false;
        }

        return true;
    }



    @Override
    public int getCount(){
       return chunks_img.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return this.chunks_img[  this.chunks_placed[position]  ];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(this.context);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //Toast.makeText(this.context, getCount()+"-GetView:"+position, Toast.LENGTH_LONG).show();

            imageView.setImageBitmap(this.chunks_img[  this.chunks_placed[position]  ]);


            //imageView.setImageBitmap(null);

        return imageView;


    }

}
