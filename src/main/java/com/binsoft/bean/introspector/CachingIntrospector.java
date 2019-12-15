package com.binsoft.bean.introspector;

import com.binsoft.core.cache.TypeCache;

/**
 * 默认的{@link ClassIntrospector }类自省，并且缓存所有的类描述符.
 * 它可以检测<b>accessible</b>或者是<b>supported</b>的字段/方法/构造函数
 * <p>
 * 仅仅缓存所有的类描述符
 * </p>
 */
public class CachingIntrospector implements ClassIntrospector {

    protected final TypeCache<ClassDescriptor> cache;
    protected final boolean scanAccessible;
    protected final boolean enhancedProperties;
    protected final boolean includeFieldAsProperties;
    protected final String[] propertyFieldPrefix;

    /**
     * 默认构造函数
     */
    public CachingIntrospector() {
        this(true, true, true, null);
    }

    /**
     * 创建新的缓存{@link ClassIntrospector}
     *
     * @param scanAccessible
     * @param enhancedProperties
     * @param includeFieldAsProperties
     * @param propertyFieldPrefix
     */
    public CachingIntrospector(final boolean scanAccessible, final boolean enhancedProperties, final boolean includeFieldAsProperties, final String[] propertyFieldPrefix) {
        this.cache = TypeCache.createDefault();
        this.scanAccessible = scanAccessible;
        this.enhancedProperties = enhancedProperties;
        this.includeFieldAsProperties = includeFieldAsProperties;
        this.propertyFieldPrefix = propertyFieldPrefix;
    }

    @Override
    public ClassDescriptor lookup(final Class type) {
        return cache.get(type, () ->
                new ClassDescriptor(type, scanAccessible, enhancedProperties, includeFieldAsProperties, propertyFieldPrefix));
    }

    @Override
    public void reset() {
        cache.clear();
    }
}
