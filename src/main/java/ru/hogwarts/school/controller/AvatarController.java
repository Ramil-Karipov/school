package ru.hogwarts.school.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(@RequestParam Long studentId, @RequestBody MultipartFile multipartFile) {
        try {
            avatarService.save(studentId, multipartFile);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping(value = "/fromdDb")
    public ResponseEntity<byte[]> getFromDb(@RequestParam Long studentId) {
        Avatar avatar = avatarService.getAvatar(studentId);
        byte[] data = avatar.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(avatar.getFileSize());
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));

        return ResponseEntity
                .status(200)
                .headers(headers)
                .body(data);

    }

    @GetMapping("/fromDisk")
    public void getFromDisk(@RequestParam Long studentId, HttpServletResponse response) {
        Avatar avatar = avatarService.getAvatar(studentId);
        response.setStatus(200);
        response.setHeader("content-type", avatar.getMediaType());
        response.setHeader("content-length", String.valueOf(avatar.getFileSize()));
        try {
            ServletOutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(avatar.getFilePath());
            fis.transferTo(os);
            fis.close();
        } catch (IOException e) {
            response.setStatus(400);
        }
    }

    @GetMapping("/page")
    public List<Avatar> getPage(int page, int size) {
        return avatarService.getPage(page, size);
    }
}
