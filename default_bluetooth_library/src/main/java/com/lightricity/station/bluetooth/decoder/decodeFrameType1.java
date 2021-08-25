package com.lightricity.station.bluetooth.decoder;

import com.lightricity.station.bluetooth.FoundSensor;
import com.lightricity.station.bluetooth.format.formatBeaconCounter;
import com.lightricity.station.bluetooth.format.formatCO2;
import com.lightricity.station.bluetooth.format.formatHumidity;
import com.lightricity.station.bluetooth.format.formatLight;
import com.lightricity.station.bluetooth.format.formatPressure;
import com.lightricity.station.bluetooth.format.formatSensorID;
import com.lightricity.station.bluetooth.format.formatTemperature;
import com.lightricity.station.bluetooth.format.formatVendorID;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class decodeFrameType1 implements LeScanResult.RuuviTagDecoder{
    //offset =7
    @Override
    public FoundSensor decode(byte[] data, int offset) {
        FoundSensor tag = new FoundSensor();
        for (int bit=offset+1; bit< data.length; ){
            int[] Data_size_type = toBinary(data[bit]);
            int[] Data_size = SubArray(6,2,Data_size_type);
            int[] Data_type = SubArray(0,6,Data_size_type);
            int Size = toDecimal(Data_size) +1;
            int TypeofData = toDecimal(Data_type);
            SensorFormat format = null;
            switch (TypeofData){
                case 0:
                    format = new formatVendorID();
                    break;
                case 1:
                    format = new formatSensorID();
                    break;
                case 2:
                    format = new formatBeaconCounter();
                    break;
                case 21:
                    format = new formatTemperature();
                    break;
                case 22:
                    format = new formatHumidity();
                    break;
                case 23:
                    format = new formatPressure();
                    break;
                case 24:
                    format = new formatLight();
                    break;
                case 25:
                    format = new formatCO2();
                    break;
                /*case 26:
                    format = new formatAcceleration();
                    break;*/
                default:

            }
            bit=bit+Size+1;
            tag = format.format(data, bit, Size);

        }
        return tag;
    }


    public int[] toBinary(int data) {
        int[] binary = new int []{0,0,0,0,0,0,0,0};
        int index = 0;
        while (data > 0) {
            binary[index] = data % 2;
            data = data / 2;
            index++;
        }
        return binary;
    }


    public int toDecimal(int[] data) {
        int value=0;
        for(int index=0;index< data.length;index++){
            value= (int) (value+data[index]*Math.pow(2,index));
        }
        return value;
    }


    public int[] SubArray(int start, int lenght, int[] data){
        int[] subarray = new int[lenght];
        int index=0;
        for(int i=start; i<lenght;i++){
            subarray[index]=data[i];
            index++;
        }
        return subarray;
    }

    public interface SensorFormat {

        FoundSensor format(byte[] data, int bit, int size);

    }

}

