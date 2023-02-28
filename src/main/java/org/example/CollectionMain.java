package org.example;

import java.util.Arrays;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsRequest;
import com.serotonin.modbus4j.msg.ReadDiscreteInputsResponse;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersResponse;
import com.serotonin.modbus4j.serial.SerialPortWrapper;

/**
 * 通过串口解析MODBUS协议
 * @author yaohj
 */
public class CollectionMain {
    // 设定MODBUS网络上从站地址
    private final static int SLAVE_ADDRESS = 1;
    //串行波特率
    private final static int BAUD_RATE = 115200;

    public static void main(String[] args) {
//        SerialPortWrapper serialParameters = new
//                SerialPortWrapperImpl("/dev/ttyS2", BAUD_RATE, 8, 1, 0, 0, 0);
//        /* 创建ModbusFactory工厂实例 */
//        ModbusFactory modbusFactory = new ModbusFactory();
//        /* 创建ModbusMaster实例 */
//        ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);
//        /* 初始化 */
//        try {
//            master.init();
//            readHoldingRegistersTest(master, SLAVE_ADDRESS, 256, 1);
//        } catch (ModbusInitException e) {
//            e.printStackTrace();
//        } finally {
//            master.destroy();
//        }

        SerialPortWrapper serialParameters = new
                SerialPortWrapperImpl("/dev/ttyS0", BAUD_RATE, 8, 1, 0, 0, 0);
        /* 创建ModbusFactory工厂实例 */
        ModbusFactory modbusFactory = new ModbusFactory();
        /* 创建ModbusMaster实例 */
        ModbusMaster master = modbusFactory.createRtuMaster(serialParameters);
        /* 初始化 */
        try {
            master.init();
            readDiscreteInputTest(master, SLAVE_ADDRESS, 0, 20);
        } catch (ModbusInitException e) {
            e.printStackTrace();
        } finally {
            master.destroy();
        }
    }

    /**
     * 读保持寄存器上的内容
     * @param master 主站
     * @param slaveId 从站地址
     * @param start 起始地址的偏移量
     * @param len 待读寄存器的个数
     */
    private static void readHoldingRegistersTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse)master.send(request);
            if (response.isException()) {
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            } else {
                System.out.println(Arrays.toString(response.getShortData()));
                short[] list = response.getShortData();
                for (int i = 0; i < list.length; i++) {
                    System.out.print(list[i] + " ");
                }
            }
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void readDiscreteInputTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, start, len);
            ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getBooleanData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

}

