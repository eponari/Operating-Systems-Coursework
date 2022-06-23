import java.util.Scanner;

public class ContiguousMemoryAllocation {
    static class Process{
        int startLocation;
        int length;
        String name;
        Process nextProcess;

        public Process(int startLocation, int length, String name, Process nextProcess) {
            this.startLocation = startLocation;
            this.length = length;
            this.name = name;
            this.nextProcess = nextProcess;
        }
    }

    static Process head = null;
    static int totalMemory;

    static void insert(String name, int duration, String strategy){
        if(head==null){
            head = new Process(0, duration, name, null);
            return;
        }

        Process temp = head;
        int free;

        switch (strategy) {
            case "F" -> {
                if (head.startLocation > duration) {
                    head = new Process(0, duration, name, head);
                    return;
                }
                while (temp.nextProcess != null) {
                    if (temp.nextProcess.startLocation - temp.startLocation - temp.length > duration) {
                        temp.nextProcess = new Process(temp.startLocation + temp.length, duration, name, temp.nextProcess);
                        return;
                    }

                    temp = temp.nextProcess;
                }
                if (totalMemory - temp.startLocation - temp.length > duration) {
                    temp.nextProcess = new Process(temp.startLocation + temp.length, duration, name, null);
                }
            }
            case "B" -> {
                Process best = null;
                free = Integer.MAX_VALUE;
                if (head.startLocation > duration) {
                    free = head.startLocation - 1;
                }
                while (temp.nextProcess != null) {
                    if (temp.nextProcess.startLocation - temp.startLocation - temp.length > duration) {
                        if (free > temp.nextProcess.startLocation - temp.startLocation - temp.length) {
                            free = temp.nextProcess.startLocation - temp.startLocation - temp.length;
                            best = temp;
                        }
                    }

                    temp = temp.nextProcess;
                }

                if (totalMemory - temp.startLocation - temp.length > duration) {
                    if (free > totalMemory - temp.startLocation - temp.length) {
                        best = temp;
                    }
                }

                if (best != null) {
                    best.nextProcess = new Process(best.startLocation + best.length, duration, name, best.nextProcess);
                }
            }
            case "W" -> {
                Process worst = null;
                free = Integer.MIN_VALUE;
                if (head.startLocation > duration) {
                    free = head.startLocation - 1;
                }
                while (temp.nextProcess != null) {
                    if (temp.nextProcess.startLocation - temp.startLocation - temp.length > duration) {
                        if (free < temp.nextProcess.startLocation - temp.startLocation - temp.length) {
                            free = temp.nextProcess.startLocation - temp.startLocation - temp.length;
                            worst = temp;
                        }
                    }

                    temp = temp.nextProcess;
                }
                if (totalMemory - temp.startLocation - temp.length > duration) {
                    if (free < totalMemory - temp.startLocation - temp.length) {
                        worst = temp;
                    }
                }

                if (worst != null) {
                    worst.nextProcess = new Process(worst.startLocation + worst.length, duration, name, worst.nextProcess);
                }
            }
        }
    }

    static void compact(){
        Process temp = head;

        temp.startLocation = 0;

        while(temp.nextProcess!=null){
            temp.nextProcess.startLocation = temp.startLocation+ temp.length;
            temp=temp.nextProcess;
        }
    }

    static void release(String name){
        if(head.name.equals(name)){
            head = head.nextProcess;
            return;
        }

        Process temp = head;

        while (temp.nextProcess!=null){
            if(temp.nextProcess.name.equals(name)){
                temp.nextProcess = temp.nextProcess.nextProcess;
            }
            temp = temp.nextProcess;
        }
    }

    static void stats(){
        if(head.startLocation!=0){
            System.out.println("Addresses[0:"+(head.startLocation-1)+"] Unused");
        }

        Process temp = head;

        while (temp!=null){
            System.out.println("Addresses["+(temp.startLocation)+":"+(temp.length+temp.startLocation-1)+"] Process "+temp.name);

            if(temp.nextProcess==null){
                if(temp.length+temp.startLocation!=totalMemory){
                    System.out.println("Addresses["+(temp.length+temp.startLocation)+":"+(totalMemory)+"] Unused");
                }
                return;
            }

            if(temp.startLocation+temp.length != temp.nextProcess.startLocation){
                System.out.println("Addresses["+(temp.startLocation+temp.length+1)+":"+(temp.nextProcess.startLocation-1)+"] Unused");
            }

            temp=temp.nextProcess;
        }
    }

    public static void main(String[] args){
        totalMemory = Integer.parseInt(args[0]);

        Scanner input = new Scanner(System.in);

        String command = "";

        while(!command.equalsIgnoreCase("X")){
            System.out.print("allocator>");
            command = input.nextLine();

            String[] commands = command.split(" ");

            if(commands[0].equalsIgnoreCase("RQ")){
                insert(commands[1], Integer.parseInt(commands[2]),commands[3]);
            }
            else if(commands[0].equalsIgnoreCase("C")){
                compact();
            }else if(commands[0].equalsIgnoreCase("RL")){
                release(commands[1]);
            }
            else if(commands[0].equalsIgnoreCase("STAT")){
                stats();
            }
        }
    }
}
