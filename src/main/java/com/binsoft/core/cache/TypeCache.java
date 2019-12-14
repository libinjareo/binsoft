package com.binsoft.core.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 类型缓存。
 * 如果作为Key的类型能够在运行期间被替换的话，应该用弱引用weak Map，这样可以自动删除过期的键值
 * @param <T>
 */
public class TypeCache<T> {

    public static class Defaults {
        public static Supplier<TypeCache> implementation = () -> TypeCache.create().get();
    }

    //-----------------------------------------构建 -----------------------------------------

    /**
     * 创建一个默认的类型缓存实现
     * @param <A>
     * @return
     */
    public static <A> TypeCache<A> createDefault(){
        return (TypeCache<A>) Defaults.implementation.get();
    }

    /**
     * 利用构建器创建一个类型缓存
     * @param <A>
     * @return
     */
    public static <A> Builder<A> create(){
        return new Builder<>();
    }

    public static class Builder<A> {
        private boolean threadsafe;
        private boolean weak;
        private boolean none;

        /**
         * 使用无缓存
         * 设置其他属性不会有任何影响
         * @return
         */
        public Builder<A> noCache(){
            none = true;
            return this;
        }

        /**
         * 缓存的Key将会使用弱引用
         * @param weak
         * @return
         */
        public Builder<A> weak(final boolean weak){
            this.weak = weak;
            return this;
        }

        /**
         * 缓存将会是线程安全的
         * @param threadsafe
         * @return
         */
        public Builder<A> threadsafe(final boolean threadsafe){
            this.threadsafe = threadsafe;
            return this;
        }

        /**
         * 构建一个类型缓存
         * @return
         */
        public TypeCache<A> get(){
            final Map<Class<?>,A> map;
            if(none){
                //如果不使用缓存
                map = new AbstractMap<Class<?>, A>() {

                    @Override
                    public A get(final Object key) {
                        return null;
                    }

                    @Override
                    public A put(final Class<?> key, final A value) {
                        return null;
                    }

                    @Override
                    public Set<Entry<Class<?>, A>> entrySet() {
                        return Collections.emptySet();
                    }
                };
            }else if(weak){//如果使用弱引用
                //如果设置为线程安全
                if(threadsafe){
                    map = Collections.synchronizedMap(new WeakHashMap<>());
                }else{
                    //如果没有设置为线程安全的，则直接new
                    map = new WeakHashMap<>();
                }

            }else{ //如果设置为使用缓存但不是弱引用的情况
                //如果线程安全
                if(threadsafe){
                    map = new ConcurrentHashMap<>();
                }else{
                    map = new IdentityHashMap<>();//IdentityHashMap doesn't use equals() and hashcode() methods,use==
                }

            }

            return new TypeCache<>(map);
        }
    }

    //-----------------------------------------缓存操作-----------------------------------------
    private final Map<Class<?>,T> map;

    /**
     * 构造函数
     * @param backedMap
     */
    private TypeCache(final Map<Class<?>,T> backedMap){
        this.map = backedMap;
    }

    /**
     * 增加值到缓存
     * @param type
     * @param value
     * @return
     */
    public T put(final Class<?> type,final T value){
        return map.put(type,value);
    }

    /**
     * 取值，若不存在则返回{@code  null}
     * @param key
     * @return
     */
    public T get(final Class<?> key){
        return map.get(key);
    }

    /**
     * <p>
     * 返回存在的值或者是增加一个默认值.
     * 当线程安全很重要的场景下，用此方法可以解决的NULL值检查。
     * </p>
     * <p>
     *  首先判断缓存Map中是否存在指定key的值，如果不存在，会自动调用mappingFunction(key)计算key的value，
     *  然后将key=value放入到缓存Map。
     *  如果mappingFunction(key)返回值为null或抛出错误，则不会记录
     *
     * </p>
     *
     * @param key
     * @param valueSupplier
     * @return
     */
    public T get(final Class<?> key,final Supplier<T> valueSupplier){
        return map.computeIfAbsent(key,aClass -> valueSupplier.get());
    }

    /**
     * 移除元素
     * @param type
     * @return
     */
    public T remove(final Class<?> type) {
        return map.remove(type);
    }

    /**
     * 完全清空缓存
     */
    public void clear() {
        map.clear();;
    }

    /**
     * 返回缓存大小
     * @return
     */
    public int size(){
        return map.size();
    }

    /**
     * 如果缓存为空，则返回{@code true}
     * @return
     */
    public boolean isEmptry(){
        return map.isEmpty();
    }

    /**
     * 迭代所有缓存值
     * @param valueConsumer
     */
    public void forEachValue(final Consumer<? super T > valueConsumer){
        map.values().forEach(valueConsumer);
    }
}
