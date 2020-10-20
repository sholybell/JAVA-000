import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object obj = new MyClassLoader().findClass("Hello").newInstance();
        Method helloMethod = obj.getClass().getMethod("hello");
        helloMethod.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            InputStream in = new FileInputStream(new File("C:\\Users\\Sholybell\\Desktop\\java进阶作业\\Hello.xlass"));
            byte[] bytes = new byte[399];
            IOUtils.read(in, bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
