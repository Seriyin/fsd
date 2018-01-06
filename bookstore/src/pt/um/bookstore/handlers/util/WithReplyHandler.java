package pt.um.bookstore.handlers.util;

import pt.um.bookstore.messaging.util.Reply;
import pt.um.bookstore.messaging.util.Request;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * An abstract handler that applies a function returning a reply.
 * @param <T> Must be a {@link Request} type.
 * @param <U> Must be a {@link Reply} type.
 */
public interface WithReplyHandler<T extends Request, U extends Reply>
        extends Function<T,CompletableFuture<U>>
{
}
