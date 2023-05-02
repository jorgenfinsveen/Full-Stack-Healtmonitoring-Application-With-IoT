### How to start this application

1. Make sure that the java-server is running (RaspberryPiServer.java) and take note of the IP address of the computer which is running this software.
2. Go to /Bridge/ ___main ___.py
   1. Change the value of `<var>THIS_HOST</var> to the IP address of this computer.`
   2. Change the value of `<var></var> to the IP address of the computer running the java-server.`
   3. Run the file.
3. Go to /data/DummySensor.py
   1. Set IP address in the tuple `<var>address</var> to the IP address of the device which is running the Bridge program.`
   2. Run this file.
4. Login to https://finsveen.dev
   1. User ID: 900
   2. Password: ExaminatorNTNU
