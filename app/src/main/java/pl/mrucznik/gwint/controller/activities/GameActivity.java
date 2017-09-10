package pl.mrucznik.gwint.controller.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import pl.mrucznik.gwint.R;
import pl.mrucznik.gwint.model.Game;
import pl.mrucznik.gwint.model.Player;
import pl.mrucznik.gwint.model.cards.AttackRow;
import pl.mrucznik.gwint.model.cards.CardBehaviour;
import pl.mrucznik.gwint.model.cards.GwentCard;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class GameActivity extends AppCompatActivity implements IGameController {
    /*Game Controller*/
    Game game;
    Player playerOne, playerTwo;

    public void startGame()
    {
        playerOne = new Player("Gracz dolny");
        playerTwo = new Player("Gracz górny");
        game = new Game(this, playerOne, playerTwo);
        game.start();
    }

    public void showRowMenu(Consumer<AttackRow> callback)
    {

        callback.accept(AttackRow.CloseCombat);
    }
    public void sendButtonMessage(String message, String buttonMeesage)
    {
        //nie robić
    }

    public void updatePoints(Map<Player, Map<AttackRow, Integer>> points)
    {
        downPlayerSwordPoint.setText(points.get(playerOne).get(AttackRow.CloseCombat).toString());
        downPlayerBowPoint.setText(points.get(playerOne).get(AttackRow.LongRange).toString());
        downPlayerTowerPoint.setText(points.get(playerOne).get(AttackRow.Siege).toString());
        upPlayerSwordPoint.setText(points.get(playerTwo).get(AttackRow.CloseCombat).toString());
        upPlayerBowPoint.setText(points.get(playerTwo).get(AttackRow.LongRange).toString());
        upPlayerTowerPoint.setText(points.get(playerTwo).get(AttackRow.Siege).toString());
    }
    public void chooseCard(Stream<GwentCard> cards, Consumer<GwentCard> callback)
    {
        sendMessage("Wybierz kartę");
        callback.accept(cards.findAny().get()); //wybrana karta
    }


    public void sendMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updatePlayer(Player player)
    {
        if(game.getActivePlayer() == playerOne)
        {
            upPlayerFrameL.setBackgroundColor(getResources().getColor(R.color.invisible));
            downPlayerFrameL.setBackgroundColor(getResources().getColor(R.color.blueFrame));
            coinImg.setImageResource(R.drawable.coin_right_blue);
        }
        else
        {
            upPlayerFrameL.setBackgroundColor(getResources().getColor(R.color.redFrame));
            downPlayerFrameL.setBackgroundColor(getResources().getColor(R.color.invisible));
            coinImg.setImageResource(R.drawable.coin_right_red);
        }
    }

    @Override
    public void onNextRound() {

    }

    @Override
    public void onGameEnds() {

    }


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;


    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    TextView downPlayerCountFD;
    TextView downPlayerCountFU;
    TextView upPlayerCountFD;
    TextView upPlayerCountFU;

    private TextView downPlayerSwordPoint; //macierzyste
    private TextView downPlayerBowPoint;
    private TextView downPlayerTowerPoint;

    private TextView upPlayerSwordPoint;
    private TextView upPlayerBowPoint;
    private TextView upPlayerTowerPoint;

    TextView downPlayerSwordClone;
    TextView downPlayerBowClone;
    TextView downPlayerTowerClone;

    TextView upPlayerSwordClone;
    TextView upPlayerBowClone;
    TextView upPlayerTowerClone;

    FrameLayout downPlayerFrameL;
    FrameLayout upPlayerFrameL;

    ImageView coinImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        coinImg = (ImageView)findViewById(R.id.coin);

        downPlayerFrameL = (FrameLayout)findViewById(R.id.downPlayerFrame);
        upPlayerFrameL = (FrameLayout)findViewById(R.id.upPlayerFrame);

        downPlayerCountFD = (TextView)findViewById(R.id.downPlayerCenterCountForDown);
        downPlayerCountFU = (TextView)findViewById(R.id.downPlayerCenterCountForUp);
        upPlayerCountFU = (TextView)findViewById(R.id.upPlayerCenterCountForUp);
        upPlayerCountFD = (TextView)findViewById(R.id.upPlayerCenterCountForDown);


        downPlayerSwordPoint = (TextView)findViewById(R.id.downPlayerSwordScoreForDown);
        downPlayerBowPoint = (TextView)findViewById(R.id.downPlayerBowScoreForDown);
        downPlayerTowerPoint = (TextView)findViewById(R.id.downPlayerTowerScoreForDown);


        upPlayerSwordPoint = (TextView)findViewById(R.id.upPlayerSwordScoreForUp);
        upPlayerBowPoint = (TextView)findViewById(R.id.upPlayerBowScoreForUp);
        upPlayerTowerPoint = (TextView)findViewById(R.id.upPlayerTowerScoreForUp);

        downPlayerSwordClone = (TextView)findViewById(R.id.downPlayerSwordScoreForUp);
        downPlayerBowClone = (TextView)findViewById(R.id.downPlayerBowScoreForUp);
        downPlayerTowerClone = (TextView)findViewById(R.id.downPlayerTowerScoreForUp);


        upPlayerSwordClone = (TextView)findViewById(R.id.upPlayerSwordScoreForDown);
        upPlayerBowClone = (TextView)findViewById(R.id.upPlayerBowScoreForDown);
        upPlayerTowerClone = (TextView)findViewById(R.id.upPlayerTowerScoreForDown);


        refreshCount(downPlayerSwordPoint, downPlayerSwordClone);
        refreshCount(downPlayerBowPoint, downPlayerBowClone);
        refreshCount(downPlayerTowerPoint, downPlayerTowerClone);

        refreshCount(upPlayerSwordPoint, upPlayerSwordClone);
        refreshCount(upPlayerBowPoint, upPlayerBowClone);
        refreshCount(upPlayerTowerPoint, upPlayerTowerClone);


        startGame();
    }

    public void refreshCount(TextView textView, TextView textClone)
    {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int CountPlayerDown = Integer.parseInt(downPlayerSwordPoint.getText().toString()) + Integer.parseInt(downPlayerBowPoint.getText().toString()) + Integer.parseInt(downPlayerTowerPoint.getText().toString());
                int CountPlayerUp = Integer.parseInt(upPlayerSwordPoint.getText().toString()) + Integer.parseInt(upPlayerBowPoint.getText().toString()) + Integer.parseInt(upPlayerTowerPoint.getText().toString());
                downPlayerCountFD.setText(""+CountPlayerDown);
                downPlayerCountFU.setText(""+CountPlayerDown);
                upPlayerCountFD.setText(""+CountPlayerUp);
                upPlayerCountFU.setText(""+CountPlayerUp);
            }

            @Override
            public void afterTextChanged(Editable s) {

                textClone.setText(""+textView.getText().toString());
            }
        });
    }
int i = 0;
    //symulacja gry na monetce
    public void imageView(View v)
    {
        Random rand = new Random();
        int n = rand.nextInt(40);
        /*downPlayerBowPoint.setText(""+n); // randomuje wartosci
        upPlayerTowerPoint.setText(""+n);*/



        //game.processCard(new GwentCard(0, "Letho z Gulety", 10, AttackRow.CloseCombat, true, CardBehaviour.None));
        //game.processCard(new GwentCard(1, "Sheldon Skaggs", 4, AttackRow.LongRange, false, CardBehaviour.None));

        game.processCard(new GwentCard(i++, "Wsparcie Łuczników", 1, AttackRow.LongRange, false, CardBehaviour.Mgla));



        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getApplicationContext(), game.getActivePlayer().toString() + " Spasowal!",Toast.LENGTH_SHORT).show();
                game.processCard(new GwentCard(i++, "Pass", 0, AttackRow.None, false, CardBehaviour.Pass));
                Toast.makeText(getApplicationContext(), "Idzie teraz " + game.getActivePlayer().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {



        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
