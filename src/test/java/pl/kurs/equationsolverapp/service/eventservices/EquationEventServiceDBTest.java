package pl.kurs.equationsolverapp.service.eventservices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.equationsolverapp.config.JpaConfig;
import pl.kurs.equationsolverapp.dao.EquationEventDao;
import pl.kurs.equationsolverapp.model.EquationEvent;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = { JpaConfig.class, EquationEventDao.class, EquationEventServiceImpl.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class EquationEventServiceDBTest {

    @Resource
    private EquationEventServiceImpl equationEventService;

    @Test
    public void givenEquationEvent_whenSave_thenGetOk() {

        String expression = "2 + 2 * 2";
        Timestamp date = Timestamp.from(Instant.now());

        EquationEvent eventToSave = new EquationEvent(date, expression);
        equationEventService.saveEvent(eventToSave);

        EquationEvent savedEvent = equationEventService.get(1L);
        assertEquals("2 + 2 * 2", savedEvent.getExpression());

    }

}