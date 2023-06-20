package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    NumberWorker numWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {5, 13, 13411, 48353, 108359, 109253})
    void isPrimeForPrimes(int num) {
        Assertions.assertTrue(numWorker.isPrime(num));
    }

}
