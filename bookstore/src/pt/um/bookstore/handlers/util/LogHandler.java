package pt.um.bookstore.handlers.util;

import io.atomix.catalyst.serializer.CatalystSerializable;

import java.util.function.BiConsumer;

/**
 * Log handler is a biconsumer over a serializable object.
 * @param <T> the type of the serializable object.
 */
public interface LogHandler<T extends CatalystSerializable>
        extends BiConsumer<Integer,T>
{}
