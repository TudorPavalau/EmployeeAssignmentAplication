import BusinesLogic.TaskManagement;
import DataAccess.SerializationOperations;
import DataModel.Employee;
import DataModel.Task;
import GraphicalUserInterface.GUI;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Employee> employees = new ArrayList<Employee>();
        HashMap<Employee, ArrayList<Task>> map = new HashMap<>();
        try{
            tasks=SerializationOperations.deserializeTasks();
            employees=SerializationOperations.deserializeEmployees();
            map = SerializationOperations.deserializeMap();
        }catch(Exception ex){
            tasks = new ArrayList<Task>();
            employees = new ArrayList<Employee>();
        }
        TaskManagement taskManagement = new TaskManagement(employees,tasks,map);
        GUI gui = new GUI(taskManagement);
    }
}
