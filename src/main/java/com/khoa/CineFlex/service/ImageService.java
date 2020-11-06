package com.khoa.CineFlex.service;

import com.khoa.CineFlex.model.ImageModal;
import com.khoa.CineFlex.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void uploadImage(MultipartFile file, String email) throws IOException {
        ImageModal img = this.imageRepository.findByEmail(email);

        if (img != null) {
            img.setName(file.getOriginalFilename());
            img.setType(file.getContentType());
            img.setPicByte(this.compressBytes(file.getBytes()));
        } else {
            img = new ImageModal();
            img.setEmail(email);
            img.setName(file.getOriginalFilename());
            img.setType(file.getContentType());
            img.setPicByte(this.compressBytes(file.getBytes()));
        }

        // Remember to run the MySQL statement [SET GLOBAL max_allowed_packet=1073741824;] to expand the "max_allowed_packet"
        // variable to 1GB.
        // If you do not do this, you will likely get the error "Packet for query is too large (2.077.170 > 1.048.576)"
        this.imageRepository.save(img);
    }

    @Transactional
    public ImageModal getAvatar(String email) {
        ImageModal retrievedImg = this.imageRepository.findByEmail(email);

        if (retrievedImg != null) {
            ImageModal img = new ImageModal();

            img.setName(retrievedImg.getName());
            img.setType(retrievedImg.getType());
            img.setPicByte(this.decompressBytes(retrievedImg.getPicByte()));

            return img;
        }

        return null;
    }

    // compress the image bytes before storing it in the database
    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Cannot compress bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    private byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
