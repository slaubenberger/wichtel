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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for objects.
 *
 * @author Stefan Laubenberger
 * @version 0.2.0, 2014-05-12
 * @since 0.0.1
 */
public final class HelperObject {
	private static final Logger log = LoggerFactory.getLogger(HelperObject.class);

    private HelperObject() {
        //do nothing
    }

	/**
	 * Creates an instance of a class.
	 *
	 * @param clazz full qualified class name
	 * @return instantiated object
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @see Class
	 * @since 0.0.1
	 */
	public static <T> T createInstance(final Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(clazz));

		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}

		final T result = clazz.getConstructor().newInstance();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates an instance of a class with parameters.
	 *
	 * @param clazz		  full qualified class name
	 * @param paramClazzes classes for the constructor
	 * @param params		 parameters for the constructor
	 * @return instantiated object
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @see Class
	 * @since 0.0.1
	 */
	public static <T> T createInstance(final Class<T> clazz, final Class<?>[] paramClazzes, final Object... params) throws SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(clazz, paramClazzes, params));
		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}
		if (null == paramClazzes) {
			throw new RuntimeExceptionIsNull("paramClazzes"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(paramClazzes)) {
			throw new RuntimeExceptionIsEmpty("paramClazzes"); //$NON-NLS-1$
		}
		if (null == params) {
			throw new RuntimeExceptionIsNull("params"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(params)) {
			throw new RuntimeExceptionIsEmpty("params"); //$NON-NLS-1$
		}

		final T result = clazz.getConstructor(paramClazzes).newInstance(params);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Serialize an {@link Object} into a byte-array.
	 *
	 * @param object to convert into a byte-array.
	 * @return object as byte-array
	 * @throws IOException
	 * @see Serializable
	 * @since 0.0.1
	 */
	public static byte[] serialize(final Serializable object) throws IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(object));
		if (null == object) {
			throw new RuntimeExceptionIsNull("object"); //$NON-NLS-1$
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos)){
			oos.writeObject(object);
			oos.flush();

			final byte[] result = baos.toByteArray();

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}

	/**
	 * Deserialize a given byte-array into an {@link Object}.
	 *
	 * @param data byte-array to convert into an {@link Object}
	 * @return {@link Object} from given byte-array
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @since 0.0.1
	 */
	public static Object deserialize(final byte... data) throws IOException, ClassNotFoundException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(data));
		if (null == data) {
			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(data)) {
			throw new RuntimeExceptionIsEmpty("data"); //$NON-NLS-1$
		}

		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
			final Object result = ois.readObject();

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}

	/**
	 * Deserialize a given byte-array into an {@link Object} from the given {@link Class}.
	 *
	 * @param clazz full qualified class name
	 * @param data  byte-array to convert into an {@link Object}
	 * @return {@link Object} from given byte-array and {@link Class}
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @since 0.0.1
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(final Class<T> clazz, final byte... data) throws IOException, ClassNotFoundException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(clazz, data));
		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}

		final T result = (T) deserialize(data);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
    /**
     * Clones an object via serialize (deep clone).
     *
     * @param original to clone
     * @return cloned object
     * @throws ClassNotFoundException
     * @since 0.0.1
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(final T original) throws IOException, ClassNotFoundException { //$JUnit$
        if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(original));
        if (null == original) {
            throw new RuntimeExceptionIsNull("original"); //$NON-NLS-1$
        }

        T result = null;
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
      		  CloneOutput co = new CloneOutput(baos)) {
            co.writeObject(original);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            		ObjectInputStream ois = new CloneInput(bais, co)) {
            	result = (T) ois.readObject();
            }
        }
        if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
        return result;
    }
    
	/**
	 * Checks if a {@link Class} implements the given method name.
	 *
	 * @param clazz		for searching
	 * @param methodName to check
	 * @return true/false
	 * @see Class
	 * @since 0.0.1
	 */
	public static boolean isMethodAvailable(final Class<?> clazz, final String methodName) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(clazz, methodName));
		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}
		if (null == methodName) {
			throw new RuntimeExceptionIsNull("methodName"); //$NON-NLS-1$
		}

		final Method[] methods = clazz.getMethods();
		boolean result = false;

		for (final Method method : methods) {
			if (isEquals(method.getName(), methodName)) {
				result = true;
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}


	//TODO replace by dump
//	/**
//	 * Generic toString() method for {@link Object} and different purposes.
//	 *
//	 * @param object to dump
//	 * @return dumped object string
//	 * @since 0.0.1
//	 */
//	public static String toString(final Object object) {
//		if (null == object) {
//			throw new RuntimeExceptionIsNull("object"); //$NON-NLS-1$
//		}
//
//		final Collection<String> list = new ArrayList<String>();
//		toString(object, list);
//
//		return object.getClass().getName() + list.toString();
//	}

	/**
	 * Compare if two objects are equals.
	 *
	 * @param objectA first object to compare
	 * @param objectB second object to compare
	 * @return true/false
	 * @since 0.0.1
	 */
	public static <T> boolean isEquals(final T objectA, final T objectB) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(objectA, objectB));

		final boolean result = !((null == objectB && null != objectA) || (null != objectB && !objectB.equals(objectA)));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

//	/**
//	 * Quotes a given {@link Object} for different purposes (e.g. logging).
//	 *
//	 * @param object to quote
//	 * @return quoted object string
//	 * @since 0.0.1
//	 */
//	public static String quote(final Object object) { //$JUnit$
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(object));
//		if (null == object) {
//			throw new RuntimeExceptionIsNull("object"); //$NON-NLS-1$
//		}
//
//		final String result = HelperString.SINGLE_QUOTE + String.valueOf(object) + HelperString.SINGLE_QUOTE;
//
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
//		return result;
//	}


	/*
	 * Private methods
	 */

//	private static void toString(final Object object, final Collection<String> list) {
//		final Field[] fields = object.getClass().getDeclaredFields();
//		AccessibleObject.setAccessible(fields, true);
//
//		for (final Field field : fields) {
//			try {
////    			if (objectField == object ||
////            		(objectField instanceof Collection<?> && ((Collection<?>)objectField).contains(object)) ||
////            		(objectField instanceof Map<?, ?> && (((Map<?, ?>)objectField).containsKey(object) || ((Map<?, ?>)objectField).containsValue(object)))) {
////            		list.add(field.getName() + '=' + object.getClass().getName());
////              	} else {
////	               list.add(field.getName() + '=' + objectField);
////	            }
//
//				if (isEquals(field.getType(), object.getClass())) {
//					list.add(field.getName() + '=' + object.getClass().getName());
////       			} else if (objectField instanceof Collection<?> && ((Collection<?>)objectField).contains(object)) {
////       				System.out.println("COLL");
////            		list.add(field.getName() + '=' + object.getClass().getName());
////       			} else if (objectField instanceof Map<?, ?> && (((Map<?, ?>)objectField).containsKey(object) | ((Map<?, ?>)objectField).containsValue(object))) {
//////       			} else if (objectField instanceof Map<?, ?>) {
////       				System.out.println("MAP");
////               		list.add(field.getName() + '=' + object.getClass().getName());
//				} else {
//					list.add(field.getName() + '=' + field.get(object));
//				}
//
//			} catch (Exception ex) {
//				// do nothing
//			}
//		}
//
////    	if (clazz.getSuperclass().getSuperclass() != null) {
//////    	if (clazz.getSuperclass() != null) {
////    		toString(object, clazz.getSuperclass(), list);
////    	}
//	}


	/*
	 * Inner classes
	 */

	private static class CloneOutput extends ObjectOutputStream {
		final Queue<Class<?>> classQueue = new LinkedList<>();

		CloneOutput(final OutputStream os) throws IOException {
			super(os);
		}

		@Override
		protected void annotateClass(final Class<?> c) {
			classQueue.add(c);
		}

		@Override
		protected void annotateProxyClass(final Class<?> c) {
			classQueue.add(c);
		}
	}

	private static class CloneInput extends ObjectInputStream {
		private final CloneOutput output;

		CloneInput(final InputStream is, final CloneOutput output) throws IOException {
			super(is);
			this.output = output;
		}

		@Override
		protected Class<?> resolveClass(final ObjectStreamClass osc) throws IOException, ClassNotFoundException {
			final Class<?> c = output.classQueue.poll();
			final String expected = osc.getName();
			final String found = (null == c) ? null : c.getName();
			if (!isEquals(expected, found)) {
				throw new InvalidClassException("Classes desynchronized - found: " + found + " when expecting: " + expected); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return c;
		}

		@Override
		protected Class<?> resolveProxyClass(final String[] interfaceNames) throws IOException, ClassNotFoundException {
			return output.classQueue.poll();
		}
	}
}