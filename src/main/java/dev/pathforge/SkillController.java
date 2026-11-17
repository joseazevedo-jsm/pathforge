package dev.pathforge;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService){
        this.skillService = skillService;
    }

    @GetMapping("/api/skills")
    public List<Skill> getSkills(){
        return skillService.findAll();
    }

    @GetMapping("/api/skills/{id}")
    public ResponseEntity<Skill> getSkillsById(@PathVariable Long id){
        Optional<Skill> skillById = skillService.findById(id);
        return skillById.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(skillById.get());
    }

    @PostMapping("/api/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody @Valid CreateSkillRequest skill){
        Skill tempSkill = new Skill(skill.name(),skill.estimatedHours(),false);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(skillService.add(tempSkill));
    }

    @DeleteMapping("/api/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id){
        return skillService.deleteById(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/api/skills/{id}/complete")
    public ResponseEntity<Skill> markSkillCompleted(@PathVariable Long id){
        Optional<Skill> tempSkill = skillService.markCompleted(id);
        return tempSkill.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(tempSkill.get())
                : ResponseEntity.notFound().build();
    }
}
