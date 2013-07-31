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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.junit.Test;


/**
 * JUnit test for {@link HelperMath}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperMathTest {
	@Test
	public void testGcd() {
		assertEquals(0, new BigDecimal("2").compareTo(HelperMath.gcd(new BigDecimal("2"), new BigDecimal("4"))));	//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		assertEquals(0, new BigDecimal("2").compareTo(HelperMath.gcd(new BigDecimal("2"), new BigDecimal("2")))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(0, new BigDecimal("2").compareTo(HelperMath.gcd(new BigDecimal("4"), new BigDecimal("2"))));	//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		assertEquals(0, new BigDecimal("2.5").compareTo(HelperMath.gcd(new BigDecimal("2.5"), new BigDecimal("5"))));  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		assertEquals(0, BigDecimal.ZERO.compareTo(HelperMath.gcd(BigDecimal.ZERO, BigDecimal.ZERO)));

        try {
            HelperMath.gcd(null, BigDecimal.ZERO);
            fail("a is null"); //$NON-NLS-1$
        } catch (IllegalArgumentException ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
        try {
			HelperMath.gcd(new BigDecimal("-2"), BigDecimal.ZERO); //$NON-NLS-1$
			fail("a is negative"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

        try {
            HelperMath.gcd(BigDecimal.ZERO, null);
            fail("b is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
		try {
			HelperMath.gcd(BigDecimal.ZERO, new BigDecimal("-2")); //$NON-NLS-1$
			fail("b is negative"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testLcm() {
		assertEquals(0, BigDecimal.TEN.compareTo(HelperMath.lcm(new BigDecimal("2"), new BigDecimal("5"))));  //$NON-NLS-1$//$NON-NLS-2$
		assertEquals(0, BigDecimal.TEN.compareTo(HelperMath.lcm(new BigDecimal("5"), new BigDecimal("2")))); //$NON-NLS-1$ //$NON-NLS-2$

        try {
            HelperMath.lcm(null, BigDecimal.ZERO);
            fail("a is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
		try {
			HelperMath.lcm(new BigDecimal("-2"), BigDecimal.ZERO); //$NON-NLS-1$
			fail("a is negative"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

        try {
            HelperMath.lcm(BigDecimal.ZERO, null);
            fail("b is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
		try {
			HelperMath.lcm(BigDecimal.ZERO, new BigDecimal("-2")); //$NON-NLS-1$
			fail("b is negative"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testIsPrime() {
		assertFalse(HelperMath.isPrime(-1));
		assertFalse(HelperMath.isPrime(0));
		assertFalse(HelperMath.isPrime(1));
		assertTrue(HelperMath.isPrime(2));
		assertFalse(HelperMath.isPrime(21));
		assertTrue(HelperMath.isPrime(23));
		assertTrue(HelperMath.isPrime(997));
	}

	@Test
	public void testCalcNearestPrime() {
		assertEquals(23, HelperMath.calcNearestPrime(21));
		assertEquals(43, HelperMath.calcNearestPrime(42));
		assertEquals(2, HelperMath.calcNearestPrime(-23));
		assertEquals(2, HelperMath.calcNearestPrime(0));
	}

	@Test
	public void testCalcPrimes() {
		assertEquals(168, HelperMath.calcPrimes(0, 1000).size());
		assertEquals(168, HelperMath.calcPrimes(-1000, 1000).size());
		assertEquals(0, HelperMath.calcPrimes(0, 0).size());
		assertEquals(2, HelperMath.calcPrimes(0, 3).size());

		try {
			HelperMath.calcPrimes(50, -10);
			fail("end value (-10) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperMath.calcPrimes(50, 10);
			fail("end value (10) must be greater than the start value (50)"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

//	@Test
//	public void testConvertDoubleToInt() {
//		assertEquals(-2, HelperMath.convertDoubleToInt(-2.499D));
//		assertEquals(-2, HelperMath.convertDoubleToInt(-2.5D));
//		assertEquals(-3, HelperMath.convertDoubleToInt(-2.51D));
//		assertEquals(2, HelperMath.convertDoubleToInt(2.499D));
//		assertEquals(3, HelperMath.convertDoubleToInt(2.5D));
//		assertEquals(0, HelperMath.convertDoubleToInt(0.0D));
//	}

	@Test
	public void testLog() {
		assertEquals(2.0D, HelperMath.log(10.0D, 100.0D), 0.00001D);
		assertEquals(0.0D, HelperMath.log(2.0D, 1.0D), 0.00001D);
		assertEquals(1.0D, HelperMath.log(2.0D, 2.0D), 0.00001D);
		assertEquals(4.19180654857877D, HelperMath.log(3.0, 100.0), 0.00001D);

		try {
			HelperMath.log(1.0D, 100.0D);
			fail("base must be greater than 1"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperMath.log(10.0D, -100.0D);
			fail("value (-100) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testRound() {
		assertEquals(0.0D, HelperMath.round(0.0D, 4), 0.00001D);
		assertEquals(-2.0D, HelperMath.round(-2.499D, 0), 0.00001D);
		assertEquals(-2.0D, HelperMath.round(-2.5D, 0), 0.00001D);
		assertEquals(-3.0D, HelperMath.round(-2.51D, 0), 0.00001D);
		assertEquals(-2.55D, HelperMath.round(-2.554D, 2), 0.00001D);
		assertEquals(-2.55D, HelperMath.round(-2.546D, 2), 0.00001D);
		assertEquals(2.0D, HelperMath.round(2.499D, 0), 0.00001D);
		assertEquals(3.0D, HelperMath.round(2.5D, 0), 0.00001D);
		assertEquals(2.55D, HelperMath.round(2.554D, 2), 0.00001D);
		assertEquals(2.55D, HelperMath.round(2.545D, 2), 0.00001D);
		assertEquals(3.0D, HelperMath.round(2.545D, -2), 0.00001D);
	}

	@Test
	public void testRandom() {
	    assertEquals(0, HelperMath.getRandom(0));
	    
		//positive numbers
		int range = Integer.MAX_VALUE;
		for (int ii = 0; 1000 > ii; ii++) {
			final int number = HelperMath.getRandom(range);

			assertTrue(0 <= number && range >= number);
		}

		//negative numbers
		range = Integer.MIN_VALUE;
		for (int ii = 0; 1000 > ii; ii++) {
			final int number = HelperMath.getRandom(range);

			assertTrue(0 >= number && range <= number);
		}
	}

	@Test
	public void testCalcAmount() {
		assertEquals(10055.710162720176D, HelperMath.calcAmount(10000.0D, 0.04D, 50), 0.00001D);
		assertEquals(9944.598480048968D, HelperMath.calcAmount(10000.0D, -0.04D, 50), 0.00001D);
		assertEquals(-9944.598480048968D, HelperMath.calcAmount(-10000.0D, -0.04D, 50), 0.00001D);
		assertEquals(-10055.710162720176D, HelperMath.calcAmount(-10000.0D, 0.04D, 50), 0.00001D);

		try {
			HelperMath.calcAmount(10000.0D, 0.04D, -50);
			fail("days (-50) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testFactorial() {
		assertEquals(BigInteger.valueOf(24), HelperMath.factorial(4L));
		assertEquals(BigInteger.ONE, HelperMath.factorial(0L));

		try {
			HelperMath.factorial(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testSum() {
		assertEquals(10L, HelperMath.sum(4L));
		assertEquals(1L, HelperMath.sum(0L));

		try {
			HelperMath.sum(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testSumSquare() {
		assertEquals(30L, HelperMath.sumSquare(4L));
		assertEquals(1L, HelperMath.sumSquare(0L));

		try {
			HelperMath.sumSquare(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testSumCubic() {
		assertEquals(100L, HelperMath.sumCubic(4L));
		assertEquals(1L, HelperMath.sumCubic(0L));

		try {
			HelperMath.sumCubic(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

    @Test
    public void testSumRange() {
        assertEquals(55L, HelperMath.sumRange(0L, 10L));
        assertEquals(0L, HelperMath.sumRange(0L, 0L));

        try {
            HelperMath.sumRange(-1L, 0L);
            fail("m (-1) must be positive"); //$NON-NLS-1$
        } catch (RuntimeExceptionMustBeGreater ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        try {
            HelperMath.sumRange(0L, -1L);
            fail("n (-1) must be positive"); //$NON-NLS-1$
        } catch (RuntimeExceptionMustBeGreater ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
	@Test
	public void testSumOdd() {
		assertEquals(9L, HelperMath.sumOdd(3L));
		assertEquals(0L, HelperMath.sumOdd(0L));

		try {
			HelperMath.sumOdd(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testSumEven() {
		assertEquals(12L, HelperMath.sumEven(3L));
		assertEquals(0L, HelperMath.sumEven(0L));

		try {
			HelperMath.sumEven(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testIsOdd() {
		assertFalse(HelperMath.isOdd(4L));
		assertTrue(HelperMath.isOdd(3L));
	}

	@Test
	public void testIsEven() {
		assertTrue(HelperMath.isEven(4L));
		assertFalse(HelperMath.isEven(3L));
	}

	@Test
	public void testCalcConnections() {
		assertEquals(6L, HelperMath.calcConnections(4L));
        
		try {
            HelperMath.calcConnections(0L);
            fail("n (0) must be greater than 0"); //$NON-NLS-1$
        } catch (RuntimeExceptionMustBeGreater ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
		try {
			HelperMath.calcConnections(-1L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

    @Test
    public void testCalcBirthdayProblem() {
//System.err.println(HelperMath.calcBirthdayProblem(3)*100);
//System.err.println(HelperMath.calcBirthdayProblem(4)*100);
//System.err.println(HelperMath.calcBirthdayProblem(5)*100);
//System.err.println(HelperMath.calcBirthdayProblem(7)*100);
//System.err.println(HelperMath.calcBirthdayProblem(8)*100);
//System.err.println(HelperMath.calcBirthdayProblem(12)*100);
//System.err.println(HelperMath.calcBirthdayProblem(16)*100);
//System.err.println(HelperMath.calcBirthdayProblem(20)*100);
//System.err.println(HelperMath.calcBirthdayProblem(24)*100);
//System.err.println(HelperMath.calcBirthdayProblem(32)*100);
//System.err.println(HelperMath.calcBirthdayProblem(48)*100);

   	 assertEquals(0.50729D, HelperMath.calcBirthdayProblem(23), 0.00001D);
        
        try {
            HelperMath.calcBirthdayProblem(0);
            fail("n (0) must be greater than 0"); //$NON-NLS-1$
        } catch (RuntimeExceptionMustBeGreater ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
        
        try {
            HelperMath.calcBirthdayProblem(-1);
            fail("n (-1) must be positive"); //$NON-NLS-1$
        } catch (RuntimeExceptionMustBeGreater ex) {
            //nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
    
    @Test
	public void tesBinomialCoefficient() {
		assertEquals(BigInteger.valueOf(35), HelperMath.binomialCoefficient(7L, 3L));
		assertEquals(BigInteger.ONE, HelperMath.binomialCoefficient(0L, 0L));
        
        try {
			HelperMath.binomialCoefficient(-1L, 4L);
			fail("n (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

        try {
			HelperMath.binomialCoefficient(3L, -1L);
			fail("k (-1) must be positive"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperMath.binomialCoefficient(3L, 4L);
			fail("n (3) must be greater than k (4)"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
