package steganography;

import exeptions.IllegalUseOfKeyBasedMethod;
import steganography.interfaces.GenericSteganography;
import steganography.interfaces.KeyBasedSteganography;
import steganography.interfaces.KeyLessSteganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GenericMethod implements GenericSteganography {
    @Override
    public int[] generateKey(Object method, Object... args) {
        BufferedImage image = null;
        String message = null;
        int[] key;
        if (method instanceof KeyBasedSteganography) {
            for (Object obj : args) {
                if (obj instanceof BufferedImage) {
                    image = (BufferedImage) obj;
                } else if (obj instanceof String) {
                    message = (String) obj;
                } else {
                    if(obj == null) {
                        System.out.println("WARNING: Found VarArgDemo of value - null");
                    }else{
                        System.out.println("WARNING: Found VarArgDemo of value - " + obj.toString() + " - might be unregnized ");
                    }
                }
            }
            if (message == null || image == null) {
                throw new IllegalArgumentException("Not enough arguments provided");
            }
            key = ((KeyBasedSteganography) method).generateKey(image, message);
        } else if (method instanceof KeyLessSteganography) {
            try {
                throw new IllegalUseOfKeyBasedMethod("Cannot generateKey for KeyLessSteganography method - " + method.getClass().toString());
            } catch (IllegalUseOfKeyBasedMethod e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Method " + method.getClass().toString() + " is not recognized");
        }
        return key;
    }

    @Override
    public void packMessage(Object method, Object... args) throws IOException {
        String message = null;
        int[] key = null;
        BufferedImage image = null;
        File outputFile = null;

        for (Object obj: args) {
            if(obj instanceof String){
                message = (String) obj;
            } else if(obj instanceof int[]){
                key = (int[]) obj;
            } else if(obj instanceof BufferedImage){
                image = (BufferedImage) obj;
            } else if(obj instanceof File){
                outputFile = (File) obj;
            } else{
                if(obj == null) {
                    System.out.println("WARNING: Found VarArgDemo of value - null");
                }else{
                    System.out.println("WARNING: Found VarArgDemo of value - " + obj.toString() + " - might be unrecognized ");
                }
            }
        }

        if(method instanceof KeyLessSteganography){
            ((KeyLessSteganography) method).packMessage(message, image, outputFile);
        } else if(method instanceof KeyBasedSteganography){
            ((KeyBasedSteganography) method).packMessage(message, key, image, outputFile);
        } else {
            throw new IllegalArgumentException("Method " + method.getClass().toString() + " is not recognized");
        }
    }

    @Override
    public String unpackMessage(Object method, Object... args) throws IOException {
        int[] key = null;
        BufferedImage image = null;
        String message = null;

        for (Object obj: args) {
            if(obj instanceof int[]){
                key = (int[]) obj;
            } else if(obj instanceof BufferedImage){
                image = (BufferedImage) obj;
            } else{
                if(obj == null) {
                    System.out.println("WARNING: Found VarArgDemo of value - null");
                }else{
                    System.out.println("WARNING: Found VarArgDemo of value - " + obj.toString() + " - might be unrecognized ");
                }
            }
        }

        if(method instanceof KeyLessSteganography){
            message = ((KeyLessSteganography) method).unpackMessage(image);
        } else if(method instanceof KeyBasedSteganography){
            message = ((KeyBasedSteganography) method).unpackMessage(key, image);
        } else {
            throw new IllegalArgumentException("Method " + method.getClass().toString() + " is not unrecognized");
        }
        return message;
    }
}
