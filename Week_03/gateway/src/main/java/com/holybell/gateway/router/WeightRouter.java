package com.holybell.gateway.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeightRouter implements HttpEndpointRouter {

    private Logger logger = LoggerFactory.getLogger(WeightRouter.class);

    private List<Double> weights;

    public WeightRouter(List<Double> weights) {
        double sumWeight = 0D;
        for (double weight : weights) {
            sumWeight += weight;
        }
        Assert.state(sumWeight > 0D, "权重之和必须大于0!");
        this.weights = weights;
    }

    @Override
    public String route(List<String> endpoints) {
        Assert.state(endpoints != null && endpoints.size() > 0, "后端服务列表为空!");
        // 拼装权重和后端服务地址
        List<BackendService> backendServices = new ArrayList<>(endpoints.size());
        for (int i = 0; i < endpoints.size(); i++) {
            BackendService bs = BackendService.of();
            if (i < weights.size()) {
                bs.setUrl(endpoints.get(i)).setWeight(weights.get(i));
            } else {
                bs.setUrl(endpoints.get(i));
            }
            backendServices.add(bs);
        }
        return getUrlByWeight(backendServices);
    }

    /**
     * 根据权重获取url
     *
     * @param backendServices 后端服务列表
     */
    public String getUrlByWeight(List<BackendService> backendServices) {

        int random = -1;

        try {
            // 计算总权重
            double sumWeight = 0;
            for (BackendService bs : backendServices) {
                sumWeight += bs.getWeight();
            }

            // 产生随机数
            double randomNumber;
            randomNumber = Math.random();

            // 根据随机数在分布的区域并确定后端服务
            double d1 = 0;
            double d2 = 0;

            for (int i = 0; i < backendServices.size(); i++) {
                d2 += Double.parseDouble(String.valueOf(backendServices.get(i).getWeight())) / sumWeight;
                if (i == 0) {
                    d1 = 0;
                } else {
                    d1 += Double.parseDouble(String.valueOf(backendServices.get(i - 1).getWeight())) / sumWeight;
                }
                if (randomNumber >= d1 && randomNumber <= d2) {
                    random = i;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("生成抽奖随机数出错", e);
        }
        return backendServices.get(random).getUrl();
    }

    // 对应一个后端服务和权重POJO
    static class BackendService {
        private String url;
        private Double weight = 0D;     // 默认权重为0

        public String getUrl() {
            return url;
        }

        public BackendService setUrl(String url) {
            this.url = url;
            return this;
        }

        public Double getWeight() {
            return weight;
        }

        public BackendService setWeight(Double weight) {
            this.weight = weight;
            return this;
        }

        public static BackendService of() {
            return new BackendService();
        }
    }

//    public static void main(String[] args) {
//        List<String> urls = Arrays.asList("http://localhost:8801", "http://localhost:8802", "http://localhost:8803");
//        WeightRouter weightRouter = new WeightRouter(Arrays.asList(10D, 10D));
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(weightRouter.route(urls));
//        }
//    }
}
