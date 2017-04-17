package pl.mrucznik.gwint;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NfcAdapter.OnNdefPushCompleteCallback, NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NFC - pobieramy adapter NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter != null && nfcAdapter.isEnabled()) //sprawdzamy, czy adapter istnieje i czy jest włączony
        {
            Toast.makeText(this, "NFC available :)", Toast.LENGTH_SHORT).show();

            //This will refer back to createNdefMessage for what it will send
            nfcAdapter.setNdefPushMessageCallback(this, this);

            //This will be called if the message is sent successfully
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        else
        {
            Toast.makeText(this, "NFC not available :(", Toast.LENGTH_SHORT).show();
        }

        //inicjalizacja przycisków
        final Button writeButton = (Button) findViewById(R.id.writeButton);
        writeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView txt = (TextView) findViewById(R.id.textView2);
                Integer value = Integer.parseInt(txt.getText().toString()) + 1;
                txt.setText(String.format("%d", value));
            }
        });

        final Button readButton = (Button) findViewById(R.id.readButton);
        readButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView txt = (TextView) findViewById(R.id.textView3);
                Integer value = Integer.parseInt(txt.getText().toString()) + 1;
                txt.setText(String.format("%d", value));
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Toast.makeText(this, "Odebrano zamiar połączenia NFC", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //This will be called when another NFC capable device is detected.
        NdefRecord[] recordsToAttach = createRecords();
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        //This is called when the system detects that our NdefMessage was
        //Successfully sent.
        Toast.makeText(this, "Pomyślnie zapisano", Toast.LENGTH_SHORT).show();
    }

    private void enableTagWriterMode()
    {

    }

    public NdefRecord[] createRecords() {

        NdefRecord[] records = new NdefRecord[1];

        TextView txt = (TextView) findViewById(R.id.textView2);
        byte[] payload = new byte[1];
        payload[0] = (byte) Integer.parseInt(txt.getText().toString());

        NdefRecord record = new NdefRecord(
                NdefRecord.TNF_WELL_KNOWN,  //Our 3-bit Type name format
                NdefRecord.RTD_TEXT,        //Description of our payload
                new byte[0],                //The optional id for our Record
                payload);                   //Our payload for the Record

        records[0] = record;
        return records;
    }
}
