package pt.um.bookstore.messaging.util;

import io.atomix.catalyst.serializer.CatalystSerializable;

/**
 * Message is the most abstract possible interface for pt.um.bookstore.messaging.
 * <p>
 * Indicates an object that must be serialiazable.
 * @see Request
 * @see Reply
 */
public interface Message extends CatalystSerializable
{
}
