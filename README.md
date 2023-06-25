# preemptive-priority-scheduling

preemptive priority-scheduling algorithm, which schedules  processes in order of priority and uses FCFS scheduling for processes with the same priority.
Priorities range from 1 to 5, where a lower numeric value indicates a higher relative priority. Assume the overhead 
of the Context Switch (CS) is 1 ms. Your program should show the following menu:
1. Enter process’ information.
2. Report detailed information about each process and different scheduling criteria.
3. Exit the program.

and also the program provides a report about processes and different scheduling criteria. The report should provide detailed information about each process, which includes [process ID, priority, arrival time, CPU burst time, starting and termination time, turn around time, waiting time, and response time] 
and also report [average turnaround time, average waiting time, and the average response time] of all the processes in the system.


when the program start:
1. If the user selects option 1, the user should be requested to enter the total number of processes in the system and then enters each processes’ information, namely, the process priority, the arrival time and the CPU burst time. 
2. If the user selects option 2, the program will display detailed information about each process in the system and different scheduling criteria on the console, and then write these information to the output file (Report.txt). 
3. If the user selects option 3, the user will exit from the program.


Example: 

Input:
![image](https://github.com/Lujain-M02/preemptive-priority-scheduling/assets/119123675/4f59bf81-37f6-42e3-9677-7449e8b0c482)


OutPut:
![image](https://github.com/Lujain-M02/preemptive-priority-scheduling/assets/119123675/24c485b1-be99-475e-bfcc-62cb9e66ca91)
