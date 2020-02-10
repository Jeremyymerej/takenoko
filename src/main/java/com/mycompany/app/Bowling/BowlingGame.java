package com.mycompany.app.Bowling;

public class BowlingGame {
    private int[] rolls = new int[21];
    private int currentRoll = 0;

    public void roll(int pins) {
        rolls[currentRoll++] = pins;
    }

    public int score() {
        int score = 0;
        int frameIndex = 0;
        for(int frame = 0; frame < 10; frame++){
            if(isaStrike(frameIndex)) {
                score += strikeBonus(frameIndex);
                frameIndex++;
            }
            else if (isaSpare(frameIndex)) {
                score += spareBonus(frameIndex);
                frameIndex += 2;
            } else {
                score += sumOfAllPinsInFrame(frameIndex);
                frameIndex += 2;
            }
        }
        return score;
    }

    private boolean isaStrike(int frameIndex) {
        return rolls[frameIndex] == 10;
    }

    private int strikeBonus(int frameIndex){
        return 10 + rolls[frameIndex + 1] + rolls[frameIndex + 2];
    }

    private int spareBonus(int frameIndex){
        return 10 + rolls[frameIndex+2];
    }

    private int sumOfAllPinsInFrame(int frameIndex) {
        return rolls[frameIndex] + rolls[frameIndex+1];
    }

    private boolean isaSpare(int frameIndex) {
        return sumOfAllPinsInFrame(frameIndex) == 10;
    }
}
