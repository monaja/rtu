package agigitatorsystem.util.rtu.operation;



import agigitatorsystem.util.rtu.constants.DeviceConfigs;
import agigitatorsystem.util.rtu.constants.RCConstants;
import agigitatorsystem.util.rtu.enums.DeviceType;
import agigitatorsystem.util.rtu.serialport.InputSerialRead;
import agigitatorsystem.util.rtu.serialport.OutputSerialWrite;

import javax.comm.SerialPort;

public class ControlSingleRelay {

    String modbusControlSingleRelayRequest = "";
    byte[] bytes = new byte[20];

    public boolean openRelay(DeviceType deviceType, String deviceAddress, String comPort, String baudrate, String addressOffset0, boolean status, int timeOut, int retries) {
        int addressOffsetInt = -1;
        try {
            addressOffsetInt = Integer.parseInt(addressOffset0);
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
            addressOffset0 = "";
        }
        String deviceAddressHex = Integer.toHexString(addressOffsetInt);
        String crc16 = "";
        System.out.println("OpenRelay|deviceType: " + deviceType + "|deviceAddress: " + deviceAddress + "|portName: " + comPort + "|baudRate: " + baudrate + "|status: " + status + "|timeout: " + timeOut + "|retries: " + retries);

        switch (addressOffsetInt) {
            case 0:
                crc16 =  RCConstants.RC_OPEN_RELAY_1_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "01050000FF00" + crc16;
                break;
            case 1:
                crc16 = RCConstants.RC_OPEN_RELAY_2_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "01050001FF00" + crc16;
                break;
            case 2:
                crc16 = RCConstants.RC_OPEN_RELAY_3_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "01050002FF00" + crc16;
                break;
            case 3:
                crc16 = RCConstants.RC_OPEN_RELAY_4_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "01050003FF00" + crc16;
                break;
            case 4:
                crc16 = RCConstants.RC_OPEN_RELAY_5_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            case 5:
                crc16 = RCConstants.RC_OPEN_RELAY_6_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            case 6:
                crc16 = RCConstants.RC_OPEN_RELAY_7_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            case 7:
                crc16 = RCConstants.RC_OPEN_RELAY_8_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            default:
        }

        System.out.println("OpenRelay|deviceType: " + deviceType + "|modbusControlSingleRelayRequest => " + modbusControlSingleRelayRequest + "|deviceAddress: " + deviceAddress + "|portName: " + comPort + "|baudRate: " + baudrate + "|status: " + status + "|timeout: " + timeOut + "|retries: " + retries);
        int maxRetry = 2;
        SerialPort serialPort = null;
        try {
            serialPort = OutputSerialWrite.write(modbusControlSingleRelayRequest, comPort);
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        if (serialPort == null) {
            return false;
        }

        try {
            Thread.sleep(100);
            bytes = new InputSerialRead().getResponse(serialPort);
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        try {
            serialPort.close();
        } catch (Exception ex) {
            System.out.println("error closing port: " + serialPort);
        }

        String response = "";
        for (int i = 0; i < RCConstants.RC_OPEN_RELAY_RESPONSE_SIZE; i++) {
            String hexStr = String.format("%02X", bytes[i]);
            System.out.println("byte index " + i + " value " + hexStr);
            response += hexStr;
        }
        System.out.println("Response: " + response);

        if (bytes.length < RCConstants.RC_OPEN_RELAY_RESPONSE_SIZE) {
            return false;
        }

        //get the crc16
        String resCrc16 = String.format("%02X", bytes[RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE - 2]) + String.format("%02X", bytes[RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE - 1]);
        String deviceAddressStr = String.format("%02X", bytes[0]);
        String registerAddressStr = String.format("%02X", bytes[3]);
        System.out.println("OpenRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr);
        if (crc16.equalsIgnoreCase(crc16)) {
            System.out.println("OpenRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + " CRC16 TEST PASSED!");
            if (DeviceConfigs.RELAY_CONTROLLER_ID_HEX.equalsIgnoreCase(deviceAddressStr)) {
                String registerAddressDecStr = Integer.parseInt(registerAddressStr, 16) + "";
                if (addressOffset0.equalsIgnoreCase(registerAddressDecStr)) {
                    System.out.println("OpenRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + " REGISTER ADDRESS MATCH PASSED!");
                    return true;
                } else {
                    System.out.println("OpenRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + "|registerOffset: " + addressOffset0 + "REGISTER ADDRESS MATCH FAILED!");
                }
            }
        }
        System.out.println("OpenRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + "|status: " + status + " COMPLETED");
        return false;
    }

    public boolean closeRelay(DeviceType deviceType, String deviceAddress, String comPort, String baudrate, String
            addressOffset0, boolean status, int timeOut, int retries) {
        int addressOffsetInt = -1;
        boolean controlStatus = false;
        try {
            addressOffsetInt = Integer.parseInt(addressOffset0);
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }
        String deviceAddressHex = Integer.toHexString(addressOffsetInt);
        String crc16 = "";
        System.out.println("CloseRelay|deviceType: " + deviceType + "|deviceAddress: " + deviceAddress + "|portName: " + comPort + "|baudRate: " + baudrate + "|status: " + status + "|timeout: " + timeOut + "|retries: " + retries);

        switch (addressOffsetInt) {
            case 0:
                crc16 = RCConstants.RC_CLOSE_RELAY_1_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "010500000000" + crc16;
                break;
            case 1:
                crc16 = RCConstants.RC_CLOSE_RELAY_2_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "010500010000" + crc16;
                break;
            case 2:
                crc16 = RCConstants.RC_CLOSE_RELAY_3_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "010500020000" + crc16;
                break;
            case 3:
                crc16 = RCConstants.RC_CLOSE_RELAY_4_REQUEST_CRC16;
                modbusControlSingleRelayRequest = "010500030000" + crc16;
                break;
            case 4:
                crc16 = RCConstants.RC_CLOSE_RELAY_5_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            case 5:
                crc16 = RCConstants.RC_CLOSE_RELAY_6_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            case 6:
                crc16 = RCConstants.RC_CLOSE_RELAY_7_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            case 7:
                crc16 = RCConstants.RC_CLOSE_RELAY_8_REQUEST_CRC16;
                modbusControlSingleRelayRequest = " " + crc16;
                break;
            default:
        }

        System.out.println("CloseRelay|deviceType: " + deviceType + "|modbusControlSingleRelayRequest => " + modbusControlSingleRelayRequest + "|deviceAddress: " + deviceAddress + "|portName: " + comPort + "|baudRate: " + baudrate + "|status: " + status + "|timeout: " + timeOut + "|retries: " + retries);
        int maxRetry = 2;
        SerialPort serialPort = null;
        try {
            serialPort = OutputSerialWrite.write(modbusControlSingleRelayRequest, comPort);
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        if (serialPort == null) {
            return false;
        }

        try {
            Thread.sleep(200);
            bytes = new InputSerialRead().getResponse(serialPort);
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
        try {
            serialPort.close();
        } catch (Exception ex) {
            System.out.println("error closing port: " + serialPort);
        }

        String response = "";
        for (int i = 0; i < RCConstants.RC_OPEN_RELAY_RESPONSE_SIZE; i++) {
            String hexStr = String.format("%02X", bytes[i]);
            System.out.println("byte index " + i + " value " + hexStr);
            response += hexStr;
        }
        System.out.println("Response: " + response);

        if (bytes.length < RCConstants.RC_OPEN_RELAY_RESPONSE_SIZE) {
            return false;
        }

        //get the crc16
        String resCrc16 = String.format("%02X", bytes[RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE - 2]) + String.format("%02X", bytes[RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE - 1]);
        String deviceAddressStr = String.format("%02X", bytes[0]);
        String registerAddressStr = String.format("%02X", bytes[3]);
        registerAddressStr = registerAddressStr.length() < 2 ? "0" + registerAddressStr : registerAddressStr;

        System.out.println("CloseRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr);
        if (crc16.equalsIgnoreCase(crc16)) {
            System.out.println("CloseRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + " CRC16 TEST PASSED!");
            if (DeviceConfigs.RELAY_CONTROLLER_ID_HEX.equalsIgnoreCase(deviceAddressStr)) {
                System.out.println("CloseRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + " DEVICE ADDRESS MATCH PASSED!");
                String registerAddressDecStr = Integer.parseInt(registerAddressStr, 16) + "";
                if (addressOffset0.equalsIgnoreCase(registerAddressDecStr)) {
                    System.out.println("CloseRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + " REGISTER ADDRESS MATCH PASSED!");
                    return true;
                } else {
                    System.out.println("CloseRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + "|registerOffset: " + addressOffset0 + "REGISTER ADDRESS MATCH FAILED!");
                }
            }
        }
        System.out.println("CloseRelay|RequestCRC16: " + crc16 + "|ResponseCRC16: " + resCrc16 + "|deviceAddress: " + deviceAddress + "|registerAddressStr: " + registerAddressStr + "|status: " + status + " COMPLETED");
        return false;
    }

}
