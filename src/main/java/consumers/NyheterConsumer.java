package consumers;

import org.jboss.ejb3.annotation.ResourceAdapter;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceAdapter("lokal-mq")
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/nyheter.*"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")})
public class NyheterConsumer implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(NyheterConsumer.class);

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textmessage = (TextMessage) message;
            try {
                LOG.info("Breaking News: {}", textmessage.getText());
            } catch (JMSException e) {
                LOG.error("Textmeddelande kunde inte utläsas: {}", e);
            }
        }
    }
}
