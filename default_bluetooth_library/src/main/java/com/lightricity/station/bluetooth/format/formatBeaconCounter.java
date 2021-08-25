package com.lightricity.station.bluetooth.format;

import com.lightricity.station.bluetooth.FoundSensor;
import com.lightricity.station.bluetooth.decoder.decodeFrameType1;

public class formatBeaconCounter implements decodeFrameType1.SensorFormat {
    //offset =7
    @Override
    public FoundSensor format(byte[] data, int bit, int Size) {
        FoundSensor tag = new FoundSensor();
        tag.setMeasurementSequenceNumber((data[bit+1] & 0xFF) << 8 | data[bit +2] & 0xFF);

        return tag;
    }
}
