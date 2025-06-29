package com.insysred.isp.segurity.controller;
import com.insysred.isp.segurity.dto.ResponseMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {


    @GetMapping("/user-message")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseMessageDto> userMessage() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol usuario"), HttpStatus.OK);
    }

    @GetMapping("/admin-message")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseMessageDto> adminMessage() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol admin"), HttpStatus.OK);
    }

}
