package pt.um.bookstore.handlers.util;

import pt.um.bookstore.messaging.util.Reply;
import pt.um.bookstore.messaging.util.Request;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * An abstract handler that applies a function returning a request.
 * @param <T> A reply type.
 * @param <U> Must be a {@link CompletableFuture} with a request.
 */
public interface WithRequestHandler<T extends Reply,U extends Request>
        extends Function<T,CompletableFuture<U>>
{
}