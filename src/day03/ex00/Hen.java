package day03.ex00;

public class Hen extends Thread {
    private final int count;

    public Hen(int count) {
        super();
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Hen");
        }
    }
}
