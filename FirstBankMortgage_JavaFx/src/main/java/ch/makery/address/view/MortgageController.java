package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.FinanceLib;

import base.RateDAL;
import ch.makery.address.MainApp;
import ch.makery.address.model.Rate;


public class MortgageController {
	
	 @FXML
	 private TextField AnnualIncome;
	 @FXML
	 private TextField MonthlyExpenses;
	 @FXML
	 private TextField CreditScore;
	 @FXML
	 private TextField HouseCost; 
	 @FXML
	 private ComboBox<String> Term;
	 @FXML
	 private Label Result;
	 
	 private boolean okClicked = false;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MortgageController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	ObservableList<String> termlist = FXCollections.observableArrayList("15 Year Term","30 Year Term");
    	Term.setItems(termlist);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	if (isInputValid()){
            Integer annualincome = Integer.parseInt(AnnualIncome.getText());
            Integer monthlyexpenses = Integer.parseInt(MonthlyExpenses.getText());
            Integer creditscore = Integer.parseInt(CreditScore.getText());
            Integer housecost = Integer.parseInt(HouseCost.getText());
            Integer term = null;
            if (Term.getValue()=="15 Year Term"){
            	term = 15;
            }
            
            if (Term.getValue()=="30 Year Term"){
            	term = 30;
            }
            double monthlyincome = annualincome/12;
            double rate = RateDAL.getRate(creditscore);
            double decimalrate = rate/100;
            double monthlyrate = decimalrate/12;
            Integer termmonths = term * 12;
            double PMT = FinanceLib.pmt(monthlyrate,termmonths, housecost, 0, false);
            DecimalFormat df = new DecimalFormat("#.00");
            String PMTFormatted = df.format(-PMT);


            if (-PMT<=monthlyincome*.36 | -PMT <=(monthlyincome+monthlyexpenses)*.18){
            	Result.setText("Your monthly mortgage payment is: $"+ PMTFormatted);
            }
            else{
            	String error = "House cost too high";
            	Result.setText(error);
            }
    	}       
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (AnnualIncome.getText() == null || AnnualIncome.getText().length() == 0) {
            errorMessage += "No annual income entered!\n"; 
        } else {
            try {
                Integer.parseInt(AnnualIncome.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Not a valid annual income (must be an integer)!\n"; 
            }
        }
        if (MonthlyExpenses.getText() == null || MonthlyExpenses.getText().length() == 0) {
            errorMessage += "No monthly expenses entered!\n"; 
        } else {
            try {
                Integer.parseInt(MonthlyExpenses.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Not valid monthly expenses (must be an integer)!\n"; 
            }
        }
        if (HouseCost.getText() == null || HouseCost.getText().length() == 0) {
            errorMessage += "House cost not entered!\n"; 
        } else {
            try {
                Integer.parseInt(HouseCost.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Not a valid house cost (must be an integer)!\n"; 
            }
        }

        if (CreditScore.getText() == null || CreditScore.getText().length() == 0) {
            errorMessage += "Credit score not entered!\n"; 
        } else {
            try {
                Integer.parseInt(CreditScore.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Not a valid credit score (must be an integer)!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
            
        } else {
            // Show the error message.
            Result.setText(errorMessage);

            return false;
        }
    }
    
   
}