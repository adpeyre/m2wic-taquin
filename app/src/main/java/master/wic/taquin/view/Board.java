package master.wic.taquin.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import static android.widget.GridLayout.ALIGN_BOUNDS;

/**
 * Created by adrien on 26/10/2017.
 */

public class Board   {

    private Context context;
    public GridLayout grid;
    private TextView[] tvs ;

    final int size = 3;

    public Board(Context c, GridLayout pGrid){
        this.grid = pGrid;
        this.context = c;


        grid.setColumnCount(size+1);
        grid.setRowCount(size+1);




        int numOfCol = size;
        int numOfRow = size;

        tvs =  new TextView[size*size];


        //Log.d("myTag", "This is my message"+pWidth);
        this.grid.removeAllViews();
        for(int yPos=0; yPos<numOfRow; yPos++) {
            for (int xPos = 0; xPos < numOfCol; xPos++) {
                TextView tv = new TextView(this.context);
                //tv.setGravity(Gravity.CENTER);
                tv.setText("POS:"+yPos+","+xPos);
                tv.setGravity(Gravity.FILL);
                tv.setBackgroundColor(Color.BLUE);

                tvs[yPos*numOfCol + xPos] = tv;
                this.grid.addView(tv);


            }
        }

        grid.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener(){

                @Override
                public void onGlobalLayout() {

                    final int MARGIN = 5;

                    int pWidth=0;
                    int pHeight=0;
                    if(grid.getWidth() < grid.getHeight()){
                        pWidth = grid.getWidth();
                        pHeight =  pWidth;
                    }
                    else{
                        pHeight = grid.getHeight();
                        pWidth = pHeight;
                    }


                    int numOfCol = size;
                    int numOfRow = size;
                    int w = pWidth/numOfCol;
                    int h = pHeight/numOfRow;

                    for(int yPos=0; yPos<numOfRow; yPos++){
                        for(int xPos=0; xPos<numOfCol; xPos++){
                            GridLayout.LayoutParams params = (GridLayout.LayoutParams)tvs[yPos*numOfCol + xPos].getLayoutParams();
                            params.rowSpec = GridLayout.spec(yPos);
                            params.columnSpec = GridLayout.spec(xPos);
                            params.width = w - 2*MARGIN;
                            params.height = h - 2*MARGIN;
                            params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                            params.setGravity(Gravity.CENTER);

                            tvs[yPos*numOfCol + xPos].setLayoutParams(params);


                            Log.d("myTag", "This is my message"+yPos+"-"+xPos);
                        }
                    }

                }});





    }

    public void test(){



    }
}
