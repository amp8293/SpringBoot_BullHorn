package com.example.demo;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryConfig {
    private Cloudinary cloudinary;

    public CloudinaryConfig(
            @Value("${cloudinary.apikey}") String apiKey,
            @Value("${cloudinary.apisecret}") String apiSecret,
            @Value("${cloudinary.cloudname}") String cloudName
    ) {
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName = cloudName;
        cloudinary.config.apiSecret = apiSecret;
        cloudinary.config.apiKey = apiKey;
    }

    public Map upload(Object file, Map options) {
        try {
            return cloudinary.uploader().upload(file, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createUrl(String name, int width, int height, String action) {
        return cloudinary.url()
                .transformation(new Transformation()
                        .width(width)
                        .height(height)
                        .border("2px_solid_black").crop(action))
                .imageTag(name);
    }
}