package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.CommentDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        @ApiResponse(code = 200, message = "Successfully", response = PasteDTO.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Access denied", response = ErrorResponse.class)
    })
    public ResponseEntity<List<PasteDTO>> getPastes() {
        return ResponseEntity.ok(userService.getPastes(getUser()));
    }

    @PostMapping("/pastes/{hashCode}")
    @ApiOperation("Update paste")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Paste successfully updated", response = PasteDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Access denied", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<PasteDTO> updatePaste(@PathVariable String hashCode,
                                                @RequestBody PasteDTO pasteDTO) {
        return ResponseEntity.ok(pasteService.updatePaste(pasteDTO, getUser()));
    }

    @DeleteMapping("/pastes/{hashCode}")
    @ApiOperation("Delete paste by hashCode")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Successfully deleted"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Access denied", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<Void> deletePaste(@PathVariable String hashCode) {
        pasteService.deleteByHashCode(hashCode, getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments")
    @ApiOperation("Add comment to paste")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Comment successfully added", response = CommentDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<CommentDTO> addComment(@RequestParam Long pasteId, @RequestParam String text) {
        return ResponseEntity.ok(pasteService.addComment(pasteId, text, getUser()));
    }

    @GetMapping("/comments")
    @ApiOperation("Get user`s comment")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Comment successfully added", response = CommentDTO.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<List<CommentDTO>> getUserComments() {
        return ResponseEntity.ok(userService.getUserComments(getUser()));
    }

    @DeleteMapping("/comments/{commentId}")
    @ApiOperation("Delete comment by commentId")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Comment successfully deleted", response = CommentDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Paste not found", response = ErrorResponse.class)
    })
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long commentId) {
        userService.deleteComment(commentId, getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/profile")
    @ApiOperation("Update user profile")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Profile successfully updated", response = UserDTO.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class)
    })
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateProfile(userDTO, getUser()));
    }
}
