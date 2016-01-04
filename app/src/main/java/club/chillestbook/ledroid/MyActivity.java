package club.chillestbook.ledroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
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
    private LEDButtonAdapter ledbuttonadapter;

    private interface LEDButton{
        String getText();
        int getBackground();
        int getForeground();
        int getIcon();
        int getIconi();
        int getCommand();
    }

    public enum Remote1 implements LEDButton{
        UP("", R.drawable.plus, R.drawable.plusi, R.color.white, R.color.black, 0xa0),
        DOWN("", R.drawable.minus, R.drawable.minusi, R.color.white, R.color.black, 0x20),
        ON("ON", -1, -1, R.color.dark_gray, R.color.black, 0x60),
        OFF("OFF", -1, -1, R.color.red, R.color.white, 0xe0),
        RED("RED", -1, -1, R.color.red, R.color.black, 0x90),
        GREEN("GREEN", -1, -1, R.color.green, R.color.black, 0x10),
        BLUE("BLUE", -1, -1, R.color.blue, R.color.white, 0x50),
        WHITE("WHITE", -1, -1, R.color.white, R.color.black, 0xd0),
        ORANGE("ORANGE", -1, -1, R.color.orange, R.color.black, 0xb0),
        YELLOW("YELLOW", -1, -1, R.color.yellow, R.color.black, 0x30),
        CYAN("CYAN", -1, -1, R.color.cyan, R.color.black, 0x70),
        PURPLE("PURPLE", -1, -1, R.color.purple, R.color.white, 0xf0),
        J3("JUMP 3", -1, -1, R.color.dark_gray, R.color.black, 0xa8),
        J7("JUMP 7", -1, -1, R.color.dark_gray, R.color.black, 0x28),
        F3("FADE 3", -1, -1, R.color.dark_gray, R.color.black, 0xe8),
        F7("FADE 7", -1, -1, R.color.dark_gray, R.color.black, 0x68),
        M1("", R.drawable.m_one, R.drawable.mi, R.color.dark_gray, R.color.red, 0x98),
        M2("", R.drawable.m_two, R.drawable.mi, R.color.dark_gray, R.color.green, 0x18),
        M3("", R.drawable.m_three, R.drawable.mi, R.color.dark_gray, R.color.blue, 0x58),
        M4("", R.drawable.m_four, R.drawable.mi, R.color.dark_gray, R.color.purple, 0xd8);

        String text;
        int background;
        int foreground;
        int icon;
        int iconi;
        int command;
        @Override
        public String getText(){return text;}

        @Override
        public int getBackground(){return background;}

        @Override
        public int getForeground(){return foreground;}

        @Override
        public int getIcon(){return icon;}

        @Override
        public int getIconi(){return iconi;}

        @Override
        public int getCommand(){return command;}

        Remote1(String text, int icon, int iconi, int background, int foreground, int command) {
            this.text = text;
            this.icon = icon;
            this.iconi=iconi;
            this.background=background;
            this.foreground=foreground;
            this.command=command;
        }
    }

    public enum Remote2 implements LEDButton{
        UP("", R.drawable.plus, R.drawable.plusi, R.color.white, R.color.black, 0x3a),
        DOWN("", R.drawable.minus, R.drawable.minusi, R.color.white, R.color.black, 0xba),
        PAUSE("PLAY/PAUSE", -1, -1, R.color.black, R.color.white, 0x82),
        POWER("ON/OFF", -1, -1, R.color.red, R.color.white, 0x02),

        RED("RED", -1, -1, R.color.red0, R.color.black, 0x1a),
        GREEN("GREEN", -1, -1, R.color.green0, R.color.black, 0x9a),
        BLUE("BLUE", -1, -1, R.color.blue0, R.color.white, 0xa2),
        WHITE("WHITE", -1, -1, R.color.white0, R.color.black, 0x22),

        R1("", -1, -1, R.color.red1, R.color.black, 0x2a),
        G1("", -1, -1, R.color.green1, R.color.black, 0xaa),
        B1("", -1, -1, R.color.blue1, R.color.white, 0x92),
        W1("", -1, -1, R.color.white1, R.color.black, 0x12),

        R2("", -1, -1, R.color.red2, R.color.black, 0x0a),
        G2("", -1, -1, R.color.green2, R.color.black, 0x8a),
        B2("", -1, -1, R.color.blue2, R.color.white, 0xb2),
        W2("", -1, -1, R.color.white2, R.color.black, 0x32),

        R3("", -1, -1, R.color.red3, R.color.black, 0x38),
        G3("", -1, -1, R.color.green3, R.color.black, 0xb8),
        B3("", -1, -1, R.color.blue3, R.color.white, 0x78),
        W3("", -1, -1, R.color.white3, R.color.black, 0xf8),

        R4("", -1, -1, R.color.red4, R.color.black, 0x18),
        G4("", -1, -1, R.color.green4, R.color.black, 0x98),
        B4("", -1, -1, R.color.blue4, R.color.white, 0x58),
        W4("", -1, -1, R.color.white4, R.color.black, 0xd8),

        UPR("", R.drawable.plusr, R.drawable.plusi, R.color.white, R.color.red0, 0x28),
        UPG("", R.drawable.plusg, R.drawable.plusi, R.color.white, R.color.green0, 0xa8),
        UPB("", R.drawable.plusb, R.drawable.plusi, R.color.white, R.color.blue0, 0x68),
        QUICK("QUICK", -1, -1, R.color.white, R.color.black, 0xe8),

        DOWNR("", R.drawable.minusr, R.drawable.minusi, R.color.white, R.color.red0, 0x08),
        DOWNG("", R.drawable.minusg, R.drawable.minusi, R.color.white, R.color.green0, 0x88),
        DOWNB("", R.drawable.minusb, R.drawable.minusi, R.color.white, R.color.blue0, 0x48),
        SLOW("SLOW", -1, -1, R.color.white, R.color.black, 0xc8),

        DIY1("DIY1", -1, -1, R.color.white, R.color.black, 0x30),
        DIY2("DIY2", -1, -1, R.color.white, R.color.black, 0xb0),
        DIY3("DIY3", -1, -1, R.color.white, R.color.black, 0x70),
        AUTO("AUTO", -1, -1, R.color.white, R.color.black, 0xf0),

        DIY4("DIY4", -1, -1, R.color.white, R.color.black, 0x10),
        DIY5("DIY5", -1, -1, R.color.white, R.color.black, 0x90),
        DIY6("DIY6", -1, -1, R.color.white, R.color.black, 0x50),
        FLASH("FLASH", -1, -1, R.color.white, R.color.black, 0xd0),

        JUMP3("JUMP3", -1, -1, R.color.white, R.color.black, 0x20),
        JUMP7("JUMP7", -1, -1, R.color.white, R.color.black, 0xa0),
        FADE3("FADE3", -1, -1, R.color.white, R.color.black, 0x60),
        FADE7("FADE7", -1, -1, R.color.white, R.color.black, 0xe0);

        String text;
        int background;
        int foreground;
        int icon;
        int iconi;
        int command;
        @Override
        public String getText(){return text;}

        @Override
        public int getBackground(){return background;}

        @Override
        public int getForeground(){return foreground;}

        @Override
        public int getIcon(){return icon;}

        @Override
        public int getIconi(){return iconi;}

        @Override
        public int getCommand(){return command;}

        Remote2(String text, int icon, int iconi, int background, int foreground, int command) {
            this.text = text;
            this.icon = icon;
            this.iconi=iconi;
            this.background=background;
            this.foreground=foreground;
            this.command=command;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ledbuttongridview = (GridView) findViewById(R.id.ledbuttons);
        ledbuttonadapter = new LEDButtonAdapter(this);
        ledbuttongridview.setAdapter(ledbuttonadapter);

        transmitter = new Transmitter(getApplicationContext());
        if (transmitter.compatible()) {
            ledbuttongridview.setVisibility(View.VISIBLE);
        }
    }

    public void toggleRemote(View view){
        ledbuttonadapter.toggleRemote();
    }

    private ShapeDrawable makeCircle(int color) {
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.getPaint().setColor(color);
        shape.setIntrinsicHeight(100);
        shape.setIntrinsicWidth(100);
        return shape;
    }

    public LayerDrawable getButtonDrawable(int background, int foreground, int icon, int iconi, boolean pressed) {
        Drawable[] drawables = new Drawable[3];
        int fg;
        int bg;
        if (pressed) {
            fg = getResources().getColor(background);
            bg = getResources().getColor(R.color.black);
        } else {
            fg = getResources().getColor(foreground);
            bg = getResources().getColor(background);
        }
        drawables[0] = makeCircle(bg);
        drawables[1] = makeCircle(fg);
        drawables[2] = makeCircle(bg);

        if (pressed && iconi != -1) {
            Drawable[] iconDrawable = {getResources().getDrawable(iconi)};
            drawables = concat(drawables, iconDrawable);
        } else if (!pressed && icon != -1) {
            Drawable[] iconDrawable = {getResources().getDrawable(icon)};
            drawables = concat(drawables, iconDrawable);

        }

        LayerDrawable layers = new LayerDrawable(drawables);
        layers.setLayerInset(1, 10, 10, 10, 10);
        layers.setLayerInset(2, 15, 15, 15, 15);
        if ((icon != -1 && !pressed) || (iconi != -1 && pressed)) {
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
        private boolean remote1=true;

        public LEDButtonAdapter(Context context) {
            LEDButtonList = Arrays.<LEDButton>asList(Remote1.values());
        }

        public void toggleRemote(){
            if(remote1){
                LEDButtonList = Arrays.<LEDButton>asList(Remote2.values());
            }else{
                LEDButtonList = Arrays.<LEDButton>asList(Remote1.values());
            }
            remote1=!remote1;
            notifyDataSetChanged();
            return;
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

            viewHolder.button.setText(led_button.getText());

            Drawable roundButton = getButtonDrawable(led_button.getBackground(), led_button.getForeground(), led_button.getIcon(), led_button.getIconi(), false);
            viewHolder.button.setBackground(roundButton);
            viewHolder.button.setTextColor(getResources().getColor(led_button.getForeground()));
            viewHolder.button.setOnClickListener(new LEDButtonListener(led_button.getCommand()));
            viewHolder.button.setOnTouchListener(new LEDTouchListener(led_button));

            return view;
        }

        class LEDButtonListener implements View.OnClickListener {
            int command;

            LEDButtonListener(int command) {
                this.command = command;
            }

            @Override
            public void onClick(View view) {
                transmitter.transmit(this.command);
            }
        }

        class LEDTouchListener implements View.OnTouchListener {
            LEDButton led_button;

            public LEDTouchListener(LEDButton led_button) {
                this.led_button = led_button;
            }

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                Drawable roundButton = null;
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    roundButton = getButtonDrawable(led_button.getBackground(), led_button.getForeground(), led_button.getIcon(), led_button.getIconi(), true);
                    ((Button) view).setTextColor(getResources().getColor(led_button.getBackground()));

                } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    roundButton = getButtonDrawable(led_button.getBackground(), led_button.getForeground(), led_button.getIcon(), led_button.getIconi(), false);
                    ((Button) view).setTextColor(getResources().getColor(led_button.getForeground()));
                }
                view.setBackground(roundButton);
                return false;
            }
        }
    }

    static class ViewHolder {
        Button button;
    }

}
