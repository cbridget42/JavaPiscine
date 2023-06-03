package day01.ex03;

import java.util.UUID;

public class Transaction {
    private final UUID identifier;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private Integer transferAmount;

    enum Category {
        DEBIT,
        CREDIT
    }

    public Transaction(User recipient, User sender, Integer transferAmount, Category transferCategory) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = transferCategory;
        if ((transferCategory == Category.DEBIT && sender.getBalance() - transferAmount >= 0 &&
                transferAmount > 0) ||
                (transferCategory == Category.CREDIT && recipient.getBalance() + transferAmount >= 0 &&
                transferAmount < 0)) {
            this.transferAmount = transferAmount;
        } else {
            this.transferAmount = 0;
        }
        performTransaction();
    }

    private void performTransaction() {
        recipient.setBalance(recipient.getBalance() + this.transferAmount);
        sender.setBalance(sender.getBalance() - this.transferAmount);
    }

    @Override
    public String toString() {
        String transfer = (transferCategory == Category.DEBIT) ? "INCOME" : "OUTCOME";
        return String.format("%s -> %s, %d, %s, %s%n",
                sender.getName(), recipient.getName(), transferAmount, transfer, identifier.toString());
    }

    public UUID getIdentifier() {return this.identifier;}
    public User getRecipient() {return this.recipient;}
    public void setRecipient(User recipient) {this.recipient = recipient;}
    public User getSender() {return this.sender;}
    public void setSender(User sender) {this.sender = sender;}
    public Category getTransferCategory() {return this.transferCategory;}
    public void setTransferCategory(Category transferCategory) {this.transferCategory = transferCategory;}
    public Integer getTransferAmount() {return this.transferAmount;}
    public void setTransferAmount(Integer transferAmount) {this.transferAmount = transferAmount;}
}
