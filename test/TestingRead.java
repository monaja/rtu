package agigitatorsystem.util.rtu.test;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class TestingRead {
    //implements Runnable {
    //        SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    static SerialPort serialPort;
    Thread readThread;

    public TestingRead() {
//        portList = CommPortIdentifier.getPortIdentifiers();
//
//        while (portList.hasMoreElements()) {
//            portId = (CommPortIdentifier) portList.nextElement();
//
//            System.out.println("list: " + portId.getName() + " type: " + portId.getPortType() + " owner: " + portId.getCurrentOwner());
//
//            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//                if (portId.getName().equals("COM4")) {
//                    //                if (portId.getName().equals("/dev/term/a")) {
//                    System.out.println("portId " + portId.getName() + " successfully matched.");
//                }
//            }
//
//            try {
//                serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
//                System.out.println("Serialport open: " + serialPort.getName());
//
//            } catch (PortInUseException e) {
//                System.out.println(e);
//            }
//            try {
//                inputStream = serialPort.getInputStream();
//            } catch (IOException e) {
//                System.out.println(e);
//            }
////            try {
////                serialPort.addEventListener(this);
////            } catch (TooManyListenersException e) {
////                System.out.println(e);
////            }
//            //     serialPort.notifyOnDataAvailable(true);
//            try {
//                serialPort.setSerialPortParams(9600,
//                        SerialPort.DATABITS_8,
//                        SerialPort.STOPBITS_1,
//                        SerialPort.PARITY_NONE);
//            } catch (UnsupportedCommOperationException e) {
//                System.out.println(e);
//            }
//        }
//        readThread = new Thread(this);
//        readThread.start();
    }

//    public void run() {
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
//    }

//    public void serialEvent(SerialPortEvent event) {
//        switch (event.getEventType()) {
//            case SerialPortEvent.BI:
//            case SerialPortEvent.OE:
//            case SerialPortEvent.FE:
//            case SerialPortEvent.PE:
//            case SerialPortEvent.CD:
//            case SerialPortEvent.CTS:
//            case SerialPortEvent.DSR:
//            case SerialPortEvent.RI:
//            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
//                break;
//            case SerialPortEvent.DATA_AVAILABLE:
//                byte[] readBuffer = new byte[20];
//                System.out.println("Data available: " + readBuffer.length);
//
//                try {
//                    while (inputStream.available() > 0) {
//                        int numBytes = inputStream.read(readBuffer);
//                    }
//                    System.out.print(new String(readBuffer));
//                } catch (IOException e) {
//                    System.out.println(e);
//                }
//                break;
//        }
//    }

    public byte[] getResponse(SerialPort serialPort1) {
        System.out.println("Serialport open: " + serialPort1.getName());
        serialPort = serialPort1;

        byte[] readBuffer = new byte[20];
        System.out.println("Data available: " + readBuffer.length + " serialPort: " + serialPort);

        try {
            inputStream = serialPort.getInputStream();
            while (inputStream.available() > 0) {
                int numBytes = inputStream.read(readBuffer);
            }
            System.out.print(new String(readBuffer));
        } catch (IOException e) {
            System.out.println(e);
        }
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