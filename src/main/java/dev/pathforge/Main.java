package dev.pathforge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Main{

    public static void main(String[] args){

        List<Skill> skillList = new ArrayList<>();

        skillList.add(new Skill("Java", 50, false));
        skillList.add(new Skill("Data Structures", 5, false));
        skillList.add(new Skill("Spring", 10, false));

        skillList.getFirst().markCompleted();

        System.out.println("Welcome to PathForge");

        for(Skill skill : skillList){
            printSkill(skill);
        }

        List<Skill> incompleteSkills = findIncompleteSkills(skillList);

        System.out.println("Incomplete Skill: ");
        for(Skill skill: incompleteSkills){
            printSkill(skill);
        }

        Map<String, Skill> skillsByName = indexSkillsByName(skillList);
        String skillName = "Python";

        System.out.println("Find by skill by name: "+skillName);
        Skill foundSkill = skillsByName.get(skillName);

        if (foundSkill != null){
            printSkill(foundSkill);
        } else {
            System.out.println("Not found: "+skillName);
        }

        Set<String> completedSkills = findCompletedSkills(skillList);
        completedSkills.add("Java");

        System.out.println(completedSkills+" Size: "+completedSkills.size()+ " Java finished: "+completedSkills.contains("Java"));

        SkillGraph skillGraph = new SkillGraph();

        skillGraph.addSkill("Logic");
        skillGraph.addSkill("Programming Basics");
        skillGraph.addSkill("Java");
        skillGraph.addSkill("Data Structures");
        skillGraph.addSkill("Spring");

        skillGraph.addSkill("Java");


        System.out.println("Skill Graph size: "+skillGraph.size());


        skillGraph.addPrerequisite("Programming Basics", "Logic");
        skillGraph.addPrerequisite("Java", "Programming Basics");
        skillGraph.addPrerequisite("Data Structures","Java");
        skillGraph.addPrerequisite("Spring", "Java");
        skillGraph.addPrerequisite("Spring","Data Structures");

        System.out.println("Has prerequisiste: "+ skillGraph.hasDirectPrerequisite("Spring", "Java"));
        System.out.println("Has prerequisiste: "+ skillGraph.hasDirectPrerequisite("Java", "Spring"));
        System.out.println("Has prerequisiste: "+ skillGraph.hasDirectPrerequisite("Spring", "Logic"));


        System.out.println("Pre reqs graph: "+ skillGraph.findAllPrerequisites("Spring"));
        System.out.println("Would create cycle: "+skillGraph.wouldCreateCycle("Java", "Spring"));

        try{
            skillGraph.addPrerequisite("Java", "Spring");
        } catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
        }

        System.out.println("has prerequisite: "+ skillGraph.hasDirectPrerequisite("Java", "Spring"));

        Queue<String> availableSkills = new ArrayDeque<>();
        availableSkills.addAll(skillGraph.findAvailableSkills(completedSkills));
        while(!availableSkills.isEmpty()){
            String tempSkillName = availableSkills.poll();
            System.out.println("Study Next: "+tempSkillName);
        }

        for(Map.Entry<String,Integer> skillCount: skillGraph.preRequisiteCounts().entrySet()){
            System.out.println(skillCount.getKey() +" : "+skillCount.getValue());
        }

        System.out.println("Dependencies: "+skillGraph.dependentsBySkill());

        System.out.println("Total hours studying: "+calculateTotalHours(skillList));
    }

    static void printSkill(Skill skill){

        if (skill.isCompleted()){
            System.out.println("{Skill: "+skill.getName()+", Study Hours: "+skill.getEstimatedHours()+", Status: Completed}");
        }else{
            System.out.println("{Skill: "+skill.getName()+", Study Hours: "+skill.getEstimatedHours()+", Status: Not completed}");
        }

    }
    static int calculateTotalHours(List<Skill> skillList){
        int totalHours = 0;

        for(Skill skill: skillList){
            totalHours +=skill.getEstimatedHours();
        }

        return totalHours;
    }

    static List<Skill> findIncompleteSkills(List<Skill> skillList){
        List<Skill> incompleteSkills = new ArrayList<>();

        for(Skill skill: skillList){
            if(!skill.isCompleted()){
                incompleteSkills.add(skill);
            }
        }
        return incompleteSkills;
    }

    static Set<String> findCompletedSkills(List<Skill> skillList){
        Set<String> completedSkills = new HashSet<>();

        for(Skill skill: skillList){
            if (skill.isCompleted()) {
                completedSkills.add(skill.getName());
            }
        }
        return completedSkills;
    }
    static Map<String, Skill> indexSkillsByName(List<Skill> skillList){
        Map<String, Skill> skillsByName = new HashMap<>();

        for (Skill skill: skillList){
            skillsByName.put(skill.getName(), skill);
        }

        return skillsByName;
    }

}
