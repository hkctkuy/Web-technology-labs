package labs.webtech.CompositeId;

import labs.webtech.table.Audience;

import java.io.Serializable;
import java.util.Objects;

public class Audience_distId implements Serializable {

    private Audience audience;

    private Integer time;

    public Audience_distId() {}

    public Audience_distId(Audience _audience, Integer _time) {
        audience = _audience;
        time = _time;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        Audience_distId other = (Audience_distId) _other;
        return Objects.equals(audience, other.audience)
                && Objects.equals(time, other.time);
    }

    @Override
    public int hashCode() {
        return audience.getId().hashCode() * 10 + time;
    }
}
