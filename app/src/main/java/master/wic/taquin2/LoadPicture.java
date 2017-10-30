package master.wic.taquin2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by adrien on 29/10/2017.
 */

public class LoadPicture {
    private Context context;

    Bitmap picture;

    public LoadPicture(Context context,Uri pUri){
        this.context = context;
        picture = this.loadPicture(pUri);
    }

    public LoadPicture(Context context, Uri pUri, int layout_width, int layout_height){
        this.context = context;
        picture = this.loadPicture(pUri);

        int widthSmallerSide = (layout_width < layout_height) ? layout_width : layout_height;
        picture = this.scalePicture(picture, widthSmallerSide);
    }

    public Bitmap getPicture(){
        return this.picture;
    }


    private Bitmap loadPicture(Uri pUri){
        try {
            InputStream is = this.context.getContentResolver().openInputStream(pUri);
            Bitmap img = BitmapFactory.decodeStream(is);
            return img;

        }
        catch(Exception e){
            // Possible erreur de droit pour l'ouverture
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap scalePicture(Bitmap picture, int dim){

        int originalWidth, originalHeight;
        originalWidth = picture.getWidth();
        originalHeight = picture.getHeight();



        int newWidth, newHeight;


        // On redimensionne à la taille de la gridview
        // Pour faire un carré, il faut rogner le côté le + grand
        if(originalWidth > originalHeight){
            newWidth = originalWidth*dim/originalHeight;
            newHeight = dim;
        }
        else{
            newWidth = dim;
            newHeight = originalHeight*dim/originalWidth;
        }

        Bitmap resized = Bitmap.createScaledBitmap(picture, newWidth, newHeight, true);
        Bitmap scaled = Bitmap.createBitmap(resized, 0, 0, dim, dim);

        return scaled;

    }
}
