# Kafka 实战应用

## 📖 目录

- [1. Spring Boot 集成 Kafka](#1-spring-boot-集成-kafka)
- [2. 实际业务场景应用](#2-实际业务场景应用)
- [3. 消息序列化与反序列化](#3-消息序列化与反序列化)
- [4. 错误处理与重试机制](#4-错误处理与重试机制)
- [5. 监控与运维](#5-监控与运维)
- [6. 最佳实践](#6-最佳实践)
- [7. 常见面试题](#7-常见面试题)

---

## 1. Spring Boot 集成 Kafka

### 1.1 添加依赖

#### 1.1.1 Maven 依赖

```xml
<dependencies>
    <!-- Spring Boot Kafka Starter -->
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    
    <!-- Spring Boot Web（如果需要 REST API） -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- JSON 序列化（如果需要） -->
    <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
    </dependency>
</dependencies>
```

#### 1.1.2 Gradle 依赖

```gradle
dependencies {
    implementation 'org.springframework.kafka:spring-kafka'
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

### 1.2 配置文件

#### 1.2.1 application.yml

```yaml
spring:
  kafka:
    # Broker 地址
    bootstrap-servers: localhost:9092
    
    # Producer 配置
    producer:
      # Key 序列化器
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      # Value 序列化器
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      # 消息确认机制
      acks: all
      # 重试次数
      retries: 3
      # 批次大小（字节）
      batch-size: 32768
      # 等待时间（毫秒）
      linger-ms: 10
      # 缓冲区大小（字节）
      buffer-memory: 67108864
      # 压缩类型
      compression-type: snappy
      # 幂等性
      enable-idempotence: true
    
    # Consumer 配置
    consumer:
      # Consumer Group ID
      group-id: my-consumer-group
      # Key 反序列化器
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      # Value 反序列化器
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      # 偏移量重置策略
      auto-offset-reset: earliest
      # 是否自动提交 Offset
      enable-auto-commit: false
      # 每次拉取的最大记录数
      max-poll-records: 500
      # 最大拉取间隔（毫秒）
      max-poll-interval-ms: 300000
    
    # Listener 配置
    listener:
      # 确认模式（manual - 手动提交，batch - 批量提交）
      ack-mode: manual_immediate
      # 并发消费者数量
      concurrency: 3
```

#### 1.2.2 application.properties

```properties
# Kafka Broker 地址
spring.kafka.bootstrap-servers=localhost:9092

# Producer 配置
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.acks=all
spring.kafka.producer.retries=3
spring.kafka.producer.batch-size=32768
spring.kafka.producer.linger-ms=10
spring.kafka.producer.buffer-memory=67108864
spring.kafka.producer.compression-type=snappy
spring.kafka.producer.enable-idempotence=true

# Consumer 配置
spring.kafka.consumer.group-id=my-consumer-group
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=false
spring.kafka.consumer.max-poll-records=500
spring.kafka.consumer.max-poll-interval-ms=300000

# Listener 配置
spring.kafka.listener.ack-mode=manual_immediate
spring.kafka.listener.concurrency=3
```

### 1.3 Producer 使用

#### 1.3.1 注入 KafkaTemplate

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Kafka Producer 服务
 * Kafka Producer Service
 */
@Service
public class KafkaProducerService {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    /**
     * 发送消息（异步）
     * Send message asynchronously
     */
    public void sendMessage(String topic, String key, String message) {
        ListenableFuture<SendResult<String, String>> future = 
            kafkaTemplate.send(topic, key, message);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("消息发送成功: " + 
                    "topic=" + result.getRecordMetadata().topic() + 
                    ", partition=" + result.getRecordMetadata().partition() + 
                    ", offset=" + result.getRecordMetadata().offset());
            }
            
            @Override
            public void onFailure(Throwable ex) {
                System.err.println("消息发送失败: " + ex.getMessage());
            }
        });
    }
    
    /**
     * 发送消息（同步）
     * Send message synchronously
     */
    public void sendMessageSync(String topic, String key, String message) {
        try {
            SendResult<String, String> result = 
                kafkaTemplate.send(topic, key, message).get();
            System.out.println("消息发送成功: " + result.getRecordMetadata().offset());
        } catch (Exception e) {
            System.err.println("消息发送失败: " + e.getMessage());
        }
    }
}
```

#### 1.3.2 REST API 示例

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka 消息发送控制器
 * Kafka Message Producer Controller
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    
    @Autowired
    private KafkaProducerService kafkaProducerService;
    
    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageRequest request) {
        kafkaProducerService.sendMessage(
            request.getTopic(),
            request.getKey(),
            request.getMessage()
        );
        return "消息已发送";
    }
    
    // 消息请求 DTO
    public static class MessageRequest {
        private String topic;
        private String key;
        private String message;
        
        // Getters and Setters
        public String getTopic() { return topic; }
        public void setTopic(String topic) { this.topic = topic; }
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
```

### 1.4 Consumer 使用

#### 1.4.1 @KafkaListener 注解

```java
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Kafka Consumer 服务
 * Kafka Consumer Service
 */
@Component
public class KafkaConsumerService {
    
    /**
     * 消费消息（自动提交 Offset）
     * Consume message with auto commit
     */
    @KafkaListener(topics = "orders", groupId = "order-consumer-group")
    public void consumeOrderMessage(@Payload String message,
                                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                    @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                    @Header(KafkaHeaders.OFFSET) long offset) {
        System.out.printf("收到消息: topic=%s, partition=%d, offset=%d, message=%s%n",
            topic, partition, offset, message);
        
        // 业务处理
        processOrderMessage(message);
    }
    
    /**
     * 消费消息（手动提交 Offset）
     * Consume message with manual commit
     */
    @KafkaListener(topics = "payments", groupId = "payment-consumer-group")
    public void consumePaymentMessage(ConsumerRecord<String, String> record,
                                      Acknowledgment acknowledgment) {
        try {
            System.out.printf("收到消息: topic=%s, partition=%d, offset=%d, key=%s, value=%s%n",
                record.topic(), record.partition(), record.offset(),
                record.key(), record.value());
            
            // 业务处理
            processPaymentMessage(record.value());
            
            // 手动提交 Offset
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("处理消息失败: " + e.getMessage());
            // 不提交 Offset，消息会重新消费
        }
    }
    
    /**
     * 批量消费消息
     * Batch consume messages
     */
    @KafkaListener(topics = "logs", groupId = "log-consumer-group",
                   containerFactory = "kafkaListenerContainerFactory")
    public void consumeLogMessages(@Payload List<String> messages,
                                  Acknowledgment acknowledgment) {
        try {
            for (String message : messages) {
                System.out.println("收到日志消息: " + message);
                processLogMessage(message);
            }
            
            // 批量提交 Offset
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("处理消息失败: " + e.getMessage());
        }
    }
    
    // 业务处理方法
    private void processOrderMessage(String message) {
        // 处理订单消息
        // Process order message
    }
    
    private void processPaymentMessage(String message) {
        // 处理支付消息
        // Process payment message
    }
    
    private void processLogMessage(String message) {
        // 处理日志消息
        // Process log message
    }
}
```

#### 1.4.2 批量消费配置

```java
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer 配置
 * Kafka Consumer Configuration
 */
@Configuration
public class KafkaConsumerConfig {
    
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500); // 批量拉取
        return new DefaultKafkaConsumerFactory<>(props);
    }
    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        
        // 设置批量消费
        factory.setBatchListener(true);
        
        // 设置手动提交
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        
        // 设置并发消费者数量
        factory.setConcurrency(3);
        
        return factory;
    }
}
```

---

## 2. 实际业务场景应用

### 2.1 订单系统

#### 2.1.1 场景描述

订单创建后，需要：
1. 发送订单创建事件
2. 库存服务消费事件，扣减库存
3. 支付服务消费事件，创建支付订单
4. 物流服务消费事件，创建物流单

#### 2.1.2 实现示例

**订单服务（Producer）**：

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * 订单服务
 * Order Service
 */
@Service
public class OrderService {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     * 创建订单
     * Create order
     */
    public Order createOrder(OrderRequest request) {
        // 1. 创建订单
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setAmount(request.getAmount());
        order.setStatus("CREATED");
        order = orderRepository.save(order);
        
        // 2. 发送订单创建事件
        OrderEvent event = new OrderEvent();
        event.setOrderId(order.getId());
        event.setUserId(order.getUserId());
        event.setProductId(order.getProductId());
        event.setQuantity(order.getQuantity());
        event.setAmount(order.getAmount());
        event.setEventType("ORDER_CREATED");
        event.setTimestamp(System.currentTimeMillis());
        
        kafkaTemplate.send("order-events", order.getId().toString(), 
                          JSON.toJSONString(event));
        
        return order;
    }
}
```

**库存服务（Consumer）**：

```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * 库存服务
 * Inventory Service
 */
@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepository;
    
    @KafkaListener(topics = "order-events", groupId = "inventory-service")
    public void handleOrderEvent(ConsumerRecord<String, String> record,
                                 Acknowledgment acknowledgment) {
        try {
            OrderEvent event = JSON.parseObject(record.value(), OrderEvent.class);
            
            if ("ORDER_CREATED".equals(event.getEventType())) {
                // 扣减库存
                Inventory inventory = inventoryRepository.findByProductId(event.getProductId());
                if (inventory.getStock() >= event.getQuantity()) {
                    inventory.setStock(inventory.getStock() - event.getQuantity());
                    inventoryRepository.save(inventory);
                    
                    System.out.println("库存扣减成功: productId=" + event.getProductId() + 
                                     ", quantity=" + event.getQuantity());
                } else {
                    throw new RuntimeException("库存不足");
                }
            }
            
            // 提交 Offset
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("处理订单事件失败: " + e.getMessage());
            // 不提交 Offset，消息会重新消费
        }
    }
}
```

### 2.2 日志收集系统

#### 2.2.1 场景描述

应用日志发送到 Kafka，然后由日志处理服务消费，存储到 Elasticsearch。

#### 2.2.2 实现示例

**日志发送（Producer）**：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 日志发送器
 * Log Sender
 */
@Component
public class LogSender {
    
    private static final Logger logger = LoggerFactory.getLogger(LogSender.class);
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    /**
     * 发送日志
     * Send log
     */
    public void sendLog(String level, String message, String serviceName) {
        LogEvent logEvent = new LogEvent();
        logEvent.setLevel(level);
        logEvent.setMessage(message);
        logEvent.setServiceName(serviceName);
        logEvent.setTimestamp(System.currentTimeMillis());
        logEvent.setThreadName(Thread.currentThread().getName());
        
        kafkaTemplate.send("application-logs", serviceName, JSON.toJSONString(logEvent));
    }
}
```

**日志处理（Consumer）**：

```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * 日志处理服务
 * Log Processing Service
 */
@Service
public class LogProcessingService {
    
    @Autowired
    private ElasticsearchService elasticsearchService;
    
    @KafkaListener(topics = "application-logs", groupId = "log-processing-service",
                   containerFactory = "kafkaListenerContainerFactory")
    public void processLogs(@Payload List<String> messages,
                            Acknowledgment acknowledgment) {
        try {
            List<LogEvent> logEvents = new ArrayList<>();
            for (String message : messages) {
                LogEvent logEvent = JSON.parseObject(message, LogEvent.class);
                logEvents.add(logEvent);
            }
            
            // 批量存储到 Elasticsearch
            elasticsearchService.bulkIndex(logEvents);
            
            // 提交 Offset
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("处理日志失败: " + e.getMessage());
        }
    }
}
```

---

## 3. 消息序列化与反序列化

### 3.1 JSON 序列化

#### 3.1.1 配置 JSON 序列化器

```java
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka JSON 序列化配置
 * Kafka JSON Serialization Configuration
 */
@Configuration
public class KafkaJsonConfig {
    
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*"); // 信任所有包
        return new DefaultKafkaConsumerFactory<>(props);
    }
}
```

#### 3.2.2 使用 JSON 序列化

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * JSON 消息发送示例
 * JSON Message Send Example
 */
@Service
public class JsonMessageService {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    /**
     * 发送 JSON 消息
     * Send JSON message
     */
    public void sendJsonMessage(OrderEvent event) {
        kafkaTemplate.send("order-events", event.getOrderId().toString(), event);
    }
}
```

```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * JSON 消息消费示例
 * JSON Message Consume Example
 */
@Component
public class JsonMessageConsumer {
    
    @KafkaListener(topics = "order-events", groupId = "order-consumer-group")
    public void consumeJsonMessage(OrderEvent event) {
        System.out.println("收到订单事件: " + event);
        // 处理订单事件
    }
}
```

### 3.2 Avro 序列化（高级）

Avro 是一种二进制序列化格式，性能优于 JSON，适合大数据场景。

**配置示例**：

```java
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;

@Bean
public ProducerFactory<String, Object> avroProducerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    configProps.put("schema.registry.url", "http://localhost:8081");
    return new DefaultKafkaProducerFactory<>(configProps);
}
```

---

## 4. 错误处理与重试机制

### 4.1 错误处理

#### 4.1.1 死信队列（DLQ）

**配置死信队列**：

```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * 错误处理示例
 * Error Handling Example
 */
@Component
public class ErrorHandlingConsumer {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @KafkaListener(topics = "orders", groupId = "order-consumer-group")
    public void consumeOrder(ConsumerRecord<String, String> record,
                            Acknowledgment acknowledgment) {
        try {
            // 处理消息
            processOrder(record.value());
            acknowledgment.acknowledge();
        } catch (Exception e) {
            System.err.println("处理消息失败: " + e.getMessage());
            
            // 发送到死信队列
            kafkaTemplate.send("orders-dlq", record.key(), record.value());
            
            // 提交 Offset，避免重复消费
            acknowledgment.acknowledge();
        }
    }
    
    private void processOrder(String orderJson) {
        // 业务处理
        // Business processing
    }
}
```

#### 4.1.2 重试机制

**配置重试**：

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaErrorHandlerConfig {
    
    @Bean
    public DefaultErrorHandler errorHandler() {
        // 重试策略：每 1 秒重试一次，最多重试 3 次
        BackOff backOff = new FixedBackOff(1000L, 3L);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(backOff);
        
        // 重试失败后，发送到死信队列
        errorHandler.setCommitRecovered(true);
        
        return errorHandler;
    }
}
```

**使用重试机制**：

```java
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

@Component
public class RetryConsumer implements ConsumerSeekAware {
    
    @KafkaListener(topics = "orders", groupId = "order-consumer-group",
                   errorHandler = "errorHandler")
    public void consumeOrder(String message) {
        // 处理消息，如果失败会自动重试
        processOrder(message);
    }
    
    private void processOrder(String message) {
        // 业务处理
    }
}
```

---

## 5. 监控与运维

### 5.1 监控指标

#### 5.1.1 Producer 监控指标

- **消息发送速率** - messages-per-second
- **消息发送延迟** - record-send-total, record-send-rate
- **发送失败率** - record-error-rate
- **批次大小** - batch-size-avg

#### 5.1.2 Consumer 监控指标

- **消息消费速率** - records-consumed-rate
- **消费延迟** - records-lag-max
- **Offset 提交延迟** - commit-latency-avg
- **重平衡次数** - rebalance-rate

### 5.2 常用运维命令

```bash
# 查看 Topic 列表
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

# 查看 Topic 详情
bin/kafka-topics.sh --describe --topic orders --bootstrap-server localhost:9092

# 查看 Consumer Group 列表
bin/kafka-consumer-groups.sh --list --bootstrap-server localhost:9092

# 查看 Consumer Group 详情
bin/kafka-consumer-groups.sh --describe \
  --group my-consumer-group \
  --bootstrap-server localhost:9092

# 查看 Consumer Group 的 Offset
bin/kafka-consumer-groups.sh --describe \
  --group my-consumer-group \
  --bootstrap-server localhost:9092 \
  --verbose

# 重置 Consumer Group Offset
bin/kafka-consumer-groups.sh --reset-offsets \
  --group my-consumer-group \
  --topic orders \
  --to-earliest \
  --execute \
  --bootstrap-server localhost:9092
```

---

## 6. 最佳实践

### 6.1 Producer 最佳实践

1. **使用批量发送** - 设置 `batch.size` 和 `linger.ms`
2. **启用压缩** - 设置 `compression.type=snappy` 或 `lz4`
3. **启用幂等性** - 设置 `enable.idempotence=true`
4. **合理设置 acks** - 根据业务需求选择 `acks=all` 或 `acks=1`
5. **异步发送** - 使用异步发送提高吞吐量
6. **错误处理** - 实现 Callback 处理发送失败

### 6.2 Consumer 最佳实践

1. **手动提交 Offset** - 设置 `enable.auto.commit=false`
2. **批量消费** - 设置 `max.poll.records` 和批量监听器
3. **快速处理** - 避免长时间处理导致超时
4. **幂等性处理** - 保证消息处理的幂等性
5. **错误处理** - 实现死信队列和重试机制
6. **监控消费延迟** - 监控 `records-lag-max` 指标

### 6.3 Topic 设计最佳实践

1. **合理设置 Partition 数量** - 根据吞吐量和 Consumer 数量
2. **设置副本因子** - `replication.factor>=3`
3. **设置保留时间** - 根据业务需求设置 `retention.ms`
4. **命名规范** - 使用有意义的 Topic 名称
5. **避免过多 Topic** - 过多的 Topic 会增加管理复杂度

---

## 7. 常见面试题

### Q1: Spring Boot 如何集成 Kafka？

**答案要点**：

1. **添加依赖** - `spring-kafka`
2. **配置文件** - 配置 Producer 和 Consumer
3. **使用 KafkaTemplate** - 发送消息
4. **使用 @KafkaListener** - 消费消息
5. **配置序列化器** - JSON、Avro 等

### Q2: 如何保证消息不丢失？

**答案要点**：

1. **Producer 端** - `acks=all`, `retries`, `enable.idempotence=true`
2. **Broker 端** - `replication.factor>=3`, `min.insync.replicas>=2`
3. **Consumer 端** - `enable.auto.commit=false`, 手动提交 Offset

### Q3: 如何处理消息重复消费？

**答案要点**：

1. **幂等性处理** - 保证业务逻辑的幂等性
2. **去重机制** - 使用 Redis 或数据库去重
3. **唯一标识** - 使用消息的唯一 ID 去重

### Q4: 如何提高 Kafka 的吞吐量？

**答案要点**：

1. **批量发送** - 设置 `batch.size` 和 `linger.ms`
2. **压缩** - 设置 `compression.type`
3. **增加 Partition** - 提高并行度
4. **增加 Consumer** - 提高消费速度
5. **异步发送** - 使用异步发送提高吞吐量

---

## 📚 扩展阅读

- [Spring Kafka 官方文档](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Kafka 最佳实践](https://kafka.apache.org/documentation/#bestpractices)
- [Confluent Platform](https://www.confluent.io/)

---

💡 **学习提示**：Kafka 实战应用需要结合实际业务场景，建议从简单的场景开始，逐步深入。

🔄 持续更新中... | 最后更新：2025年1月

