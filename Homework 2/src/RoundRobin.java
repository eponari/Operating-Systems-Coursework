import java.util.ArrayList;
import java.util.Scanner;

public class RoundRobin {
    static int upperBoundArrival = 5;
    static int upperBoundDuration = 10;
    static int upperBoundTimeQuantum = 5;

    static class Task{
        Integer arrivalTime;
        Integer duration;
        Integer consumed;
        Integer startTime;
        Integer endTime;

        public Task(Integer arrivalTime, Integer duration, Integer startTime, Integer endTime) {
            this.arrivalTime = arrivalTime;
            this.duration = duration;
            this.startTime = startTime;
            this.endTime = endTime;
            this.consumed = 0;
        }

        public String toString(int index){
            return "Task "+index+": "+
                    " Arrival Time: "+this.arrivalTime+
                    " Duration: "+this.duration+
                    " Start Time: "+this.startTime+
                    " End Time: "+this.endTime;
        }

        Integer getTimeLeft(){
            return this.duration-this.consumed;
        }

        Integer getWaitTime(){
            return this.startTime-this.arrivalTime;
        }

        Integer getTurnaroundTime(){
            return this.endTime-this.arrivalTime;
        }
    }

    static class Tasks{
        ArrayList<Task> tasks;

        public Tasks(int nrOfTasks) {

            this.tasks = new ArrayList<>();

            for(int i=0;i<nrOfTasks;i++){
                Integer arrivalTime = (int)(Math.random()*upperBoundArrival+1);
                Integer duration = (int)(Math.random()*upperBoundDuration+1);

                if(tasks.isEmpty()){
                    arrivalTime=0;
                }

                Task newTask = new Task(arrivalTime,duration,null,null);

                this.tasks.add(newTask);
            }
        }

        public String toString(int timeQuantum){
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Time quantum = ").append(timeQuantum).append("\n");
            for(int i=0;i<tasks.size();i++){
                stringBuilder.append(tasks.get(i).toString(i+1)).append("\n");
            }
            stringBuilder.append("Average Waiting Time: ").append(averageWaitTime()).append("\n");
            stringBuilder.append("Average Turnaround Time ").append(averageTurnaroundTime()).append("\n\n");

            return stringBuilder.toString();
        }

        void resetConsumed(){
            for(Task task:this.tasks){
                task.consumed=0;
            }
        }

        double averageWaitTime(){
            double average = 0;

            for(Task task:tasks){
                average += task.getWaitTime();
            }

            return  average/(double)tasks.size();
        }

        double averageTurnaroundTime(){
            double average = 0;

            for(Task task:tasks){
                average += task.getTurnaroundTime();
            }

            return  average/(double)tasks.size();
        }
    }

    static void simulateRoundRobin(Tasks myTasks, int timeQuantum){
        boolean beingUpdated = true;
        int counter = 0;
        while(beingUpdated){
            beingUpdated=false;
            for (Task task : myTasks.tasks) {
                if (task.getTimeLeft() == 0){
                    continue;
                }

                if(task.arrivalTime>counter){
                    continue;
                }

                if(task.consumed==0){
                    task.startTime = counter;
                }

                if (task.getTimeLeft() <= timeQuantum) {
                    counter += task.getTimeLeft();
                    task.consumed = task.duration;
                    task.endTime = counter;
                } else {
                    counter+=timeQuantum;
                    task.consumed += timeQuantum;
                    beingUpdated = true;
                }
            }
        }
        System.out.println(myTasks.toString(timeQuantum));
    }

    static void simulateRoundRobin(int nrOfTasks){
        Tasks myTasks = new Tasks(nrOfTasks);

        for(int time=2;time<=upperBoundTimeQuantum;time++){
            simulateRoundRobin(myTasks,time);
            myTasks.resetConsumed();
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of tasks that are going to be generated: ");
        int nrOfTasks = input.nextInt();

        simulateRoundRobin(nrOfTasks);
    }
}
