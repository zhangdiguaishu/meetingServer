package com.jingwei.TCP;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class NettyStarter implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        /*if(event.getApplicationContext().getParent() == null){
            System.out.println("NettyStarter is instantiation.");
            try {
                new Thread(new NettyServerEcho()).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }

}
