package org.launchcode.controllers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam(defaultValue = "1") int id, Model model) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        model.addAttribute("someJob", someJob);


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(
            Model model,
            @Valid JobForm jobForm,
            Errors errors) {

        String name = jobForm.getName();
        Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location location = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionId());
        CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreId());

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {

            return "new-job";
        }


        Job someJob = new Job(name, employer, location, positionType, coreCompetency);
        model.addAttribute("name", someJob.getName());
        System.out.println(someJob.getLocation());
        model.addAttribute("employer", someJob.getEmployer());
        model.addAttribute("location", someJob.getLocation());
        model.addAttribute("coreCompetency", someJob.getCoreCompetency());
        model.addAttribute("positionType", someJob.getPositionType());
        jobData.add(someJob);
        model.addAttribute(someJob);
        return "redirect:/job/?id=" + someJob.getId();
    }


    }

