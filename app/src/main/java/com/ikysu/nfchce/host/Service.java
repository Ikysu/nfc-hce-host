package com.ikysu.nfchce.host;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.widget.Toast;

import java.nio.charset.Charset;

public class Service extends HostApduService {


    public String TAG = "Host Card Emulator";
    public String STATUS_SUCCESS = "9000";
    public String STATUS_FAILED = "6F00";
    public String CLA_NOT_SUPPORTED = "6E00";
    public String INS_NOT_SUPPORTED = "6D00";
    public String AID = "A0000002471001";
    public String SELECT_INS = "A4";
    public String DEFAULT_CLA = "00";
    public int MIN_APDU_LENGTH = 12;


    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle bundle) {
        if (commandApdu == null) {
            return Utils.hex2byte(STATUS_FAILED);
        }

        String hexCommandApdu = Utils.byte2hex(commandApdu);

        if (hexCommandApdu.length() < MIN_APDU_LENGTH) {
            return Utils.hex2byte(STATUS_FAILED);
        }
        if (!hexCommandApdu.substring(0, 2).equals(DEFAULT_CLA)) {
            return Utils.hex2byte(CLA_NOT_SUPPORTED);
        }
        if (!hexCommandApdu.substring(2, 4).equals(SELECT_INS)) {
            return Utils.hex2byte(INS_NOT_SUPPORTED);
        }
        if (!hexCommandApdu.substring(10, 24).equals(AID))  {
            return Utils.hex2byte(STATUS_FAILED);
        }

        if(hexCommandApdu.length()<25) {
            return Utils.hex2byte(STATUS_SUCCESS);
        }

        String cmd = hexCommandApdu.substring(24);
        Toast.makeText(this, cmd, Toast.LENGTH_SHORT).show();

        return Utils.hex2byte("900011"); // OK
    }

    @Override
    public void onDeactivated(int i) {

    }
}
