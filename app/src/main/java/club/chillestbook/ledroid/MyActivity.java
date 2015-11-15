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


    public enum LEDButton {
        UP("", R.drawable.plus, R.color.white, R.color.black, Transmitter.Command.UP),
        DOWN("", R.drawable.minus, R.color.white, R.color.black, Transmitter.Command.DOWN),
        ON("ON", -1, R.color.dark_gray, R.color.black, Transmitter.Command.ON),
        OFF("OFF", -1, R.color.red, R.color.white, Transmitter.Command.OFF),
        RED("RED", -1, R.color.red, R.color.black, Transmitter.Command.RED),
        GREEN("GREEN", -1, R.color.green, R.color.black, Transmitter.Command.GREEN),
        BLUE("BLUE", -1, R.color.blue, R.color.white, Transmitter.Command.BLUE),
        WHITE("WHITE", -1, R.color.white, R.color.black, Transmitter.Command.WHITE),
        ORANGE("ORANGE", -1, R.color.orange, R.color.black, Transmitter.Command.ORANGE),
        YELLOW("YELLOW", -1, R.color.yellow, R.color.black, Transmitter.Command.YELLOW),
        CYAN("CYAN", -1, R.color.cyan, R.color.black, Transmitter.Command.CYAN),
        PURPLE("PURPLE", -1, R.color.purple, R.color.white, Transmitter.Command.PURPLE),
        J3("JUMP 3", -1, R.color.dark_gray, R.color.black, Transmitter.Command.J3),
        J7("JUMP 7", -1, R.color.dark_gray, R.color.black, Transmitter.Command.J7),
        F3("FADE 3", -1, R.color.dark_gray, R.color.black, Transmitter.Command.F3),
        F7("FADE 7", -1, R.color.dark_gray, R.color.black, Transmitter.Command.F7),
        M1("", R.drawable.m_one, R.color.dark_gray, R.color.black, Transmitter.Command.M1),
        M2("", R.drawable.m_two, R.color.dark_gray, R.color.black, Transmitter.Command.M2),
        M3("", R.drawable.m_three, R.color.dark_gray, R.color.black, Transmitter.Command.M3),
        M4("", R.drawable.m_four, R.color.dark_gray, R.color.black, Transmitter.Command.M4);

        String text;
        int background;
        int foreground;
        int icon;
        Transmitter.Command command;

        LEDButton(String text, int icon, int background, int foreground, Transmitter.Command command) {
            this.text = text;
            this.icon = icon;
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

    private ShapeDrawable makeCircle(int color) {
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.getPaint().setColor(color);
        shape.setIntrinsicHeight(100);
        shape.setIntrinsicWidth(100);
        return shape;
    }

    public LayerDrawable getButtonDrawable(int background, int foreground, int icon) {
        Drawable[] drawables = new Drawable[3];

        drawables[0] = makeCircle(getResources().getColor(background));
        drawables[1] = makeCircle(getResources().getColor(foreground));
        drawables[2] = makeCircle(getResources().getColor(background));

        if (icon != -1) {
            Drawable[] iconDrawable = {getResources().getDrawable(icon)};
            drawables = concat(drawables, iconDrawable);
        }

        LayerDrawable layers = new LayerDrawable(drawables);
        layers.setLayerInset(1, 10, 10, 10, 10);
        layers.setLayerInset(2, 15, 15, 15, 15);
        if (icon != -1) {
            layers.setLayerInset(3, 60, 60, 60, 60);
        }
        return layers;
    }

    private Drawable[] concat(Drawable[] a, Drawable[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Drawable[] C = new Drawable[aLen + bLen];
        System.arraycopy(a, 0, C, 0, aLen);
        System.arraycopy(b, 0, C, aLen, bLen);
        return C;
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

            Drawable roundButton = getButtonDrawable(led_button.background, led_button.foreground, led_button.icon);
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
