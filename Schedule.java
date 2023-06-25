import java.io.*;
import java.util.*;

class Process {
    String processID;
    int priority;
    int arrivalTime;
    int cpuBurst;
    int startTime;
    int terminationTime;
    int turnaroundTime;
    int waitingTime;
    int responseTime;
    int remainingTime;

    public Process(String processID, int priority, int arrivalTime, int cpuBurst) {
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurst;
        this.remainingTime=cpuBurst;
    }

    @Override
    public String toString() {
        return processID + ":\npriority=" + priority + ", arrivalT=" + arrivalTime + ", cpuBurst=" + cpuBurst + ", startT=" + startTime + ", terminationT=" + terminationTime + ", TAT=" + turnaroundTime + ", waitT=" + waitingTime + ", responseT=" + responseTime +"\n";
    }
  
}

public class Schedule {
    static int time = 0;
    static int n;
    static Process[] processes;
    static String schedule = "";
    static int timeWithOutCS=0;
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. Enter process information");
            System.out.println("2. Generate report");
            System.out.println("3. Exit");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    CreateProcesses();
                    break;
                case 2:
                    scheduleProcesses();
                    Write();
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
    
    public static void CreateProcesses(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        n = sc.nextInt();
        processes = new Process[n];

        // Input the processes' information
        for (int i = 0; i < n; i++) {
            System.out.print("Enter the priority(1-5), arrival time, and CPU burst of process " + (i+1) + ": ");
            int priority = sc.nextInt();
            while(priority>6 || priority<1){
                System.out.println("reEnter the priority");
                priority = sc.nextInt();
            }
            int arrivalTime = sc.nextInt();
            int cpuBurst = sc.nextInt();
            processes[i] = new Process("P" + (i+1), priority, arrivalTime, cpuBurst);
        
        }
    }
    
    public static void scheduleProcesses() {
        int currentProcess = findMin();
        boolean CPUrelease =false;
        while (true) {
            int nextProcess = -1;
            int minPriority = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {//this for will choose the higher priority
                if (processes[i].arrivalTime <= time && processes[i].priority < minPriority && processes[i].remainingTime > 0) {
                    nextProcess = i;
                    minPriority = processes[i].priority;
                } 
            }
            
            if (nextProcess!=-1)//that mean not the last process in the cpu
            for (int i = 0; i < n; i++) {//to check from processes with the same priority  and different arrival time "FCFS"
                if (processes[i].priority ==processes[nextProcess].priority && processes[i].arrivalTime<processes[nextProcess].arrivalTime && processes[i].remainingTime>0){
                nextProcess=i;
                minPriority = processes[i].priority;
                }}
            
            /////new loop if there's a process arrive while the cpu release one   

            if (currentProcess != nextProcess)//this if means the cpu looking for next process
                for (int i = 0; i < n; i++) {//this loop is for the note (when cpu release a process.....) 
                    if (processes[i].arrivalTime == timeWithOutCS && processes[i].priority <= minPriority && processes[i].remainingTime > 0) {
                        nextProcess = i;
                        minPriority = processes[i].priority;
                        CPUrelease=true;
                    } 
                }
            
            if(CPUrelease){      
                if(currentProcess!=nextProcess && nextProcess!=-1 ){
                    if (processes[currentProcess].priority == processes[nextProcess].priority && processes[currentProcess].remainingTime>0 ){
                        nextProcess=currentProcess;
                    }
                }
            }
            
            //the above code is only for the schdelu order now proccessing them 
            if (nextProcess == -1) {//this if statment for the last process in the cpu
                if (currentProcess != -1) {
                    schedule += " | P" + (currentProcess+1);    
                }
                break;//this is the last process and the scheduling operation is over
            }
            
            //when process is in the cpu the current process will be equal to next process
            if (currentProcess != nextProcess) {
                if (currentProcess != -1) {
                    schedule += " | P" + (currentProcess+1);
                }
                schedule += " | CS";
                currentProcess = nextProcess;
                time++;
                if (processes[nextProcess].startTime ==0 && nextProcess!= findMin() )
                    processes[nextProcess].startTime = time;
            }
            time++;
            processes[nextProcess].remainingTime--;
            timeWithOutCS++;
            if (processes[nextProcess].remainingTime == 0) {
               processes[nextProcess].terminationTime = time;
               processes[nextProcess].turnaroundTime = processes[nextProcess].terminationTime - processes[nextProcess].arrivalTime;
               processes[nextProcess].waitingTime = processes[nextProcess].turnaroundTime - processes[nextProcess].cpuBurst;
               processes[nextProcess].responseTime = processes[nextProcess].startTime - processes[nextProcess].arrivalTime;
               }
           }
    }
    

    public static void Write() throws IOException {
        System.out.println("Scheduling order: " + schedule);
        for (int i = 0; i < n; i++) {
            System.out.print(processes[i]);
        }

        // Calculate and output the average turnaround time, waiting time, and response time
        double totalTurnaroundTime = 0;
        double totalWaitingTime = 0;
        double totalResponseTime = 0;
        for (int i = 0; i < n; i++) {
            totalTurnaroundTime += processes[i].turnaroundTime;
            totalWaitingTime += processes[i].waitingTime;
            totalResponseTime += processes[i].responseTime;
        }
        System.out.println("\nAverage turnaround time: " + (totalTurnaroundTime / n));
        System.out.println("Average waiting time: " + (totalWaitingTime / n));
        System.out.println("Average response time: " + (totalResponseTime / n));
        //System.out.println("finished"+finishedProcess);
        
        //write in file
        
        try {
            FileWriter writer = new FileWriter("Report.txt");
            writer.write("Scheduling order: " + schedule+"\n");
            for (int i = 0; i < n; i++) {
                writer.write(processes[i].toString());
            }
            writer.write("\nAverage turnaround time: " + (totalTurnaroundTime / n));
            writer.write("\nAverage waiting time: " + (totalWaitingTime / n));
            writer.write("\nAverage response time: " + (totalResponseTime / n));
            
            writer.close();
        } catch (IOException e) {
            System.out.println("error while writing");
        }
        
        
    }
    
    public static int findMin(){
        int minIndex =-1;
        int minAT = Integer.MAX_VALUE;//find minimum arriavl time 
        for (int i = 0; i < n; i++) {
            if (processes[i].arrivalTime < minAT ) {
                minIndex=i;
                minAT = processes[i].arrivalTime;
            }
        }        

            // here if two process have the same arrival time but different priority we will start wit the high priority
            int higherPriority =minIndex ;
            for (int i = minIndex+1; i < n; i++) {
                if (processes[minIndex].arrivalTime == processes[i].arrivalTime ) {
                    if (processes[minIndex].priority > processes[i].priority ){
                        higherPriority=i;
                    }
                }
            }
        return higherPriority;      
    }
    
    
    
}



