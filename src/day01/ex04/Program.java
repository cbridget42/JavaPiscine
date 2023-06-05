package day01.ex04;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        service.addUser("Mike", 42000);
        service.addUser("Julia", 21000);
        service.addUser("Kate", 10000);

        service.performTransaction(3, 1, 1000);
        service.performTransaction(1, 2, 999);
        printTransactions(service.getUserTransfers(1), service.getUserBalance(1), "Mike");
        System.out.println();
        printTransactions(service.getUserTransfers(2), service.getUserBalance(2), "Julia");
        System.out.println();
        printTransactions(service.getUserTransfers(3), service.getUserBalance(3), "Kate");

        Transaction[] unpaired = service.unpairedTransactions();
        System.out.printf("%nunpaired transactions size: %d%n", unpaired.length);
        UUID transId = service.getUserTransfers(1)[0].getIdentifier();
        service.removeTransaction(1, transId);
        unpaired = service.unpairedTransactions();
        System.out.printf("unpaired transactions size(after remove one transaction): %d%n%s", unpaired.length, unpaired[0].toString());
        System.out.println();
        printTransactions(service.getUserTransfers(1), service.getUserBalance(1), "Mike");
    }

    private static void printTransactions(Transaction[] trans, int balance, String name) {
        System.out.printf("User: %s has balance %d and transcactions:%n", name, balance);
        for (int i = 0; i < trans.length; i++) {
            System.out.print(trans[i].toString());
        }
    }
}
