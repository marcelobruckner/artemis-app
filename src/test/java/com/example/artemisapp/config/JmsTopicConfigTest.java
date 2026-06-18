package com.example.artemisapp.config;

import jakarta.jms.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class JmsTopicConfigTest {

    private final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private final JmsTopicConfig jmsTopicConfig = new JmsTopicConfig();

    @Test
    void deveCriarTemplatePadraoEmModoQueue() {
        JmsTemplate jmsTemplate = jmsTopicConfig.jmsTemplate(connectionFactory);

        assertThat(jmsTemplate.isPubSubDomain()).isFalse();
    }

    @Test
    void deveCriarTemplateDeTopicEmModoPublishSubscribe() {
        JmsTemplate topicJmsTemplate = jmsTopicConfig.topicJmsTemplate(connectionFactory);

        assertThat(topicJmsTemplate.isPubSubDomain()).isTrue();
    }

    @Test
    void deveCriarListenerFactoryParaTopic() {
        DefaultJmsListenerContainerFactory factory = jmsTopicConfig.topicJmsListenerContainerFactory(connectionFactory);

        assertThat(factory).isNotNull();
    }
}
