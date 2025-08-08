import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    Color customLightGrey = new Color(212, 212, 210);
    Color customDarkGrey = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);
    Color customGreen = new Color(0, 255, 100);  // Adding custom color for the buttons

    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "=",
            "e", "π"
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};
    String[] specialSymbols = {"e", "π"}; // Adding the Buttons

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        frame.setSize(360, 540);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setOpaque(true);
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(6, 4)); // Structure preserved
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (String buttonValue : buttonValues) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGrey);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else if (Arrays.asList(specialSymbols).contains(buttonValue)) {
                button.setBackground(customGreen);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGrey);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String value = button.getText();
                    if (Arrays.asList(rightSymbols).contains(value)) {
                        if (value.equals("=")) {
                            if (A != null) {
                                B = displayLabel.getText();

                                String parsedA = A.replace("π", "3.14").replace("e", "2.71828");
                                String parsedB = B.replace("π", "3.14").replace("e", "2.71828");

                                double numA = Double.parseDouble(parsedA);
                                double numB = Double.parseDouble(parsedB);

                                if (operator.equals("+")) {
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                } else if (operator.equals("-")) {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                } else if (operator.equals("×")) {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                } else if (operator.equals("÷")) {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                clearAll();
                            }
                        } else {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = value;
                        }
                    } else if (Arrays.asList(topSymbols).contains(value)) {
                        if (value.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (value.equals("+/-")) {
                            double num = Double.parseDouble(displayLabel.getText());
                            displayLabel.setText(removeZeroDecimal(num * -1));
                        } else if (value.equals("%")) {
                            double num = Double.parseDouble(displayLabel.getText());
                            displayLabel.setText(removeZeroDecimal(num / 100));
                        }
                    } else {
                        if (value.equals(".")) {
                            if (!displayLabel.getText().contains(".")) {
                                displayLabel.setText(displayLabel.getText() + ".");
                            }
                        } else if ("0123456789".contains(value)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(value);
                            } else {
                                displayLabel.setText(displayLabel.getText() + value);
                            }
                        } else if (value.equals("π")) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText("π");
                            } else {
                                displayLabel.setText(displayLabel.getText() + "π");
                            }
                        } else if (value.equals("e")) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText("e");
                            } else {
                                displayLabel.setText(displayLabel.getText() + "e");
                            }
                        } else if (value.equals("√")) {
                            String input = displayLabel.getText().replace("π", "3.14").replace("e", "2.71828");
                            double num = Double.parseDouble(input);
                            if (num >= 0) {
                                double result = Math.sqrt(num);
                                displayLabel.setText(removeZeroDecimal(result));
                                clearAll();
                            } else {
                                displayLabel.setText("Error");
                            }
                        }
                    }
                }
            });
        }

        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
