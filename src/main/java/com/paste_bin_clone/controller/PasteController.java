package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.PasteSaveDTO;
import com.paste_bin_clone.other.AccessLevel;
import com.paste_bin_clone.other.LifeTime;
import com.paste_bin_clone.services.PasteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get last 10 public pastes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<List<PasteDTO>> get() {
        return ResponseEntity.ok(pasteService.getLastTenPastes());
    }

    @PostMapping()
    @Operation(summary = "Create new paste")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paste added success"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<PasteDTO> add(@RequestBody PasteSaveDTO pasteDTO) {
        return ResponseEntity.ok(pasteService.savePaste(pasteDTO, getUser()));
    }

    @GetMapping("/{hashCode}")
    @Operation(summary = "Get paste by hash code")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "success"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Paste not found")
    })
    public ResponseEntity<PasteDTO> getByHashCode(@PathVariable String hashCode) {
        return ResponseEntity.ok(pasteService.getPasteByHashCode(hashCode));
    }

    @GetMapping("/search/{text}")
    @Operation(summary = "Search pastes by text in name or description")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "success"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<List<PasteDTO>> find(@PathVariable String text) {
        return ResponseEntity.ok(pasteService.searchPastes(text));
    }

    @GetMapping("/lifetime-options")
    @Operation(summary = "Get list of available lifetime options")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "success"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<List<LifeTime>> getLifetimeOptions() {
        return ResponseEntity.ok(LifeTime.getAll());
    }

    @GetMapping("/access-options")
    @Operation(summary = "Get list of available access level options")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "success"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<List<AccessLevel>> getAccessLevelOptions() {
        return ResponseEntity.ok(AccessLevel.getAll());
    }

}

