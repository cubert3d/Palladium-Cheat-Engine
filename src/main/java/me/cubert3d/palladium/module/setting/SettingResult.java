package me.cubert3d.palladium.module.setting;

import me.cubert3d.palladium.util.annotation.ClassDescription;

@ClassDescription(
        authors = {
                "cubert3d"
        },
        date = "3/14/2021",
        status = "complete"
)

/*
Used for the result of attempting to change the value of a setting.

SUCCESS: the value of the setting was successfully updated.
INVALID_TYPE: the type of new value did not match the type of the setting.
OUT_OF_BOUNDS: both types matched, and the shared type is numerical,
               and the setting has min/max, but the given value exceeded those bounds.
SETTING_NOT_FOUND: used in the Module class, where a setting might not be found.
 */

public enum SettingResult {
    SUCCESS,
    INVALID_TYPE,
    OUT_OF_BOUNDS,
    SETTING_NOT_FOUND;

    public final boolean isSuccess() {
        return this.equals(SUCCESS);
    }
}
