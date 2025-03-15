package com.scm.scm20.Validators;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import javax.imageio.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile>{

    private static final long MAX_FILE_SIZE = 2*1024*1024; //2mb
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if(file==null || file.isEmpty()){
            //context.disableDefaultConstraintViolation();
           // context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();

            return true;
        }
        if(file.getSize()>MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2mb").addConstraintViolation();

            return false;
        }

        // resolution
        // try {
        //     BufferedImage bufferedImage= ImageIO.read(file.getInputStream());
        //     if(bufferedImage.getHeight()>1080 || bufferedImage.getWidth()>1920){

        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     // TODO: handle exception
        // }
        return true;
    }

}
