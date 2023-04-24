package labs.webtech.compositeId;

import labs.webtech.table.Group;

import java.io.Serializable;
import java.util.Objects;

public class GroupScheduleId implements Serializable {

    private Group group;

    private Integer time;

    public GroupScheduleId() {}

    public GroupScheduleId(Group group, Integer time) {
        this.group = group;
        this.time = time;
    }

    @Override
    public boolean equals(Object _other) {
        if (this == _other) return true;
        if (_other == null || getClass() != _other.getClass()) return false;
        GroupScheduleId other = (GroupScheduleId) _other;
        return Objects.equals(group, other.group)
                && Objects.equals(time, other.time);
    }

    @Override
    public int hashCode() {
        return group.getId().hashCode() * 10 + time;
    }
}
