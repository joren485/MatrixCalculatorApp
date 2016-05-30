package com.asserttrue.matrixcalculator.model;

public class Rational {

    long numerator;
    long denominator;

    public Rational(long numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Rational(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        simplifyFraction();
    }

    public Rational(Rational r) {
        this.numerator = r.getNumerator();
        this.denominator = r.getDenominator();
    }

    public Rational(String decimal) {
        //TODO Make rational out of string representation of a double.
        throw new UnsupportedOperationException();
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    public void plusIs(Rational r) {
        numerator = denominator * r.getNumerator() + numerator * r.getDenominator();
        denominator *= r.getDenominator();
        simplifyFraction();
    }


    public void minIs(Rational r) {
        numerator = - denominator * r.getNumerator() + numerator * r.getDenominator();
        denominator *= r.getDenominator();
        simplifyFraction();
    }

    public void timesIs(Rational r) {
        numerator *= r.getNumerator();
        denominator *= r.getDenominator();
        simplifyFraction();
    }

    public void divIs(Rational r) {
        numerator *=r.getDenominator();
        denominator *= r.getNumerator();
        simplifyFraction();
    }

    public Rational plus(Rational r) {
        return new Rational(denominator * r.getNumerator() + numerator * r.getDenominator(),
                denominator * r.getDenominator());
    }

    public Rational min(Rational r) {
        return new Rational(denominator * r.getNumerator() + numerator * r.getDenominator(),
                denominator * r.getDenominator());
    }

    public Rational times(Rational r) {
        return new Rational(numerator * r.getNumerator(), denominator * r.getDenominator());
    }

    public Rational div(Rational r) {
        return new Rational(numerator * r.getDenominator(), denominator * r.getNumerator());
    }

    public double toReal() {
        return ((double)numerator) / ((double)denominator);
    }

    public boolean equals(Rational r) {
        return numerator == r.getNumerator() && denominator == r.denominator;
    }

    public boolean greater(Rational r) {
        return numerator * r.getDenominator() > denominator * r.getNumerator();
    }

    public Rational abs() {
        return new Rational(Math.abs(numerator), denominator);
    }

    public Rational inverse() {
        return new Rational(denominator, numerator);
    }

    public Rational negative() {
        return new Rational(- numerator, denominator);
    }

    @Override
    public String toString() {
        return denominator == 1 ? String.valueOf(numerator) : numerator + "/" + denominator;
    }

    private void simplifyFraction() {
        if(numerator == 0) {
            denominator = 1;
            return;
        }

        if(denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        long gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    private long gcd(long n, long d) {
        n = Math.abs(n);
        d = Math.abs(d);

        long min = Math.min(n, d);
        long max = Math.max(n, d);

        while(min != 0) {
            final long tempMax = min;
            min = max % min;
            max = tempMax;
        }

        return max;
    }
}
