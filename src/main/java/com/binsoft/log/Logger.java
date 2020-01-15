package com.binsoft.log;

import java.util.function.Supplier;

/**
 * 简单的日志接口。仅定义带有字符串参数的日志方法。因为我们的编码风格和需要日志信息的地方基本上都是在if语句
 * 块周围。
 */
public interface Logger {

    enum Level {
        TRACE(1),
        DEBUG(2),
        INFO(3),
        WARN(4),
        ERROR(5);

        private final int value;

        Level(final int value) {
            this.value = value;
        }

        /**
         * 如果级别大于给定的级别则返回<code>true</code>
         *
         * @param level
         * @return
         */
        public boolean isEnableFor(final Level level) {
            return this.value >= level.value;
        }

    }

    /**
     * 返回日志名称
     *
     * @return
     */
    String getName();

    /**
     * 如果启用了特定的日志级别，则返回<code>true</code>
     *
     * @param level
     * @return
     */
    boolean isEnabled(Level level);

    /**
     * 按照给定的日志级别记录信息
     *
     * @param level
     * @param message
     */
    void log(Level level, String message);

    /**
     * 按照提供的日志级别记录信息
     *
     * @param level
     * @param messageSupplier
     */
    default void log(final Level level, final Supplier<String> messageSupplier) {
        if (isEnabled(level)) {
            log(level, messageSupplier.get());
        }
    }

    /**
     * 记录一条日志信息，并显示异常信息
     *
     * @param level
     * @param message
     * @param throwable
     */
    void log(Level level, String message, Throwable throwable);

    default void log(final Level level, final Supplier<String> messageSupplier, final Throwable throwable) {
        if (isEnabled(level)) {
            log(level, messageSupplier.get(), throwable);
        }
    }


    // ---------------------------------------------------------------- level

    /**
     * 动态设置新的级别，可能某些实现并不支持。
     *
     * @param level
     */
    void setLevel(Level level);

    // ---------------------------------------------------------------- trace

    /**
     * 如果启动 TRACE 级别，则返回<code>true</code>
     *
     * @return
     */
    boolean isTraceEnabled();

    /**
     * 按照 TRACE 级别记录日志信息
     *
     * @param message
     */
    void trace(String message);

    default void trace(final Supplier<String> messageSupplier) {
        if (isTraceEnabled()) {
            trace(messageSupplier.get());
        }
    }

    // ---------------------------------------------------------------- debug


}
