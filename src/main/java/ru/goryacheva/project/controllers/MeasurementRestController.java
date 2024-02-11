package ru.goryacheva.project.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.goryacheva.project.dto.MeasurementDto;
import ru.goryacheva.project.dto.MeasurementResponse;
import ru.goryacheva.project.model.Measurement;
import ru.goryacheva.project.service.MeasurementService;
import ru.goryacheva.project.utils.MeasurementErrorResponse;
import ru.goryacheva.project.utils.MeasurementException;
import ru.goryacheva.project.utils.MeasurementValidation;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static ru.goryacheva.project.utils.ErrorUtils.errorsTo;

@RestController
@RequestMapping("/measurements")
public class MeasurementRestController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidation measurementValidation;

    @Autowired
    public MeasurementRestController(MeasurementService measurementService,
                                     ModelMapper modelMapper,
                                     MeasurementValidation measurementValidation) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidation = measurementValidation;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDto measurementDto,
                                             BindingResult error){
        Measurement measurement = mappingDtoToModel(measurementDto);
        measurementValidation.validate(measurement, error);
        if (error.hasErrors())
            errorsTo(error);
        measurementService.add(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public MeasurementResponse getAll(){
        return new MeasurementResponse(measurementService
                .getAll()
                .stream()
                .map(this::mappingModelToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.getAll().stream().filter(m -> m.getRaining()).count();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement mappingDtoToModel(MeasurementDto measurementDto){
        return modelMapper.map(measurementDto,Measurement.class);
    }

    private MeasurementDto mappingModelToDto(Measurement measurement){
        return modelMapper.map(measurement,MeasurementDto.class);
    }
}
