package com.gaoge.code;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author gaoge
 * @date 2018/10/27 16:38
 * description:
 *    理解动态代理技术
 */
public interface Animal extends Serializable {
    void say();
    String food();
    void run();
    int speed();
}

class Cat implements Animal {

    @Override
    public void say() {
        System.out.println("MiaoMiao");
    }

    @Override
    public String food() {
        return "Fish";
    }

    @Override
    public void run() {
        System.out.println("Fast fly");
    }

    @Override
    public int speed() {
        return 7;
    }
}

class Dog implements Animal {

    @Override
    public void say() {
        System.out.println("Wang,wang,wang");
    }

    @Override
    public String food() {
        return "Bone, shit";
    }

    @Override
    public void run() {
        System.out.println("Fast happy");
    }

    @Override
    public int speed() {
        return 3;
    }
}

class LittleDog extends Dog {
    @Override
    public void say() {
        System.out.println("wa,wa,heng");
    }
}

interface Run extends Serializable{
    void run();
}

class ComputerRun implements Run {

    @Override
    public void run() {
        System.out.println("Hello world");
    }
}

 class AnimalInvocationHandler<T, E extends Run> implements InvocationHandler, Serializable {

    private T object;
    private E run;
    public AnimalInvocationHandler(T obj, E run) {
        this.object = obj;
        this.run = run;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        run.run();
        return method.invoke(object, args);
    }
}

class TestProxy {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Animal proxy = (Animal) Proxy.newProxyInstance(Animal.class.getClassLoader(), new Class[]{Animal.class}, new AnimalInvocationHandler(new Dog(), new ComputerRun()));
        Class<?> [] infs = Animal.class.getInterfaces();
        infs.clone();
        OutputStream outputStream = new FileOutputStream("${HOME}/test.class");

        byte[] proxyClassFile =  ProxyGenerator.generateProxyClass(proxy.getClass().getName(), proxy.getClass().getInterfaces());
        outputStream.write(proxyClassFile);

        proxy.say();
        proxy.run();
        Animal dog = new Dog();
        dog.say();
        dog.run();

        Dog dogLittle = new LittleDog();
        dogLittle.say();
        dogLittle.run();

        Class<?> proxyClazz;
        URL url = new URL("file://${HOME}/test.class");
        URLClassLoader loader = new URLClassLoader(new URL[]{url});
        Class<?> clazz = loader.loadClass("com.sun.proxy.$Proxy0");
        Animal obj = (Animal) clazz.newInstance();
        Method method = clazz.getMethod("run", void.class);
        method.invoke(dog, new AnimalInvocationHandler(new Dog(), new ComputerRun()));

    }

}
