package day01.ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User usr1 = new User("user1", 42000);
        User usr2 = new User("User2", 21000);
        TransactionsList test = new TransactionsLinkedList();
        Transaction trans = new Transaction(usr1, usr2, 200, Transaction.Category.DEBIT);

        test.addTransaction(new Transaction(usr1, usr2, 500, Transaction.Category.DEBIT));
        test.addTransaction(trans);
        test.addTransaction(new Transaction(usr1, usr2, 5000, Transaction.Category.DEBIT));
        test.addTransaction(new Transaction(usr1, usr2, -800, Transaction.Category.CREDIT));

        System.out.println("first test");
        printInfo((TransactionsLinkedList)test, test.toArray());

        test.removeTransaction(trans.getIdentifier());
        System.out.println("test after deleting 1 transaction");
        printInfo((TransactionsLinkedList)test, test.toArray());

        System.out.println("attempt to delete a transaction with an invalid ID");
        try {
            test.removeTransaction(UUID.randomUUID());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void printInfo(TransactionsLinkedList testTLL, Transaction[] testArr) {
        System.out.println("Transactions List size: " + testTLL.getSize());
        for (int i = 0; i < testArr.length; i++) {
            System.out.println(testArr[i].toString());
        }
    }
}
