package com.mycompany.app.TakenokoGame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    private String name;
    private Integer score;
    private List<Objective> objectives;
    private List<Objective> completedObjectives;


    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.objectives = new ArrayList<>();
        this.completedObjectives = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public Integer getObjectivesSize() {
        return objectives.size();
    }

    public void drawObjective(Objective objective) {
        this.objectives.add(objective);
    }

    public void completeObjective(TakenokoGame game) {
        for(Objective objective : objectives) {
            if(objective.conditionFullfilled(game.getPlateau())) {
                this.score = objective.getVictoryPoints();
                this.completedObjectives.add(objective);
            }
        }
        objectives = objectives.stream().filter(o -> !completedObjectives.contains(o)).collect(Collectors.toList());
    }

    public Integer getCompletedObjectivesSize() {
        return completedObjectives.size();
    }

    public List<Objective> getCompletedObjectives() {
        return completedObjectives;
    }
}
