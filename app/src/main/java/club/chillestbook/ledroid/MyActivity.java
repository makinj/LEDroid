package club.chillestbook.ledroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Arrays;
import java.util.List;


public class MyActivity extends Activity {
    private Transmitter transmitter;
    private GridView ledbuttongridview;

    private enum LEDButton {
        UP("UP", R.color.white, R.color.black, Transmitter.Command.UP),
        DOWN("DOWN", R.color.white, R.color.black, Transmitter.Command.DOWN),
        ON("ON", R.color.dark_gray, R.color.black, Transmitter.Command.ON),
        OFF("OFF", R.color.red, R.color.white, Transmitter.Command.OFF),
        RED("RED", R.color.red, R.color.black, Transmitter.Command.RED),
        GREEN("GREEN", R.color.green, R.color.black, Transmitter.Command.GREEN),
        BLUE("BLUE", R.color.blue, R.color.white, Transmitter.Command.BLUE),
        WHITE("WHITE", R.color.white, R.color.black, Transmitter.Command.WHITE),
        ORANGE("ORANGE", R.color.orange, R.color.black, Transmitter.Command.ORANGE),
        YELLOW("YELLOW", R.color.yellow, R.color.black, Transmitter.Command.YELLOW),
        CYAN("CYAN", R.color.cyan, R.color.black, Transmitter.Command.CYAN),
        PURPLE("PURPLE", R.color.purple, R.color.white, Transmitter.Command.PURPLE),
        J3("JUMP 3", R.color.dark_gray, R.color.black, Transmitter.Command.J3),
        J7("JUMP 7", R.color.dark_gray, R.color.black, Transmitter.Command.J7),
        F3("FADE 3", R.color.dark_gray, R.color.black, Transmitter.Command.F3),
        F7("FADE 7", R.color.dark_gray, R.color.black, Transmitter.Command.F7),
        M1("MUSIC 1", R.color.dark_gray, R.color.black, Transmitter.Command.M1),
        M2("MUSIC 2", R.color.dark_gray, R.color.black, Transmitter.Command.M2),
        M3("MUSIC 3", R.color.dark_gray, R.color.black, Transmitter.Command.M3),
        M4("MUSIC 4", R.color.dark_gray, R.color.black, Transmitter.Command.M4);

        String text;
        int background;
        int foreground;
        Transmitter.Command command;

        LEDButton(String text, int background, int foreground, Transmitter.Command command) {
            this.text = text;
            this.background = background;
            this.foreground = foreground;
            this.command = command;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ledbuttongridview = (GridView) findViewById(R.id.ledbuttons);
        ledbuttongridview.setAdapter(new LEDButtonAdapter(this));

        transmitter = new Transmitter(getApplicationContext());
        if (transmitter.compatible()) {
            ledbuttongridview.setVisibility(View.VISIBLE);
        }

    }

    public class LEDButtonAdapter extends BaseAdapter {

        private List<LEDButton> LEDButtonList;

        public LEDButtonAdapter(Context context) {
            LEDButtonList = Arrays.asList(LEDButton.values());
        }

        public int getCount() {
            return LEDButtonList.size();
        }

        public Object getItem(int index) {
            return LEDButtonList.get(index);
        }

        public long getItemId(int index) {
            return index;
        }

        private ShapeDrawable makeCircle(int color) {
            ShapeDrawable shape = new ShapeDrawable(new OvalShape());
            shape.getPaint().setColor(color);
            shape.setIntrinsicHeight(100);
            shape.setIntrinsicWidth(100);
            return shape;
        }

        private LayerDrawable getButtonDrawable(LEDButton led_button) {
            Drawable[] drawables = new Drawable[3];

            drawables[0] = makeCircle(getResources().getColor(led_button.background));
            drawables[1] = makeCircle(getResources().getColor(led_button.foreground));
            drawables[2] = makeCircle(getResources().getColor(led_button.background));

            LayerDrawable layers = new LayerDrawable(drawables);
            layers.setLayerInset(1, 10, 10, 10, 10);
            layers.setLayerInset(2, 15, 15, 15, 15);
            return layers;
        }

        // create a new ImageView for each item referenced by the Adapter
        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = getLayoutInflater().inflate(R.layout.led_button, null);
                viewHolder.button = (Button) view.findViewById(R.id.button);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final LEDButton led_button = LEDButtonList.get(index);

            viewHolder.button.setText(led_button.text);

            Drawable roundButton = getButtonDrawable(led_button);
            viewHolder.button.setBackground(roundButton);
            viewHolder.button.setTextColor(getResources().getColor(led_button.foreground));
            viewHolder.button.setOnClickListener(new LEDButtonListener(led_button.command));

            return view;
        }

        class LEDButtonListener implements View.OnClickListener {
            Transmitter.Command command;

            LEDButtonListener(Transmitter.Command command) {
                this.command = command;
            }

            @Override
            public void onClick(View view) {
                transmitter.transmit(this.command);
            }
        }
    }

    static class ViewHolder {
        Button button;
    }

}
