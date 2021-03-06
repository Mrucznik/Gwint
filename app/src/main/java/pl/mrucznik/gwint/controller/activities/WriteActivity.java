package pl.mrucznik.gwint.controller.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pl.mrucznik.gwint.R;
import pl.mrucznik.gwint.model.cards.GwentCard;
import pl.mrucznik.gwint.model.cards.GwentCards;

public class WriteActivity extends Activity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private GwentCard card;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);




        final SeekBar cardSeekBar = findViewById(R.id.seekBar);
        final TextView cardText = findViewById(R.id.cardText);
        cardSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                card = GwentCards.getCard(i);
                cardText.setText(card.toHumanString());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //inicjalizacja NFC
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0,  new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        mFilters = new IntentFilter[] {
                ndef,
        };
        mTechLists = new String[][] { new String[] { Ndef.class.getName() }, new String[] { NdefFormatable.class.getName() }};
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null) mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                mTechLists);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if(card == null)
            return;

        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String externalType = "application/vnd.mrucznik";
        NdefRecord extRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, externalType.getBytes(), new byte[0], card.toString().getBytes());
        NdefMessage newMessage = new NdefMessage(new NdefRecord[] { extRecord });
        writeNdefMessageToTag(newMessage, tag);

    }

    boolean writeNdefMessageToTag(NdefMessage message, Tag detectedTag) {
        //TODO:
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                    Toast.makeText(this, "Tag is read-only.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    Toast.makeText(this, "The data cannot written to tag, Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + size + " bytes.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                ndef.writeNdefMessage(message);
                ndef.close();
                Toast.makeText(this, "Message is written tag.", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                NdefFormatable ndefFormat = NdefFormatable.get(detectedTag);
                if (ndefFormat != null) {
                    try {
                        ndefFormat.connect();
                        ndefFormat.format(message);
                        ndefFormat.close();
                        Toast.makeText(this, "The data is written to the tag: " + message , Toast.LENGTH_SHORT).show();
                        return true;
                    } catch (IOException e) {
                        Toast.makeText(this, "Failed to format tag", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "NDEF is not supported", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Write opreation is failed", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}