package com.offcn.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offcn.po.User;
import com.offcn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    //远程服务调用客户端
    @Autowired
    RestTemplate restTemplate;
    //Eureka客户端
//    @Autowired
//    DiscoveryClient discoveryClient;
    //支持负载均衡的调用客户端
//    @Autowired
//    LoadBalancerClient loadBalancerClient;
    //开启Ribbon后，RestTemplate直接使用服务名就可以发起调用
    String url = "http://USERPROVIDER";

    /***
     * 通过客户端负载均衡器获取生产者服务器基础地址
     *
     * @return
     */
//    public String getServerUrl() {
//        //通过客户端调用器查找指定服务
//        // List<ServiceInstance> instList = discoveryClient.getInstances("USERPROVIDER");
//        //获取第一个服务器
//        // ServiceInstance inst = instList.get(0);
//        //通过客户端调用服务均衡器查找服务
//        ServiceInstance inst = loadBalancerClient.choose("USERPROVIDER");
//        //获取服务提供者服务器ip、端口号
//        String ip = inst.getHost();
//        int port = inst.getPort();
//        //拼接调用地址
//        String url = "http://" + ip + ":" + port + "/user";
//        return url;
//    }

    /*
    @HystrixCommand(fallbackMethod="getUserMapFallbackMethod")：
    声明一个失败回滚处理函数getUserMapFallbackMethod，当getUserMap执行超时（默认是1000毫秒），就会执行getUserMapFallbackMethod函数，返回错误提示。
    为了方便查看熔断的触发时机，我们记录请求访问时间。
    */
    @Override
    @HystrixCommand(fallbackMethod = "getUserMapFallbackMethod")
    public Map getUserMap() {
        long beginTime = System.currentTimeMillis();
//        Map map = restTemplate.getForObject(getServerUrl() + "/getall", Map.class);
        Map map = restTemplate.getForObject(url + "/user/getall", Map.class);
        long endTime = System.currentTimeMillis();
        System.out.println("程序执行时间:" + (endTime - beginTime));
        return map;
    }

    /**
     * 获取全部用户数据，发生熔断后调用方法
     *
     * @return
     */
    public Map<String, Object> getUserMapFallbackMethod() {
        Map map = new HashMap();
        map.put("list", new ArrayList<>());
        map.put("ProviderVersion", "获取远程调用失败");
        return map;
    }

    @Override
    public void createUser(User user) {
//        restTemplate.postForObject(getServerUrl() + "/save", user, String.class);
        restTemplate.postForObject(url + "/user/save", user, String.class);
    }

    @Override
    public User getUser(Long id) {
//        return restTemplate.getForObject(getServerUrl() + "/get/" + id, User.class);
        return restTemplate.getForObject(url + "/user/get/" + id, User.class);
    }

    @Override
    public void updateUser(Long id, User user) {
//        restTemplate.put(getServerUrl() + "/update/" + id, user);
        restTemplate.put(url + "/user/update/" + id, user);
    }

    @Override
    public void deleteUser(Long id) {
//        restTemplate.delete(getServerUrl() + "/delete/" + id);
        restTemplate.delete(url + "/user/delete/" + id);
    }
}