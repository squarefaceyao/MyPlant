package com.songc.controller;

import com.songc.dao.DatasetDao;
import com.songc.entity.Dataset;
import com.songc.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by songc on 4/27/2017.
 */
@RestController
@RequestMapping(value = "/dataset")
public class DatasetController {

    private DatasetService datasetService;

    @Autowired
    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }

    @PostMapping
    public Long addDataset(@RequestBody Dataset dataset) {
        return datasetService.save(dataset);
    }

    @GetMapping
    public Page<Dataset> getPageDataset(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return datasetService.getPageDataset(page, size);
    }
}