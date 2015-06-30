import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.logging.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
public class AutoFill extends JPanel implements ActionListener, NativeKeyListener 
{
    Robot robot;
    JLabel labels[];
    JTextField textFields[];
    JComboBox comboBoxes[];
    String choices[][];
    JButton save;
    public AutoFill() 
    {
        labels = new JLabel[8];
        choices = new String[3][];
        textFields = new JTextField[4];
        comboBoxes = new JComboBox[3];
        choices[0] = new String[] {"Visa/Master Card(Powered By ICICI Bank)", "Visa/Master Card(Powered By CITI Bank)", "Visa/Master Card(Powered By HDFC Bank)"};
        choices[1] = new String[] {"VISA", "MASTER"};
        choices[2] = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        labels[0] = new JLabel("Select A Payment Gateway: ");
        labels[1] = new JLabel("Credit Card Type: ");
        labels[2] = new JLabel("Credit Card Number: ");
        labels[3] = new JLabel("Card Expiry Date: ");
        labels[4] = new JLabel("Month:");
        labels[5] = new JLabel("Year(yyyy):");
        labels[6] = new JLabel("CVV Number: ");
        labels[7] = new JLabel("Name on Card: ");
        textFields[0] = new JTextField("Please enter you credit card number", 19);
        textFields[1] = new JTextField(4);
        textFields[2] = new JTextField(4);
        textFields[3] = new JTextField("Please enter your name specified on the card", 24);
        comboBoxes[0] = new JComboBox(choices[0]);
        comboBoxes[1] = new JComboBox(choices[1]);
        comboBoxes[2] = new JComboBox(choices[2]);
        save = new JButton("          Save          ");
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labels[0], gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(comboBoxes[0], gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labels[1], gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(comboBoxes[1], gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(labels[2], gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(textFields[0], gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(labels[3], gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(labels[4], gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, -230, 5, 5);
        add(comboBoxes[2], gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, -170, 5, 5);
        add(labels[5], gbc);
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, -105, 5, 5);
        add(textFields[1], gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(labels[6], gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(textFields[2], gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(labels[7], gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(textFields[3], gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        add(save, gbc);
        save.addActionListener(this);
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try 
        {
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.registerNativeHook();
        } 
        catch (NativeHookException e) {}
        GlobalScreen.addNativeKeyListener(this);
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("info.txt"));
            for (int a = 0; a < 3; a++) 
            {
                int choice = Integer.parseInt(reader.readLine());
                comboBoxes[a].setSelectedIndex(choice);
            }
            for (int a = 0; a < 4; a++)
            textFields[a].setText(reader.readLine());
            robot = new Robot();
            robot.setAutoDelay(10);
            robot.setAutoWaitForIdle(true);
            Runtime.getRuntime().exec("taskkill /f /im cmd.exe");
        } 
        catch (Exception e) {}
    }
    public static void main(String args[]) 
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                JFrame fr = new JFrame("Autofill: www.irctc.co.in");
                AutoFill obj = new AutoFill();
                fr.setSize(600, 300);
                fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fr.setLocationRelativeTo(null);
                fr.setResizable(false);
                fr.add(obj);
                fr.setVisible(true);
                JOptionPane.showMessageDialog(null, "                                    Illegal distribution of this software is a criminal offence.\n             For distribution rights, please email the developer at sourish.banerjeee@gmail.com.\n                                                 \u00A9 Sourish Banerjee. All rights reserved.\n\n                          Instructions: Enter the necessary details and click on the save button.\n To autofill, select Payment Gateway/Credit Card from the Payment Modes and press the left control key.");
            }
        });
    }
    public void actionPerformed(ActionEvent e) 
    {
        try 
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("info.txt"));
            if (textFields[0].getText().equals("Please enter you credit card number") || textFields[1].getText().equals("") || textFields[2].getText().equals("") || textFields[3].getText().equals("Please enter your name specified on the card") || textFields[0].getText().equals("") || textFields[3].getText().equals("")) 
            {
                JOptionPane.showMessageDialog(null, "Please fill in all details before saving!");
            } 
            else 
            {
                for (int a = 0; a < 3; a++)
                writer.write(Integer.toString(comboBoxes[a].getSelectedIndex()) + "\r\n");
                for (int a = 0; a < 4; a++)
                writer.write(textFields[a].getText() + "\r\n");
                writer.close();
                JOptionPane.showMessageDialog(null, "File saved successfully!");
            }
        } 
        catch (IOException ex) {}
    }
    public void nativeKeyPressed(NativeKeyEvent e) {}
    public void nativeKeyReleased(NativeKeyEvent e) 
    {
        if (e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L) 
        {
            try 
            {
                new Thread(new Runnable() 
                {
                    public void run() 
                    {
                        int temp;
                        temp = comboBoxes[0].getSelectedIndex() + 1;
                        clickTab();
                        clickDown(temp);
                        robot.delay(50);
                        clickTab();
                        temp = comboBoxes[1].getSelectedIndex();
                        clickDown(temp);
                        clickTab();
                        type(textFields[0].getText());
                        clickTab();
                        temp = comboBoxes[2].getSelectedIndex();
                        clickDown(temp + 1);
                        clickTab();
                        type(textFields[1].getText());
                        clickTab();
                        type(textFields[2].getText());
                        clickTab();
                        type(textFields[3].getText());
                    }
                }).start();
            } catch (Exception ex) {}
        }
    }
    public void nativeKeyTyped(NativeKeyEvent ef) {}
    public void clickTab() 
    {
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }
    public void type(String str) 
    {
        byte[] bytes = str.getBytes();
        for (byte a: bytes) 
        {
            int ascii = a;
            boolean decider = false;
            if (ascii > 96 && ascii < 123) 
            {
                ascii -= 32;
                decider = true;
            }
            if (decider == false) 
            {
                robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
            }
            robot.keyPress(ascii);
            robot.keyRelease(ascii);
            if (decider == false) 
            {
                robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
            }
        }
    }
    public void clickDown(int no) 
    {
        for (int a = 1; a <= no; a++) 
        {
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
        }
    }
}