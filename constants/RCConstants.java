package agigitatorsystem.util.rtu.constants;

public class RCConstants {

    //relay controller request and response configs the CRC are always the last 2 size -1 abd size -2
    public final static String RC_READ_DEVICE_ADDRESS_REQUEST_CRC16 ="901B";
    public final static String RC_READ_DEVICE_ADDRESS_RESPONSE_CRC16 ="3188";
    public final static int RC_READ_DEVICE_ADDRESS_REQUEST_SIZE = 8;
    public final static int RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE= 6;

    //relay controller request and response configs for open relay the CRC are always the last 2 size -1 abd size -2
    public final static String RC_OPEN_RELAY_1_REQUEST_CRC16 ="8C3A";
    public final static String RC_OPEN_RELAY_1_RESPONSE_CRC16 ="8C3A";
    public final static String RC_OPEN_RELAY_2_REQUEST_CRC16 ="DDFA";
    public final static String RC_OPEN_RELAY_2_RESPONSE_CRC16 ="DDFA";
    public final static String RC_OPEN_RELAY_3_REQUEST_CRC16 ="2DFA";
    public final static String RC_OPEN_RELAY_3_RESPONSE_CRC16 ="2DFA";
    public final static String RC_OPEN_RELAY_4_REQUEST_CRC16 ="7C3A";
    public final static String RC_OPEN_RELAY_4_RESPONSE_CRC16 ="7C3A";
    public final static String RC_OPEN_RELAY_5_REQUEST_CRC16 ="";
    public final static String RC_OPEN_RELAY_5_RESPONSE_CRC16 ="";
    public final static String RC_OPEN_RELAY_6_REQUEST_CRC16 ="";
    public final static String RC_OPEN_RELAY_6_RESPONSE_CRC16 ="";
    public final static String RC_OPEN_RELAY_7_REQUEST_CRC16 ="";
    public final static String RC_OPEN_RELAY_7_RESPONSE_CRC16 ="";
    public final static String RC_OPEN_RELAY_8_REQUEST_CRC16 ="";
    public final static String RC_OPEN_RELAY_8_RESPONSE_CRC16 ="";
    public final static int RC_OPEN_RELAY_REQUEST_SIZE = 8;
    public final static int RC_OPEN_RELAY_RESPONSE_SIZE= 8;

    //relay controller request and response configs for close relay the CRC are always the last 2 size -1 abd size -2
    public final static String RC_CLOSE_RELAY_1_REQUEST_CRC16 ="CDCA";
    public final static String RC_CLOSE_RELAY_1_RESPONSE_CRC16 ="CDCA";
    public final static String RC_CLOSE_RELAY_2_REQUEST_CRC16 ="9C0A";
    public final static String RC_CLOSE_RELAY_2_RESPONSE_CRC16 ="9C0A";
    public final static String RC_CLOSE_RELAY_3_REQUEST_CRC16 ="6C0A";
    public final static String RC_CLOSE_RELAY_3_RESPONSE_CRC16 ="6C0A";
    public final static String RC_CLOSE_RELAY_4_REQUEST_CRC16 ="3DCA";
    public final static String RC_CLOSE_RELAY_4_RESPONSE_CRC16 ="3DCA";
    public final static String RC_CLOSE_RELAY_5_REQUEST_CRC16 ="";
    public final static String RC_CLOSE_RELAY_5_RESPONSE_CRC16 ="";
    public final static String RC_CLOSE_RELAY_6_REQUEST_CRC16 ="";
    public final static String RC_CLOSE_RELAY_6_RESPONSE_CRC16 ="";
    public final static String RC_CLOSE_RELAY_7_REQUEST_CRC16 ="";
    public final static String RC_CLOSE_RELAY_7_RESPONSE_CRC16 ="";
    public final static String RC_CLOSE_RELAY_8_REQUEST_CRC16 ="";
    public final static String RC_CLOSE_RELAY_8_RESPONSE_CRC16 ="";
    public final static int RC_CLOSE_RELAY_REQUEST_SIZE = 8;
    public final static int RC_CLOSE_RELAY_RESPONSE_SIZE= 8;

    //relay controller request and response configs for get all relay states the CRC are always the last 2 size -1 abd size -2
    public final static String RC_READ_RELAY_STATES_REQUEST_CRC16 = "CDFA";
    public final static String RC_READ_RELAY_STATES_RESPONSE_CRC16 = "5188";
    public final static int RC_READ_RELAY_STATES_REQUEST_SIZE = 8;
    public final static int RC_READ_RELAY_STATES_RESPONSE_SIZE = 6;
}
