
import socket
import threading
import __main__ as App


class Process (threading.Thread):
    '''
    Represents the task of forwarding a
    received measurement to the main server.

    @author 
        jorgenfinsveen
    @version
        02-12-2022
    @since
        10-11-2022 
    '''

    def __init__(self, message):
        self.message = message
        threading.Thread.__init__(self)
        self._stop()


    def run(self):
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.connect((App.SERVER_HOST, App.SERVER_PORT))
            s.send(self.message.encode("utf-8"))

