package com.ncedu.cheetahtest.controller.project;

import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.project.ProjectDto;
import com.ncedu.cheetahtest.entity.project.ProjectResponse;
import com.ncedu.cheetahtest.entity.project.ResponseProjectPaginated;
import com.ncedu.cheetahtest.service.project.ProjectService;
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
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectDto projectDto) {
        projectService.createNewProject(projectDto);
        return ResponseEntity.ok(new ProjectResponse("A new project has been created successfully!"));
    }

    @GetMapping("/projects")
    public ResponseProjectPaginated getAllProjects(@RequestParam int page,
                                                   @RequestParam int size) {
        return projectService.getAllProjects(page, size);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/projects/search")
    public ResponseEntity<List<Project>> getProjectsByTitle( @RequestParam int page,
                                                             @RequestParam int size,
                                                             @RequestParam String title) {
        return ResponseEntity.ok(projectService.getProjectsPaginatedByTitle(page, size, title));
    }


    @GetMapping("projects/archive")
    public ResponseEntity<List<Project>> getAllArchievedProjects() {
        return ResponseEntity.ok(projectService.getAllArchievedProjects());
    }

    @PutMapping("projects/archive/{id}")
    public ResponseEntity<ProjectResponse> setArchivedStatus(@PathVariable int id) {
        projectService.setArchievedStatus(id);
        return ResponseEntity.ok(new ProjectResponse("A project No. "+id+" has been archieved!"));
    }
}
