package pl.kurs.equationsolverapp.service.eventservices;

import org.springframework.stereotype.Service;
import pl.kurs.equationsolverapp.dao.IEquationEventDao;
import pl.kurs.equationsolverapp.model.EquationEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
public class EquationEventServiceImpl implements EquationEventService {

    private IEquationEventDao equationEventDao;

    public EquationEventServiceImpl(IEquationEventDao equationEventDao) {
        this.equationEventDao = equationEventDao;
    }

    @Override
    public void saveEvent(EquationEvent event) {

        SimpleRunnable myRunnableObject = new SimpleRunnable();
        FutureTask<String> futureTask = new FutureTask<>(myRunnableObject, "FutureTask is complete");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(futureTask);

        while (true) {
            try {
                if (futureTask.isDone()) {
                    equationEventDao.save(event);
                    executor.shutdown();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

    @Override
    public EquationEvent get(long id) {
        return equationEventDao.get(id);
    }


}
