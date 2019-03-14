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
                name = "java:/jms/queue/nyheter",
                className = "javax.jms.Queue",
                interfaceName = "javax.jms.Queue",
                destinationName = "nyheter"),
        @JMSDestinationDefinition(
                name = "java:/jms/queue/nyheter.globala",
                className = "javax.jms.Queue",
                interfaceName = "javax.jms.Queue",
                destinationName = "nyheter.globala"),
        @JMSDestinationDefinition(
                name = "java:/jms/queue/nyheter.lokala",
                className = "javax.jms.Queue",
                interfaceName = "javax.jms.Queue",
                destinationName = "nyheter.lokala")
})
@Startup
@Stateless
public class NyheterProducer {

    private static final Logger LOG = LoggerFactory.getLogger(NyheterProducer.class);
    private static final String GLOBAL_NYHET = "En nyhet har precis kommit in: JMS används fortfarande på många arbetsplatser. Hundratals förvånade, tusentals besvikna!";
    private static final String LOKAL_NYHET = "En nyhet har kommit in: En flock renar blokerar trafiken till Lycksele djurpark. Polis på plats säger: \"Varför klagar ni? Ni får ju se djur!\"";
    @Inject
    @JMSConnectionFactory("java:/jms/lokal-mq")
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/nyheter")
    private Queue queue;

    @Resource(mappedName = "java:/jms/queue/nyheter.globala")
    private Queue queue2;

    @Resource(mappedName = "java:/jms/queue/nyheter.lokala")
    private Queue queue3;

    @Schedule(minute = "*/1", hour = "*")
    public void skickaNyttDokumentId() {

        context.createProducer().send(queue2, GLOBAL_NYHET);
        LOG.info("Skickat nyhet till globala nyheter: {}", GLOBAL_NYHET);

        context.createProducer().send(queue3, LOKAL_NYHET);
        LOG.info("Skickat nyhet till lokala nyheter: {}", LOKAL_NYHET);

    }
}
