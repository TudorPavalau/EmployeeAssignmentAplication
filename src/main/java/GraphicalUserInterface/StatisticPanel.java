package GraphicalUserInterface;

import BusinesLogic.TaskManagement;
import BusinesLogic.Utility;
import DataModel.Employee;
import DataModel.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Map;

public class StatisticPanel extends JPanel {
    private DefaultTableModel plusModel;
    private JTable plusTable;
    private JScrollPane plusScrollPane;
    private Utility utility;
    private DefaultTableModel nrTasks;
    private JTable nrTasksTable;
    private JScrollPane nrTasksScrollPane;
    private TaskManagement taskManagement;

    public StatisticPanel(TaskManagement taskManagement, Utility utility) {
        this.utility = utility;
        this.taskManagement = taskManagement;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        nrTasks = new DefaultTableModel();
        nrTasksTable = new JTable(nrTasks);
        nrTasksScrollPane = new JScrollPane(nrTasksTable);
        nrTasks.addColumn("Nume");
        nrTasks.addColumn("NrCompleted");
        nrTasks.addColumn("NrUncompleted");

        plusModel = new DefaultTableModel();
        plusTable = new JTable(plusModel);
        plusScrollPane = new JScrollPane(plusTable);

        plusModel.addColumn("Nume");
        plusModel.addColumn("WorkDuration");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, nrTasksScrollPane, plusScrollPane);
        this.add(splitPane);

        List<Pair> employeeList = utility.getEmployeeList();
        Map<String, Map<String,Integer>> taskStatusMap = utility.employeeTaskStatusMap();
        if(employeeList!=null && employeeList.size()>0) {
            for (Pair entry : employeeList) {
                String employeeName = entry.getEmployee().getName();
                int employeeStatus = entry.getNrHours();
                plusModel.addRow(new Object[]{employeeName, employeeStatus});
            }
        }
        if(taskStatusMap!=null && taskStatusMap.size()>0) {
            for (Map.Entry<String, Map<String, Integer>> entry : taskStatusMap.entrySet()) {
                String employeeName = entry.getKey();
                Map<String, Integer> taskStatus = entry.getValue();
                nrTasks.addRow(new Object[]{employeeName, taskStatus.get("Completed"), taskStatus.get("Uncompleted")});
            }
        }
    }

    @Override
    public String toString() {
        return "Statistic Panel";
    }
}
