package domain;


import java.io.Serializable;
import java.time.LocalDateTime;

public class NoteTheChange<T> implements Serializable {
    private String role;
    private String typeOfChange;
    private T valueBefore;
    private T valueAfter;
    private LocalDateTime localDateTime;

    public NoteTheChange(String role, String typeOfChange, T valueBefore, T valueAfter) {
        this.role = role;
        this.typeOfChange = typeOfChange;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
        this.localDateTime = LocalDateTime.now();
    }


    public String getTypeOfChange() {
        return typeOfChange;
    }

    public void setTypeOfChange(String typeOfChange) {
        this.typeOfChange = typeOfChange;
    }

    public T getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(T valueBefore) {
        this.valueBefore = valueBefore;
    }

    public T getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(T valueAfter) {
        this.valueAfter = valueAfter;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
