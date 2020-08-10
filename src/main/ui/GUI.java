package ui;

import model.Account;
import model.BudgetManager;
import model.Item;
import persistence.Reader;
import persistence.Writer;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame {

    public HashMap<String, List<Item>> budgetList = new HashMap<>();
    Account account1 = new Account(10000, 1000);
    BudgetManager budgetManager = new BudgetManager(account1, budgetList);
    private static final String BUDGET_FILE = "./data/budgetGUI.txt";

    private JFrame frame = new JFrame();
    private JTextField itemNameInput = new JTextField();
    private JTextField itemAmountInput = new JTextField();
    private JTextField itemCategoryInput = new JTextField();
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton loadButton;
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
        JButton saveButton = saveButton();
        JButton loadButton = loadButton(model, row);
        addItemPanel.add(addButton);
        displayPanel.add(deleteButton);
        displayPanel.add(saveButton);
        displayPanel.add(loadButton);

        model.setColumnIdentifiers(columns);
        table.setModel(model);
        Font font = new Font("Open Sans", Font.PLAIN, 15);
        table.setFont(font);
        table.setRowHeight(15);
        addPane(table);

    }

    public void addPane(JTable table) {
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

                    drawToGUI(model, row);

                    playSound("notification.wav");
                }

            }
        });
        return addButton;
    }

    public void drawToGUI(DefaultTableModel model, Object[] row) {
        model.setRowCount(0);
        for (String i : budgetManager.budgetList.keySet()) {
            row[0] = i;
            for (Item j : budgetManager.budgetList.get(i)) {
                row[1] = j.getItemName();
                row[2] = j.getItemAmount();
                model.addRow(row);
            }
        }
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

                    playSound("pop.wav");
                    model.removeRow(row);
                }
            }
        });
        return deleteButton;
    }

    private JButton saveButton() {
        saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Writer writer = new Writer(new File(BUDGET_FILE));
                    writer.write(budgetManager);
                    writer.close();
                    System.out.println("Accounts saved to file " + BUDGET_FILE);
                    playSound("ping2.wav");
                } catch (FileNotFoundException i) {
                    System.out.println("Unable to save accounts to " + BUDGET_FILE);
                } catch (UnsupportedEncodingException i) {
                    i.printStackTrace();
                    // this is due to a programming error
                }

            }
        });
        return saveButton;
    }

    private JButton loadButton(DefaultTableModel model, Object[] row) {
        loadButton = new JButton("load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    budgetManager = Reader.readBudgetContents(new File(BUDGET_FILE));
                    drawToGUI(model, row);
                    playSound("ping1.wav");
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });
        return loadButton;
    }

    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
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

}
