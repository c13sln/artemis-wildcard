package producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

@JMSDestinationDefinitions({
        @JMSDestinationDefinition(
                name = "java:/jms/topic/nyheter.*",
                className = "javax.jms.Topic",
                interfaceName = "javax.jms.Topic",
                destinationName = "nyheter"),
        @JMSDestinationDefinition(
                name = "java:/jms/topic/nyheter.globala",
                className = "javax.jms.Topic",
                interfaceName = "javax.jms.Topic",
                destinationName = "nyheter.globala"),
        @JMSDestinationDefinition(
                name = "java:/jms/topic/nyheter.lokala",
                className = "javax.jms.Topic",
                interfaceName = "javax.jms.Topic",
                destinationName = "nyheter.lokala")
})
@Startup
@Stateless
public class NyheterProducer {

    private static final Logger LOG = LoggerFactory.getLogger(NyheterProducer.class);
    private static final String GLOBAL_NYHET = "En nyhet har precis kommit in: JMS används fortfarande på många arbetsplatser. Hundratals förvånade, tusentals besvikna!";
    private static final String LOKAL_NYHET = "En nyhet har kommit in: En flock renar blokerar trafiken till Lycksele djurpark. Polis på plats säger: \"Varför klagar ni? Ni får ju se djur!\"";

    private int counter = 0;

    @Inject
    @JMSConnectionFactory("java:/jms/lokal-mq")
    private JMSContext context;

    @Resource(mappedName = "java:/jms/topic/nyheter.*")
    private Topic topic;

    @Resource(mappedName = "java:/jms/topic/nyheter.globala")
    private Topic topic2;

    @Resource(mappedName = "java:/jms/topic/nyheter.lokala")
    private Topic topic3;

    @Schedule(minute = "*/1", hour = "*")
    public void skickaNyttDokumentId() {

        if (counter < 1) {
            String text = "nyheter.*";
            context.createProducer().send(topic, text);
            LOG.info("Initierar kö: {}", text);
            counter++;
        }

        context.createProducer().send(topic2, GLOBAL_NYHET);
        LOG.info("Skickat nyhet till globala nyheter: {}", GLOBAL_NYHET);

        context.createProducer().send(topic3, LOKAL_NYHET);
        LOG.info("Skickat nyhet till lokala nyheter: {}", LOKAL_NYHET);

    }
}
