package com.damytec.vivendasvagas.pojo;

/**
 * @author lgdamy on 25/01/2021
 */
public class VivendasvagasPojo {

    private int randomNumber;

    public VivendasvagasPojo(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public boolean isEven() {
        return randomNumber % 2 == 0;
    }

    public boolean isOdd() {
        return randomNumber % 2 != 0;
    }

    public boolean isPrime() {
        return java.math.BigInteger.valueOf(randomNumber).isProbablePrime(10);
    }
}
