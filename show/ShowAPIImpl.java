package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowAttributeSumEliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowestRatingEliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class ShowAPIImpl implements ShowAPI{
    private Ergenka[] ergenkas;
    private EliminationRule[] defaultEliminationRules;

    public ShowAPIImpl(Ergenka[] ergenkas,EliminationRule[] defaultEliminationRules){
        this.ergenkas=ergenkas;
        this.defaultEliminationRules=defaultEliminationRules;
    }

    @Override
    public Ergenka[] getErgenkas() {
        return ergenkas;
    }

    @Override
    public void playRound(DateEvent dateEvent) {
        for(Ergenka ergenka:ergenkas){
            organizeDate(ergenka,dateEvent);
        }
        eliminateErgenkas(defaultEliminationRules);
    }

    @Override
    public void eliminateErgenkas(EliminationRule[] eliminationRules) {
        if(eliminationRules.length==0){
            LowestRatingEliminationRule eliminationRule=new LowestRatingEliminationRule();
            ergenkas=eliminationRule.eliminateErgenkas(ergenkas);
            return;
        }
        for (int i = 0; i <eliminationRules.length ; i++) {
            if(ergenkas.length!=0){
                ergenkas=eliminationRules[i].eliminateErgenkas(ergenkas);
            }
        }
    }

    @Override
    public void organizeDate(Ergenka ergenka, DateEvent dateEvent) {
        ergenka.reactToDate(dateEvent);
    }
}
