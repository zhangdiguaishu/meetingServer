package com.jingwei;

import com.jingwei.TCP.NettyServerEcho;
import com.jingwei.TCP.NettyServerRecord;
import com.jingwei.TCP.NettyStarter;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

//配置重定向
//处理post请求

//@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.jingwei.dao")
public class JingweiMeetingApplication {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(Application.class);
	}*/

	public static void main(String[] args) throws InterruptedException {
		SpringApplication springApplication = new SpringApplication(JingweiMeetingApplication.class);
		ConfigurableApplicationContext ctx = springApplication.run(args);

		NettyServerRecord nettyServerRecord = ctx.getBean(NettyServerRecord.class);
		nettyServerRecord.start();
	}
}
