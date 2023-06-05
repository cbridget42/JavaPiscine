package day01.ex04;

import java.util.UUID;

public class TransactionsService {
    private UsersList users;

    public TransactionsService() {
        this.users = new UsersArrayList();
    }

    public void addUser(String name, Integer balance) {
        this.users.addUser(new User(name, balance));
    }

    public int getUserBalance(int id) {
        return this.users.getUserById(id).getBalance();
    }

    public void performTransaction(int recipientId, int senderId, int transferAmount) {
        User recipient = this.users.getUserById(recipientId);
        User sender = this.users.getUserById(senderId);
        if (recipientId == senderId || transferAmount < 0 || sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException("Illegal transaction");
        }
        Transaction trans = new Transaction(recipient, sender, transferAmount, Transaction.Category.DEBIT);
        recipient.getTransactions().addTransaction(trans);
        sender.getTransactions().addTransaction(new Transaction(trans));
    }

    public Transaction[] getUserTransfers(int id) {
        return this.users.getUserById(id).getTransactions().toArray();
    }

    public void removeTransaction(int userId, UUID transcactionId) {
        this.users.getUserById(userId).getTransactions().removeTransaction(transcactionId);
    }

    public Transaction[] unpairedTransactions() {
        TransactionsLinkedList allTransactions = new TransactionsLinkedList();
        for (int i = 0; i < users.getNumberOfUsers(); i++) {
            Transaction[] tmp = users.getUserByIndex(i).getTransactions().toArray();
            for (int j = 0; j < tmp.length; j++) {
                allTransactions.addTransaction(tmp[j]);
            }
        }
        int i = 0;
        while (allTransactions.getSize() != i) {
            if (!isTransactionHaveDuplicate(i, allTransactions)) {
                i++;
            }
        }
        return allTransactions.toArray();
    }

    private boolean isTransactionHaveDuplicate(int index, TransactionsLinkedList arrOfTrans) {
        Transaction[] arrTmp = arrOfTrans.toArray();
        UUID check = arrTmp[index].getIdentifier();
        for (int i = 0; i < arrTmp.length; i++) {
            if (check == arrTmp[i].getIdentifier() && i != index) {
                arrOfTrans.removeTransaction(check);
                arrOfTrans.removeTransaction(arrTmp[i].getIdentifier());
                return true;
            }
        }
        return false;
    }
}
