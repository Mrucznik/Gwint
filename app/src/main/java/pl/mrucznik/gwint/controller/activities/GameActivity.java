package pl.mrucznik.gwint.controller.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
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

    @Override
    public void startGame()
    {
        playerOne = new Player("Gracz dolny");
        playerTwo = new Player("Gracz górny");
        game = new Game(this, playerOne, playerTwo);
    }

    @Override
    public void showRowMenu(Consumer<AttackRow> callback)
    {
        ArrayList<LinearLayout> closeCombatLayouts = new ArrayList<>();
        ArrayList<LinearLayout> longRangeLayouts = new ArrayList<>();
        ArrayList<LinearLayout> siegeLayouts = new ArrayList<>();
        closeCombatLayouts.add((LinearLayout)findViewById(R.id.upPlayerTowerContainer));
        longRangeLayouts.add((LinearLayout)findViewById(R.id.upPlayerBowContainer));
        siegeLayouts.add((LinearLayout)findViewById(R.id.upPlayerSwordContainer));
        closeCombatLayouts.add((LinearLayout)findViewById(R.id.downPlayerTowerContainer));
        longRangeLayouts.add((LinearLayout)findViewById(R.id.downPlayerBowContainer));
        siegeLayouts.add((LinearLayout)findViewById(R.id.downPlayerSwordContainer));

        for (LinearLayout layout : closeCombatLayouts) {
            layout.setOnClickListener(v -> {
                callback.accept(AttackRow.CloseCombat);
                v.setOnClickListener(null);
            });
        }

        for (LinearLayout layout : longRangeLayouts) {
            layout.setOnClickListener(v -> {
                callback.accept(AttackRow.LongRange);
                v.setOnClickListener(null);
            });
        }

        for (LinearLayout layout : siegeLayouts) {
            layout.setOnClickListener(v -> {
                callback.accept(AttackRow.Siege);
                v.setOnClickListener(null);
            });
        }
    }

    @Override
    public void updatePoints(Map<Player, Map<AttackRow, Integer>> points)
    {
        downPlayerSwordPoint.setText(points.get(playerOne).get(AttackRow.CloseCombat).toString());
        downPlayerBowPoint.setText(points.get(playerOne).get(AttackRow.LongRange).toString());
        downPlayerTowerPoint.setText(points.get(playerOne).get(AttackRow.Siege).toString());
        upPlayerSwordPoint.setText(points.get(playerTwo).get(AttackRow.CloseCombat).toString());
        upPlayerBowPoint.setText(points.get(playerTwo).get(AttackRow.LongRange).toString());
        upPlayerTowerPoint.setText(points.get(playerTwo).get(AttackRow.Siege).toString());
    }

    @Override
    public void sendMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
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
            coinImg.setImageResource(R.drawable.coin_left_red);
        }
    }

    @Override
    public void onNextRound() {

        if(playerOne.getWins() == 1)
        {
            upPlayerHeart1.setImageResource(R.drawable.heart_off);
        }
        if(playerTwo.getWins() == 1)
        {
            downPlayerHeart1.setImageResource(R.drawable.heart_off);
        }

        Log.d("INFO", "Tost: " + playerOne.getWins() + " " + playerTwo.getWins());

        downPlayerShadowPass.setVisibility(View.GONE);
        upPlayerShadowPass.setVisibility(View.GONE);
    }

    @Override
    public void onGameEnds() {

        centerShadow.setVisibility(View.VISIBLE);
        if(playerTwo.getWins() == 2 && playerOne.getWins() == 2 )
        {
            upPlayerHeart2.setImageResource(R.drawable.heart_off);
            downPlayerHeart2.setImageResource(R.drawable.heart_off);
            winnerTextView.setText("Remis");
        }
        else if(playerTwo.getWins() == 2)
        {
            downPlayerHeart2.setImageResource(R.drawable.heart_off);
            winnerTextView.setText(playerTwo.toString());
        }
        else if(playerOne.getWins() == 2 )
        {
            upPlayerHeart2.setImageResource(R.drawable.heart_off);
            winnerTextView.setText(playerOne.toString());
        }


        final CounterClass timer = new CounterClass(this, 5000, 1000);
        timer.start();

    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------< NFC >-------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------
    //region NFC
    public static final String MIME_TYPE = "application/vnd.mrucznik";

    private TextView info;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        /*
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        /*
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /*
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // TODO: handle Intent

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {

            NdefMessage[] messages = getNdefMessages(intent);
            NdefRecord record = messages[0].getRecords()[0];
            GwentCard card = new GwentCard(new String(record.getPayload()));
            Toast.makeText(this, "Tost: " + card.toString(), Toast.LENGTH_SHORT).show();
            game.processCard(card);
        }
    }

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TYPE);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link ReadActivity} requesting to stop the foreground dispatch.
     * @param adapter The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    NdefMessage[] getNdefMessages(Intent intent) {

        // Parse the intent
        NdefMessage[] msgs = null;
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {
                        record
                });
                msgs = new NdefMessage[] {
                        msg
                };
            }
        }else {
            Log.d("NFC Transportation", "Unknown intent.");
            finish();
        }

        return msgs;
    }
    //endregion

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

    FrameLayout downPlayerShadowPass;
    FrameLayout upPlayerShadowPass;
    FrameLayout centerShadow;


    ImageView downPlayerHeart1;
    ImageView downPlayerHeart2;
    ImageView upPlayerHeart1;
    ImageView upPlayerHeart2;
    ImageView coinImg;

    TextView winnerTextView;
    TextView countDown;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        //*******************************************************************************************************

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

        downPlayerShadowPass = (FrameLayout)findViewById(R.id.downPlayerShadowPass);
        upPlayerShadowPass = (FrameLayout)findViewById(R.id.upPlayerShadowPass);
        centerShadow = (FrameLayout)findViewById(R.id.centerShadow);

        downPlayerHeart1 = (ImageView)findViewById(R.id.downPlayer1Heart);
        downPlayerHeart2 = (ImageView)findViewById(R.id.downPlayer2Heart);
        upPlayerHeart1 = (ImageView)findViewById(R.id.upPlayer1Heart);
        upPlayerHeart2 = (ImageView)findViewById(R.id.upPlayer2Heart);

        winnerTextView = (TextView)findViewById(R.id.winnerTextView);
        countDown = (TextView)findViewById(R.id.countDown);

        refreshCount(downPlayerSwordPoint, downPlayerSwordClone);
        refreshCount(downPlayerBowPoint, downPlayerBowClone);
        refreshCount(downPlayerTowerPoint, downPlayerTowerClone);

        refreshCount(upPlayerSwordPoint, upPlayerSwordClone);
        refreshCount(upPlayerBowPoint, upPlayerBowClone);
        refreshCount(upPlayerTowerPoint, upPlayerTowerClone);


        coinImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                onPass();

                return true;
            }
        });

        startGame();

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //handleIntent(getIntent());
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

int i = 0; // chwilowo
    //symulacja gry na monetce
    public void coinHoldPass(View v)
    {
        //game.processCard(new GwentCard(0, "Letho z Gulety", 10, AttackRow.CloseCombat, true, CardBehaviour.None));
        //game.processCard(new GwentCard(1, "Sheldon Skaggs", 4, AttackRow.LongRange, false, CardBehaviour.None));

      // game.processCard(new GwentCard(i++, "Wsparcie Łuczników", 1, AttackRow.LongRange, false, CardBehaviour.Mgla));
    }

    public void onPass()
    {
        Toast.makeText(getApplicationContext(), game.getActivePlayer().toString() + " Spasowal!",Toast.LENGTH_SHORT).show();

        if(game.getActivePlayer() == playerOne)
        {
            downPlayerShadowPass.setVisibility(View.VISIBLE);
        }
        else
        {
            upPlayerShadowPass.setVisibility(View.VISIBLE);
        }

        game.processCard(new GwentCard(i++, "Pass", 0, AttackRow.None, false, CardBehaviour.Pass));



    }




    /*****************************************************************************************************************************
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
