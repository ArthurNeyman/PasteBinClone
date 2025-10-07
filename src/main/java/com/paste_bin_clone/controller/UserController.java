package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.PasteService;
import com.paste_bin_clone.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController extends CommonController {

    private final PasteService pasteService;
    private final UserService userService;

    @GetMapping("/pastes")
    @ApiOperation("Get current user's pastes")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Successfully retrieved", response = PasteDTO[].class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Access denied", response = ErrorResponse.class)
    })
    public ResponseEntity<List<PasteDTO>> getPastes() {
        return ResponseEntity.ok(userService.getPastes(getUser()));
    }

    @PostMapping("/pastes/{hashCode}")
    @ApiOperation("Create new paste")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Paste successfully updated", response = PasteDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Access denied", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<PasteDTO> updatePaste(@PathVariable String hashCode,
                                                          @Valid @RequestBody PasteDTO pasteDTO) {
        return ResponseEntity.ok(pasteService.updatePaste(pasteDTO, getUser()));
    }

    @DeleteMapping("/pastes/{hashCode}")
    @ApiOperation("Delete paste by hashCode")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Successfully deleted", response = ErrorResponse.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Access denied", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<Void> deletePaste(@PathVariable String hashCode) {
        pasteService.deleteByHashCode(hashCode, getUser());
        return ResponseEntity.noContent().build();
    }

    //-----------------------------------------------------------

    //todo отладить изменение данных пользователя
    @PostMapping("/changeProfile")
    public UserDTO changeProfile(UserDTO user) {
        return user;
    }

    //------------------------------------------------------------------------------------------------------------------
}
