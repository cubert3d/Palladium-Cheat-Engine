package me.cubert3d.palladium.util;

import me.cubert3d.palladium.util.annotation.ClassDescription;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/12/2021",
        status = "in-progress"
)

// Unused, will probably be deleted.
@Deprecated
public final class ArgumentTree {

    enum Type {
        STRING,
        INTEGER,
        DOUBLE
    }

    static class Node implements Named {

        private final String name;
        private final Type type;
        private final Set<Node> next = new HashSet<>();

        protected Node(@NotNull String name, Type type) {
            this.name = name.toLowerCase();
            this.type = type;
        }

        protected Node(@NotNull String name, Type type, Node... next) {
            this.name = name.toLowerCase();
            this.type = type;
            this.next.addAll(Arrays.asList(next));
        }

        @Override
        public final String getName() {
            return name;
        }

        public final Type getType() {
            return type;
        }

        public final Set<Node> getNext() {
            return next;
        }
    }

    private final Set<Node> head = new HashSet<>();

    public ArgumentTree(Node... head) {
        this.head.addAll(Arrays.asList(head));
    }
}
