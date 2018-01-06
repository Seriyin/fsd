package pt.um.bookstore.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;

import java.util.Objects;

/**
 * Stands in for an actual Object with an object handler comprised of the host machine, an
 * unique numeric tag and the object's classname.
 */
public final class RemoteObj implements CatalystSerializable {
    private Address address;
    private long id;
    private String cls;

    /**
     * Regular fully parameterized constructor.
     * @param address Hostname and port.
     * @param id Unique object tag.
     * @param cls Class-name.
     */
    public RemoteObj(Address address, long id, String cls) {
        this.address = address;
        this.id = id;
        this.cls = cls;
    }

    /**
     * Standard getter.
     * @return Hostname in Address form.
     * @see Address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Standard getter.
     * @return Unique id
     */
    public long getId() {
        return id;
    }

    /**
     * Standard getter.
     * @return Class-name.
     */
    public String getCls() {
        return cls;
    }

    /**
     * @see BufferOutput
     * @see Serializer
     * @param bufferOutput buffer to write to.
     * @param serializer the catalyst underlying serializer.
     */
    @Override
    public void writeObject(BufferOutput<?> bufferOutput, Serializer serializer) {
        bufferOutput.writeLong(id);
        serializer.writeObject(address, bufferOutput);
        bufferOutput.writeString(cls);
    }

    /**
     * @see BufferInput
     * @see Serializer
     * @param bufferInput buffer to read from.
     * @param serializer the catalyst underlying serializer.
     */
    @Override
    public void readObject(BufferInput<?> bufferInput, Serializer serializer) {
        id = bufferInput.readLong();
        serializer.readObject(bufferInput);
        cls = bufferInput.readString();
    }

    /**
     * Equals that does not check for subclass.
     * @param o Object to compare to.
     * @return boolean with comparison value.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteObj remoteObj = (RemoteObj) o;
        return getId() == remoteObj.getId() &&
                Objects.equals(getAddress(), remoteObj.getAddress()) &&
                Objects.equals(getCls(), remoteObj.getCls());
    }

    /**
     * Standard hashCode implementation, calls Objects.hash()
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(address, id, cls);
    }


}
