package messaging;

import io.atomix.catalyst.serializer.CatalystSerializable;
import util.RemoteObj;


/**
 * ObjRequest is an abstract class taking a gettable RemoteObj that is to
 * be included in the specific request.
 */
public abstract class ObjRequest implements CatalystSerializable
{
    private RemoteObj ro;

    protected ObjRequest(RemoteObj ro) {
        this.ro = ro;
    }

    public RemoteObj getRemoteObj() {
        return ro;
    }

    protected void setRemoteObj(RemoteObj ro) {
        this.ro = ro;
    }
}
