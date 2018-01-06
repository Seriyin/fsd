package pt.um.bookstore.handlers.util;

import pt.um.bookstore.messaging.util.Message;
import pt.um.bookstore.messaging.util.Reply;
import pt.um.bookstore.messaging.util.Request;

import java.util.function.Consumer;

/**
 * An handler that wholly consumes either a request or a reply.
 * @param <T> A type that is either a {@link Request} or a {@link Reply}.
 */
public interface ConsumingHandler<T extends Message>
        extends Consumer<T>
{
}
