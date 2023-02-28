package org.example;

import gnu.io.SerialPort;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.Register;
import net.wimpi.modbus.util.SerialParameters;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        String portname = "/dev/ttyS0";
        int baudrate = 9600;
        int databits = 8;
        int stopbits = 1;
        int parity = SerialPort.PARITY_NONE;
        SerialParameters params = new SerialParameters();
        SerialConnection connection = new SerialConnection(params);
        params.setPortName(portname);
        params.setBaudRate(9600);
        params.setDatabits(8);
        params.setParity(SerialPort.PARITY_NONE);
        params.setStopbits(1);
        params.setEncoding(Modbus.SERIAL_ENCODING_RTU);
        params.setEcho(false);
        connection = new SerialConnection(params);
        connection.open();

        ModbusSerialTransaction transaction = new ModbusSerialTransaction(connection);
        int slaveId = 1; // The slave ID of the Modbus device
        int startAddress = 256; // The starting address of the data to read
        int numberOfRegisters = 1; // The number of registers to read
        ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(startAddress, numberOfRegisters);
        request.setUnitID(slaveId);

        transaction.setRequest(request);
        transaction.execute();
        ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();
        int temperature = response.getRegisterValue(0);
        System.out.println(temperature);


    }
}