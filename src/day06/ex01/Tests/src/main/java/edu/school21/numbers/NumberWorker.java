package edu.school21.numbers;

public class NumberWorker {
    private static final int TENTHLY = 10;

    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException();
        }
        for (int j = 2; j * j <= number; j++) {
            if (number % j == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int res = 0;
        while (number != 0) {
            res += number % TENTHLY;
            number /= TENTHLY;
        }
        return res;
    }

    class IllegalNumberException extends RuntimeException {
        public IllegalNumberException() {
            super("your number is less than 2");
        }
    }
}
