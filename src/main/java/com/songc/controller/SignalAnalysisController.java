package com.songc.controller;

import com.songc.core.ap.APExtractService;
import com.songc.core.ap.APJudgeService;
import com.songc.core.ap.detect.wave.APInRawWave;
import com.songc.service.ImageAnalysisService;
import com.songc.util.SmoothFilter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author songc
 */
@RestController
@RequestMapping(value = "/analysis")
public class SignalAnalysisController {

    private ImageAnalysisService imageAnalysisService;

    @Autowired
    public SignalAnalysisController(ImageAnalysisService imageAnalysisService) {
        this.imageAnalysisService = imageAnalysisService;
    }

    @ApiOperation(value = "Smooth waveform")
    @PostMapping(value = "/smooth/{windowWidth}")
    public List<double[]> smooth(@RequestBody Double[][] rawSignal, @PathVariable("windowWidth") Integer windowWidth) {
        Assert.isTrue(windowWidth > 1, "windowWidth must > 1");
        return SmoothFilter.filter(Arrays.asList(rawSignal), windowWidth);
    }

    @ApiOperation(value = "extract AP-like waveform from the raw waveform")
    @PostMapping(value = "/ap/extraction/{rate}")
    public List<List<APInRawWave>> findAP(@RequestBody Double[][] rawSignal, @PathVariable Integer rate) {
        return new APExtractService(rawSignal, rate).getAllAPInfo();
    }

    @ApiOperation(value = "Determine if a waveform is an AP waveform")
    @PostMapping(value = "/ap/judgement/{rate}")
    public List<Boolean> isAP(@RequestBody Double[][] apLike, @PathVariable Integer rate) {
        return new APJudgeService(apLike, rate).isAP();
    }

    @ApiOperation(value = "Get single region signal from images series")
    @GetMapping(value = "/image/single/{datasetId}")
    public String singleRegion(@PathVariable Long datasetId,
                               @RequestParam int startX,
                               @RequestParam int startY,
                               @RequestParam int width,
                               @RequestParam int height) {
        return imageAnalysisService.singleRegion(datasetId, startX, startY, width, height);
    }

    @ApiOperation(value = "Get multiple region signal from images series")
    @GetMapping(value = "/image/multiple/{datasetId}")
    public String multiRegion(@PathVariable Long datasetId, @RequestParam int width, @RequestParam int height) {
        return imageAnalysisService.multiRegion(datasetId, width, height);
    }
}
