package com.binsoft.myioc.def;


import java.util.Objects;

/**
 * 表示单个注入点的Bean引用的名称集合。
 * 每个注入点能够拥有多个Bean引用的定义。
 * 通常被用于引用名称没有显示定义的时候。
 */
public class BeanReferences {

    /**
     * 每个注入点，可能拥有多个Bean的引用
     */
    private final String[] names;

    BeanReferences(final String... names) {
        this.names = names;
    }

    /**
     * 创建新的BeanReference
     *
     * @param names
     * @return
     */
    public static BeanReferences of(final String... names) {
        Objects.requireNonNull(names);
        return new BeanReferences(names);
    }

    /**
     * 取得名称
     *
     * @return
     */
    public String name(final int idx) {
        return names[idx];
    }

    /**
     * 创建新的 Bean 引用
     *
     * @param name
     * @return
     */
    public static BeanReferences of(final String name) {
        return new BeanReferences(name);
    }

    /**
     * 如果名称引用数组为空，则返回{@code true}
     *
     * @return
     */
    public boolean isEmpty() {
        return names.length == 0;
    }

    /**
     * 返回名称引用数组的数量
     *
     * @return
     */
    public int size() {
        return names.length;
    }

    /**
     * 删除数组中后加入的重复的引用名称。
     * 如果有变化，则返回新的BeanReference实例。
     * 否则返回新的实例
     *
     * @return
     */
    public BeanReferences removeDuplicateNames() {
        if (names.length < 2) {
            return this;
        }

        int nullCount = 0;

        for (int i = 1; i < names.length; i++) {
            String thisRef = names[i];
            if (thisRef == null) {
                nullCount++;//记录null值数量
                continue;
            }

            for (int j = 0; j < i; j++) {
                if (names[j] == null) {
                    continue;
                }
                //如果有相同的值，则赋值为null
                if (thisRef.equals(names[j])) {
                    names[i] = null;
                    break;
                }
            }
        }

        //如果没有NULL值情况
        if (nullCount == 0) {
            return this;
        }

        String[] newRefs = new String[names.length - nullCount];
        int ndx = 0;

        for (String name : names) {
            if (name == null) {
                continue;
            }

            newRefs[ndx] = name;
            ndx++;
        }

        return new BeanReferences(newRefs);

    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append('[');
        for (int i = 0; i < names.length; i++) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(names[i]);
        }
        sb.append(']');

        return sb.toString();
    }
}
