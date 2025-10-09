package DataAccess;

import DataModel.Employee;
import DataModel.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SerializationOperations {
    public static void serializeEmployees(ArrayList<Employee> employees) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("Employee.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(employees);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    public static ArrayList<Employee> deserializeEmployees() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("Employee.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ArrayList<Employee> employees = (ArrayList<Employee>) objectInputStream.readObject();
        objectInputStream.close();
        return employees;
    }

    public static void serializeTasks(ArrayList<Task> tasks) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("Task.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(tasks);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    public static ArrayList<Task> deserializeTasks() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("Task.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ArrayList<Task> tasks = (ArrayList<Task>) objectInputStream.readObject();
        objectInputStream.close();
        return tasks;
    }

    public static void serializeMap(HashMap<Employee, ArrayList<Task>> map) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("Map.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(map);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
    public static HashMap<Employee, ArrayList<Task>> deserializeMap() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("Map.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        HashMap<Employee, ArrayList<Task>> map = (HashMap<Employee, ArrayList<Task>>) objectInputStream.readObject();
        objectInputStream.close();
        return map;
    }
}
