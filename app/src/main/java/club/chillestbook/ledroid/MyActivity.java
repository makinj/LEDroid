package club.chillestbook.ledroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MyActivity extends Activity {
    private int command=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        updateCommand();
    }

    public void nextCommand(View view) {
        command+=15;
        if(command>255){
            command=0;
        }
        updateCommand();
    }

    public void prevCommand(View view) {
        command-=15;
        if(command<0){
            command=255;
        }
        updateCommand();
    }

    public void updateCommand() {
        ((TextView)findViewById(R.id.command)).setText(Integer.toString(command));
    }

    public void execCommand(View view){
        Transmitter transmitter = new Transmitter(getApplicationContext());
        transmitter.transmit(command);
    }


}
