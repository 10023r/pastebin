package com.pet_project.pastebin.paste;

import com.pet_project.pastebin.tools.PasteStatus;
import jakarta.validation.Valid;
import org.javatuples.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class PasteController {

    PasteService service;

    PasteController(PasteService service) {
        this.service = service;
    }

    @PostMapping(path = "/{hash}")
    public ResponseEntity<?> getPaste(
            @PathVariable String hash,
            @RequestBody Map<String, String> obj
    ) {
        String password = obj.get("password") != null ? obj.get("password") : "";
        Pair<PasteStatus, String> paste = service.getPasteByHash(hash, password);
        Map<String, String> result = new HashMap<>();
        result.put("status", paste.getValue0().getMessage());
        result.put("text", paste.getValue1());
        return switch (paste.getValue0()) {
            case NO_SUCH_PASTE -> new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            case PASSWORD_INCORRECT -> new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            default -> new ResponseEntity<>(result, HttpStatus.OK);
        };
    }

    @PostMapping
    public String newPaste(
            @Valid @RequestBody Paste paste
    ) {
        service.savePaste(paste);
        return paste.getId();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach( error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleBadRequestException(NoSuchElementException ex) {
        return "No such paste";
    }
}
