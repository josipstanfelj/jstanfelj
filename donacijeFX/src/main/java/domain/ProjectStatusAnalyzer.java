package domain;

import javafx.scene.control.Label;

public class ProjectStatusAnalyzer<A extends Number,B extends Number>{

    private int collectedFunds;
    private int missingFunds;

    public int getCollectedFunds() {
        return collectedFunds;
    }

    public void setCollectedFunds(int collectedFunds) {
        this.collectedFunds = collectedFunds;
    }

    public int getMissingFunds() {
        return missingFunds;
    }
    public void setMissingFunds(int missingFunds) {
        this.missingFunds = missingFunds;
    }
    public void analyze(A currentAmount, B targetAmount, Label notifycationLabel){

        double result = currentAmount.doubleValue()/targetAmount.doubleValue();

        double round = 0;
        if(result >= 1){
            collectedFunds = 100;
            missingFunds = 0;
            notifycationLabel.setText("All necessary funds were collected for the selected project!");
        }else{
            round = Math.round(result * 100);
            collectedFunds = (int)round;
            missingFunds = 100-collectedFunds;
            notifycationLabel.setText("Funds have not yet been collected!");
        }
    }
}
