package GraphicalUserInterface;

import BusinesLogic.TaskManagement;
import BusinesLogic.Utility;
import DataModel.ComplexTask;
import DataModel.Employee;
import DataModel.SimpleTask;
import DataModel.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;

public class EmployeePanel extends JPanel {
    private Utility utility;
    private JButton addEButton;
    private JButton addTButton;
    private JButton assignButton;
    private JButton statusButton;

    private DefaultTableModel employeeTableModel;
    private JTable employeeTable;

    private DefaultTableModel taskTableModel;
    private JTable taskTable;
    private ComplexTask complexTask;
    private JTree tree;

    private TaskManagement taskManagement;
    public EmployeePanel(TaskManagement taskManagement, Utility utility) {
        addEButton = new JButton("Add Employee");
        addTButton = new JButton("Add Task");
        assignButton = new JButton("Assign Employee");
        statusButton = new JButton("ModifyStatus");

        this.utility = utility;
        this.taskManagement = taskManagement;

        taskTableModel = new DefaultTableModel();
        employeeTableModel = new DefaultTableModel();

        employeeTableModel.addColumn("EmployeeName");
        employeeTableModel.addColumn("EmployeeID");
        taskTableModel.addColumn("TaskID");
        taskTableModel.addColumn("TaskStartHour");
        taskTableModel.addColumn("TaskEndHour");
        taskTableModel.addColumn("TaskStatus");

        taskTable=new JTable(taskTableModel);
        employeeTable=new JTable(employeeTableModel);

        if(this.getEmployees()!=null) {
            for (Employee employee : this.getEmployees()) {
                this.employeeTableModel.addRow(new Object[]{employee.getName(), employee.getId()});
            }
        }
        if(this.getTasks()!=null) {
            for (Task task : this.getTasks()) {
                if (task instanceof SimpleTask) {
                    int startHour = ((SimpleTask) task).getStartHour();
                    int endHour = ((SimpleTask) task).getEndHour();
                    String status = task.getStatus();
                    this.taskTableModel.addRow(new Object[]{task.getIdTask(), startHour, endHour, status});
                } else {
                    this.taskTableModel.addRow(new Object[]{task.getIdTask(), "-", "-", task.getStatus()});
                }
            }
        }

        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(1,0));
        jp.add(addEButton);
        jp.add(addTButton);
        jp.add(assignButton);
        jp.add(statusButton);
        add(jp);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(employeeTable);
        JScrollPane scrollPane1 = new JScrollPane(employeeTable);

        JPanel jp1 = new JPanel();
        jp1.add(taskTable);
        JScrollPane scrollPane2 = new JScrollPane(taskTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, scrollPane2);
        splitPane.setDividerLocation(40);
        splitPane.setResizeWeight(0.5);
        jp1.add(splitPane, BorderLayout.CENTER);
        splitPane.setDividerLocation(150);
        splitPane.setResizeWeight(0.5);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Employee");
        tree = new JTree(root);
        ListSelectionModel lsm=employeeTable.getSelectionModel();
        lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsm.addListSelectionListener(e -> selectionListener(root));
        JScrollPane scrollPane3 = new JScrollPane(tree);
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane2, scrollPane3);
        jp1.add(splitPane1, BorderLayout.CENTER);

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            Object object = node.getUserObject();
            if (object instanceof ComplexTask) {
                complexTask = (ComplexTask) object;
            } else {
                complexTask = null;
            }
        });

        this.add(jp1);
        setSize(500, 500);
        setVisible(true);

        addEButton.addActionListener(e -> addEmployee());
        addTButton.addActionListener(e -> addTask());
        statusButton.addActionListener(e -> modifyStatus());
        assignButton.addActionListener(e -> assignEmployee());
       setVisible(true);
    }

    public String toString() {
        return "Employee Panel";
    }
    public void selectionListener(DefaultMutableTreeNode node) {
        int index = employeeTable.getSelectedRow();
        int id = (int)employeeTable.getValueAt(index, 1);

        node.removeAllChildren();
        for (Employee employee : taskManagement.getEmployeeListHashMap().keySet()) {
            if(employee.getId() == id) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (Task task1 : taskManagement.getEmployeeListHashMap().get(employee)) {
                    for (Task task2 : taskManagement.getTaskList()) {
                        if(task1.getIdTask() == task2.getIdTask()) {
                            tasks.add(task2);
                        }
                    }
                }
                if(tasks!=null) {
                    buildTree(node, tasks);
                }
            }
        }
        ((DefaultTreeModel)tree.getModel()).nodeStructureChanged(node);
    }

    public void buildTree(DefaultMutableTreeNode root,ArrayList<Task> task) {
        for(Task t1: task){
            DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(t1);
            if(t1 instanceof ComplexTask){
                buildTree(taskNode,((ComplexTask)t1).getTasks());
            }
            root.add(taskNode);
        }
    }

    private void modifyStatus() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            int id= taskManagement.getTaskList().get(selectedRow).getIdTask();
            taskManagement.modifyTaskStatus(id);
        }
        taskTableModel.setValueAt(taskManagement.getTaskList().get(selectedRow).getStatus(), selectedRow, 3);
    }

    private void addTask() {
        int taskID = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID"));
        int option=JOptionPane.showConfirmDialog(null, "ComplexTask?");
        if(option!=0){
            int startTime = Integer.parseInt(JOptionPane.showInputDialog("Enter Start Time:"));
            int endTime = Integer.parseInt(JOptionPane.showInputDialog("Enter End Time:"));
            Task task1= new SimpleTask(startTime, endTime, taskID);
            taskManagement.addTask(task1);
            if(complexTask!=null) {
                complexTask.AddTask(task1);
            }
            this.taskTableModel.addRow(new Object[]{taskID,startTime, endTime,task1.getStatus()});
        }
        else{
            Task task1=new ComplexTask(taskID);
            taskManagement.addTask(task1);
            if(complexTask!=null) {
                complexTask.AddTask(task1);
            }
            this.taskTableModel.addRow(new Object[]{taskID,"-", "-",task1.getStatus()});
        }
    }

    private void addEmployee() {
      String name = JOptionPane.showInputDialog("Enter Employee Name");
      int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Employee ID"));
      Employee employee = new Employee(id, name);
        taskManagement.addEmployee(employee);
      this.employeeTableModel.addRow(new Object[]{employee.getName(),employee.getId()});
    }

    private void assignEmployee() {
        int i = taskTable.getSelectedRow();
        int j = employeeTable.getSelectedRow();

        if (i == -1 || j == -1) {
            JOptionPane.showMessageDialog(this, "Select a task and an employee first!");
            return;
        }

        int employeeId = (int) employeeTable.getValueAt(j, 1);
        int taskId = (int) taskTable.getValueAt(i, 0);

        Employee employee1 = taskManagement.getEmployeeById(employeeId);
        Task task1 = taskManagement.getTask(taskId);

        if (employee1 == null || task1 == null) {
            JOptionPane.showMessageDialog(this, "Invalid selection!");
            return;
        }

        taskManagement.assignEmployeeTask(employee1, task1);
        JOptionPane.showMessageDialog(this, "Employee assigned successfully!");
    }

    public ArrayList<Employee> getEmployees() {
        return taskManagement.getEmployeeList();
    }
    public ArrayList<Task> getTasks() {
        return taskManagement.getTaskList();
    }
}
