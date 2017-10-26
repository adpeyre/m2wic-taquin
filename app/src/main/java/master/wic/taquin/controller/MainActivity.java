package master.wic.taquin.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;

import master.wic.taquin.R;
import master.wic.taquin.view.Board;

public class MainActivity extends AppCompatActivity {

    Board board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new Board(this,(GridLayout) findViewById(R.id.board));
        board.test();

    }
}
