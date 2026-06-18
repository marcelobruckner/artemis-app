package com.example.artemisapp.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsTopicConfig {

    @Bean
    @Primary
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        // Queue usa Point-to-Point: uma mensagem deve ser processada por um unico consumidor.
        jmsTemplate.setPubSubDomain(false);
        return jmsTemplate;
    }

    @Bean
    public JmsTemplate topicJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        // Topic usa Publish/Subscribe: a mesma mensagem e entregue a varios assinantes.
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // Mantem os listeners abaixo em modo Topic, sem alterar os listeners existentes de Queue.
        factory.setPubSubDomain(true);
        return factory;
    }
}
