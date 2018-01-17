package com.avikenz.ba.picontrol.control.management;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.avikenz.ba.picontrol.control.ButtonControl;
import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.SignalType;
import com.avikenz.ba.picontrol.view.Editable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AviKenz on 1/6/2018.
 * Class containings all data needed by a controller
 * Its also contain info about the GPIO
 * e.g how many are avaible and which can be set as GPIO or PWM
 * and things like this haha... i have kein Inspiration mehr :)
 */

public class ControlManager
        extends Application
        implements Editable {

    private static ControlManager mControlManager = new ControlManager();

    public static String KEY_SERVER_URL = "server_url";
    public static String KEY_NUMBERING_MODE = "numbering_mode";

    // Define how many controls can access a single port simultanously
    public int mPortsUsableTime = 1;

    private String mServerUrl;
    private Mode mMode;

    private ArrayList<Port> mPortList;
    private ArrayList<GPIOPort> mGpioPortList;
    private ArrayList<GPIOPort> mFreeGpioPortList;
    private ArrayList<PowerPort> mPowerPortList;
    private ArrayList<GroundPort> mGroundPortList;

    private ArrayList<SignalType> mGpioSupportedSignals;
    
    private ControlManager() {
        init();
    }

    public static ControlManager getInstace() {
        return mControlManager;
    }


    private void init() {
        mGpioSupportedSignals = generateGpioSupportedSignals();
        mPortList = generatePortList();
        mGpioPortList = portFilter(PortType.GPIO);
        mFreeGpioPortList = mGpioPortList;
        mPowerPortList = portFilter(PortType.Power5V);
        mGroundPortList = portFilter(PortType.GROUND);
    }

    private <T> ArrayList<T> portFilter(PortType pPortType) {
        ArrayList<T> result = new ArrayList<>();
        for(Port item : mPortList) {
            if(item.getType().toString().equals(pPortType.getName())) {
                result.add((T)item);
            }
        }
        return result;
    }

    private <T> int findPortByNumber(int pPortNumber, ArrayList<T> pPortList) {
        // Error
        int result = -1;
        int len = pPortList.size();
        for(int i = 0; i < len; i++) {
            if(((Port) pPortList.get(i)).getPinNumber() == pPortNumber) {
                result = i;
            }
        }
        return result;
    }

    protected ArrayList<SignalType> generateGpioSupportedSignals() {
        return new ArrayList<SignalType>() {{
            add(SignalType.DC);
            add(SignalType.PWM);
            add(SignalType.I2C);
            add(SignalType.SPI);
            add(SignalType.UART);
        }};
    }


    protected ArrayList<Port> generatePortList() {
        ArrayList<Port> list = new ArrayList<>();
        // ground ports
        list.add(new GroundPort(6, null));
        list.add(new GroundPort(9, null));
        list.add(new GroundPort(14, null));
        list.add(new GroundPort(20, null));
        list.add(new GroundPort(25, null));
        list.add(new GroundPort(30, null));
        list.add(new GroundPort(34, null));
        list.add(new GroundPort(39, null));
        // power ports
        list.add(new PowerPort(PortType.Power3v3, 1, null));
        list.add(new PowerPort(PortType.Power5V, 2, null));
        list.add(new PowerPort(PortType.Power5V, 4, null));
        // GPIO ports
        list.add(new GPIOPort(2, 3, "{I2C1 SDA1}", mGpioSupportedSignals));
        list.add(new GPIOPort(3, 5, "{I2C1 SCL1}", mGpioSupportedSignals));
        list.add(new GPIOPort(4, 7, "{I2C1_GCLK0}", mGpioSupportedSignals));
        list.add(new GPIOPort(14, 8, "{TXD0}", mGpioSupportedSignals));
        list.add(new GPIOPort(15, 10, "{RXD0}", mGpioSupportedSignals));
        list.add(new GPIOPort(17, 11, "{GPIO_GEN0}", mGpioSupportedSignals));
        list.add(new GPIOPort(18, 12, "{PWM0}", mGpioSupportedSignals));
        list.add(new GPIOPort(27, 13, "{GPIO_GEN2}", mGpioSupportedSignals));
        list.add(new GPIOPort(22, 15, "{GPIO_GEN3}", mGpioSupportedSignals));
        list.add(new GPIOPort(23, 16, "{GPIO_GEN_4}", mGpioSupportedSignals));
        list.add(new GPIOPort(24, 18, "{GPIO_GEN_5}", mGpioSupportedSignals));
        list.add(new GPIOPort(10, 19, "{SPIO_MOSI}", mGpioSupportedSignals));
        list.add(new GPIOPort(9, 21, "{SPIO_MISO}", mGpioSupportedSignals));
        list.add(new GPIOPort(25, 22, "{GPIO_GEN_6}", mGpioSupportedSignals));
        list.add(new GPIOPort(11, 23, "{SPIO_CLK}", mGpioSupportedSignals));
        list.add(new GPIOPort(8, 24, "{SPI_CE0_N}", mGpioSupportedSignals));
        list.add(new GPIOPort(7, 26, "{SPI_CE1_N}", mGpioSupportedSignals));
        list.add(new GPIOPort(0, 27, "{ID_SD}", mGpioSupportedSignals));
        list.add(new GPIOPort(1, 28, "{ID_SC}", mGpioSupportedSignals));
        list.add(new GPIOPort(5, 29, "{}", mGpioSupportedSignals));
        list.add(new GPIOPort(6, 31, "{}", mGpioSupportedSignals));
        list.add(new GPIOPort(12, 32, "{PWM0}", mGpioSupportedSignals));
        list.add(new GPIOPort(13, 33, "{PWM1}", mGpioSupportedSignals));
        list.add(new GPIOPort(19, 35, "{SPI_MISO}", mGpioSupportedSignals));
        list.add(new GPIOPort(16, 36, "{}", mGpioSupportedSignals));
        list.add(new GPIOPort(26, 37, "{}", mGpioSupportedSignals));
        list.add(new GPIOPort(20, 38, "{SPI_MOSI}", mGpioSupportedSignals));
        list.add(new GPIOPort(40, 21, "{SPI_SCLK}", mGpioSupportedSignals));
        return list;
    }

    public void occupyGpioPort(int pPortNumber) {
        // remove port from free port list
        int index = findPortByNumber(pPortNumber, mFreeGpioPortList);
        if(index == -1) {
            // TODO [M] port not found; handle error
        } else {
            GPIOPort port = mFreeGpioPortList.get(index);
            // update the Port usable time
            port.updateAvaibility();
            // remove the port form free port list only when the usable time of port is 0.
            if( !port.isAvaible() ) {
                mFreeGpioPortList.remove(index);
            } else {
                // TODO [L] print something nice like how many time the port can be use again
            }
        }
    }

    public void releaseGPIOPort(int pPortNumber) {
        int index = findPortByNumber(pPortNumber, mGpioPortList);
        if(index == -1) {
            // TODO [M] port not found; handle error
        } else {
            mFreeGpioPortList.add(mGpioPortList.get(index));
        }
    }


    public  void setMode(Mode pMode) {
        mMode = pMode;
        Log.d("ControlManager", "mode Setteddddddd. Value: " + mMode.getName());
    }

    public void setServerUrl(String pUrl) {
        mServerUrl = pUrl;
    }


    public  String getServerUrl() {
        if (mServerUrl == null) {
            // TODO [M] handle Exception
        }
        return mServerUrl;
    }

    public  Mode getMode() {
        if(mMode == null) {
            // TODO [M] handle Exception
            Log.e("ERR", "MODE is null");
        }
        return mMode;
    }

    public ArrayList<Port> getPortList() {
        return mPortList;
    }

    public int getPortUsableTime() {
        return mPortsUsableTime;
    }

    public ArrayList<String> getFreeGpioPortNumberList() {
        ArrayList<String> result = new ArrayList<>();
        for(GPIOPort item : mFreeGpioPortList) {
            result.add(Integer.toString(item.getPinNumber()));
        }
        return result;
    }

    public ArrayList<GPIOPort> getFreeGpioPortList() {
        return mFreeGpioPortList;
    }

    public ArrayList<GPIOPort> getGpioPortList() {
        return mGpioPortList;
    }

    public ArrayList<PowerPort> getPowerPortList() {
        return mPowerPortList;
    }

    public ArrayList<GroundPort> getGroundPortList() {
        return mGroundPortList;
    }

    @Override
    public Class getClazz() {
        return getClass();
    }

    @Override
    public ContentValues getEditableFields() {
        ContentValues result = new ContentValues();
        result.put(KEY_SERVER_URL, String.class.getName());
        result.put(KEY_NUMBERING_MODE, Mode.class.getName());
        return result;
    }
}
