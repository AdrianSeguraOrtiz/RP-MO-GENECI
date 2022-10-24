package eagrn.fitnessfunctions.impl.Quality.impl;

import eagrn.fitnessfunctions.impl.Quality.Quality;
import eagrn.fitnessfunctions.impl.Quality.TrendMeasureEnum;
import java.util.Map;


/**
 * Try to minimize the quantity of high quality links (getting as close as possible
 * to 10 percent of the total possible links in the network) and at the same time maximize
 * the quality of these good links (maximize the mean of their confidence and weight adjustment).
 *
 * High quality links are those whose confidence-distance mean is above average.
 */

public class QualityMedian extends Quality {
    
    public QualityMedian(Map<String, Double[]> inferredNetworks) {
        super(inferredNetworks, TrendMeasureEnum.MEDIAN);
    }

    public double run(Map<String, Double> consensus, Double[] x) {
        /** 
         * 1. We create the map with the distances corresponding to the difference between the maximum and minimum 
         * of the vectors containing the mean between the weight and the distance normalized to the median of the total 
         * number of techniques 
         */
        Map<String, Double> distances = super.makeDistanceMap(consensus, x);

        /** 2. Calculate the mean of the confidence-distance means. */
        double conf, dist, confDistSum = 0;
        for (Map.Entry<String, Double> pair : consensus.entrySet()) {
            conf = pair.getValue();
            dist = distances.get(pair.getKey());
            confDistSum += (conf + (1 - dist)) / 2.0;
        }
        double mean = confDistSum / consensus.size();

        /** 3. Return fitness */
        return 1.0 - mean;
    }
}
