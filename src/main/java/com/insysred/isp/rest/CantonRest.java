package com.insysred.isp.rest;

import com.insysred.isp.dto.CantonDto;
import com.insysred.isp.service.declare.CantonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CantonRest {

    @Autowired
    private CantonService cantonService;

    @GetMapping("/cantones")
    public ResponseEntity<List<CantonDto>> getAllCanton(){
        try{
            return new ResponseEntity<>(cantonService.lstCanton(), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping("/cantones/{id}")
    public ResponseEntity<CantonDto> getCanton(@PathVariable Long id){
        try{
            return new ResponseEntity<>(cantonService.getCantonById(id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/cantones/provincia/{id}")
    public ResponseEntity<List<CantonDto>> getCantonbyProvincia(@PathVariable Long id){
        try{
            return new ResponseEntity<>(cantonService.getCantonByProvincia(id), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/cantones")
    public ResponseEntity<CantonDto> nuevoCanton(@RequestBody CantonDto cantonDTO){
        try {
            return ResponseEntity.ok(cantonService.saveCanton(cantonDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
