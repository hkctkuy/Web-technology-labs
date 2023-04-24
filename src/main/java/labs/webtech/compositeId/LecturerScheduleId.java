package labs.webtech.compositeId;

import labs.webtech.table.Lecturer;

import java.io.Serializable;
import java.util.Objects;

public class LecturerScheduleId implements Serializable {

    private Lecturer lecturer;

    private Integer time;

    public LecturerScheduleId() {}

    public LecturerScheduleId(Lecturer lecturer, Integer time) {
        this.lecturer = lecturer;
        this.time = time;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        LecturerScheduleId other = (LecturerScheduleId) _other;
        return Objects.equals(lecturer, other.lecturer)
                && Objects.equals(time, other.time);
    }

    @Override
    public int hashCode() {
        return lecturer.getId().hashCode() * 10 + time;
    }
}
