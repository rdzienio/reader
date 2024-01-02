package pl.gienius.reader;

import java.io.Serializable;
import java.util.Objects;

public class Reader implements Serializable {

    private Long id;

    private String name;

    public Reader() {
    }

    public Reader(String name) {
        this.name = name;
    }

    public Reader(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return Objects.equals(getName(), reader.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
