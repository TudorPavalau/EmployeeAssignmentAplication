package DataModel;

public class Pair implements Comparable<Pair> {

    private Employee employee;
    private int nrHours;

    public Pair(Employee employee, int nrHours) {
        this.employee = employee;
        this.nrHours = nrHours;
    }

    @Override
    public int compareTo(Pair o) {
        return nrHours - o.nrHours;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNrHours() {
        return nrHours;
    }

    public void setNrHours(int nrHours) {
        this.nrHours = nrHours;
    }
}

