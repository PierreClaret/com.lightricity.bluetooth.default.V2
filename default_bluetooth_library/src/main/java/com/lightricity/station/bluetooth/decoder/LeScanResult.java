package com.lightricity.station.bluetooth.decoder;

import android.bluetooth.BluetoothDevice;
import com.neovisionaries.bluetooth.ble.advertising.ADManufacturerSpecific;
import com.neovisionaries.bluetooth.ble.advertising.ADPayloadParser;
import com.neovisionaries.bluetooth.ble.advertising.ADStructure;
import com.neovisionaries.bluetooth.ble.advertising.EddystoneURL;
import com.lightricity.station.bluetooth.FoundSensor;
import java.io.ByteArrayOutputStream;
import java.util.List;
import timber.log.Timber;


public class LeScanResult {
    public static Integer PROTOCOL_OFFSET = 7;
    public BluetoothDevice device;
    public byte[] scanData;
    public int rssi;

    public FoundSensor parse() {
        FoundSensor tag = null;

        try {
            // Parse the payload of the advertisement packet
            // as a list of AD structures.
            List<ADStructure> structures =
                    ADPayloadParser.getInstance().parse(this.scanData);

            // For each AD structure contained in the advertisement packet.
            for (ADStructure structure : structures) {
                if (structure instanceof EddystoneURL) {
                    // Eddystone URL
                    EddystoneURL es = (EddystoneURL) structure;
                    if (es.getURL().toString().startsWith("https://test.1/#") || es.getURL().toString().startsWith("https://test/")) {
                        tag = from(
                                this.device.getAddress(),
                                es.getURL().toString(),
                                null,
                                this.rssi
                        );
                    }
                }
                // If the AD structure represents Eddystone TLM.
                else if (structure instanceof ADManufacturerSpecific) {
                    ADManufacturerSpecific es = (ADManufacturerSpecific) structure;
                    //Search for the ID company of lightricity

<<<<<<< HEAD
                    if (es.getCompanyId() == 0x0A96) {
=======
                    if (es.getCompanyId() == 0x0A96 || es.getCompanyId() == 0x004C) {
>>>>>>> a69581708b3878b952e2028bba57a54bc2f99870
                        tag = from(this.device.getAddress(), null, this.scanData, this.rssi);
                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e,"Parsing ble data failed");
        }

        return tag;
    }

    public static FoundSensor from(String id, String url, byte[] rawData, int rssi) {
        RuuviTagDecoder decoder = null;
        if (url != null && url.contains("#")) {
            String data = url.split("#")[1];
            rawData = parseByteDataFromB64(data);
            decoder = new DecodeFormat2and4();
        } else if (rawData != null) {
<<<<<<< HEAD
            int FrameType = rawData[PROTOCOL_OFFSET];
            switch (FrameType){
                case 0: decoder=null;
                break;
                case 1: decoder= new decodeFrameType1();
                break;
                default : new decodeFrameType1();
=======
            if (rawData[PROTOCOL_OFFSET - 1] == 0x4C && rawData[PROTOCOL_OFFSET] == 0x00) {
                decoder = new DecodeFormatNoModzee();
            } else {
                int protocolVersion = rawData[PROTOCOL_OFFSET + 1];
           /* if (protocolVersion>=1 && protocolVersion<10){
                decoder = new DecodeFormatTemperature();
            }else if(protocolVersion>=10 && protocolVersion<20){
                decoder = new DecodeFormatHumidity();
            }else if(protocolVersion>=20 && protocolVersion<30){
                decoder = new DecodeFormatPressure();
            }else if(protocolVersion>=30 && protocolVersion<40) {
                decoder = new DecodeFormatMultiSensor();
            }*/

                switch (protocolVersion) {
                    case 1:
                        decoder = new DecodeFormatTemperature();
                        break;
                    case 2:
                        decoder = new DecodeFormatHumidity();
                        break;
                    case 3:
                        decoder = new DecodeFormatPressure();
                        break;
                    case 4:
                        decoder = new DecodeFormatMultiSensor();
                        break;
                    case 5:
                        decoder = new DecodeFormat5();
                        break;
                    default:
                        Timber.d("Unknown tag protocol version: %1$s (PROTOCOL_OFFSET: %2$s)", protocolVersion, PROTOCOL_OFFSET);
                }
>>>>>>> a69581708b3878b952e2028bba57a54bc2f99870
            }
        }

        if (decoder != null) {
            FoundSensor tag = decoder.decode(rawData, PROTOCOL_OFFSET);
            if (tag != null) {
                tag.setId(id);
<<<<<<< HEAD
                tag.setFrame((int) rawData[PROTOCOL_OFFSET]);
=======
                tag.setSensorID(rawData[PROTOCOL_OFFSET+3]<<8 | rawData[PROTOCOL_OFFSET+4]& 0xFF);
>>>>>>> a69581708b3878b952e2028bba57a54bc2f99870
                tag.setUrl(url);
                tag.setRssi(rssi);
            }
            return tag;
        }
        return null;
    }

    private static byte[] parseByteDataFromB64(String data) {
        try {
            byte[] bData = decode(data);
            int[] pData = new int[8];
            for (int i = 0; i < bData.length; i++)
                pData[i] = bData[i] & 0xFF;
            return bData;
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] decode(String data) {
        int[] tbl = {
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54,
                55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2,
                3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30,
                31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
                48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        byte[] bytes = data.getBytes();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        for (int i = 0; i < bytes.length; ) {
            int b;
            if (tbl[bytes[i]] != -1) {
                b = (tbl[bytes[i]] & 0xFF) << 18;
            }
            // skip unknown characters
            else {
                i++;
                continue;
            }

            int num = 0;
            if (i + 1 < bytes.length && tbl[bytes[i + 1]] != -1) {
                b = b | ((tbl[bytes[i + 1]] & 0xFF) << 12);
                num++;
            }
            if (i + 2 < bytes.length && tbl[bytes[i + 2]] != -1) {
                b = b | ((tbl[bytes[i + 2]] & 0xFF) << 6);
                num++;
            }
            if (i + 3 < bytes.length && tbl[bytes[i + 3]] != -1) {
                b = b | (tbl[bytes[i + 3]] & 0xFF);
                num++;
            }

            while (num > 0) {
                int c = (b & 0xFF0000) >> 16;
                buffer.write((char) c);
                b <<= 8;
                num--;
            }
            i += 4;
        }
        return buffer.toByteArray();
    }

    public interface RuuviTagDecoder {

        FoundSensor decode(byte[] data, int offset);

    }
}
