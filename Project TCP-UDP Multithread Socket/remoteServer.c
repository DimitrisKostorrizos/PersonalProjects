#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <sys/un.h>

#define PIPE_BUF 100 //Char size in c = 1 byte
static int parseStatus = 0;


int validateCommand(char* inputCommand, char** validCommands);
int parseCommand(char* command, FILE* OutputFilename);
void readSignal(int sig);

int main(int argc, char **argv)
{
	int NUMCHILDREN;
	int portNumber;
	if ( argc != 3 )
	{
		fprintf(stderr, "Enter exactly 2 arguments.\n");
        exit(1);
	}
	else
	{
		portNumber = atoi(argv[1]);
		if (portNumber < 2000 || portNumber > 65535) //65535 is the highest TCP Port Number
		{
			fprintf(stderr, "portNumber has to be an integer between 0 and 65535.\n");
			exit(1);
		}
		NUMCHILDREN = atoi(argv[2]);
		if (NUMCHILDREN < 1)
		{
			fprintf(stderr, "NUMCHILDREN has to be an integer, at least 1.\n");
			exit(1);
		}
	}
	
	
	char* TCPSocketPathname = "remoteServer"; //Server's filename
    unlink(TCPSocketPathname); //Unlink previous socket, if exists
	int TCPSocketFileDescriptor; //TCP socket
	if((TCPSocketFileDescriptor = socket(AF_UNIX, SOCK_STREAM, 0)) == -1)//TCP socket Creation
	{
		fprintf(stderr, "TCC socket creation failed.\n");
		exit(1);
	}
	
	
	struct sockaddr_un TCPSocketAddress; //Struct for TCP socket address
	memset(&TCPSocketAddress, 0, sizeof(struct sockaddr_un)); //Reset struct values to 0
	TCPSocketAddress.sun_family = AF_UNIX; //Local UNIX filename connection
	strncpy(TCPSocketAddress.sun_path, TCPSocketPathname, sizeof(TCPSocketAddress.sun_path) - 1); //Set socket pathname
	
    if (bind(TCPSocketFileDescriptor, (const struct sockaddr*)&TCPSocketAddress, sizeof(struct sockaddr_un)) == -1) //Bind TCP socket  
    { 
        fprintf(stderr, "TCP socket failed to bind to the %d portNumber.\n",portNumber);
        exit(1); 
    }
	
	
	if ((listen(TCPSocketFileDescriptor, 3)) == -1) //Listening for TCP connection requests.
    { 
        fprintf(stderr, "TCP server failed listening for client requests.\n");
        exit(1); 
    }
	
	char TCPbuffer[PIPE_BUF]; //TCP buffer
	int commandPipe[2]; //Pipe used for transfering commands,read[0] and write[1]
	pipe(commandPipe); //Create pipe
	pid_t* pid = (pid_t*)malloc(sizeof(pid_t) * NUMCHILDREN); //Allocate the necessary memory
	int lineCounter = 1;
	char c[4]; //Char representasion of linecounter
	int index = 0;
	char inputCommand[100];	
	pid_t father = getpid();
	int TCPSocket;
	
	while(index < NUMCHILDREN && father == getpid())
	{
		pid[index] = fork(); //Create children processes
		index++;
	}
	signal(SIGPIPE, SIG_IGN); //Ignore SIGPIPE signal
	
	
	while(1)
	{	
		if (getpid() != father) //Code for Child processes
		{
			close(commandPipe[1]); //Close writing end
			read(commandPipe[0], inputCommand, 100); //Read command
			close(commandPipe[0]); //Close reading end
			close(commandPipe[1]); //Close writing end
			read(commandPipe[0], c, 4); //Read lineCounter
			close(commandPipe[0]); //Close reading end
			FILE* outputFile;
			char OutputFilename[13];
			strcpy(OutputFilename,"output.");
			strcat(OutputFilename, c);
			strcat(OutputFilename, ".txt"); //Concatenate the output filename
			outputFile = fopen(OutputFilename, "w");
			parseStatus = parseCommand(inputCommand, outputFile); //Parse the command
			if(parseStatus == -1) //Error in command
			{
				fprintf(stderr, "Invalid command in process:%d\n",getpid());
			}
			fclose(outputFile);
			if(parseStatus == 2 || parseStatus == 1) //End or timeToStop found
			{
				signal(SIGUSR1,readSignal);
				exit(0);	
			}
		}
		else //Code for Father process
		{
			fprintf(stderr,"TCP socket is ready to accept a client request.\n");
			if ((TCPSocket = accept(TCPSocketFileDescriptor, NULL, NULL)) == -1) //Accepting TCP requests from the clients
			{ 
				fprintf(stderr, "TCP server failed accepting a client request.\n");
				continue;
			}
			read(TCPSocket, inputCommand, 100); //Receive a TCP message
			
			close(commandPipe[0]);
			write(commandPipe[1], inputCommand, 100);
			close(commandPipe[1]);		
			close(commandPipe[0]);
			sprintf(c, "%d", lineCounter); //line counter to char conversion
			write(commandPipe[1], c, 4);
			close(commandPipe[1]);
			lineCounter++;
			
	
			signal(SIGQUIT, SIG_IGN); //Signal all children to finish
			kill(-father, SIGQUIT); //Kill all chldren
			pid_t processID;
			for (index = 0; index < NUMCHILDREN; index++)
			{
				processID = wait(&pid[index]); //Wait each children
				fprintf(stderr, "Child Process with ID:%d, exited.\n",processID);
			}
			break;
		}
	}
	close(TCPSocket); //Close TCP connection
	close(TCPSocketFileDescriptor); //Close TCP socket
	unlink(TCPSocketPathname); //Unlink TCP socket, for next use
	fprintf(stderr, "Father Process with ID:%d, exited.\n",father);
	exit(0);
}

int validateCommand(char* inputCommand, char** validCommands) //Function that validates a command
{
    for (int index = 0; index < 5; index++)
    {
        if (strcmp(validCommands[index], inputCommand) == 0)
        {
            return 1;
        }
    }
    return 0;
}

void readSignal(int sig)
{
    if (sig == SIGUSR1)
    {
        parseStatus = 1;
    }
	parseStatus = 0;
}

int parseCommand(char* str, FILE* OutputFilename) //Function that parses an input
{
    char* commands[5] = { "ls","cat","cut","grep","tr" };
    char* command = strdup(str); //Copy the input
    char* token = strtok(command, " ");
    int parsingFlag = 0;
    int pipelinePrinting = 0; //Flag used to print pipelining
    while (token != NULL && parsingFlag == 0)
    {
        if (validateCommand(token, commands) == 1)
        {
            fprintf(OutputFilename,"%s", token); //Write to file
            token = strtok(NULL, " ");
            while (token != NULL && parsingFlag == 0)
            {
                if (token[0] == '|')
                {
                    if (strlen(token) != 1)
                    {
                        if (validateCommand(&token[1], commands) == 1)
                        {
                            pipelinePrinting = 1;
                            token = &token[1];
                        }
                        else
                        {
                            parsingFlag = 1;
                        }
                    }
                    else
                    {
                        token = strtok(NULL, " ");
                        if (validateCommand(token, commands) == 1)
                        {
                            pipelinePrinting = 1;
                        }
                        else
                        {
                            parsingFlag = 1;
                        }
                    }
                }
                if (pipelinePrinting == 1)
                {
                    fprintf(OutputFilename, " | %s", token);
                    pipelinePrinting = 0;
                }
                else
                {
                    if (parsingFlag == 0)
                    {
                        fprintf(OutputFilename, " %s", token);
                    }
                }
				
				if(strcmp(token,"end") == 0)
				{
					parsingFlag = 1;
					return 1;
				}
				else
				{
					if(strcmp(token,"timeToStop") == 0)
					{
						parsingFlag = 1;
						return 2;
					}
				}
				if (token[strlen(token) - 1] == ';')
				{
					parsingFlag = 1;
				}
                token = strtok(NULL, " ");
            }
        }
        else
        {
			if(strcmp(token,"end") == 0)
			{
				return 1;
			}
			else
			{
				if(strcmp(token,"timeToStop") == 0)
				{
					return 2;
				}
				else
				{
					parsingFlag = 1;
					return -1;
				}
			}
        }
    }
	return 0;
}