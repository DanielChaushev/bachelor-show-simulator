package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class PublicVoteEliminationRule implements EliminationRule{
    private String[] votes;

    public PublicVoteEliminationRule(String[] votes){
        this.votes=votes;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        Ergenka candidate=ergenkas[0];
        int count=1;
        for (int i = 0; i <votes.length ; i++) {
            if(count==0){
                for(Ergenka ergenka:ergenkas){
                    if(ergenka.getName().equals(votes[i])){
                        candidate=ergenka;
                        break;
                    }
                }
                count=1;
            }
            else if(candidate.getName().equals(votes[i])){
                count++;
            }
            else{
                count--;
            }
        }
        int votesCount=0;
        for (String vote:votes){
            if(vote.equals(candidate.getName())){
                votesCount++;
            }
        }
        if(votesCount<=votes.length*0.5){
            return ergenkas;
        }
        else{
            Ergenka[] survived=new Ergenka[ergenkas.length-1];
            int index=0;
            for (int i = 0; i < ergenkas.length ; i++) {
                if(!ergenkas[i].getName().equals(candidate.getName())){
                    survived[index]=ergenkas[i];
                    index++;
                }
            }
            return survived;
        }
    }
}
