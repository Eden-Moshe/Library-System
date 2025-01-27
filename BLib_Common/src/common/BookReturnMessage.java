package common;

import java.io.Serializable;

/**
 * The {@code BookReturnMessage} class represents a message used in the context of book return operations.
 * This class is serializable and can be transmitted across network connections or saved to a file.
 * 
 * It contains information related to the book return process, such as the borrower ID, borrow number,
 * book barcode, and a flag indicating whether all conditions are met for the return operation.
 * 
 * @see Serializable
 */
public class BookReturnMessage implements Serializable {
    
    /** 
     * A flag indicating whether all conditions for the book return are satisfied.
     * Default value is {@code false}.
     */
    public boolean allOfCon = false;

    /** 
     * A boolean flag that indicates if the book return operation is successfully processed.
     */
    public boolean editBool;

    /** 
     * The unique identifier of the borrower who is returning the book.
     */
    public String borrowerId;

    /** 
     * The unique borrow number associated with the borrowing transaction.
     */
    public String borrowNum;

    /** 
     * The unique barcode of the book being returned.
     */
    public String bookBarcode;

}
