#include<sys/socket.h>
#include<sys/types.h>
#include<sys/wait.h>
#include<arpa/inet.h>
#include<netinet/in.h>
#include<stdio.h>
#include<unistd.h>
#include<fcntl.h>
#include<netdb.h>
#include<unistd.h>
#include<errno.h>
#include<string.h>
#include<signal.h>


#define PORT1 8000
#define PORT2 9000
#define BLOCK(x) fcntl(x,F_GETFL,O_NONBLOCK)

int timeleft;

struct sockaddr_in create_sockaddr(int port, char IPaddress[]) {
	struct sockaddr_in addr;
	struct hostent *host;
	host = gethostbyname(IPaddress);
	
	addr.sin_family = AF_INET;
	addr.sin_addr = *((struct in_addr *)host->h_addr);
	addr.sin_port = htons(port);
	bzero(&(addr.sin_zero), 8);

	return addr;
}

void timeout(int signo) {
	if(signo == SIGALRM) {
		timeleft=0;
	}
}

int create_connection(int sockfd, struct sockaddr_in addr) {
	timeleft = 1;
	signal(SIGALRM, timeout);
	alarm(1);
	
	printf("trying to connect ... \n");
	while(timeleft && 
		connect(sockfd, (struct sockaddr *)&addr, sizeof(struct sockaddr)) == -1);
	alarm(0);

	
	if(timeleft == 1) {
		printf("Connection Succesful\n");
		return 0;
	} else {
		printf("Connection Failed\n");
		return -1;
	}
}
int dup_fd_sock(int fd, int port, char IPaddress[]) {
	int sock;
	struct sockaddr_in sockadd;
	
	sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	sockadd = create_sockaddr(port, IPaddress);
	
	if(create_connection(sock, sockadd) == -1) {
		perror("Connection Failed");
		return -1;
	}

	dup2(sock, fd);
	close(sock);
	return 0;
}
int new_player(int port, char *player, char IPaddress[]) {
	
	if(dup_fd_sock(STDIN_FILENO, port, IPaddress) != -1 ) {
		dup_fd_sock(STDOUT_FILENO, port + 1, IPaddress);
		execlp(player, player, NULL);
		return 0;
	}
	return -1;
}
int main(int argc, char *argv[])
{
	if(argc!=3)
	{
		printf("Usage : <./client> <player> <server_IP_address>\n");
		return 0;
	}

	int child_pid, status;
	if((child_pid = fork()) == 0) {
		if(new_player(PORT1, argv[1], argv[2]) == -1 ) {
			if(new_player(PORT2, argv[1], argv[2]) == -1) {
				printf("Slots filled try Again later....\n");
			}
		}
	}
	
	child_pid = wait(&status);
	printf("Returned  Process ID : %d\n", child_pid);
	printf("Exit Status : %d\n", WIFEXITED(status));
	return 0;
}
