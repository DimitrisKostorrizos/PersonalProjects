#include <stdio.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stdlib.h>

   
int main(int argc, char const *argv[]) 
{
	char* serverName;
	int serverPort;
	int receivePort;
	char* inputFileWithCommands;
	if ( argc != 5 )
	{
		fprintf(stderr, "Enter exactly 4 arguments.\n");
        exit(1);
	}
	else
	{
		serverName = strdup(argv[1]);
		
		serverPort = atoi(argv[2]);
		if (serverPort < 2000 || serverPort > 65535) //65535 is the highest TCP Port Number
		{
			fprintf(stderr, "serverPort has to be an integer between 0 and 65535.\n");
			exit(1);
		}
		
		receivePort = atoi(argv[3]);
		if (receivePort < 2000 || receivePort > 65535) //65535 is the highest TCP Port Number
		{
			fprintf(stderr, "receivePort has to be an integer between 0 and 65535.\n");
			exit(1);
		}
		inputFileWithCommands = strdup(argv[4]);
	}
	
	
	int TCPSocketFileDescriptor; //TCP socket
	if((TCPSocketFileDescriptor = socket(AF_UNIX, SOCK_STREAM, 0)) == -1)//TCP socket Creation
	{
		fprintf(stderr, "TCP socket creation failed.\n");
		exit(1);
	}
	
	
	struct sockaddr_un TCPSocketAddress; //Struct for TCP socket address
	memset(&TCPSocketAddress, 0, sizeof(struct sockaddr_un)); //Reset struct values to 0
	TCPSocketAddress.sun_family = AF_UNIX; //Local UNIX filename connection
	strncpy(TCPSocketAddress.sun_path, serverName, sizeof(TCPSocketAddress.sun_path) - 1); //Set socket pathname


	if (connect(TCPSocketFileDescriptor, (const struct sockaddr*)&TCPSocketAddress, sizeof(struct sockaddr_un)) == -1) 
	{
		fprintf(stderr, "TCP socket connection failed.\n");
		exit(1);
    }
	fprintf(stderr, "TCP socket connection was successful.\n");
	
	
	FILE* inputFile;
	int eofFound = 0; //EndOfFile flag
	char lineCommand[100] = {0};
	int index = 0;
	int sentCounter = 1; //Counter for sent messages
	inputFile = fopen(inputFileWithCommands, "r");
	while(eofFound != EOF)
	{
		index = 0;
		while (index < 100 && (eofFound = fscanf(inputFile, "%c", &lineCommand[index])) != EOF) //Read 100 characters or until EndOfFile
		{
			index = index + 1;
		}
		char* inputCommand = strdup(lineCommand);
		write(TCPSocketFileDescriptor, inputCommand, strlen(inputCommand) + 1); //Sent TCP message
		memset(lineCommand, 0, 100); //reset lineCommand array to 0
		if((sentCounter % 10) == 0) //For every 10 messages,sleep for 5 secs
		{
			sleep(5); //Sleep for 5 seconds
		}
		sentCounter++;
	}
	fclose(inputFile); //Close input file
	
	close(TCPSocketFileDescriptor); //Close TCP socket
	exit(0);
} 