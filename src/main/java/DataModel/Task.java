package DataModel;

import java.io.Serializable;

public abstract class Task implements Serializable{
    private static final long serialVersionUID = 2L;
    private int idTask;
    private String status;

    public Task(int idTask) {
        this.idTask = idTask;
        this.status = "Uncompleted";
    }
    public void changeStatus() {
        if(status.equals("Completed")){
            setStatus("Uncompleted");
        }
        else if(status.equals("Uncompleted")){
            setStatus("Completed");
        }
    }
    public int getIdTask() {
        return idTask;
    }
    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public abstract int estimateDuration();
    public String toString(){
        return "ID:" + idTask + ":" + status;
    }
}
