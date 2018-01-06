package pt.um.bookstore.util;

/**
 * Skeleton contains {@link RemoteObj} management methods.
 */
public abstract class Skeleton {
    private RemoteObj ro = null;

    /**
     * Returns the underlying skeleton's object reference.
     * @return null or RemoteObj of this skeleton.
     */
    protected RemoteObj getRef() {
        return ro;
    }

    /**
     * Set the underlying skeleton's remote reference.
     * Pair with an exportRef call in a {@link Server}.
     * @param ro The RemoteObj to set on this skeleton.
     */
    protected void setRef(RemoteObj ro) {
        this.ro = ro;
    }

    /**
     * @return if the object has already been exported.
     */
    protected boolean isExported() {
        return ro!=null;
    }
}
