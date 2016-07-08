package busy.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class provides a delegate setter for the Serializable DTO's, enhancing the security for
 * methods which we do not want to make accessible to the client layer.
 * 
 * @author malkomich
 *
 */
public class SecureSetter {


    /**
     * Sets an attribute of the DTO, whose setter is inaccessible.
     * 
     * @param dto
     *            domain object to modify
     * @param methodName
     *            setter method name
     * @param type
     *            type of the attribute to set
     * @param value
     *            value of the attribute to set
     */
    public static <T> void setAttribute(Serializable dto, String methodName, Class<T> type, T value) {

        try {
            Method setIdMethod = findMethod(dto.getClass(), methodName, type);
            setIdMethod.setAccessible(true);
            setIdMethod.invoke(dto, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(e);
        }
    }

    /**
     * Retrieves the method instance we want to access.
     * 
     * @param dtoClass
     *            DTO specific class
     * @param methodName
     *            setter method name
     * @param type
     *            type of the attribute to set
     * @return Setter method
     */
    private static Method findMethod(Class<?> dtoClass, String methodName, Class<?> type) {

        Method method = null;
        try {
            method = dtoClass.getDeclaredMethod(methodName, type);
        } catch (NoSuchMethodException e) {
            System.out.println(e);
        }

        return method;
    }

}
