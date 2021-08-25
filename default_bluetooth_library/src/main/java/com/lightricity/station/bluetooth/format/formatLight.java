package com.lightricity.station.bluetooth.format;

import com.lightricity.station.bluetooth.FoundSensor;
import com.lightricity.station.bluetooth.decoder.decodeFrameType1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class formatLight implements decodeFrameType1.SensorFormat {
    //offset =7
    @Override
    public FoundSensor format(byte[] data, int bit, int Size) {
        FoundSensor tag = new FoundSensor();
        switch (Size){
            case 1:
                tag.setLight((double) (data[bit+1] << 8));
                break;
            case 2:
                tag.setLight((double) (data[bit+1] << 8 | data[bit+2] & 0XFF));
                break;
            case 3:
                tag.setLight((double) (data[bit+1] << 16 | data[bit+2] << 8 | data[bit+3] & 0XFF));
                break;
            case 4:
                tag.setLight((double) (data[bit+1] << 24 | data[bit+2] << 16 | data[bit+3] << 8 | data[bit+4] & 0XFF));
                break;
            default: tag.setLight(0.0);
        }
        tag.setLight(round(tag.getLight() != null ? tag.getLight() : 0.0, 4));
        return tag;
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
