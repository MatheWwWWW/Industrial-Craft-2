/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.util;

import ic2.core.IC2;
import ic2.core.network.DataEncoder;
import ic2.core.util.LogCategory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    public static Field getField(Class<?> clazz, String string) {
        try {
            Field field = clazz.getDeclaredField(string);
            field.setAccessible(true);
            return field;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new RuntimeException(reflectiveOperationException);
        }
    }

    public static Field getField(Class<?> clazz, String ... stringArray) {
        for (String string : stringArray) {
            try {
                Field field = clazz.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
            }
            catch (SecurityException securityException) {
                throw new RuntimeException(securityException);
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, Class<?> clazz2) {
        Field field = null;
        for (Field field2 : clazz.getDeclaredFields()) {
            if (!clazz2.isAssignableFrom(field2.getType())) continue;
            if (field != null) {
                return null;
            }
            field2.setAccessible(true);
            field = field2;
        }
        return field;
    }

    public static Field getFieldRecursive(Class<?> clazz, String string) {
        Field field = null;
        do {
            try {
                field = clazz.getDeclaredField(string);
                field.setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {
                clazz = clazz.getSuperclass();
            }
        } while (field == null && clazz != null);
        return field;
    }

    public static Field getFieldRecursive(Class<?> clazz, Class<?> clazz2, boolean bl) {
        Field field = null;
        do {
            for (Field field2 : clazz.getDeclaredFields()) {
                if (!clazz2.isAssignableFrom(field2.getType())) continue;
                if (!bl) {
                    field2.setAccessible(true);
                    return field2;
                }
                if (field != null) {
                    return null;
                }
                field2.setAccessible(true);
                field = field2;
            }
            clazz = clazz.getSuperclass();
        } while (field == null && clazz != null);
        return field;
    }

    public static <T> T getFieldValue(Field field, Object object) {
        try {
            return (T)field.get(object);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static <T> T getValue(Object object, Class<?> clazz) {
        Field field = ReflectionUtil.getField(object.getClass(), clazz);
        if (field == null) {
            return null;
        }
        return ReflectionUtil.getFieldValue(field, object);
    }

    public static <T> T getValueRecursive(Object object, String string) throws NoSuchFieldException {
        Field field = ReflectionUtil.getFieldRecursive(object.getClass(), string);
        if (field == null) {
            throw new NoSuchFieldException(string);
        }
        return ReflectionUtil.getFieldValue(field, object);
    }

    public static <T> T getValueRecursive(Object object, Class<?> clazz, boolean bl) throws NoSuchFieldException {
        Field field = ReflectionUtil.getFieldRecursive(object.getClass(), clazz, bl);
        if (field == null) {
            throw new NoSuchFieldException(clazz.getName());
        }
        return ReflectionUtil.getFieldValue(field, object);
    }

    public static void setValue(Object object, Field field, Object object2) {
        if (field.getType().isEnum() && object2 instanceof Integer) {
            object2 = field.getType().getEnumConstants()[(Integer)object2];
        }
        try {
            Object object3 = field.get(object);
            if (!DataEncoder.copyValue(object2, object3)) {
                field.set(object, object2);
            }
        }
        catch (Exception exception) {
            throw new RuntimeException("can't set field " + field.getName() + " in " + object + " to " + object2, exception);
        }
    }

    public static boolean setValueRecursive(Object object, String string, Object object2) {
        Field field = ReflectionUtil.getFieldRecursive(object.getClass(), string);
        if (field == null) {
            IC2.log.warn(LogCategory.General, "Can't find field %s in %s to set it to %s.", string, object, object2);
            return false;
        }
        ReflectionUtil.setValue(object, field, object2);
        return true;
    }

    public static Method getMethod(Class<?> clazz, String[] stringArray, Class<?> ... classArray) {
        for (String string : stringArray) {
            try {
                Method method = clazz.getDeclaredMethod(string, classArray);
                method.setAccessible(true);
                return method;
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (SecurityException securityException) {
                throw new RuntimeException(securityException);
            }
        }
        return null;
    }
}

