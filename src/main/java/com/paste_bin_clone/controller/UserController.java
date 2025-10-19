package com.paste_bin_clone.controller;

import com.paste_bin_clone.dto.CommentDTO;
import com.paste_bin_clone.dto.PasteDTO;
import com.paste_bin_clone.dto.UserDTO;
import com.paste_bin_clone.services.PasteService;
import com.paste_bin_clone.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get current user's pastes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<PasteDTO>> getPastes() {
        return ResponseEntity.ok(userService.getPastes(getUser()));
    }

    @PostMapping("/pastes/{hashCode}")
    @Operation(summary = "Update paste")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paste successfully updated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Paste not found")
    })
    public ResponseEntity<PasteDTO> updatePaste(@PathVariable String hashCode,
                                                @RequestBody PasteDTO pasteDTO) {
        return ResponseEntity.ok(pasteService.updatePaste(pasteDTO, getUser()));
    }

    @DeleteMapping("/pastes/{hashCode}")
    @Operation(summary = "Delete paste by hashCode")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Successfully deleted"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Paste not found")
    })
    public ResponseEntity<Void> deletePaste(@PathVariable String hashCode) {
        pasteService.deleteByHashCode(hashCode, getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments")
    @Operation(summary = "Add comment to paste")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comment successfully added"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Paste not found")
    })
    public ResponseEntity<CommentDTO> addComment(@RequestParam Long pasteId, @RequestParam String text) {
        return ResponseEntity.ok(pasteService.addComment(pasteId, text, getUser()));
    }

    @GetMapping("/comments")
    @Operation(summary = "Get user`s comment")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comment successfully added"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Paste not found")
    })
    public ResponseEntity<List<CommentDTO>> getUserComments() {
        return ResponseEntity.ok(userService.getUserComments(getUser()));
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "Delete comment by commentId")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Comment successfully deleted"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Paste not found")
    })
    public ResponseEntity<CommentDTO> deleteComment(@PathVariable Long commentId) {
        userService.deleteComment(commentId, getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/profile")
    @Operation(summary = "Update user profile")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Profile successfully updated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateProfile(userDTO, getUser()));
    }
}
