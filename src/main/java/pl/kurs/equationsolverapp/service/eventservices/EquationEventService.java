package pl.kurs.equationsolverapp.service.eventservices;

import pl.kurs.equationsolverapp.model.EquationEvent;

public interface EquationEventService {

    void saveEvent(EquationEvent event);
    EquationEvent get(long id);
}
