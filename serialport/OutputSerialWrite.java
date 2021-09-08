package agigitatorsystem.util.rtu.serialport;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class OutputSerialWrite {
    static Enumeration portList;
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    static OutputStream outputStream;

    public static SerialPort write(String modbusRtuRequest, String comPort) throws IOException, PortInUseException {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            System.out.println("list: " + portId.getName() + " type: " + portId.getPortType() + " owner: " + portId.getCurrentOwner());

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(comPort.trim())) {
                    System.out.println("ports equal|owner: " + portId.getCurrentOwner());
                    //if (portId.getName().equals("/dev/term/a")) {
                    try {
                        if (!"Port currently not owned".equalsIgnoreCase(portId.getCurrentOwner())) {
                            Thread.sleep(50);
                        }else {

                        }
                    } catch (Exception ex) {
                        System.out.println("failed to sleep thread. ");
                    }
                    try {

                        serialPort = (SerialPort)
                                portId.open("AutomatedAgitator", 2000);
                        System.out.println("Serialport open: " + serialPort.getName());
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                        throw e;
                    }
                    try {
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                    }

                    try {
                        serialPort.setSerialPortParams(9600,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        System.out.println("ports: " + serialPort.getName() + "|" + serialPort.getDataBits() + "|" +
                                serialPort.getStopBits() + "|" + serialPort.getParity() + "|" + serialPort.getParity()
                                + " params.");
                    } catch (UnsupportedCommOperationException e) {
                    }
                    try {

                        byte[] bytes = new byte[modbusRtuRequest.length() / 2];

                        String requestStr = "";

                        for (int i = 0; i < bytes.length; i++) {
                            int index = i * 2;
                            int value = Integer.parseInt(modbusRtuRequest.substring(index, index + 2), 16);
                            //System.out.println("Value at index: " + i + " is " + value);
                            requestStr += " " + value;

                            bytes[i] = (byte) value;
                        }
                        outputStream.write(bytes);

                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
        return serialPort;
    }
}
