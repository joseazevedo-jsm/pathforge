package dev.pathforge;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class SkillGraph {

    private final Map<String, Set<String>> preReqsBySkill = new HashMap<>();

    public void addSkill(String skillName){
        this.preReqsBySkill.putIfAbsent(skillName, new HashSet<>());
    }

    public int size(){
        return preReqsBySkill.size();
    }

    public void addPrerequisite(String skillName, String preReqsSkillName){
        requireSkill(preReqsSkillName) ;
        requireSkill(skillName);

        if(wouldCreateCycle(skillName, preReqsSkillName)){
            throw new IllegalArgumentException("Adding this prerequisite will create a cycle");
        }

        this.preReqsBySkill.get(skillName).add(preReqsSkillName);
    }

    public boolean hasDirectPrerequisite (String skillName, String preReqsSkillName){

        requireSkill(preReqsSkillName);
        requireSkill(skillName);

        return preReqsBySkill.get(skillName).contains(preReqsSkillName);
    }

    private void requireSkill(String skillName){
        if(!preReqsBySkill.containsKey(skillName)){
            throw new IllegalArgumentException("Unknown skill: " + skillName);
        }
    }


    public Set<String> findAllPrerequisites(String skillName){

        requireSkill(skillName);

        Set<String> preRequisiteList = new HashSet<>();

        Queue<String> allPreRequisiteSkills = new ArrayDeque<>();
        allPreRequisiteSkills.addAll(preReqsBySkill.get(skillName));

        while(!allPreRequisiteSkills.isEmpty()){

            String currSkill = allPreRequisiteSkills.poll();
            Set<String> currSkillList = preReqsBySkill.get(currSkill);

            if(!preRequisiteList.contains(currSkill)){
                preRequisiteList.add(currSkill);
                allPreRequisiteSkills.addAll(currSkillList);
            }
        }
        return preRequisiteList;
    }

    public boolean wouldCreateCycle(String skillName,String preReqSkillName){

        requireSkill(skillName);
        requireSkill(preReqSkillName);

        if(skillName.equals(preReqSkillName)){
            return true;
        }

        return findAllPrerequisites(preReqSkillName).contains(skillName);
    }


    public Set<String> findAvailableSkills(Set<String> completedSkills){

        Set<String> availableSkills = new HashSet<>();

        for (Map.Entry<String, Set<String>> requisitesBySkill: preReqsBySkill.entrySet()){

            if (!completedSkills.contains(requisitesBySkill.getKey())
                && completedSkills.containsAll(requisitesBySkill.getValue())){
                    availableSkills.add(requisitesBySkill.getKey());
            }
        }
        return availableSkills;
    }

    public Map<String,Integer> preRequisiteCounts(){
        Map<String,Integer> countsBySkill = new HashMap<>();

        for(Map.Entry<String,Set<String>> skills: preReqsBySkill.entrySet()){
            countsBySkill.put(skills.getKey(), skills.getValue().size());
        }

        return countsBySkill;
    }

    public Map<String,Set<String>> dependentsBySkill(){
        Map<String,Set<String>> tempMapDependencies = new HashMap<>();

        for(Map.Entry<String,Set<String>> skills: preReqsBySkill.entrySet()){
            tempMapDependencies.put(skills.getKey(), new HashSet<>());
        }

        for(Map.Entry<String,Set<String>> skillsDependencies: preReqsBySkill.entrySet()){
            for(String preReq : skillsDependencies.getValue()){
                tempMapDependencies.get(preReq).add(skillsDependencies.getKey());
            }
        }

        return tempMapDependencies;
    }

    public Set<String> dependentsOf(String skillName){

        requireSkill(skillName);

        Set<String> tempDepsBySkill = new HashSet<>();

        for(Map.Entry<String,Set<String>> skills: preReqsBySkill.entrySet()){
            if(skills.getValue().contains(skillName)){
                tempDepsBySkill.add(skills.getKey());
            }
        }

        return tempDepsBySkill;
    }
}
