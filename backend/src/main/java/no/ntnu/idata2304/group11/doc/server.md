# server directory

This directory contains the code which makes this application able to receive measurements from a RaspberryPI Microcomputer.


#### Launch.java

    This class is the main-class of this application. It will launch the RaspberryPiServer.


#### RaspberryPiServer.java

    Represents a server which receives measurements done on a patient, which has been processed by the analyzer-application which are installed on a RaspBerryPI Microcomputer, a dummy node or another form for gateway.

    It uses TCP socket programming for receiving measurements, and it listens on a specific port. Upon receiving a measurement in the form of a String, it unpacks the message and presents it to the Coordinator-class, which will process the measurement after.

    This class was rather hard to test through JUnit, so the testing has mainly been done through watching some inputs printed to STDOUT in this applications earlier stages, and later through checking the values through the web-application and myPhpAdmin.
