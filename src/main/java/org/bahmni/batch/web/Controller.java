package org.bahmni.batch.web;

import org.bahmni.batch.BatchConfiguration;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    BatchConfiguration batchConfiguration;

    @Autowired
    JobLauncher jobLauncher;

    @RequestMapping(path = "/submit-to-dhis")
    public String submitToDHIS(@RequestParam("name") String program){
        return "Hello Controller";
    }

}
