/**
 * The {@code BorrowMessage} class represents a message related to a borrowing transaction.
 * It is used to transfer information between the server and client sides with the necessary details.
 * 
 * This class implements {@code Serializable} to support object serialization.
 */
package common;

import java.io.Serializable;

public class BorrowMessage implements Serializable {
    /**
     * The subscriber associated with the borrowing transaction.
     */
    public Subscriber s;

    /**
     * The borrowing transaction details.
     */
    public Borrow borrow;

    /**
     * The book involved in the transaction.
     */
    public Book b;

    /**
     * The librarian's ID handling the transaction.
     */
    public String lib_id;

}
