package common;

import java.io.Serializable;

public class ReportMessage implements Serializable {
	public boolean statusReport;
	public boolean borrowReport;
	public int activeCount=0;
	public int frozenCount=0;
	
	
	
	public boolean isStatusReport() {
		return statusReport;
	}
	public void setStatusReport(boolean statusReport) {
		this.statusReport = statusReport;
	}
	public boolean isBorrowReport() {
		return borrowReport;
	}
	public void setBorrowReport(boolean borrowReport) {
		this.borrowReport = borrowReport;
	}
	public int getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}
	public int getFrozenCount() {
		return frozenCount;
	}
	public void setFrozenCount(int frozenCount) {
		this.frozenCount = frozenCount;
	}
	
	
}
