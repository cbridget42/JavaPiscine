package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    NumberWorker numWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {5, 13, 13411, 48353, 108359, 109253})
    void isPrimeForPrimes(int num) {
        Assertions.assertTrue(numWorker.isPrime(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 42, 381, 1366, 4816})
    void isPrimeForNotPrimes(int num) {
        Assertions.assertFalse(numWorker.isPrime(num));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1, -5, -42})
    void isPrimeForIncorrectNumbers(int num) {
        Throwable exception = Assertions.assertThrows(
                NumberWorker.IllegalNumberException.class, () -> numWorker.isPrime(num));
        Assertions.assertEquals("your number is less than 2", exception.getMessage());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv", delimiter = ',')
    void checkDigitsSum(int input, int expected) {
        Assertions.assertEquals(expected, numWorker.digitsSum(input));
    }
}
