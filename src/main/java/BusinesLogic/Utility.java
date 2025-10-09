package BusinesLogic;

import DataModel.Employee;
import DataModel.Pair;
import DataModel.Task;

import java.util.*;

public class Utility {
    private TaskManagement taskManagement;
    public Utility(TaskManagement taskManagement) {
        this.taskManagement = taskManagement;
    }

    public  ArrayList<Pair> getEmployeeList() {
        ArrayList<Pair> plus40 = new ArrayList<Pair>();
        if(taskManagement.getEmployeeList()!=null) {
            for (Employee employee : taskManagement.getEmployeeList()) {
                if (taskManagement.calculateEmployeeWorkDuration(employee.getId()) >= 40) {
                    plus40.add(new Pair(employee, taskManagement.calculateEmployeeWorkDuration(employee.getId())));
                }
            }
        }
        Collections.sort(plus40);
        return plus40;
    }

    public Map<String, Map<String,Integer>> employeeTaskStatusMap() {
        Map<String, Map<String,Integer>> employeeTaskStatusMap = new HashMap<String, Map<String,Integer>>();
        for(Employee employee: taskManagement.getEmployeeListHashMap().keySet())
        {
            int nD=0;
            int nU=0;
            Map<String,Integer> StatusMap = new HashMap<String,Integer>();
            for (Task task: taskManagement.getEmployeeListHashMap().get(employee)) {
                if(task.getStatus().equals("Completed")) {
                    nD++;
                }
                else{
                    nU++;
                }
            }
            StatusMap.put("Completed", nD);
            StatusMap.put("Uncompleted", nU);
            employeeTaskStatusMap.put(employee.getName(), StatusMap);
        }
        return employeeTaskStatusMap;
    }
}
