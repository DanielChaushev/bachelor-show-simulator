package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowestRatingEliminationRule implements EliminationRule{
    public LowestRatingEliminationRule() {

    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        int min=ergenkas[0].getRating();
        int counter=0;
        for (int i = 0; i < ergenkas.length ; i++) {
            if(ergenkas[i].getRating()<min){
                min=ergenkas[i].getRating();
            }
        }
        for (int i = 0; i <ergenkas.length ; i++) {
            if(ergenkas[i].getRating()>min){
                counter++;
            }
        }
        Ergenka[] survived=new Ergenka[counter];
        int index=0;
        for (int i = 0; i < ergenkas.length ; i++) {
            if(ergenkas[i].getRating()>min){
                survived[index]=ergenkas[i];
                index++;
            }
        }
        return survived;
    }
}
