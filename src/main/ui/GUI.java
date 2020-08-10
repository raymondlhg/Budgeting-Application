package ui;

import model.Account;
import model.BudgetManager;
import model.Item;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame implements ActionListener {

    BudgetApp budgetApp;
    public HashMap<String, List<Item>> budgetList = new HashMap<>();
    Account account1 = new Account(10000, 1000);
    BudgetManager budgetManager = new BudgetManager(account1, budgetList);

    private JFrame frame = new JFrame();
    private JTextField itemNameInput = new JTextField();
    private JTextField itemAmountInput = new JTextField();
    private JTextField itemCategoryInput = new JTextField();
    private JButton addButton;
    private JButton deleteButton;
    private JPanel addItemPanel = new JPanel();
    private JPanel displayPanel = new JPanel();
    private JTabbedPane tabbedPane;


    public GUI() {

        frame.setSize(680, 500);
        tabbedPane = new JTabbedPane();

        JComponent addItemPanel1 = addItemPanel;
        tabbedPane.addTab("Add Items", null, addItemPanel1, "add items");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent displayPanel1 = displayPanel;
        tabbedPane.addTab("Display Budget", null, displayPanel1,
                "displays budget and its components");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        addItemToPanel();

        addJTable();

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Budget Manager");
//        frame.pack(); // sets frame to size of all components inside panel
        frame.setVisible(true);


    }

    public void addJTable() {
        JTable table = new JTable();
        Object[] columns = {"Category", "Item", "Item Amount"};
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column);
            }
        };

        Object[] row = new Object[3];
        JButton addButton = addButton(budgetList, model, row);
        JButton deleteButton = deleteButton(budgetList, model, table);
        addItemPanel.add(addButton);
        displayPanel.add(deleteButton);

        model.setColumnIdentifiers(columns);
        table.setModel(model);
        Font font = new Font("Open Sans", Font.PLAIN, 15);
        table.setFont(font);
        table.setRowHeight(15);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 680, 500);

        displayPanel.add(pane);

    }

    public void addItemToPanel() {
        SetUpInputs set = new SetUpInputs().invoke();
        JLabel itemName = set.getLblItemName();
        JLabel itemAmount = set.getLblItemAmount();
        JLabel itemCategory = set.getLblItemCategory();
        addItemPanel.add(itemName);
        addItemPanel.add(itemNameInput);
        addItemPanel.add(itemAmount);
        addItemPanel.add(itemAmountInput);
        addItemPanel.add(itemCategory);
        addItemPanel.add(itemCategoryInput);
        itemNameInput.setPreferredSize(new Dimension(120, 20));
        itemAmountInput.setPreferredSize(new Dimension(120, 20));
        itemCategoryInput.setPreferredSize(new Dimension(120, 20));

    }

    private JButton addButton(HashMap<String, List<Item>> budgetList, DefaultTableModel model, Object[] row) {
        addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(itemNameInput.getText().isEmpty() || itemAmountInput.getText().isEmpty()
                        || itemCategoryInput.getText().isEmpty())) {
                    budgetManager.addItem(itemNameInput.getText(), Double.parseDouble(itemAmountInput.getText()),
                            itemCategoryInput.getText());

                    for (String i : budgetManager.budgetList.keySet()) {
                        row[0] = i;
                        for (Item j : budgetManager.budgetList.get(i)) {
                            row[1] = j.getItemName();
                            row[2] = j.getItemAmount();
                        }
                    }

                    model.addRow(row);
                    playSound("notification.wav");
                }

            }
        });
        return addButton;
    }

    private JButton deleteButton(HashMap<String, List<Item>> budgetList, DefaultTableModel model, JTable table) {
        deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    budgetManager.removeItemGUI(table.getValueAt(row,
                            0).toString(), table.getValueAt(row, 1).toString(),
                            Double.parseDouble(table.getValueAt(row, 2).toString()));

                    model.removeRow(row);
                    playSound("pop.wav");
                }
            }
        });
        return deleteButton;
    }

//        @Override
//        public void actionPerformed(ActionEvent e) {
//            Object src = e.getSource();
//            if (src == addButton) {
//                budgetManager.addItem(itemNameInput.getText(), Double.parseDouble(itemAmountInput.getText()),
//                        itemCategoryInput.getText());
//
//            }

    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class SetUpInputs {
        private JLabel lblItemName;
        private JLabel lblItemAmount;
        private JLabel lblItemCategory;

        public JLabel getLblItemName() {
            return lblItemName;
        }

        public JLabel getLblItemAmount() {
            return lblItemAmount;
        }

        public JLabel getLblItemCategory() {
            return lblItemCategory;
        }

        public SetUpInputs invoke() {
            lblItemName = new JLabel("Item Name: ");
            lblItemName.setFont(new Font("Open Sans", Font.PLAIN, 15));
            lblItemAmount = new JLabel("Item Amount: ");
            lblItemAmount.setFont(new Font("Open Sans", Font.PLAIN, 15));
            lblItemCategory = new JLabel("Item Category: ");
            lblItemCategory.setFont(new Font("Open Sans", Font.PLAIN, 15));
            return this;
        }

    }

    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName)
                    .getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

//    private void loadToTable(HashMap<String, List<Item>> budgetList, DefaultTableModel model, Object[] row) {
//        for (Map.Entry<String, List<Item>> entry : budgetList.entrySet()) {
//            row[0] = entry.getKey();
//            row[1] = entry.getValue();
//            row[2] = entry.getValue();
//
//            model.addRow(row);
//        }
//    }
}


//        itemNameInput.setPreferredSize(new Dimension(120, 20));
//        itemAmountInput.setPreferredSize(new Dimension(120, 20));
//        itemCategoryInput.setPreferredSize(new Dimension(120, 20));