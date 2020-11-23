package com.ncedu.cheetahtest.controller.project;

import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.project.ProjectResponse;
import com.ncedu.cheetahtest.service.project.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-management")
@CrossOrigin(origins = "${frontend.ulr}")
@Slf4j
public class ProjectController {
    private final ProjectServiceImpl projectService;

    @Autowired
    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/projects/{title}")
    public ResponseEntity<List<Project>> getProjectByTitle(@PathVariable("title") String projectTitle) {
        return ResponseEntity.ok(projectService.getProjectsByName(projectTitle));
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody Project project) {
        System.out.println(project);
        projectService.createNewProject(project);
        return ResponseEntity.ok(new ProjectResponse("A new project has been created successfully!"));
    }

    @PutMapping("/projects")
    public ResponseEntity<ProjectResponse> editProject(@RequestBody Project project) {
        return ResponseEntity.ok(
                new ProjectResponse("The project '"+project.getName()+"' has been updated!")
        );
    }
}
