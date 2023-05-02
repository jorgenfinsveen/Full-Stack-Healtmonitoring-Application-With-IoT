#!/usr/bin/python

import Server as Server

# Address of this application.
THIS_HOST = '10.24.88.135'
THIS_PORT = 9852


# Address of the main server.
SERVER_HOST = '10.24.88.135'
SERVER_PORT = 9235


# Booting the server.
server = Server.Server()
server.start()