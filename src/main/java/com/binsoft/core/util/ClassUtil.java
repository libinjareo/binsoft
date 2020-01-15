

package com.binsoft.core.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

/**
 * 类工具类
 */
public class ClassUtil {


    // ---------------------------------------------------------------- match classes

    /**
     * <code>isAssignableFrom</code> 方法增强
     *
     * @param lookupClass
     * @param targetClass
     * @return
     */
    public static boolean isTypeOf(final Class<?> lookupClass, final Class<?> targetClass) {
        if (targetClass == null || lookupClass == null) {
            return false;
        }
        return targetClass.isAssignableFrom(lookupClass);
    }

    /**
     * 取得一个类型的所有接口。保持接口唯一。
     * 直接接口排在超类的接口前面。
     *
     * @param type
     * @return
     */
    public static Class[] resolveAllInterfaces(final Class type) {
        Set<Class> bag = new LinkedHashSet<>();//维护插入顺序
        _resolveAllInterfaces(type, bag);

        return bag.toArray(new Class[0]);
    }

    private static void _resolveAllInterfaces(final Class type, final Set<Class> bag) {
        //首先取得该类型的接口
        Class[] interfaces = type.getInterfaces();
        Collections.addAll(bag, interfaces);

        //然后再取得接口的接口
        for (Class iface : interfaces) {
            _resolveAllInterfaces(iface, bag);
        }
        //解析超类
        Class superClass = type.getSuperclass();
        if (superClass == null) {
            return;
        }
        if (superClass == Object.class) {
            return;
        }
        _resolveAllInterfaces(type.getSuperclass(), bag);
    }

    /**
     * 取得所有超类，从直接超类开始查找。不包含<code>Object</code>
     *
     * @param type
     * @return
     */
    public static Class[] resolveAllSuperclasses(Class type) {
        List<Class> list = new ArrayList<>();

        while (true) {
            type = type.getSuperclass();

            if ((type == null) || (type == Object.class)) {
                break;
            }
            list.add(type);
        }
        return list.toArray(new Class[0]);
    }

    /**
     * 如果类是public的，则返回<code>true</code>
     * @param c
     * @return
     */
    public static boolean isPublic(final Class c){
        return Modifier.isPublic(c.getModifiers());
    }

    /**
     * 如果类成员是Public，则返回<code>true</code>
     * @param member
     * @return
     */
    public static boolean isPublic(final Member member){
        return Modifier.isPublic(member.getModifiers());
    }

    /**
     * 禁止对反射对象进行访问检查，SecurityException将会被忽略。
     * 首先检查对象是否已经被访问。
     * @param accObject
     */
    public static void forceAccess(final AccessibleObject accObject){

        try{
            if(System.getSecurityManager() == null){
                accObject.setAccessible(true);
            }else{
                AccessController.doPrivileged((PrivilegedAction) () -> {
                    accObject.setAccessible(true);
                    return null;
                });
            }
        }catch(SecurityException e){
            //ignore
        }

    }
}
