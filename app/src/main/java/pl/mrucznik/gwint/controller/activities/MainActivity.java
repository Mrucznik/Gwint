package pl.mrucznik.gwint.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import pl.mrucznik.gwint.R;
import pl.mrucznik.gwint.model.cards.GwentCards;

public class MainActivity extends AppCompatActivity {

    public void openGameActivity(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //inicjalizacja przycisków
        final Button writeButton = (Button) findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent( view.getContext(), WriteActivity.class), 0);
            }
        });

        final Button readButton = (Button) findViewById(R.id.readButton);
        readButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivityForResult(new Intent( view.getContext(), ReadActivity.class), 0);
            }
        });

        final Button throwCardButton = (Button) findViewById(R.id.throwCardButton);
        throwCardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        final SeekBar cardSeekBar = (SeekBar) findViewById(R.id.cardsSeekBar);
        final TextView cardText = (TextView) findViewById(R.id.cardText);
        cardSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cardText.setText(GwentCards.getCard(i).toHumanString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
