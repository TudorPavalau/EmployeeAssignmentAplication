package DataModel;

import java.io.Serializable;
import java.util.ArrayList;

public final class ComplexTask extends Task {
    private ArrayList<Task> tasks;
    public ComplexTask(int idTask) {
        super(idTask);
        tasks = new ArrayList<Task>();
    }
    public void AddTask(Task task) {
        tasks.add(task);
    }
    public void DelTask(Task task) {
        tasks.remove(task);
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public void changeStatus() {
        for(Task task: tasks){
            task.changeStatus();
        }
        if(this.getStatus().equals("Completed")) {
            this.setStatus("Uncompleted");
        }
        else if(this.getStatus().equals("Uncompleted")) {
            this.setStatus("Completed");
        }
    }
    @Override
    public int estimateDuration() {
        int duration = 0;
        for (Task task : tasks) {
            duration += task.estimateDuration();
        }
        return duration;
    }
    public String toString(){
        return  "ComplexTask:" + super.toString();
    }
}
