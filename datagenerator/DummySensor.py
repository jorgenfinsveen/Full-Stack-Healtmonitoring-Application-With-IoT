import HealthDataGenerator
import socket
import time

port = 9852 
address = ('10.24.90.91',port)

""" 
Creates a socket and connects to specified adress 
"""
def connect():
    s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    s.connect(address)
    return s

"""
Sends data which are extracted from a data-generator
"""
def sendData(duration):
    
    print("Trying to establish connection...")
    
    connection = connect()
    
    print("Connection established")
    
    AllPatientData = HealthDataGenerator.generateRegisteredSensorData(duration)
        
    for data in AllPatientData:
        
        connection.send(data.encode("utf-8"))
        
        time.sleep(0.2) 

    print("Data delivered!")
               
        
      


