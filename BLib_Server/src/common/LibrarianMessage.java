package common;

import java.io.Serializable;

/**
 * Represents a message related to a librarian's function request.
 * This class is serializable and contains information about the function requested by the librarian,
 * as well as the subscriber associated with the request.
 */
public class LibrarianMessage implements Serializable {

    /** The function request made by the librarian. */
    public String funcRequest;

    /** The subscriber associated with the function request. */
    public Subscriber sub;

    /**
     * Gets the function request made by the librarian.
     * 
     * @return the function request.
     */
    public String getFuncRequest() {
        return funcRequest;
    }

    /**
     * Sets the function request made by the librarian.
     * 
     * @param funcRequest the function request to set.
     */
    public void setFuncRequest(String funcRequest) {
        this.funcRequest = funcRequest;
    }

    /**
     * Gets the subscriber associated with the function request.
     * 
     * @return the subscriber.
     */
    public Subscriber getSub() {
        return sub;
    }

    /**
     * Sets the subscriber associated with the function request.
     * 
     * @param sub the subscriber to set.
     */
    public void setSub(Subscriber sub) {
        this.sub = sub;
    }
}
