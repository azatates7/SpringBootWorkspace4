package fileupload.controller;

import ch.qos.logback.classic.spi.ILoggingEvent;
import fileupload.form.UploadForm;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainRestController {
    // Linux: /home/{user}/Downloads//testupload
    // Windows: C:/Users/{user}/test
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/Downloads" + "/testupload";

    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> uploadFileMulti(@ModelAttribute UploadForm form) throws Exception {
        String result = null;
        try {
            result = this.saveUploadedFiles(form.getFiles());
        } catch (Exception e) {
            URLConnection con = new URL("/templates/error.html").openConnection();
            con.connect();
        }
        return new ResponseEntity<String>("Uploaded to : \n " + result, HttpStatus.OK);
    }

    private String saveUploadedFiles(MultipartFile[] files) throws IOException {
        try {
            File uploadDir = new File(UPLOAD_DIR);
            uploadDir.mkdirs();
            StringBuilder sb = new StringBuilder();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadFilePath);
                    Files.write(path, bytes);
                    sb.append(uploadFilePath).append(", ");
                }
            }
            return sb.toString();
        } catch (Exception ex) {
            URLConnection con = new URL("/templates/error.html").openConnection();
            con.connect();
        }
        return "error";
    }

    @GetMapping("/rest/getAllFiles")
    public List<String> getListFiles() throws IOException {
        try {
            File uploadDir = new File(UPLOAD_DIR);
            File[] files = uploadDir.listFiles();
            List<String> list = new ArrayList<String>();
            for (File file : files) {
                list.add(file.getName());
            }
            return list;
        } catch (Exception ex) {
            URLConnection con = new URL("/templates/error.html").openConnection();
            con.connect();
        }
        return null;
    }

    @GetMapping("/rest/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename, ILoggingEvent ex) throws IOException {
        try {
            File file = new File(UPLOAD_DIR + "/" + filename);
            if (!file.exists()) {
                URLConnection con = new URL("/templates/error.html").openConnection();
                con.connect();
            }
            Resource resource = (Resource) new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            URLConnection con = new URL("/templates/error.html").openConnection();
            con.connect();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}