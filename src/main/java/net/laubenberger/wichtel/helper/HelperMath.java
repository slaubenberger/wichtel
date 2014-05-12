/*
 * Copyright (c) 2007-2013 by Stefan Laubenberger.
 *
 * "wichtel" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License v3.0.
 *
 * "wichtel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * -----------------------------------------------------------
 * http://www.gnu.org/licenses
 *
 *
 * This distribution is available at:
 * ----------------------------------
 * https://github.com/slaubenberger/wichtel/
 *
 *
 * Contact information:
 * --------------------
 * Stefan Laubenberger
 * Bullingerstrasse 53
 * CH-8004 Zuerich
 *
 * http://www.laubenberger.net
 * laubenberger@gmail.com
 */

package net.laubenberger.wichtel.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for various math problems.
 *
 * @author Stefan Laubenberger
 * @version 0.2.0, 2014-05-12
 * @since 0.0.1
 */
public final class HelperMath { //TODO replace primitive types by BigDecimal/BigInteger
	private static final Logger log = LoggerFactory.getLogger(HelperMath.class);

    private HelperMath() {
        //do nothing
    }

	/**
	 * Returns the greatest common divisor of two given numbers.
	 *
	 * @param a first number
	 * @param b second number
	 * @return calculated gcd
	 * @since 0.0.1
	 */
	public static BigDecimal gcd(final Number a, final Number b) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(a, b));
		if (null == a) {
			throw new RuntimeExceptionIsNull("a"); //$NON-NLS-1$
		}
		if (null == b) {
			throw new RuntimeExceptionIsNull("b"); //$NON-NLS-1$
		}

		final BigDecimal numberA = new BigDecimal(a.toString());
		final BigDecimal numberB = new BigDecimal(b.toString());

		if (0 < BigDecimal.ZERO.compareTo(numberA)) {
			throw new RuntimeExceptionMustBeGreater("a", a, 0); //$NON-NLS-1$
		}
		if (0 < BigDecimal.ZERO.compareTo(numberB)) {
			throw new RuntimeExceptionMustBeGreater("b", b, 0); //$NON-NLS-1$
		}

		if (0 == numberA.compareTo(numberB)) {
			return numberA;
		}

		final BigDecimal result = 0 == BigDecimal.ZERO.compareTo(numberB) ? numberA : gcd(numberB, numberA.remainder(numberB));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the least common multiple of two given numbers.
	 *
	 * @param a first number
	 * @param b second number
	 * @return calculated lcm
	 * @since 0.0.1
	 */
	public static BigDecimal lcm(final Number a, final Number b) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(a, b));
		if (null == a) {
			throw new RuntimeExceptionIsNull("a"); //$NON-NLS-1$
		}
		if (null == b) {
			throw new RuntimeExceptionIsNull("b"); //$NON-NLS-1$
		}

		final BigDecimal numberA = new BigDecimal(a.toString());
		final BigDecimal numberB = new BigDecimal(b.toString());

		if (0 <= BigDecimal.ZERO.compareTo(numberA)) {
			throw new RuntimeExceptionMustBeGreater("a", a, 0); //$NON-NLS-1$
		}
		if (0 <= BigDecimal.ZERO.compareTo(numberB)) {
			throw new RuntimeExceptionMustBeGreater("b", b, 0); //$NON-NLS-1$
		}

		final BigDecimal result = numberA.multiply(numberB, Constants.DEFAULT_MATHCONTEXT).divide(gcd(a, b), Constants.DEFAULT_MATHCONTEXT);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks if a number is a prime.
	 *
	 * @param n number for the check
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean isPrime(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

		boolean result = true;
		if (2L >= n) { // 2 is the smallest prime
			result = 2L == n;
		} else if (0L == n % 2L) { // even numbers other than 2 are not prime
			result = false;
		} else {
			// check odd divisors from 3 to the square root of n
			for (long ii = 3L, end = (long) StrictMath.sqrt(n); ii <= end && result; ii += 2L) {
				if (0L == n % ii) {
					result = false;
				}
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Find the nearest prime >= n.
	 *
	 * @param n number for the check
	 * @return nearest prime number
	 * @since 0.0.1
	 */
	public static long calcNearestPrime(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

		final long result;
		if (2L >= n) { // 2 is the smallest prime
			result = 2L;
		} else {
			long number = n;

			while (!isPrime(number)) {
				number++;
			}
			result = number;
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates a {@link List} containing all primes in a given range.
	 *
	 * @param start number of the range
	 * @param end	number of the range
	 * @return {@link List} containing all calculated prime numbers
	 * @since 0.0.1
	 */
	public static List<Long> calcPrimes(final long start, final long end) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(start, end));
//        if (0 > start) {
//            throw new IllegalArgumentException("start value must be positive: " + start); //$NON-NLS-1$
//        }
		if (0 > end) {
			throw new RuntimeExceptionMustBeGreater("end", end, 0); //$NON-NLS-1$
		}
		if (start > end) {
			throw new IllegalArgumentException("end value (" + end + ") must be greater than the start value (" + start + ')'); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (Integer.MAX_VALUE < end - start + 1) {
			throw new RuntimeExceptionMustBeSmaller("range (end - start + 1)", end - start + 1, Integer.MAX_VALUE); //$NON-NLS-1$
		}

		final List<Long> result = new ArrayList<>((int) (end - start + 1));

		for (long ii = start; ii <= end; ii++) {
			if (isPrime(ii)) {
				result.add(ii);
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

//	/**
//	 * Convert a double to an int-value.
//	 * Rounds the double value with {@link Math#round(double)}.
//	 *
//	 * @param value double-value
//	 * @return int-value
//	 * @since 0.0.1
//	 */
//	public static int convertDoubleToInt(final double value) { //$JUnit$
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(value));
//
//		final int result = (int) StrictMath.round(value);
//
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
//		return result;
//	}

	/**
	 * Calculate the logarithm with a given base.
	 *
	 * @param base  of the logarithm
	 * @param value double-value
	 * @return double-value
	 * @since 0.0.1
	 */
	public static double log(final double base, final double value) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(base, value));
		if (1.0D >= base) {
			throw new RuntimeExceptionMustBeGreater("base", base, 1); //$NON-NLS-1$
		}
		if (0.0D >= value) {
			throw new RuntimeExceptionMustBeGreater("value", value, 0); //$NON-NLS-1$
		}

		final double result = StrictMath.log(value) / StrictMath.log(base);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Round a double with decimal place.
	 *
	 * @param value		  double-value
	 * @param decimalPlace for round
	 * @return double-value
	 * @since 0.0.1
	 */
	public static double round(final double value, final int decimalPlace) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(value, decimalPlace));

		int dp = decimalPlace;
		double powerOfTen = 1.0D;

		while (0 < dp) {
			dp--;
			powerOfTen *= 10.0D;
		}

		final double result = StrictMath.round(value * powerOfTen) / powerOfTen;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

    /**
     * Returns a random int between 0 and n-1.
     *
     * @param n int-Value
     * @return random int-value between 0 and n-1
     * @since 0.0.1
     */
    public static int getRandom(final int n) { //$JUnit$
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

        if (0 > n) {
            throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
        }

        final int result = (int) (StrictMath.random() * n);

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
	 * Returns a random long between 0 and n-1.
	 *
	 * @param n long-Value
	 * @return random long-value between 0 and n-1
	 * @since 0.2.0
	 */
	public static long getRandom(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

        if (0L > n) {
            throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
        }

        final long result = (long) (StrictMath.random() * n);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

    /**
     * Returns a random double between 0 and n-1.
     *
     * @param n double-Value
     * @return random double-value between 0 and n-1
     * @since 0.2.0
     */
    public static double getRandom(final double n) {
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

        if (0D > n) {
            throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
        }

        final double result = StrictMath.random() * n;

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
     * Returns a random integer between start and end-1.
     *
     * @param start int start value
     * @param end int end value
     * @return random int-value between 0 and n-1
     * @since 0.2.0
     */
    public static int getRandom(final int start, final int end) {
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(start, end));

        if (start > end) {
            throw new IllegalArgumentException("end value (" + end + ") must be greater than the start value (" + start + ')'); //$NON-NLS-1$ //$NON-NLS-2$
        }

        int result = -1;

        while(result < start || result > end) {
            result = getRandom(end);
        }

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
     * Returns a random long between start and end-1.
     *
     * @param start long start value
     * @param end long end value
     * @return random long-value between 0 and n-1
     * @since 0.2.0
     */
    public static long getRandom(final long start, final long end) {
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(start, end));

        if (start > end) {
            throw new IllegalArgumentException("end value (" + end + ") must be greater than the start value (" + start + ')'); //$NON-NLS-1$ //$NON-NLS-2$
        }

        long result = -1;

        while(result < start || result > end) {
            result = getRandom(end);
        }

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
     * Returns a random double between start and end-1.
     *
     * @param start double start value
     * @param end double end value
     * @return random double-value between 0 and n-1
     * @since 0.2.0
     */
    public static double getRandom(final double start, final double end) {
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(start, end));

        if (start > end) {
            throw new IllegalArgumentException("end value (" + end + ") must be greater than the start value (" + start + ')'); //$NON-NLS-1$ //$NON-NLS-2$
        }

        double result = -1f;

        while(result < start || result > end) {
            result = getRandom(end);
        }

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

	/**
	 * Returns the amount with a given start amount, interest and elapsed days.
	 * Note: the interest p.a. is based on 360 days
	 *
	 * @param amount	start
	 * @param interest in % p.a. (e.g. 0.04)
	 * @param days	  elapsed
	 * @return calculated amount with a given start amount, interest and elapsed days
	 * @since 0.0.1
	 */
	public static double calcAmount(final double amount, final double interest, final int days) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(amount, interest, days));

		if (0 >= days) {
			throw new RuntimeExceptionMustBeGreater("days", days, 0); //$NON-NLS-1$
		}

		final double result = amount * StrictMath.pow(StrictMath.E, days / 360.0D * interest);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the factorial (n!) from 0 to n.
	 *
	 * @param n number to calculate
	 * @return factorial (n!) from 1 to n
	 * @since 0.0.1
	 */
	public static BigInteger factorial(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));
		if (0L > n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}

		BigInteger result = BigInteger.ONE;

		for (long ii = n; 0L < ii; ii--) {
			result = result.multiply(BigInteger.valueOf(ii));
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the Gauss sum from 0 to n.
	 *
	 * @param n number to calculate
	 * @return Gauss sum from 1 to n
	 * @since 0.0.1
	 */
	public static long sum(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

		if (0L > n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}

		if (0L == n) {
			return 1L;
		}

		final long result = n * (n + 1L) / 2L;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the Gauss square sum from 0 to n^2.
	 *
	 * @param n number to calculate
	 * @return Gauss square sum from 1 to n^2
	 * @since 0.0.1
	 */
	public static long sumSquare(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

		if (0L > n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}

		if (0L == n) {
			return 1L;
		}

		final long result = n * (n + 1L) * (2L * n + 1L) / 6L;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the Gauss cubic sum from 0 to n^3.
	 *
	 * @param n number to calculate
	 * @return Gauss cubic sum from 1 to n^3
	 * @since 0.0.1
	 */
	public static long sumCubic(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

		if (0L > n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}

		if (0L == n) {
			return 1L;
		}

		final long result = (long) StrictMath.pow(sum(n), 2.0D);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the sum between the range m - n.
	 *
	 * @param m start number for the range
	 * @param n end number for the range
	 * @return sum between the range m - n
	 * @since 0.0.1
	 */
	public static long sumRange(final long m, final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(m, n));
		if (0L > m) {
			throw new RuntimeExceptionMustBeGreater("m", m, 0); //$NON-NLS-1$
		}
		if (0L > n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}
		if (m > n) {
			throw new IllegalArgumentException("n value (" + n + ") must be greater than the m value (" + m + ')'); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final long result = (m + n) * (n - m + 1L) / 2L;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

    /**
     * Calculates the sum of odd numbers between 0 - n.
     *
     * @param n distance for the range (e.g. 3 means 1+3+5=9)
     * @return sum of odd numbers between 0 - n
     * @since 0.0.1
     */
    public static long sumOdd(final long n) { //$JUnit$
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));
        if (0L > n) {
            throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
        }

        final long result = (long) StrictMath.pow(n, 2.0D);

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
     * Calculates the sum of even numbers between 0 - n.
     *
     * @param n distance for the range (e.g. 3 means 2+4+6=12)
     * @return sum of even numbers between 0 - n
     * @since 0.0.1
     */
    public static long sumEven(final long n) { //$JUnit$
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));
        if (0L > n) {
            throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
        }

        long result = 0L;
        for (long ii = 2L; ii <= n * 2L; ii += 2L) {
            result += ii;
        }

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
     * Checks if the given number is odd.
     *
     * @param n number to check
     * @return true/false
     * @since 0.0.1
     */
    public static boolean isOdd(final long n) { //$JUnit$
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

        final boolean result = 0L != n % 2L;

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

    /**
     * Checks if the given number is even.
     *
     * @param n number to check
     * @return true/false
     * @since 0.0.1
     */
    public static boolean isEven(final long n) { //$JUnit$
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));

        final boolean result = !isOdd(n);

        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }

	/**
	 * Calculates the number of connections from n.
	 *
	 * @param n number to calculate
	 * @return number of connections from n
	 * @since 0.0.1
	 */
	public static long calcConnections(final long n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));
		if (0L >= n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}

		final long result = n * (n - 1L) / 2L;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates for the given number of persons the probability that two people will share a birthday.
	 *
	 * @param n number of persons
	 * @return probability that two people will share a birthday
	 * @since 0.0.1
	 */
	public static double calcBirthdayProblem(final int n) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n));
		if (0 >= n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}

		//TODO does it also work for other parameters than days p.a.?

		final BigDecimal term01 = new BigDecimal(factorial(HelperTime.DAYS_PER_YEAR));
		final BigDecimal term02 = new BigDecimal(factorial(HelperTime.DAYS_PER_YEAR - n));
		final BigDecimal term03 = BigDecimal.valueOf(HelperTime.DAYS_PER_YEAR).pow(n);

		final BigDecimal result = BigDecimal.ONE.subtract(term01.divide(term02.multiply(term03, Constants.DEFAULT_MATHCONTEXT), Constants.DEFAULT_MATHCONTEXT));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result.doubleValue();
	}

	/**
	 * Calculates the binomial coefficient ("n choose k").
	 *
	 * @param n range
	 * @param k subset of n ("n choose k")
	 * @return binomial coefficient
	 * @since 0.0.1
	 */
	public static BigInteger binomialCoefficient(final long n, final long k) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(n, k));
		if (0L > n) {
			throw new RuntimeExceptionMustBeGreater("n", n, 0); //$NON-NLS-1$
		}
		if (0L > k) {
			throw new RuntimeExceptionMustBeGreater("k", k, 0); //$NON-NLS-1$
		}
		if (k > n) {
			throw new IllegalArgumentException("n value (" + n + ") must be greater than the k value (" + k + ')'); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final BigInteger result = factorial(n).divide(((factorial(n - k).multiply(factorial(k)))));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}

