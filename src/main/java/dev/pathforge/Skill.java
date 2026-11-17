package dev.pathforge;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Skill{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int estimatedHours;
    private boolean completed;

    protected Skill(){

    }

    public Skill(String name, int estimatedHours, boolean completed){
        verifyInput(name, estimatedHours);
        this.name = name;
        this.estimatedHours = estimatedHours;
        this.completed = completed;
    }

    private void verifyInput(String name, int estimatedHours){
        if(name == null || name.isBlank() )
            throw new IllegalArgumentException("Field Name invalid pls verify");
        if(estimatedHours<1)
            throw new IllegalArgumentException("field Estimated hours invalid pls insert a number greater than 0");
    }


    public void markCompleted(){
        this.completed = true;
    }
    public String getName(){
        return name;
    }

   public Long getId(){
        return this.id;
    }

    public int getEstimatedHours(){
        return estimatedHours;
    }

    public boolean isCompleted(){
        return completed;
    }
}