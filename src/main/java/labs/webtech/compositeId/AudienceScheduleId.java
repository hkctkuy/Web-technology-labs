package labs.webtech.compositeId;

import labs.webtech.table.Audience;

import java.io.Serializable;
import java.util.Objects;

public class AudienceScheduleId implements Serializable {

    private Audience audience;

    private Integer time;

    public AudienceScheduleId() {}

    public AudienceScheduleId(Audience audience, Integer time) {
        this.audience = audience;
        this.time = time;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        AudienceScheduleId other = (AudienceScheduleId) _other;
        return Objects.equals(audience, other.audience)
                && Objects.equals(time, other.time);
    }

    @Override
    public int hashCode() {
        return audience.getId().hashCode() * 10 + time;
    }
}
