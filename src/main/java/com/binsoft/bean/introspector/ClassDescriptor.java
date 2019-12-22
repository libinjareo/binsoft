package com.binsoft.bean.introspector;

import com.binsoft.core.util.ClassUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 一个类的所有方法/字段/属性/构造函数的描述符类。
 * 静态方法/字段忽略。
 * <P>
 * 惰性赋值：在第一次请求时各种内部缓存将会被创建
 * </P>
 * <p>
 * 首选公共类中定义的公共成员
 * </p>
 */
public class ClassDescriptor {
    protected final Class type;
    protected final boolean scanAccessible;//判断类描述符是否适用于可访问的字段/方法/构造函数或都支持
    protected final boolean extendedProperties;//判断是否扩展了类描述符中的属性并包含字段描述
    protected final boolean includeFieldsAsProperties;//判断是否将字段包括为属性
    protected final String[] propertyFieldPrefix;//属性字段前缀
    protected final Class[] interfaces; //类型所有的接口
    protected final Class[] superclasses;//类型所有的

    public ClassDescriptor(final Class type, final boolean scanAccessible, final boolean extendedProperties, final boolean includeFieldsAsProperties, final String[] propertyFieldPrefix) {
        this.type = type;
        this.scanAccessible = scanAccessible;
        this.extendedProperties = extendedProperties;
        this.includeFieldsAsProperties = includeFieldsAsProperties;
        this.propertyFieldPrefix = propertyFieldPrefix;

        isArray = type.isArray();
        isMap = ClassUtil.isTypeOf(type, Map.class);
        isList = ClassUtil.isTypeOf(type, List.class);
        isSet = ClassUtil.isTypeOf(type, Set.class);
        isCollection = ClassUtil.isTypeOf(type, Collection.class);
        isSupplier = ClassUtil.isTypeOf(type, Supplier.class);

        interfaces = ClassUtil.resolveAllInterfaces(type);
        superclasses = ClassUtil.resolveAllSuperclasses(type);

        isSystemClass = type.getName().startsWith("java.") && !type.getName().startsWith("java.awt.geom.");
    }

    /**
     * 取得描述符描述的类对象
     *
     * @return
     */
    public Class getType() {
        return type;
    }

    /**
     * 如果这个类描述符适用于可访问的字段/方法/构造函数或都支持的话，则返回<code>true</code>
     *
     * @return
     */
    public boolean isScanAccessible() {
        return scanAccessible;
    }

    /**
     * 如果扩展了这个类描述符中的属性并包含字段描述的话，则返回<code>true</code>
     *
     * @return
     */
    public boolean isExtendedProperties() {
        return extendedProperties;
    }

    /**
     * 是否将字段包括为属性
     *
     * @return
     */
    public boolean isIncludeFieldsAsProperties() {
        return includeFieldsAsProperties;
    }

    /**
     * 返回属性字段前缀；
     * 如果没有设置前缀的情况下，如果需要同时访问前缀和非前缀字段，用空字符作为其中的一个前缀。
     *
     * @return
     */
    public String[] getPropertyFieldPrefix() {
        return propertyFieldPrefix;
    }
// ---------------------------------------------------------------- 特殊属性

    private final boolean isArray;

    /**
     * 如果类是一个数组，则返回<code>true</code>
     *
     * @return
     */
    public boolean isArray() {
        return isArray;
    }

    private final boolean isMap;

    /**
     * 如果类一个<code>Map</code>，则返回<code>true</code>
     *
     * @return
     */
    public boolean isMap() {
        return isMap;
    }

    private final boolean isList;

    /**
     * 如果类是一个<code>List</code>,则返回<code>true</code>
     *
     * @return
     */
    public boolean isList() {
        return isList;
    }

    private final boolean isSet;

    /**
     * 如果类是一个<code>Set</code>,则返回<code>true</code>
     *
     * @return
     */
    public boolean isSet() {
        return isSet;
    }

    private final boolean isCollection;

    /**
     * 如果类一个collection,则返回<code>true</code>
     *
     * @return
     */
    public boolean isCollection() {
        return isCollection;
    }

    private final boolean isSupplier;

    /**
     * 如果类是一个supplier,则返回<code>true</code>
     *
     * @return
     */
    public boolean isSupplier() {
        return isSupplier;
    }

    private boolean isSystemClass;

    /**
     * 如果类是一个系统类并且不能暴露子弹或声明的方法时，则返回<code>true</code>
     *
     * @return
     */
    public boolean isSystemClass() {
        return isSystemClass;
    }


    // ---------------------------------------------------------------- 构造函数
    private Ctors ctors;

    /**
     * 返回构造函数集合。
     * 第一次访问时，会创建一个集合
     * @return
     */
    protected Ctors getCtors(){
        if(ctors == null){
            ctors = new Ctors(this);
        }
        return ctors;
    }

    /**
     * 返回默认的构造函数，如果未找到则返回<code>null</code>
     * @param declared
     * @return
     */
    public CtorDescriptor getDefaultCtorDescriptor(final boolean declared){
        CtorDescriptor defaultCtor = getCtors().getDefaultCtor();
        if((null != defaultCtor) && defaultCtor.matchDeclared(declared)){
            return defaultCtor;
        }
        return null;

    }

    /**
     * 返回指定参数的构造函数描述器，如果未找到则返回<code>null</code>
     * @param args
     * @param declared
     * @return
     */
    public CtorDescriptor getCtorDescriptor(final Class[] args,final boolean declared){
        CtorDescriptor ctorDescriptor = getCtors().getCtorDescriptor(args);
        if((null != ctorDescriptor) && ctorDescriptor.matchDeclared(declared)){
            return ctorDescriptor;
        }
        return null;
    }

    /**
     * 返回{@link CtorDescriptor}构造函数描述符数组
     * @return
     */
    public CtorDescriptor[] getAllCtorDescriptors(){
        return getCtors().getAllCtorDescriptors();
    }
}
