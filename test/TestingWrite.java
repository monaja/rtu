package agigitatorsystem.util.rtu.test;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

public class TestingWrite {
    static Enumeration portList;
    static CommPortIdentifier portId;

    static SerialPort serialPort;
    static OutputStream outputStream;

    public static SerialPort write(String messageString) {
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            System.out.println("list: " + portId.getName() + " type: " + portId.getPortType() + " owner: " + portId.getCurrentOwner());

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals("COM4")) {
                    System.out.println("ports equal");
                    //if (portId.getName().equals("/dev/term/a")) {
                    try {

                        serialPort = (SerialPort)
                                portId.open("AutomatedAgitator", 2000);
                        System.out.println("Serialport open: " + serialPort.getName());
                    } catch (PortInUseException e) {
                        e.printStackTrace();
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

                        byte[] bytes = new byte[messageString.length() / 2];

                        for (int i = 0; i < bytes.length; i++) {
                            int index = i * 2;
                            int value = Integer.parseInt(messageString.substring(index, index + 2), 16);
                            System.out.println("Value at index: " + i + " is " + value);
                            bytes[i] = (byte) value;
                        }
                        outputStream.write(bytes);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return serialPort;
    }
}
