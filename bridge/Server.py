
import socket
import threading
import Process as Process
import __main__ as App


class Server (threading.Thread):
    '''
    Represents the server which a sensor is 
    connecting to when uploading new measurements.

    @author 
        jorgenfinsveen
    @version
        02-12-2022
    @since
        10-11-2022 
    '''

    def __init__(self):
        threading.Thread.__init__(self)
        print("\nbooting server...")


    def run(self):

        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.bind((App.THIS_HOST, App.THIS_PORT))
            print("active server listening: " + str(s.getsockname()))
            s.listen(10)

            while True:

                conn, addr = s.accept()
                with conn:
                    print(f"Connected by: {addr}\n")
                    while True:
                        data = conn.recv(8192)
                        if not data: break
                        data = str(data.decode('utf-8'))
                        print(data + "\n")
                        process = Process.Process(data)
                        process.start()
                        
            s.close()

