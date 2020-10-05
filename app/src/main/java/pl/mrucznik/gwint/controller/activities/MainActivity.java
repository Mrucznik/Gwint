package pl.mrucznik.gwint.controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pl.mrucznik.gwint.R;

public class MainActivity extends Activity {

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
        final Button writeButton = findViewById(R.id.writeButton);
        writeButton.setOnClickListener(view -> startActivityForResult(new Intent( view.getContext(), WriteActivity.class), 0));

        final Button readButton = findViewById(R.id.readButton);
        readButton.setOnClickListener(view -> startActivityForResult(new Intent( view.getContext(), ReadActivity.class), 0));
    }
}
