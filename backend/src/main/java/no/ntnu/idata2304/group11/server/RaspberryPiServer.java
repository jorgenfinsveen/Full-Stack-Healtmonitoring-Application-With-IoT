package no.ntnu.idata2304.group11.server;

import java.io.*;  
import java.net.*;
import java.nio.charset.StandardCharsets;

import no.ntnu.idata2304.group11.app.Coordinator;  


/**
 * Receive data from the analyzer application.<p>
 * 
 * Acts as a TCP-server receiving data from a client.
 * Unpacks the data and does handles it to the next station.
 * 
 * @since    15-10-2022
 * @version  10-11-2022
 * @author   jorgenfinsveen
 */
public class RaspberryPiServer {
    
    /** Port-number which the server is listening on. */
    private static final int PORT = 9235;
    /** Constraint-variable for server activity time. */
    private boolean listening = true;


    /**
     * Thread running the server receiving measurements.
     */
    private final Thread SERVER_THREAD = new Thread() {
 
        @Override
        public void run() { 
            try { initConnectivity(); } 
            catch (IOException e) { e.printStackTrace(); } 
        }
    };


    private Thread getProcessThread(String data) {
        return new Thread() {

            @Override
            public void run() {
                Coordinator.receive(data);
            }
        };
    }



    public void run() {
        SERVER_THREAD.start();
    }



    /**
     * Initialize a listener on a specified port.
     * 
     * <p>Receive data and print it to STDOUT.
     */
    private void initConnectivity() throws IOException {

        /* Variables. */
        Socket socket;
        DataInputStream ds;
        String message;
        int counter = 0;
        int limit = 10000;
        ServerSocket serverSocket = new ServerSocket(PORT);


        while (listening) {

            /* Receive data from a client. */
            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress() + ":" + socket.getPort());
            /* Extract the DataInputStream from the socket. */
            ds = new DataInputStream(socket.getInputStream());
            /* Converts the DataInputStream to a String. */
            message = new String(ds.readAllBytes(), StandardCharsets.UTF_8);

            /* Send message to the coordinator. */
            Thread PROCESSOR_THREAD = getProcessThread(message);
            PROCESSOR_THREAD.start();
            try {
                PROCESSOR_THREAD.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            /* Manage server timeout condition. */
            counter++;
            if (counter >= limit) {
                listening = false;
            }
        }
        serverSocket.close();
    }
}