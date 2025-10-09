package BusinesLogic;

import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManagement {
    private HashMap<Employee, ArrayList <Task>> employeeListHashMap;
    private ArrayList<Employee> employeeList;
    private ArrayList<Task> taskList;

    public TaskManagement(ArrayList<Employee> employeeList, ArrayList<Task> taskList, HashMap<Employee, ArrayList <Task>> employeeListHashMap) {
        this.employeeListHashMap = employeeListHashMap;
        this.employeeList = employeeList;
        this.taskList = taskList;
    }

    public HashMap<Employee, ArrayList <Task>> getEmployeeListHashMap() {
        return employeeListHashMap;
    }

    public int  calculateEmployeeWorkDuration(int idEmployee){
        int workDuration=0;
        for(Employee e:employeeListHashMap.keySet()) {
            if(e.getId()==idEmployee) {
                    for (Task task1 : employeeListHashMap.get(e)) {
                        for (Task task2 : taskList) {
                            if (task1.getIdTask() == task2.getIdTask() && task2.getStatus().equals("Completed")) {
                                workDuration += task1.estimateDuration();
                            }
                        }
                    }
                }
            }
        return workDuration;
    }

    public void modifyTaskStatus(int idTask){
        for(Task task: taskList){
             if(task.getIdTask()==idTask){
                 task.changeStatus();
             }
        }
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void addTask(Task task){
        this.taskList.add(task);
        if(task instanceof ComplexTask)
        {
            for (Task subtask:((ComplexTask)task).getTasks())
                this.taskList.add(subtask);
        }
    }

    public void removeTask(Task task){
        this.taskList.remove(task);
    }

    public void addEmployee(Employee employee){
        this.employeeList.add(employee);
    }

    public void removeEmployee(Employee employee){
        this.employeeList.remove(employee);
    }

    public void assignEmployeeTask(Employee employee,Task task){
        if(!employeeListHashMap.containsKey(employee)){
            ArrayList<Task> tasks1=new ArrayList<Task>();
            tasks1.add(task);
            employeeListHashMap.put(employee,tasks1);
        }
        else{
            employeeListHashMap.get(employee).add(task);
        }
    }

    public Task getTask(int idTask){
        for(Task task1: taskList){
                if(task1.getIdTask()==idTask){
                    return task1;
                }
            }
        return null;
    }

    public Employee getEmployeeById(int idEmployee){
        for(Employee  employee: employeeList)
            if(employee.getId() == idEmployee)
                return employee;
        return null;
    }
}
