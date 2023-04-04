package steganography;

import steganography.interfaces.SteganographyMethod;
import steganography.keybased.IQMethod;
import steganography.keybased.PRIMethod;
import steganography.keybased.PRPMethod;
import steganography.keyless.BHMethod;
import steganography.keyless.KJBMethod;
import steganography.keyless.LSBMethod;

import java.io.IOException;


public class GenericMethod<T extends SteganographyMethod>{

    private T method;

    public GenericMethod(){
    }

    public GenericMethod(T method) {
        this.method = method;
    }

    public <T extends SteganographyMethod> void generateKey(Object... args) throws IOException {
        this.method.generateKey(this.method, args);
    }

    //public <T extends SteganographyMethod> void packMessage(Object... args) throws IOException {
    //     this.method.packMessage(this.method, args);
    //}

    public <T extends SteganographyMethod, U> void unpackMessage(U... args) throws IOException {
        this.method.unpackMessage(this.method, (Object) args);
    }

    public T getMethod() {
        return method;
    }

    public void setMethod(T method) {
        this.method = method;
    }

    public GenericMethod(String methodName) {
        switch (methodName){
            case "LSB": this.method = (T) new LSBMethod();
            case "PRP": this.method = (T) new PRPMethod();
            case "PRI": this.method = (T) new PRIMethod();
            case "BH": this.method = (T) new BHMethod();
            case "IQ": this.method = (T) new IQMethod();
            case "KJB": this.method = (T) new KJBMethod();
        }
    }
}
