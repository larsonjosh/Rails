package rails.game.specific._1880;

import java.util.List;

import rails.algorithms.RevenueAdapter;
import rails.algorithms.RevenueDynamicModifier;
import rails.algorithms.RevenueTrainRun;
import rails.game.PublicCompanyI;

public class StockMarketBonusModifier implements RevenueDynamicModifier {

    private PublicCompanyI company;
    
    public boolean prepareModifier(RevenueAdapter revenueAdapter) {
        company = revenueAdapter.getCompany();
        return true;
    }
    
    public int predictionValue() {
        return company.getCurrentSpace().getType().hasAddRevenue()*10;
    }

    public int evaluationValue(List<RevenueTrainRun> runs, boolean optimalRuns) {
        return company.getCurrentSpace().getType().hasAddRevenue()*10;
    }

    public void adjustOptimalRun(List<RevenueTrainRun> optimalRuns) {        
    }

    public boolean providesOwnCalculateRevenue() {
        return false;
    }

    public int calculateRevenue(RevenueAdapter revenueAdpater) {
        return 0;
    }

    public String prettyPrint(RevenueAdapter revenueAdapter) {
        return null;
    }

}
