package org.example;


import java.net.*;
import java.io.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;
import net.wimpi.modbus.util.*;

public class SerialAITest {

    public static void main(String[] args) {
        try {
            /* The important instances of the classes mentioned before */
            SerialConnection con = null; //the connection
            ModbusSerialTransaction trans;
            ReadInputRegistersRequest req = null; //the request
            ReadInputRegistersResponse res = null; //the response

            /* Variables for storing the parameters */
            String portname= "/dev/ttyS2"; //the name of the serial port to be used
            int unitid = 1; //the unit identifier we will be talking to
            int ref = 256; //the reference, where to start reading from
            int count = 1; //the count of IR's to read
            int repeat = 1; //a loop for repeating the transaction



            //2. Set master identifier

            ModbusCoupler.getReference().setUnitID(1);

//3. Setup serial parameters
            SerialParameters params = new SerialParameters();
            params.setPortName(portname);
            params.setBaudRate(9600);
            params.setDatabits(8);
            params.setParity("None");
            params.setStopbits(1);

            params.setEcho(false);

            //4. Open the connection
            con = new SerialConnection(params);
            con.open();

//5. Prepare a request
            req = new ReadInputRegistersRequest(ref, count);
            req.setUnitID(unitid);
            req.setHeadless();

//6. Prepare a transaction
            trans = new ModbusSerialTransaction(con);
            trans.setRequest(req);

            //7. Execute the transaction repeat times
            int k = 0;
            do {
                trans.execute();
                res = (ReadInputRegistersResponse) trans.getResponse();
                for (int n = 0; n < res.getWordCount(); n++) {
                    System.out.println("Word " + n + "=" + res.getRegisterValue(n));
                }
                k++;
            } while (k < repeat);

//8. Close the connection
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//main

}//class SerialAITest
