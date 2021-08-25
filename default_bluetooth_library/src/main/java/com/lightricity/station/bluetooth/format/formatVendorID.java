package com.lightricity.station.bluetooth.format;

import com.lightricity.station.bluetooth.FoundSensor;
import com.lightricity.station.bluetooth.decoder.decodeFrameType1;

public class formatVendorID implements decodeFrameType1.SensorFormat {
    //offset =7
    @Override
    public FoundSensor format(byte[] data, int bit, int Size) {
        FoundSensor tag = new FoundSensor();
        switch (Size){
            case 1:
            tag.setVendorID(data[bit+1] << 8);
            break;
            case 2:
                tag.setVendorID(data[bit+1] << 8 | data[bit+2] & 0XFF);
                break;
            case 3:
                tag.setVendorID(data[bit+1] << 16 | data[bit+2] << 8 | data[bit+3] & 0XFF);
                break;
            case 4:
                tag.setVendorID(data[bit+1] << 24 | data[bit+2] << 16 | data[bit+3] << 8 | data[bit+4] & 0XFF);
                break;
            default: tag.setVendorID(0);
        }
        return tag;
    }
}
