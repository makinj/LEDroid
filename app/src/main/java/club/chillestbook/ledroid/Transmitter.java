/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * makinj64@gmail.com wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Joshua Makinen
 * ----------------------------------------------------------------------------
 */
package club.chillestbook.ledroid;

import android.content.Context;
import android.hardware.ConsumerIrManager;

/**
 * Created by root on 7/25/15.
 */
public class Transmitter {

    private int pulse=600;
    private int zero_low =500;
    private int one_low =1600;
    private int header_high_pulse =9000;
    private int header_low_pulse =4000;
    private int tag_high_pulse =9000;
    private int tag_low_pulse =2000;
    private int pause_low = 38750;
    private int _freq = 38000;
    private ConsumerIrManager irManager;

    public enum Command {
        GREEN(0x10),
        DOWN(0x20),
        YELLOW(0x30),
        BLUE(0x50),
        ON(0x60),
        CYAN(0x70),
        RED(0x90),
        UP(0xa0),
        ORANGE(0xb0),
        WHITE(0xd0),
        OFF(0xe0),
        PURPLE(0xf0),
        M2(0x18),
        J7(0x28),
        M3(0x58),
        F7(0x68),
        M1(0x98),
        J3(0xa8),
        M4(0xd8),
        F3(0xe8);
        public int code;

        Command(int code) {
            this.code = code;
        }

        int getCode() {
            return this.code;
        }

        int getInverse() {
            return this.code ^ 0xff;
        }
    }

    public Transmitter(Context context) {
        irManager = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
    }

    public boolean compatible(){
        if(irManager.hasIrEmitter()) {
            ConsumerIrManager.CarrierFrequencyRange[] freqRange = irManager.getCarrierFrequencies();
            return freqRange[0].getMaxFrequency() >= _freq && freqRange[0].getMinFrequency() <= _freq;
        }else {
            return false;
        }
    }

    public void transmit(int command) {
        int[] pattern = constructPattern(command);
        irManager.transmit(_freq,pattern);
    }

    private int[] constructPattern(int command) {
        int[] pattern = constructHead();
        pattern = concat(pattern, int2pat(0x00));
        pattern = concat(pattern, int2pat(0xff));
        pattern = concat(pattern, int2pat(command));
        pattern = concat(pattern, int2pat(command ^ 0xff));
        return concat(pattern, constructTag());
    }

    private int[] constructHead() {
        int[] pattern = new int[3];
        pattern[0] = header_high_pulse;
        pattern[1] = header_low_pulse;
        pattern[2] = pulse;
        return pattern;
    }

    private int[] constructTag() {
        int[] pattern = new int[4];
        pattern[0] = pause_low;
        pattern[1] = tag_high_pulse;
        pattern[2] = tag_low_pulse;
        pattern[3] = pulse;
        return pattern;
    }

    private int[] int2pat(int val){
        int[] pattern = new int[16];
        int curr=128;
        for(int i = 0;i<pattern.length;i++){
            if(i%2==0){
                if(val>=curr) {
                    pattern[i]= one_low;
                    val-=curr;
                }else{
                    pattern[i]= zero_low;
                }
                curr/=2;
            }else{
                pattern[i]=pulse;
            }
        }
        return pattern;
    }

    private int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] C= new int[aLen+bLen];
        System.arraycopy(a, 0, C, 0, aLen);
        System.arraycopy(b, 0, C, aLen, bLen);
        return C;
    }
}