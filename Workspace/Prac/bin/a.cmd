start java server -ORBInitialPort 1050 -ORBInitialHost localhost
timeout 5;
start java client -ORBInitialPort 1050 -ORBInitialHost localhost
timeout 5;