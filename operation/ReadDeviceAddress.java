package agigitatorsystem.util.rtu.operation;


import agigitatorsystem.util.rtu.constants.DeviceConfigs;
import agigitatorsystem.util.rtu.constants.RCConstants;
import agigitatorsystem.util.rtu.enums.DeviceType;
import agigitatorsystem.util.rtu.serialport.InputSerialRead;
import agigitatorsystem.util.rtu.serialport.OutputSerialWrite;

import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import java.io.IOException;

public class ReadDeviceAddress {
    String readRcDeviceAddressRequest = "000340000001" + RCConstants.RC_READ_DEVICE_ADDRESS_REQUEST_CRC16;
    byte[] bytes = new byte[20];

    public boolean readDeviceAddress(DeviceType deviceType, String comPort, int deviceAddress) throws PortInUseException, IOException {
        String deviceAddressHex = Integer.toHexString(deviceAddress);
        System.out.println("readRcDeviceAddressRequest => " + readRcDeviceAddressRequest + "|comPort: " + comPort + "|deviceAddress: " + deviceAddress + "|deviceAddressHex: " + deviceAddressHex);
        switch (deviceType) {
            case RELAY_CONTROLLER:
                int maxRetry = 2;
                SerialPort serialPort = null;
                try {
                    serialPort = OutputSerialWrite.write(readRcDeviceAddressRequest, comPort);
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                    throw ex;
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
                for (int i = 0; i < RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE; i++) {
                    String hexStr = String.format("%02X", bytes[i]);
                    System.out.println("byte index " + i + " value " + hexStr);
                    response += hexStr;
                }
                System.out.println("Response: " + response);

                if (bytes.length < RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE) {
                    return false;
                }

                //get the crc16
                String crc16 = String.format("%02X", bytes[RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE - 2]) + String.format("%02X", bytes[RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_SIZE - 1]);
                String deviceAddressStr = String.format("%02X", bytes[3]);
                System.out.println("RequestCRC16: " + RCConstants.RC_READ_DEVICE_ADDRESS_REQUEST_CRC16 + "|ResponseCRC16: " + crc16 + "|deviceAddress: " + deviceAddress);
                if (RCConstants.RC_READ_DEVICE_ADDRESS_RESPONSE_CRC16.equalsIgnoreCase(crc16)) {
                    if (DeviceConfigs.RELAY_CONTROLLER_ID_HEX.equalsIgnoreCase(deviceAddressStr)) {
                        return true;
                    }
                }
                return false;
            case SWG:
                break;
        }
        return false;
    }
}
