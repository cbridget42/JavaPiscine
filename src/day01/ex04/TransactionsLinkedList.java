package day01.ex04;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private final Node head;
    private Integer size;

    public TransactionsLinkedList() {
        this.head = new Node(null, null, null);
        this.size = 0;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (size == 0) {
            Node tmp = new Node(transaction, head, head);
            head.prev = tmp;
            head.next = tmp;
        } else {
            Node tmp = new Node(transaction, head.prev, head);
            head.prev.next = tmp;
            head.prev = tmp;
        }
        ++size;
    }

    @Override
    public void removeTransaction(UUID id) {
        Node tmp = head.next;
        for (int i = 0; i < size; i++) {
            if (tmp.item.getIdentifier().equals(id)) {
                tmp.next.prev = tmp.prev;
                tmp.prev.next = tmp.next;
                --size;
                return;
            }
            tmp = tmp.next;
        }
        throw new TransactionNotFoundException("Invalid transaction id!");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] res = new Transaction[size];
        Node tmp = head.next;
        for (int i = 0; i < size; i++) {
            res[i] = tmp.item;
            tmp = tmp.next;
        }
        return res;
    }

    public Integer getSize() {return this.size;}

    private class Node {
        private final Transaction item;
        private Node prev;
        private Node next;

        public Node(Transaction item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
