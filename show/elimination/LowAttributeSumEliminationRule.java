package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowAttributeSumEliminationRule implements EliminationRule {
    private int threshold;

    public LowAttributeSumEliminationRule(int threshold){
        this.threshold=threshold;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        int counter=0;
        for (int i = 0; i <ergenkas.length ; i++) {
            if(ergenkas[i].getHumorLevel()+ergenkas[i].getRomanceLevel()>=threshold){
                counter++;
            }
        }
        Ergenka[] survived=new Ergenka[counter];
        int index=0;
        for (int i = 0; i <ergenkas.length ; i++) {
            if(ergenkas[i].getRomanceLevel()+ergenkas[i].getHumorLevel()>=threshold){
                survived[index]=ergenkas[i];
                index++;
            }
        }
        return survived;
    }
}
