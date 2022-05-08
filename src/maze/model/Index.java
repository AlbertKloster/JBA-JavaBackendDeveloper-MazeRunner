package maze.model;

import java.util.Objects;

public class Index {
    private final int i;
    private final int j;

    public Index(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return i == index.i && j == index.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
