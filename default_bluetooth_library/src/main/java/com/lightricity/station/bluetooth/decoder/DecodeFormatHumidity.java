package com.lightricity.station.bluetooth.decoder;

import com.lightricity.station.bluetooth.FoundSensor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import timber.log.Timber;

public class DecodeFormatHumidity implements LeScanResult.RuuviTagDecoder {

    @Override
    public FoundSensor decode(byte[] data, int offset) {
        FoundSensor tag = new FoundSensor();
        tag.setDataFormat("Decode Pressure");
        int decodeFormat = data[offset+2];
        switch (decodeFormat) {
            case 1:
                tag.setBrand("BME 280 Bosch");
                tag.setHumidity(((data[10 + offset] & 0xFF) << 8 | data[9 + offset] & 0xFF) / 1000d);
            break;
            case 2:
                tag.setBrand("BME 680 Bosch");
                tag.setHumidity(((data[10 + offset] & 0xFF) << 8 | data[9 + offset] & 0xFF) / 100d);
                break;
            default:
                Timber.d("Unknown tag protocol version: %1$s (PROTOCOL_OFFSET: %2$s)", decodeFormat, offset);
        }

        // make it pretty
        tag.setHumidity(round(tag.getHumidity() != null ? tag.getHumidity() : 0.0, 2));


        return tag;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
