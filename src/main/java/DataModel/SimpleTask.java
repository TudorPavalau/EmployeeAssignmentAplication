package DataModel;

import java.io.Serializable;

public final class SimpleTask extends Task {
    private int startHour;
    private int endHour;
    public int estimateDuration() {
        return endHour - startHour;
    }
    public SimpleTask(int startHour, int endHour,int idTask) {
        super(idTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }
    public int getStartHour() {
        return startHour;
    }
    public int getEndHour() {
        return endHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }
    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
    public String toString() {
        return  "SimpleTask" + super.toString() + startHour + ":" + endHour;
    }
}
