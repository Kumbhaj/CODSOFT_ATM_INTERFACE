import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}

class ATMInterface extends JFrame {
    private final BankAccount account;
    private final JLabel balanceLabel;
    private final JTextField amountField;
    public final JButton depositButton;
    private final JLabel messageLabel;

    public ATMInterface() {
        account = new BankAccount(1000);

        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 1));

        balanceLabel = new JLabel("Balance: $" + account.getBalance());
        amountField = new JTextField();
        var withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        var checkBalanceButton = new JButton("Check Balance");
        messageLabel = new JLabel("Enter an amount and choose an action.");

        add(balanceLabel);
        add(amountField);
        add(withdrawButton);
        add(depositButton);
        add(checkBalanceButton);
        add(messageLabel);

        withdrawButton.addActionListener(new WithdrawButtonListener());
        depositButton.addActionListener(new DepositButtonListener());
        checkBalanceButton.addActionListener(new CheckBalanceButtonListener());

        setVisible(true);
    }

    private class WithdrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (account.withdraw(amount)) {
                    messageLabel.setText("Withdrawal successful.");
                } else {
                    messageLabel.setText("Insufficient balance or invalid amount.");
                }
                updateBalanceLabel();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid amount.");
            }
        }
    }

    private class DepositButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                account.deposit(amount);
                messageLabel.setText("Deposit successful.");
                updateBalanceLabel();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid amount.");
            }
        }
    }

    private class CheckBalanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateBalanceLabel();
            messageLabel.setText("Balance checked.");
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + account.getBalance());
    }

    public static void main(String[] args) {
        new ATMInterface();
    }
}
