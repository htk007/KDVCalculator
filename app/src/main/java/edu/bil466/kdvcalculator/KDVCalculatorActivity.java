package edu.bil466.kdvcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class KDVCalculatorActivity extends Activity {
	
	// constant variables used when saving/restoring app state info.
	// we should remember bill amount and custom KDV percentage.
	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";
	
	// instance variables
	private double currentBillTotal;// bill amount entered by the user
	private int currentCustomPercent; // KDV % set with the SeekBar
	private EditText kdv8EditText; // displays 8% KDV
	private EditText total8EditText; // displays total with 10% KDV
	private EditText kdv18EditText; // displays 18% KDV
	private EditText total18EditText; // displays total with 18% KDV
	private EditText billEditText; // accepts user input for bill total
	private EditText kdv25EditText; // displays 25% KDV
	private EditText total25EditText;  // displays total with 25% KDV
	private TextView customKDVTextView; // displays custom KDV percentage
	private EditText kdvCustomEditText; // displays custom KDV amount
	private EditText totalCustomEditText; // displays total with custom KDV
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); // you must call the superclass method first.
		
		setContentView(R.layout.main); // inflating your layout xml as GUI.

		// LOAD SAVED APP STATE INFO
		
		// check if app just started or is being restored from memory
		if ( savedInstanceState == null ) // the app just started running
		{
		   currentBillTotal = 0.0; // initialize the bill amount to zero
		   currentCustomPercent = 18; // initialize the custom KDV to 18%
		} // end if
		else // app is being restored from memory, not executed from scratch
		{
		   // initialize the bill amount to saved amount
		   currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL); 
		   
		   // initialize the custom KDV to saved KDV percent 
		   currentCustomPercent = 
		      savedInstanceState.getInt(CUSTOM_PERCENT); 
		} // end else
		
		// GET REFERENCES TO YOUR INSTANCE VARIABLES
		kdv8EditText = (EditText) findViewById(R.id.kdv8EditText);
		total8EditText = (EditText) findViewById(R.id.total8EditText);
		kdv18EditText = (EditText) findViewById(R.id.kdv18EditText);
		total18EditText = (EditText) findViewById(R.id.total18EditText);
		kdv25EditText = (EditText) findViewById(R.id.kdv25EditText);
		total25EditText = (EditText) findViewById(R.id.total25EditText);

		// get the TextView displaying the custom KDV percentage
		customKDVTextView = (TextView) findViewById(R.id.customKDVTextView);
		// get the custom KDV and total EditTexts 
		kdvCustomEditText = (EditText) findViewById(R.id.kdvCustomEditText);
		totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);
		// get the billEditText 
		billEditText = (EditText) findViewById(R.id.billEditText);
		// SET YOUR EVENT HANDLERS
		// billEditTextWatcher handles billEditText's onTextChanged event
		billEditText.addTextChangedListener(billEditTextWatcher);

		// get the SeekBar used to set the custom KDV amount
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	}
	
	// updates 8, 18 and 25 percent KDV EditTexts
	private void updateStandard() 
	{
	   // calculate bill total with a ten percent KDV
	   double eightPercentKDV = currentBillTotal * .08;
	   double eightPercentTotal = currentBillTotal + eightPercentKDV;

	   // set KDVTenEditText's text to eightPercentKDV
	   kdv8EditText.setText(String.format("%.02f", eightPercentKDV));

	   // set totalTenEditText's text to eightPercentTotal
	   total8EditText.setText(String.format("%.02f", eightPercentTotal));

	   // calculate bill total with a eighteen percent KDV
	   double eighteenPercentKDV = currentBillTotal * .18;
	   double eighteenPercentTotal = currentBillTotal + eighteenPercentKDV;

	   // set kdv18EditText's text to eighteenPercentKDV
	   kdv18EditText.setText(String.format("%.02f", eighteenPercentKDV));

	   // set total18EditText's text to eighteenPercentTotal
	   total18EditText.setText(
	      String.format("%.02f", eighteenPercentTotal));

	   // calculate bill total with a twenty five percent KDV
	   double twentyFivePercentKDV = currentBillTotal * .25;
	   double twentyFivePercentTotal = currentBillTotal + twentyFivePercentKDV;

	   // set kdv25EditText's text to twentyFivePercentKDV
	   kdv25EditText.setText(String.format("%.02f", twentyFivePercentKDV));

	   // set total25EditText's text to twentyFivePercentTotal
	   total25EditText.setText(String.format("%.02f", twentyFivePercentTotal));
	} // end method updateStandard

	// updates the custom KDV and total EditTexts
	private void updateCustom() 
	{
	   // set customKDVTextView's text to match the position of the SeekBar
	   customKDVTextView.setText(currentCustomPercent + "%");

	   // calculate the custom KDV amount
	   double customKDVAmount = 
	      currentBillTotal * currentCustomPercent * .01;

	   // calculate the total bill, including the custom KDV
	   double customTotalAmount = currentBillTotal + customKDVAmount;

	   // display the KDV and total bill amounts
	   kdvCustomEditText.setText(String.format("%.02f", customKDVAmount));
	   totalCustomEditText.setText(
	      String.format("%.02f", customTotalAmount));
	} // end method updateCustom

	// save values of billEditText and customSeekBar
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
	   super.onSaveInstanceState(outState);
	   
	   outState.putDouble(BILL_TOTAL, currentBillTotal);
	   outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	} // end method onSaveInstanceState

	// called when the user changes the position of SeekBar
	private OnSeekBarChangeListener customSeekBarListener = 
	   new OnSeekBarChangeListener() 
	{
	   // update currentCustomPercent, then call updateCustom
	   @Override
	   public void onProgressChanged(SeekBar seekBar, int progress,
	      boolean fromUser) 
	   {
	      // sets currentCustomPercent to position of the SeekBar's thumb
	      currentCustomPercent = seekBar.getProgress();
	      updateCustom(); // update EditTexts for custom KDV and total
	   } // end method onProgressChanged

	   @Override
	   public void onStartTrackingTouch(SeekBar seekBar) 
	   {
	   } // end method onStartTrackingTouch

	   @Override
	   public void onStopTrackingTouch(SeekBar seekBar) 
	   {
	   } // end method onStopTrackingTouch
	}; // end OnSeekBarChangeListener

	// event-handling object that responds to billEditText's events
	private TextWatcher billEditTextWatcher = new TextWatcher() 
	{
	   // called when the user enters a number
	   @Override
	   public void onTextChanged(CharSequence s, int start, 
	      int before, int count) 
	   {         
	      // convert billEditText's text to a double
	      try
	      {
	         currentBillTotal = Double.parseDouble(s.toString());
	      } // end try
	      catch (NumberFormatException e)
	      {
	         currentBillTotal = 0.0; // default if an exception occurs
	      } // end catch 

	      // update the standard and custom KDV EditTexts
	      updateStandard(); // update the 8, 18 and 25% EditTexts
	      updateCustom(); // update the custom KDV EditTexts
	   } // end method onTextChanged

	   @Override
	public void afterTextChanged(Editable s)
	{
	} // end method afterTextChanged

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
								  int after)
	{
	} // end method beforeTextChanged
}; // end billEditTextWatcher

}
