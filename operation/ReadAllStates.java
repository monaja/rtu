package agigitatorsystem.util.rtu.operation;


import agigitatorsystem.util.rtu.constants.DeviceConfigs;
import agigitatorsystem.util.rtu.constants.RCConstants;
import agigitatorsystem.util.rtu.enums.DeviceType;
import agigitatorsystem.util.rtu.serialport.InputSerialRead;
import agigitatorsystem.util.rtu.serialport.OutputSerialWrite;

import javax.comm.SerialPort;
import java.util.HashMap;
import java.util.Map;

public class ReadAllStates {
    String readRelayStatesRequest = "";
    byte[] bytes = new byte[20];

    public Map<String, Boolean> getRelayStates(DeviceType deviceType, String comPort, String baudrate, String deviceAddress) {
        int deviceAddressInt = -1;
        boolean controlStatus = false;
        try {
            deviceAddressInt = Integer.parseInt(deviceAddress);
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
        String deviceAddressHex = Integer.toHexString(deviceAddressInt);

        Map<String, Boolean> relayStates = new HashMap<>();
        switch (deviceType) {
            case RELAY_CONTROLLER:
                readRelayStatesRequest = "010100FF0001" + RCConstants.RC_READ_RELAY_STATES_REQUEST_CRC16;
                System.out.println("GetRelayStates|readRelayStatesRequest => " + readRelayStatesRequest + "|comPort: " + comPort + "|deviceAddress: " + deviceAddress + "|deviceAddressHex: " + deviceAddressHex);
                int maxRetry = 2;
                SerialPort serialPort = null;
                try {
                    serialPort = OutputSerialWrite.write(readRelayStatesRequest, comPort);
                } catch (Exception ex) {
                    System.out.println("GetRelayStates|ERROR: " + ex.getMessage());
                }

                if (serialPort == null) {
                    return null;
                }

                try {
                    Thread.sleep(1000);
                    bytes = new InputSerialRead().getResponse(serialPort);
                } catch (Exception ex) {
                    System.out.println("GetRelayStates|ERROR: " + ex.getMessage());
                }
                try {
                    serialPort.close();
                } catch (Exception ex) {
                    System.out.println("GetRelayStates|error closing port: " + serialPort);
                }

                String response = "";
                for (int i = 0; i < RCConstants.RC_READ_RELAY_STATES_RESPONSE_SIZE; i++) {
                    String hexStr = String.format("%02X", bytes[i]);
                    System.out.println("GetRelayStates|byte index " + i + " value " + hexStr);
                    response += hexStr;
                }
                System.out.println("GetRelayStates|Response: " + response);

                if (bytes.length < RCConstants.RC_READ_RELAY_STATES_RESPONSE_SIZE) {
                    return null;
                }

                String relayStatesHex = String.format("%02X", bytes[3]);
                String relayStatesBin = "";
                System.out.println("GetRelayStates|Response: " + response + "|relayStatesByte: " + relayStatesHex);
                int bitLength = relayStatesHex.length() * 4;
                int intValue = Integer.parseInt(relayStatesHex, 16);

                String binValue = Integer.toBinaryString(intValue);

                int lostBits = bitLength - binValue.length();
                String zeroBits = "";
                for (int i = 0; i < lostBits; i++) {
                    zeroBits += "0";
                }

                relayStatesBin = zeroBits.concat(binValue);
                System.out.println("GetRelayStates|Response: " + response + "|relayStatesByte: " + relayStatesHex + "|relayStatesBin: " + relayStatesBin);

                int len = relayStatesBin.length() -1;

                for (int i = len; i > -1; i--) {
                    String key = deviceAddress + "-" + (len - i);
                    String relayStatusStr = String.valueOf(relayStatesBin.charAt(i));
                    if ("1".equalsIgnoreCase(relayStatusStr)) {
                        System.out.println("GetRelayStates|Response: " + response + "|relayStatesByte: " + relayStatesHex + "|relayStatesBin: " + relayStatesBin + "|key: " + key + "|relayStatusStr: " + relayStatusStr + " OPEN.");
                        relayStates.put(key, true);
                    } else {
                        System.out.println("GetRelayStates|Response: " + response + "|relayStatesByte: " + relayStatesHex + "|relayStatesBin: " + relayStatesBin + "|key: " + key + "|relayStatusStr: " + relayStatusStr + " CLOSE");
                        relayStates.put(key, false);
                    }
                }

                //get the crc16
                String crc16 = String.format("%02X", bytes[RCConstants.RC_READ_RELAY_STATES_RESPONSE_SIZE - 2]) + String.format("%02X", bytes[RCConstants.RC_READ_RELAY_STATES_RESPONSE_SIZE - 1]);
                String deviceAddressStr = String.format("%02X", bytes[0]);
                System.out.println("GetRelayStates|RequestCRC16: " + RCConstants.RC_READ_RELAY_STATES_REQUEST_CRC16 + "|ResponseCRC16: " + crc16 + "|deviceAddress: " + deviceAddress);
//                if (RCConstants.RC_READ_RELAY_STATES_RESPONSE_CRC16.equalsIgnoreCase(crc16)) {
//                    System.out.println("GetRelayStates|RequestCRC16: " + RCConstants.RC_READ_RELAY_STATES_REQUEST_CRC16 + "|ResponseCRC16: " + crc16 + "|deviceAddress: " + deviceAddress + " CRC16 CHECK PASSED!");
                if (DeviceConfigs.RELAY_CONTROLLER_ID_HEX.equalsIgnoreCase(deviceAddressStr)) {
                    System.out.println("GetRelayStates|RequestCRC16: " + RCConstants.RC_READ_RELAY_STATES_REQUEST_CRC16 + "|ResponseCRC16: " + crc16 + "|deviceAddress: " + deviceAddress + " DEVICE ADDRESS MATCH PASSED!");
                    return relayStates;
                } else {
                    System.out.println("GetRelayStates|RequestCRC16: " + RCConstants.RC_READ_RELAY_STATES_REQUEST_CRC16 + "|ResponseCRC16: " + crc16 + "|deviceAddress: " + deviceAddress + " DEVICE ADDRESS MATCH FAIL!");
                }
//                } else {
//                    System.out.println("GetRelayStates|RequestCRC16: " + RCConstants.RC_READ_RELAY_STATES_REQUEST_CRC16 + "|ResponseCRC16: " + crc16 + "|deviceAddress: " + deviceAddress + " CRC16 CHECK FAIL!");
//
//                }
                return null;
            case SWG:
                break;
        }
        return null;
    }
}
