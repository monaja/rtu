package agigitatorsystem.util.rtu.serialport;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class InputSerialRead {
    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    static SerialPort serialPort;

    public byte[] getResponse(SerialPort serialPort1) {
        System.out.println("Serialport open: " + serialPort1.getName());
        serialPort = serialPort1;

        byte[] readBuffer = new byte[20];
        System.out.println("Data available: " + readBuffer.length + " serialPort: " + serialPort.getName());

        try {
            inputStream = serialPort.getInputStream();
            while (inputStream.available() > 0) {
                int numBytes = inputStream.read(readBuffer);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        //close();
        return readBuffer;
    }

    public static SerialPort getSerialPort() {
        return serialPort;
    }

    public void close() {
        if (serialPort != null) {
            serialPort.close();
        }
    }
}
