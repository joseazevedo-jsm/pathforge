package dev.pathforge;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SkillTest {

    @Test
    public void rejectsBlankName(){
        assertThrows(IllegalArgumentException.class,()-> new Skill("",10,false));
    }

    @Test
    public void rejectsNonPositiveEstimatedHours(){
        assertThrows(IllegalArgumentException.class, () -> new Skill("Java", 0, true));
    }

    @Test
    public void markCompletedMarksSkillAsCompleted(){
        Skill skill = new Skill("Java", 10, false);
        skill.markCompleted();
        assertTrue(skill.isCompleted());
    }
}
