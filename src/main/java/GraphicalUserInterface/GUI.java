package GraphicalUserInterface;

import BusinesLogic.TaskManagement;
import BusinesLogic.Utility;
import DataAccess.SerializationOperations;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    private StatisticPanel statisticPanel;
    private EmployeePanel employeePanel;
    private JMenuBar menuBar;
    private JMenu panelMenu;
    private JMenuItem employeesMenu;
    private JMenuItem statisticMenu;
    private TaskManagement taskManagement;
    private Utility utility;

    public GUI(TaskManagement taskManagement) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    SerializationOperations.serializeTasks(taskManagement.getTaskList());
                    SerializationOperations.serializeEmployees(taskManagement.getEmployeeList());
                    SerializationOperations.serializeMap(taskManagement.getEmployeeListHashMap());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        this.setTitle("Task Management System");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        this.taskManagement = taskManagement;
        this.utility= new Utility(taskManagement);

        employeePanel = new EmployeePanel(this.taskManagement, this.utility);
        statisticPanel = new StatisticPanel(this.taskManagement, this.utility);

        employeePanel.setVisible(true);
        statisticPanel.setVisible(true);

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        panelMenu = new JMenu("Panel");
        menuBar.add(panelMenu);
        employeesMenu = new JMenuItem("Employees");
        statisticMenu = new JMenuItem("Statistic");
        panelMenu.add(employeesMenu);
        panelMenu.add(statisticMenu);
        menuBar.setVisible(true);

        employeesMenu.addActionListener(e -> eMenu());
        statisticMenu.addActionListener(e->sMenu());

        this.setJMenuBar(menuBar);
        this.setContentPane(employeePanel);
        this.setVisible(true);
        this.pack();

    }
    public EmployeePanel getEmployeePanel() {
        return employeePanel;
    }
    public StatisticPanel getStatisticPanel() {
        return statisticPanel;
    }
    private void sMenu() {
        this.setContentPane(statisticPanel);
    }

    private void eMenu() {
        this.setContentPane(employeePanel);
    }

}

