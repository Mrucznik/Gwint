package pl.mrucznik.gwint.controller.activities;

import android.content.Intent;
import android.os.CountDownTimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sebastian on 11.09.2017.
 */
class CounterClass extends CountDownTimer {

    private GameActivity gameActivity;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    CounterClass(GameActivity gameActivity, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.gameActivity = gameActivity;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        String hms = String.format("%1d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
        System.out.println(hms);
        gameActivity.countDown.setText(hms);
    }

    @Override
    public void onFinish() {

        Intent i = new Intent(gameActivity, MainActivity.class);
        gameActivity.startActivity(i);
        gameActivity.finish();
    }
}
