package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class RomanticErgenka implements Ergenka{
    private String name;
    private short age;
    private int romanceLevel;
    private int humorLevel;
    private int rating;
    private String favoriteDateLocation;

    public RomanticErgenka(String name, short age, int romanceLevel,
                           int humorLevel, int rating, String favoriteDateLocation){
        this.name=name;
        this.age=age;
        this.romanceLevel=romanceLevel;
        this.humorLevel=humorLevel;
        this.rating=rating;
        this.favoriteDateLocation=favoriteDateLocation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public short getAge() {
        return age;
    }

    @Override
    public int getRomanceLevel() {
        return romanceLevel;
    }

    @Override
    public int getHumorLevel() {
        return humorLevel;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        if (dateEvent.getTensionLevel() == 0) {
            return;
        }
        int bonuses=0;
        if(dateEvent.getLocation().equalsIgnoreCase(this.favoriteDateLocation)){
            bonuses+=5;
        }
        if(dateEvent.getDuration()<30){
            bonuses-=3;
        }
        if(dateEvent.getDuration()>120){
            bonuses-=2;
        }
        this.rating+=romanceLevel*7/dateEvent.getTensionLevel()+humorLevel/3+bonuses;
    }
}
