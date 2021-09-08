package agigitatorsystem.util.rtu;



import agigitatorsystem.util.rtu.enums.DeviceType;
import agigitatorsystem.util.rtu.operation.ControlSingleRelay;
import agigitatorsystem.util.rtu.operation.ReadAllStates;
import agigitatorsystem.util.rtu.operation.ReadDeviceAddress;

import javax.comm.PortInUseException;
import java.io.IOException;
import java.util.Map;

public class ModbusMaster {
    public boolean isActive(DeviceType deviceType, String comPort, int deviceAddress) throws PortInUseException, IOException {
        boolean controlStatus = false;

        switch (deviceType) {
            case RELAY_CONTROLLER:
                try {
                    controlStatus = new ReadDeviceAddress().readDeviceAddress(deviceType, comPort, deviceAddress);
                } catch (Exception ex) {
                    throw ex;
                }
        }
        return controlStatus;
    }

    public boolean controlRelay(DeviceType deviceType, String staticRelaySld, String staticRelayPortName, String staticRelayBaudrate, String addressOffset0, boolean status, int timeOut, int retries) {
        try {
            if (deviceType == DeviceType.RELAY_CONTROLLER) {
                if (status) {
                    return new ControlSingleRelay().openRelay(deviceType, staticRelaySld, staticRelayPortName, staticRelayBaudrate, addressOffset0, status, timeOut, retries);
                } else {
                    return new ControlSingleRelay().closeRelay(deviceType, staticRelaySld, staticRelayPortName, staticRelayBaudrate, addressOffset0, status, timeOut, retries);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error " + ex.getMessage());
        }

        //logics to retry..
        return false;
    }

    public Map<String, Boolean> getRelayStates(DeviceType deviceType, String comPort, String baudrate, String deviceAddress) {
        switch (deviceType) {
            case RELAY_CONTROLLER:
                return new ReadAllStates().getRelayStates(deviceType, comPort,baudrate, deviceAddress);
        }
        return null;
    }

    public boolean closeRelay() {
        return false;
    }
}
