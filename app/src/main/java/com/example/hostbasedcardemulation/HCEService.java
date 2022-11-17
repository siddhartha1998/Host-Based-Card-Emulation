package com.example.hostbasedcardemulation;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HCEService extends HostApduService {
    private final String TAG = "Host Card Emulator";
    private final String STATUS_SUCCESS = "9000";
    private final String STATUS_FAILED = "6F00";
    private final String CLA_NOT_SUPPORTED = "6E00";
    private final String INS_NOT_SUPPORTED = "6D00";
    private final String AID = "A0000002471001";
    private final String SELECT_INS = "A4";
    private final String DEFAULT_CLA = "00";
    private final int MIN_APDU_LENGTH = 12;


    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle bundle) {

        if(commandApdu == null){
            Toast.makeText(this,commandApdu.toString(),Toast.LENGTH_LONG);
            return Utils.hexToByteArray(STATUS_FAILED);
        }
        String hexCommandApdu = Utils.byteArrayToHex(commandApdu);

        if(hexCommandApdu.length() < MIN_APDU_LENGTH){
            return Utils.hexToByteArray(STATUS_FAILED);
        }

        if(hexCommandApdu.substring(0,2) != DEFAULT_CLA){
            return Utils.hexToByteArray(CLA_NOT_SUPPORTED);
        }

        if(hexCommandApdu.substring(2,4) != SELECT_INS){
            return Utils.hexToByteArray(INS_NOT_SUPPORTED);
        }

        if(hexCommandApdu.substring(10,24) == AID){
            return Utils.hexToByteArray(STATUS_SUCCESS);
        }else{
            return commandApdu;
        }
    }

    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "Deactivated: " + reason);
    }
}
