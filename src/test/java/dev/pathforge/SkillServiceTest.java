package dev.pathforge;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class SkillServiceTest {

    @Test
    public void findByNameReturnsEmptyWhenMissing(){
        SkillRepository skillRepository = mock(SkillRepository.class);
        when(skillRepository.findByName("Python")).thenReturn(Optional.empty());
        SkillService skillService = new SkillService(skillRepository);
        Optional<Skill> tempSkill = skillService.findByName("Python");
        assertTrue(tempSkill.isEmpty());
    }


    @Test
    public void findByIdReturnsSkillWhenPresent(){
        SkillRepository skillRepository = mock(SkillRepository.class);
        when(skillRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(new Skill("Java",10,false)));
        SkillService skillService = new SkillService(skillRepository);
        Optional<Skill> tempSkill = skillService.findById(Long.valueOf(1));
        assertTrue(tempSkill.isPresent());
    }
}
