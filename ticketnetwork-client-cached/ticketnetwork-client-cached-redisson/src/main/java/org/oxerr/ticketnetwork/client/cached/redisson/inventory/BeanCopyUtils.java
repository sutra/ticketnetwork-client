package org.oxerr.ticketnetwork.client.cached.redisson.inventory;

import org.apache.commons.beanutils2.BeanUtils;
import org.apache.commons.beanutils2.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public final class BeanCopyUtils {

	private BeanCopyUtils() {
		throw new AssertionError("No instances for you!");
	}

	public static void copyNonNullProperties(Object source, Object target)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> properties = PropertyUtils.describe(source);
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String propertyName = entry.getKey();
			Object value = entry.getValue();
			if (value != null && !"class".equals(propertyName)) {
				BeanUtils.copyProperty(target, propertyName, value);
			}
		}
	}

}
