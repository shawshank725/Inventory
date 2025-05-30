package bro.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadImpl implements FileUpload{

    private Cloudinary cloudinary;

    @Autowired
    public FileUploadImpl (Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String url = cloudinary.uploader().upload(multipartFile.getBytes(),
                Map.of("public_id", UUID.randomUUID().toString()))
                .get("url").toString();
        System.out.println(url);
        return url;
    }

    public String extractPublicId(String imageUrl) {
        // Remove base URL and version
        int uploadIndex = imageUrl.indexOf("/upload/");
        if (uploadIndex == -1) return null;

        String afterUpload = imageUrl.substring(uploadIndex + "/upload/".length());
        // Remove versioning
        String[] parts = afterUpload.split("/");
        if (parts.length < 2) return null;

        String versionOrFolder = parts[0];
        if (versionOrFolder.matches("v\\d+")) {
            afterUpload = afterUpload.substring(versionOrFolder.length() + 1);
        }

        // Remove file extension
        int dotIndex = afterUpload.lastIndexOf('.');
        return (dotIndex > -1) ? afterUpload.substring(0, dotIndex) : afterUpload;
    }

    @Override
    public void deleteFile(String url) throws IOException {
        String publicID = extractPublicId(url);
        Map result = cloudinary.uploader().destroy(publicID, ObjectUtils.emptyMap());
        System.out.println(result.toString());
    }
}
