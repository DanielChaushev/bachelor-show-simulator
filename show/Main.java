package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowAttributeSumEliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowestRatingEliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.PublicVoteEliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;
import bg.sofia.uni.fmi.mjt.show.ergenka.HumorousErgenka;
import bg.sofia.uni.fmi.mjt.show.ergenka.RomanticErgenka;

public class Main {

    static void printErgenkas(String label, Ergenka[] ergenkas) {
        System.out.println("[" + label + "]");
        for (Ergenka e : ergenkas) {
            System.out.printf("  %-10s rating=%-4d romance=%-3d humor=%d%n",
                    e.getName(), e.getRating(), e.getRomanceLevel(), e.getHumorLevel());
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // Test 1: rating formula after a single date
        // RomanticErgenka: romance*7/tension + humor/3 + bonuses
        // 6*7/5 + 3/3 + 5 (favorite location bonus) = 8 + 1 + 5 = 14
        RomanticErgenka romantic = new RomanticErgenka("Ana", (short) 25, 6, 3, 0, "Paris");
        romantic.reactToDate(new DateEvent("paris", 5, 60));
        System.out.println("Test 1a - RomanticErgenka rating after date in Paris (expected 14): " + romantic.getRating());

        // HumorousErgenka: humor*5/tension + romance/3 + bonuses
        // 6*5/5 + 3/3 + 4 (duration bonus) = 6 + 1 + 4 = 11
        HumorousErgenka humorous = new HumorousErgenka("Betty", (short) 22, 3, 6, 0);
        humorous.reactToDate(new DateEvent("Cafe", 5, 60));
        System.out.println("Test 1b - HumorousErgenka rating after date (expected 11): " + humorous.getRating());
        System.out.println();

        // Test 2: LowestRatingEliminationRule eliminates all with the minimum rating
        Ergenka[] group = {
                new RomanticErgenka("Ana",   (short) 25, 6, 3, 10, "Paris"),
                new RomanticErgenka("Maria", (short) 23, 4, 4, 5,  "Rome"),
                new HumorousErgenka("Betty", (short) 22, 3, 6, 5),
                new HumorousErgenka("Geri",  (short) 27, 2, 2, 2)  // lowest rating
        };
        printErgenkas("Test 2 - before LowestRating elimination", group);
        Ergenka[] afterLowest = new LowestRatingEliminationRule().eliminateErgenkas(group);
        printErgenkas("Test 2 - after (Geri should be gone)", afterLowest);

        // Test 3: LowAttributeSumEliminationRule removes contestants below threshold
        Ergenka[] group2 = {
                new RomanticErgenka("Ana",   (short) 25, 6, 3, 10, "Paris"), // sum=9, stays
                new RomanticErgenka("Maria", (short) 23, 2, 2, 5,  "Rome"),  // sum=4, eliminated
                new HumorousErgenka("Betty", (short) 22, 3, 6, 5),           // sum=9, stays
                new HumorousErgenka("Geri",  (short) 27, 1, 1, 8)            // sum=2, eliminated
        };
        printErgenkas("Test 3 - before LowAttributeSum elimination (threshold=8)", group2);
        Ergenka[] afterSum = new LowAttributeSumEliminationRule(8).eliminateErgenkas(group2);
        printErgenkas("Test 3 - after (only Ana and Betty should remain)", afterSum);

        // Test 4: PublicVoteEliminationRule eliminates contestant with 50%+1 votes
        Ergenka[] group3 = {
                new RomanticErgenka("Ana",   (short) 25, 6, 3, 10, "Paris"),
                new RomanticErgenka("Maria", (short) 23, 4, 4, 5,  "Rome"),
                new HumorousErgenka("Betty", (short) 22, 3, 6, 5)
        };
        String[] votes = {"Ana", "Ana", "Maria", "Ana", "Betty", "Ana"}; // Ana has 4/6 votes
        printErgenkas("Test 4 - before public vote", group3);
        Ergenka[] afterVote = new PublicVoteEliminationRule(votes).eliminateErgenkas(group3);
        printErgenkas("Test 4 - after (Ana should be eliminated)", afterVote);

        // Test 5: full round — date with all contestants, then elimination
        Ergenka[] group4 = {
                new RomanticErgenka("Ana",   (short) 25, 6, 3, 0, "Paris"),
                new RomanticErgenka("Maria", (short) 23, 4, 4, 0, "Rome"),
                new HumorousErgenka("Betty", (short) 22, 3, 6, 0),
                new HumorousErgenka("Geri",  (short) 27, 1, 1, 0)
        };
        ShowAPI show = new ShowAPIImpl(group4, new EliminationRule[]{ new LowestRatingEliminationRule() });
        printErgenkas("Test 5 - before round", show.getErgenkas());
        show.playRound(new DateEvent("Paris", 5, 60));
        printErgenkas("Test 5 - after round (lowest rated should be gone)", show.getErgenkas());

        // Test 6: eliminateErgenkas with empty rules array falls back to LowestRatingEliminationRule
        Ergenka[] group5 = {
                new RomanticErgenka("Ana",  (short) 25, 6, 3, 10, "Paris"),
                new HumorousErgenka("Geri", (short) 27, 1, 1, 2)
        };
        ShowAPI show2 = new ShowAPIImpl(group5, new EliminationRule[]{});
        printErgenkas("Test 6 - before elimination with empty rules", show2.getErgenkas());
        show2.eliminateErgenkas(new EliminationRule[]{});
        printErgenkas("Test 6 - after (Geri should be gone)", show2.getErgenkas());
    }
}