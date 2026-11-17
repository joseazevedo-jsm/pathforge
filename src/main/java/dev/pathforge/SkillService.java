package dev.pathforge;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository){
        this.skillRepository = skillRepository;
    }
    public Optional<Skill> findByName(String skillName){
        return skillRepository.findByName(skillName);
    }
    public List<Skill> findAll(){
        return skillRepository.findAll();
    }
    public Skill add(Skill skill){
        return skillRepository.save(skill);
    }

    public Optional<Skill> findById(Long id){
        return skillRepository.findById(id);
    }

    public boolean deleteById(Long id){

        if(skillRepository.existsById(id)){
            skillRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Transactional
    public Optional<Skill> markCompleted(Long id){
        Optional<Skill> tempSkill = skillRepository.findById(id);

        if(tempSkill.isPresent()){
            tempSkill.get().markCompleted();
        }

        return tempSkill;
    }
}
