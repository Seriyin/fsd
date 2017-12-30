package messaging;

import util.RemoteObj;

public abstract class ObjReply {
    private RemoteObj ro;

    protected ObjReply(RemoteObj ro) {
        this.ro = ro;
    }

    protected RemoteObj getRemoteObj() {
        return ro;
    }
}
