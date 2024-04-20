package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;

    }

    public void save(Long studentId, MultipartFile multipartFile) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(studentRepository.getReferenceById(studentId));
        avatar.setData(multipartFile.getBytes());
        avatar.setFileSize(multipartFile.getSize());
        avatar.setMediaType(multipartFile.getContentType());

        Files.createDirectories(Path.of("avatars"));
        InputStream is = multipartFile.getInputStream();
        int lastIndexOf = multipartFile.getOriginalFilename().lastIndexOf('.');
        String extension = multipartFile.getOriginalFilename().substring(lastIndexOf);
        String filePath = "avatars/" + studentId + "." + extension;
        FileOutputStream fos = new FileOutputStream(filePath);
        is.transferTo(fos);
        fos.close();
        avatar.setFilePath(filePath);


        avatarRepository.save(avatar);

    }

    public Avatar getAvatar(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }
}
