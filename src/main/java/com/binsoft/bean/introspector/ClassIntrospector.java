package com.binsoft.bean.introspector;

import java.util.Objects;

/**
 * 默认类自省{@link ClassIntrospector},简单的委托方法调用
 */
public interface ClassIntrospector {

    class Implementation {
        private static ClassIntrospector classIntrospector = new CachingIntrospector();

        /**
         * 设置默认实现
         *
         * @param classIntrospector
         */
        public static void set(final ClassIntrospector classIntrospector) {
            Objects.requireNonNull(classIntrospector);
            Implementation.classIntrospector = classIntrospector;
        }
    }

    /**
     * 返回默认实现
     *
     * @return
     */
    static ClassIntrospector get() {
        return Implementation.classIntrospector;
    }


    /**
     * 返回指定类型的类描述符
     *
     * @param type
     * @return
     */
    ClassDescriptor lookup(Class type);

    /**
     * 清空所有的缓存数据
     */
    void reset();
}
