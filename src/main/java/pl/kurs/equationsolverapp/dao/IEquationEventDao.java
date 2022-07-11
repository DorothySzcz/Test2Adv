package pl.kurs.equationsolverapp.dao;

import pl.kurs.equationsolverapp.model.EquationEvent;

public interface IEquationEventDao {

    void save(EquationEvent event);
    EquationEvent get(long id);
}
