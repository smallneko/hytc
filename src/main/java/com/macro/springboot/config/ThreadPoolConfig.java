package com.macro.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
	/*
	* 默认线程数线程池
	* @ return Executor
	* @ Created by macro
	* */
	@Bean
	public Executor getExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);//线程池维护线程的最少数量
		executor.setMaxPoolSize(30);//线程池维护线程的最大数量
		executor.setQueueCapacity(8); //缓存队列
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //对拒绝task的处理策略
		executor.setKeepAliveSeconds(60);//允许的空闲时间
		executor.initialize();
		return executor;
	}

}
