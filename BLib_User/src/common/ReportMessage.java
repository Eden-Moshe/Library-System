/**
 * The {@code ReportMessage} class represents a message containing report details
 * related to library operations such as status reports and borrowing reports.
 * 
 * This class implements {@code Serializable} to support object serialization.
 */
package common;

import java.io.Serializable;

public class ReportMessage implements Serializable {

    /**
     * Indicates whether the message contains a status report.
     */
    public boolean statusReport;

    /**
     * Indicates whether the message contains a borrow report.
     */
    public boolean borrowReport;

    /**
     * The count of active items or users in the report.
     */
    public int activeCount;

    /**
     * The count of frozen items or users in the report.
     */
    public int frozenCount;

    /**
     * Gets the status report flag.
     * 
     * @return {@code true} if the message contains a status report, {@code false} otherwise
     */
    public boolean isStatusReport() {
        return statusReport;
    }

    /**
     * Sets the status report flag.
     * 
     * @param statusReport {@code true} to indicate the presence of a status report, {@code false} otherwise
     */
    public void setStatusReport(boolean statusReport) {
        this.statusReport = statusReport;
    }

    /**
     * Gets the borrow report flag.
     * 
     * @return {@code true} if the message contains a borrow report, {@code false} otherwise
     */
    public boolean isBorrowReport() {
        return borrowReport;
    }

    /**
     * Sets the borrow report flag.
     * 
     * @param borrowReport {@code true} to indicate the presence of a borrow report, {@code false} otherwise
     */
    public void setBorrowReport(boolean borrowReport) {
        this.borrowReport = borrowReport;
    }

    /**
     * Gets the count of active items or users.
     * 
     * @return the number of active items or users
     */
    public int getActiveCount() {
        return activeCount;
    }

    /**
     * Sets the count of active items or users.
     * 
     * @param activeCount the number of active items or users
     */
    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    /**
     * Gets the count of frozen items or users.
     * 
     * @return the number of frozen items or users
     */
    public int getFrozenCount() {
        return frozenCount;
    }

    /**
     * Sets the count of frozen items or users.
     * 
     * @param frozenCount the number of frozen items or users
     */
    public void setFrozenCount(int frozenCount) {
        this.frozenCount = frozenCount;
    }
}
