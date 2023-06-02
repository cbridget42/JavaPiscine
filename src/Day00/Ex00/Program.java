package Day00.Ex00;

public class Program {
    private static final int TENTHLY = 10;
    private static final int HUNDREDS = 100;
    private static final int THOUSANDS = 1000;
    private static final int TENS_OF_THOUSANDS = 10000;
    private static final int HUNDREDS_OF_THOUSANDS = 100000;

    public static void main(String[] args) {
        int number = 479598;
        int res = 0;
        res += number % TENTHLY;
        res += number / TENTHLY % TENTHLY;
        res += number / HUNDREDS % TENTHLY;
        res += number / THOUSANDS % TENTHLY;
        res += number / TENS_OF_THOUSANDS % TENTHLY;
        res += number / HUNDREDS_OF_THOUSANDS % TENTHLY;
        System.out.println(res);
    }
}
