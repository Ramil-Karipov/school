package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
import java.util.List;


@Service
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(
            AvatarService.class);

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final String pathToAvatars;


    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository, @Value("${path.to.avatars.folder}") String pathToAvatars) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;

        this.pathToAvatars = pathToAvatars;
    }

    public void save(Long studentId, MultipartFile multipartFile) throws IOException {
        logger.info("Method save invoked");
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setStudent(studentRepository.getReferenceById(studentId));
        avatar.setData(multipartFile.getBytes());
        avatar.setFileSize(multipartFile.getSize());
        avatar.setMediaType(multipartFile.getContentType());

        Files.createDirectories(Path.of("avatars"));
        InputStream is = multipartFile.getInputStream();
        int lastIndexOf = multipartFile.getOriginalFilename().lastIndexOf('.');
        String extension = multipartFile.getOriginalFilename().substring(lastIndexOf);
        String filePath = pathToAvatars + studentId + "." + extension;
        FileOutputStream fos = new FileOutputStream(filePath);
        is.transferTo(fos);
        fos.close();
        avatar.setFilePath(filePath);


        avatarRepository.save(avatar);

    }

    public Avatar getAvatar(Long studentId) {
        logger.info("Method getAvatar invoked");
        return avatarRepository.findByStudentId(studentId).orElseThrow();
    }

    public List<Avatar> getPage(int page, int size) {
        logger.info("Method getPage invoked");
        return avatarRepository.findAll(PageRequest.of(page, size)).getContent();
    }
}
