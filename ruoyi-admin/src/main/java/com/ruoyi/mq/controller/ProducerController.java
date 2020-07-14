package com.ruoyi.mq.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mq.producer.Producer;
import com.ruoyi.project.biz.subscribe.domain.Subscribe;
import com.ruoyi.project.biz.subscribe.service.SubscribeService;
import com.ruoyi.project.biz.train.TrainClient;
import com.ruoyi.project.biz.weather.WeatherClient;
import com.ruoyi.webservice.gen.TrainDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/mq")
public class ProducerController {
    @Autowired
    private Producer producer;
    @Autowired
    private SubscribeService subscribeService;

    @Log(title = "气象数据", businessType = BusinessType.IMPORT)
    @RequestMapping("/weather/{city}")
    public String test01(@PathVariable String city) {
        List<String> list = WeatherClient.getWeatherbyCityName(city);

        Subscribe subscribe = new Subscribe();
        subscribe.setDataCode("weather");
        List<Subscribe> ls = subscribeService.selectSubscribeList(subscribe);
        for (Subscribe item : ls) {
            producer.sendToQueue("weather_" +item.getFeedCode(), String.join("\n", list));
        }
        return "消息已发送队列";
    }

    @Log(title = "车次数据", businessType = BusinessType.IMPORT)
    @RequestMapping("/train/{code}")
    public String test02(@PathVariable String code) {
        List<TrainDetailInfo> list = TrainClient.getDetailInfoByTrainCode(code);
        String json = JSON.toJSONString(list);

        Subscribe subscribe = new Subscribe();
        subscribe.setDataCode("train");
        List<Subscribe> ls = subscribeService.selectSubscribeList(subscribe);
        for (Subscribe item : ls) {
            producer.sendToQueue("train_" +item.getFeedCode(), json);
        }
        return "消息已发送队列";
    }

//    @RequestMapping("/notify/{msg}")
//    public String test03(@PathVariable String msg) {
//        producer.sendToTopic("notify", msg);
//        return "消息已发送共享队列";
//    }

}
