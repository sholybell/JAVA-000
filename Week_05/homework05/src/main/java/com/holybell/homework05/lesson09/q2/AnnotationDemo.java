package com.holybell.homework05.lesson09.q2;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class AnnotationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 指定扫描路径
        applicationContext.scan("com.holybell.homework05.lesson09.q2");
        applicationContext.refresh();

        System.out.println("-------------------------------------------------------------");

        //1. 使用@Component注解，装配bean，@Configuration、@Controller、@Service其实派生自@Component注解
        CarComponent lexusCar = applicationContext.getBean("lexusCar", CarComponent.class);
        System.out.println(lexusCar);

        System.out.println("-------------------------------------------------------------");

        //2.使用@Configuration+@Bean组合
        Car benzCar = applicationContext.getBean("benzCar", Car.class);
        System.out.println(benzCar);

        System.out.println("-------------------------------------------------------------");

        //3. 直接从外部创建一个对象，注册到容器，这么注册并不会经历bean的生命周期
        Car hyundaiCar = new Car();
        hyundaiCar.setBrand("现代");
        hyundaiCar.setPlateNo("闽D123456");
        applicationContext.getBeanFactory().registerSingleton("hyundaiCar", hyundaiCar);
        Car _hyundaiCar = applicationContext.getBean("hyundaiCar", Car.class);
        System.out.println(_hyundaiCar);
        System.out.println("取出来的现代汽车对象是否为旧对象:" + (hyundaiCar == _hyundaiCar));

        //4. 直接构造BeanDefinition，注册到容器，由容器创建
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Car.class)
                .addPropertyValue("brand", "五菱")
                .addPropertyValue("plateNo", "闽D123456").getBeanDefinition();
        applicationContext.registerBeanDefinition("wulingCar", beanDefinition);
        Car wulingCar = applicationContext.getBean("wulingCar", Car.class);
        System.out.println(wulingCar);

        applicationContext.close();
    }
}
