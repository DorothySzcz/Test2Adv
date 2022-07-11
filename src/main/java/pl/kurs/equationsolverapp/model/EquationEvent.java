package pl.kurs.equationsolverapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class EquationEvent implements Serializable {
    static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue
    @Column(name = "id_equation_event")
    private long id;
    private Timestamp date;
    private String expression;

    public EquationEvent() {
    }

    public EquationEvent(Timestamp date, String expression) {
        this.date = date;
        this.expression = expression;
    }

    public long getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getExpression() {
        return expression;
    }

}
