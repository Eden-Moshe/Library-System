package client;

public class BarcodeWrapper {

	
	public void scan(String scannedCode)
	{
		SubscriberUI.mainController.controller.barcodeScan(scannedCode);
	}
}
