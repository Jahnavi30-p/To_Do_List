import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ToDoListApp {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private File file = new File("tasks.txt");

    public ToDoListApp() {
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        
        taskListModel = new DefaultListModel<>();
        loadTasks();
        
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        
        taskInput = new JTextField(20);
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        
        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        
        JPanel panel = new JPanel();
        panel.add(taskInput);
        panel.add(addButton);
        panel.add(removeButton);
        
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskListModel.addElement(task);
            saveTasks();
            taskInput.setText("");
        }
    }
    
    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
            saveTasks();
        }
    }
    
    private void saveTasks() {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.println(taskListModel.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadTasks() {
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    taskListModel.addElement(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}