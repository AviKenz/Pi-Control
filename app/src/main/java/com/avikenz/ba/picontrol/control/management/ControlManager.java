package com.avikenz.ba.picontrol.control.management;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avikenz.ba.picontrol.control.ButtonControl;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.SignalType;
import com.avikenz.ba.picontrol.view.Generatable;

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
        extends Application {

    // Define how many controls cann access a single port simultanously
    public static int sPortsUsableTime = 1;


    private static String sServerUrl;
    private static Mode sMode;

    private static List<Port> sPortList = generatePortList();

    private  static ArrayList<SignalType> sGpioSupportedSignals = new ArrayList<SignalType>() {{
        add(SignalType.DC);
        add(SignalType.PWM);
        add(SignalType.I2C);
        add(SignalType.SPI);
        add(SignalType.UART);
    }};


    private static void init() {

    }

    public void setServerUrl(String pUrl) {
        sServerUrl = pUrl;
    }

    public static String getServerUrl() {
        if (sServerUrl == null) {
            // TODO [M] handle Exception
        }
        return sServerUrl;
    }

    public static void setMode(Mode pMode) {
        sMode = pMode;
    }

    public static Mode getMode() {
        if(sMode == null) {
            // TODO [M] handle Exception
            Log.e("ERR", "MODE is null");
        }
        return sMode;
    }

    public static List<Port> getPortList() {
        return sPortList;
    }

    public static ArrayList<Generatable> getGeneratableControls(Context pContext) {
        ArrayList<Generatable> result = new ArrayList<>();
        result.add(new SwitchControl(pContext, null));
        result.add(new ButtonControl(pContext, null));
        result.add(new PwmControl(pContext, null));
        return result;
    }

    private static List<Port> generatePortList() {
        List<Port> list = new ArrayList<>();
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
        list.add(new GPIOPort(2, 3, "{I2C1 SDA1}", sGpioSupportedSignals));
        list.add(new GPIOPort(3, 5, "{I2C1 SCL1}", sGpioSupportedSignals));
        list.add(new GPIOPort(4, 7, "{I2C1_GCLK0}", sGpioSupportedSignals));
        list.add(new GPIOPort(14, 8, "{TXD0}", sGpioSupportedSignals));
        list.add(new GPIOPort(15, 10, "{RXD0}", sGpioSupportedSignals));
        list.add(new GPIOPort(17, 11, "{GPIO_GEN0}", sGpioSupportedSignals));
        list.add(new GPIOPort(18, 12, "{PWM0}", sGpioSupportedSignals));
        list.add(new GPIOPort(27, 13, "{GPIO_GEN2}", sGpioSupportedSignals));
        list.add(new GPIOPort(22, 15, "{GPIO_GEN3}", sGpioSupportedSignals));
        list.add(new GPIOPort(23, 16, "{GPIO_GEN_4}", sGpioSupportedSignals));
        list.add(new GPIOPort(24, 18, "{GPIO_GEN_5}", sGpioSupportedSignals));
        list.add(new GPIOPort(10, 19, "{SPIO_MOSI}", sGpioSupportedSignals));
        list.add(new GPIOPort(9, 21, "{SPIO_MISO}", sGpioSupportedSignals));
        list.add(new GPIOPort(25, 22, "{GPIO_GEN_6}", sGpioSupportedSignals));
        list.add(new GPIOPort(11, 23, "{SPIO_CLK}", sGpioSupportedSignals));
        list.add(new GPIOPort(8, 24, "{SPI_CE0_N}", sGpioSupportedSignals));
        list.add(new GPIOPort(7, 26, "{SPI_CE1_N}", sGpioSupportedSignals));
        list.add(new GPIOPort(0, 27, "{ID_SD}", sGpioSupportedSignals));
        list.add(new GPIOPort(1, 28, "{ID_SC}", sGpioSupportedSignals));
        list.add(new GPIOPort(5, 29, "{}", sGpioSupportedSignals));
        list.add(new GPIOPort(6, 31, "{}", sGpioSupportedSignals));
        list.add(new GPIOPort(12, 32, "{PWM0}", sGpioSupportedSignals));
        list.add(new GPIOPort(13, 33, "{PWM1}", sGpioSupportedSignals));
        list.add(new GPIOPort(19, 35, "{SPI_MISO}", sGpioSupportedSignals));
        list.add(new GPIOPort(16, 36, "{}", sGpioSupportedSignals));
        list.add(new GPIOPort(26, 37, "{}", sGpioSupportedSignals));
        list.add(new GPIOPort(20, 38, "{SPI_MOSI}", sGpioSupportedSignals));
        list.add(new GPIOPort(40, 21, "{SPI_SCLK}", sGpioSupportedSignals));
        return list;
    }
}
