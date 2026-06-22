package com.quitqecom.utility;

import com.quitqecom.exceptions.FileInvalidExtensionException;
import com.quitqecom.exceptions.FileNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtility {

    public static void validateFile(
            MultipartFile file){

        if(file.isEmpty()) {

            throw new FileNotFoundException(
                    "Please select a file");
        }

        List<String> allowedExts =
                List.of(
                        "jpg",
                        "jpeg",
                        "png",
                        "webp"
                );

        String filename =
                file.getOriginalFilename();

        String ext =
                filename.substring(
                        filename.lastIndexOf(".") + 1
                ).toLowerCase();

        if(!allowedExts.contains(ext)) {

            throw new FileInvalidExtensionException(
                    ext + " not allowed"
            );
        }
    }
}
