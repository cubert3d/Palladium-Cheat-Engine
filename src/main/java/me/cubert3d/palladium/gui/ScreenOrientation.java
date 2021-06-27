package me.cubert3d.palladium.gui;

import me.cubert3d.palladium.util.Common;
import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "4/9/2021",
        status = "in-progress"
)

public enum ScreenOrientation {
    TOP_LEFT {
        @Override
        public int getX() {
            return 2;
        }
        @Override
        public int getY() {
            return 2;
        }
    },
    TOP_RIGHT {
        @Override
        public int getX() {
            return Common.getMC().getWindow().getScaledWidth() - 2;
        }
        @Override
        public int getY() {
            return 2;
        }
    },
    BOTTOM_LEFT {
        @Override
        public int getX() {
            return 2;
        }
        @Override
        public int getY() {
            return Common.getMC().getWindow().getScaledHeight() - 2;
        }
    },
    BOTTOM_RIGHT {
        @Override
        public int getX() {
            return Common.getMC().getWindow().getScaledWidth() - 2;
        }
        @Override
        public int getY() {
            return Common.getMC().getWindow().getScaledHeight() - 2;
        }
    };

    public abstract int getX();
    public abstract int getY();
}
