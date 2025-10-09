package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.PasteSaveDTO;
import com.paste_bin_clone.other.AccessLevel;
import com.paste_bin_clone.other.LifeTime;
import com.paste_bin_clone.services.PasteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("paste")
@RequiredArgsConstructor
public class PasteController extends CommonController {

    private final PasteService pasteService;

    @GetMapping()
    @ApiOperation("Get last 10 public pastes")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = PasteDTO.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    public ResponseEntity<List<PasteDTO>> get() {
        return ResponseEntity.ok(pasteService.getLastTenPastes());
    }

    @PostMapping()
    @ApiOperation("Create new paste")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Paste added success", response = PasteDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    public ResponseEntity<PasteDTO> add(@RequestBody PasteSaveDTO pasteDTO) {
        return ResponseEntity.ok(pasteService.savePaste(pasteDTO, getUser()));
    }

    @GetMapping("/{hashCode}")
    @ApiOperation("Get paste by hash code")
    @ApiResponses({
        @ApiResponse(code = 200, message = "success", response = PasteDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<PasteDTO> getByHashCode(@PathVariable String hashCode) {
        return ResponseEntity.ok(pasteService.getPasteByHashCode(hashCode));
    }

    @GetMapping("/search/{text}")
    @ApiOperation("Search pastes by text in name or description")
    @ApiResponses({
        @ApiResponse(code = 200, message = "success", response = PasteDTO.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    public ResponseEntity<List<PasteDTO>> find(@PathVariable String text) {
        return ResponseEntity.ok(pasteService.searchPastes(text));
    }

    @GetMapping("/lifetime-options")
    @ApiOperation("Get list of available lifetime options")
    @ApiResponses({
        @ApiResponse(code = 200, message = "success", response = LifeTime.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    public ResponseEntity<List<LifeTime>> getLifetimeOptions() {
        return ResponseEntity.ok(LifeTime.getAll());
    }

    @GetMapping("/access-options")
    @ApiOperation("Get list of available access level options")
    @ApiResponses({
        @ApiResponse(code = 200, message = "success", response = AccessLevel.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    public ResponseEntity<List<AccessLevel>> getAccessLevelOptions() {
        return ResponseEntity.ok(AccessLevel.getAll());
    }

}

