package com.lightricity.station.bluetooth.format;

import com.lightricity.station.bluetooth.FoundSensor;
import com.lightricity.station.bluetooth.decoder.decodeFrameType1;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class formatHumidity implements decodeFrameType1.SensorFormat {
    //offset =7
    @Override
    public FoundSensor format(byte[] data, int bit, int Size) {
        FoundSensor tag = new FoundSensor();
        if (Size==2){
            tag.setHumidity((data[bit+1]<<8 | data[bit+2] & 0XFF)/100d);
            tag.setHumidity(round(tag.getHumidity() != null ? tag.getHumidity() : 0.0, 4));
        }else{
            tag.setHumidity(null);
        }
        return tag;
    }
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
