package com.binsoft.myioc;

import com.binsoft.myioc.def.BeanReferences;

import java.util.HashMap;
import java.util.Map;

public abstract class MyBeans {

    protected final Map<String, BeanDefinition> beans = new HashMap<>();

    /**
     * 查找 {@link BeanDefinition bean definition}.
     * 如果名称不存在，则返回<code>null</code>
     *
     * @param beanReferences
     * @return
     */
    public BeanDefinition lookupBeanDefinition(final BeanReferences beanReferences) {
        //先从缓存Map中查找
        BeanDefinition beanDefinition = beans.get(beanReferences.name());
        if (beanDefinition != null) {
            return beanDefinition;
        }

        return null;
    }

    /**
     * 查找 {@link BeanDefinition bean definition}.
     * 如果名称不存在，则返回<code>null</code>
     *
     * @param name
     * @return
     */
    public BeanDefinition lookupBeanDefinition(final String name) {
        BeanDefinition beanDefinition = beans.get(name);
        return beanDefinition;
    }

    /**
     * 返回存在的{@link BeanDefinition }.
     * 如果bean不存在，则抛出异常
     *
     * @param name
     * @return
     */
    protected BeanDefinition lookupExistingBeanDefinition(final String name) {
        BeanDefinition beanDefinition = lookupBeanDefinition(name);
        if (beanDefinition == null) {
            throw new MyIOCException("Bean not found: " + name);
        }
        return beanDefinition;
    }

    /**
     * 注册或定义一个 bean
     *
     * @param type bean的类型，必须指定
     * @param name bean的名称，如果为<code>null</code>,将会从class中解析
     * @param <T>
     * @return
     */
    public <T> BeanDefinition<T> registerMyBean(final Class<T> type, String name) {
        //如果name为空的情况下，则解析type来取得name
        if (null == name) {
            name = resolveBeanName(type);
        }

        //移除缓存中存在的Bean
        BeanDefinition existing = removeBean(name);

        BeanDefinition beanDefinition = createBeanDefinitionForRegistration(name, type);

        //注册Bean到Map
        registerBean(name, beanDefinition);

        return beanDefinition;
    }

    /**
     * 注册Bean到缓存Map中
     *
     * @param name
     * @param beanDefinition
     */
    protected void registerBean(final String name, final BeanDefinition beanDefinition) {
        beans.put(name, beanDefinition);
    }

    /**
     * 返回一个{@link BeanDefinition}的新实例
     *
     * @param name
     * @param type
     * @param <T>
     * @return
     */
    protected <T> BeanDefinition createBeanDefinitionForRegistration(final String name, final Class<T> type) {
        return new BeanDefinition(name, type);
    }

    /**
     * 移除bean并且返回移除的bean的定义。
     *
     * @param name
     * @return <code>BeanDefinition</code>or <code>null</code>
     */
    public BeanDefinition removeBean(final String name) {
        BeanDefinition bd = beans.remove(name);
        if (bd == null) {
            return null;
        }

        return bd;
    }

    /**
     * 从类型信息取得Bean的名字
     *
     * @param type
     * @param <T>
     * @return
     */
    private <T> String resolveBeanName(Class<T> type) {
        String name = null;
        name = type.getName();
        return name;
    }


    //-----------------------------------统计相关类

    /**
     * 返回注册bean的总数量
     *
     * @return
     */
    public int beansCount() {
        return beans.size();
    }

}
