package me.cubert3d.palladium.input;

import me.cubert3d.palladium.Common;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Keys {

    public static final int LEFT_MOUSE_BUTTON = 0;
    public static final int RIGHT_MOUSE_BUTTON = 1;
    public static final int MIDDLE_MOUSE_BUTTON = 2;

    // This prefix in the translation key of the keys is to be removed.
    private static final String TRANSLATION_KEY_PREFIX = "key.keyboard.";

    // The translation key uses '.' as its delimiter, but in order to make
    // it resemble other user-input, such as that of the /give command.
    // E.g. "tipped_arrow" vs "tipped.arrow"; "numpad_8" vs "numpad.8"
    private static final char TRANSLATION_KEY_DELIMITER = '.';
    private static final char NEW_DELIMITER = '_';

    private static final Map<String, InputUtil.Key> keys = new HashMap<>();

    private Keys() {}

    public static void mapKey(InputUtil.@NotNull Key key) {
        if (key.getCategory().equals(InputUtil.Type.KEYSYM) && key.getCode() >= 0) {
            String abbreviatedTranslationKey = key.getTranslationKey();
            if (abbreviatedTranslationKey.startsWith(TRANSLATION_KEY_PREFIX)) {
                abbreviatedTranslationKey = abbreviatedTranslationKey.substring(TRANSLATION_KEY_PREFIX.length());
                abbreviatedTranslationKey = abbreviatedTranslationKey.replace(TRANSLATION_KEY_DELIMITER, NEW_DELIMITER);
                keys.put(abbreviatedTranslationKey, key);
            }
        }
    }

    static Optional<InputUtil.Key> getKey(String translationKey) {
        return Optional.ofNullable(keys.get(translationKey));
    }
    
    /*
    List of InputUtil.Key mappings, taken from InputUtil.Type:
    
    "key.keyboard.unknown": -1
    "key.mouse.left": 0
    "key.mouse.right": 1
    "key.mouse.middle": 2
    "key.mouse.4": 3
    "key.mouse.5": 4
    "key.mouse.6": 5
    "key.mouse.7": 6
    "key.mouse.8": 7
    "key.keyboard.0": 48
    "key.keyboard.1": 49
    "key.keyboard.2": 50
    "key.keyboard.3": 51
    "key.keyboard.4": 52
    "key.keyboard.5": 53
    "key.keyboard.6": 54
    "key.keyboard.7": 55
    "key.keyboard.8": 56
    "key.keyboard.9": 57
    "key.keyboard.a": 65
    "key.keyboard.b": 66
    "key.keyboard.c": 67
    "key.keyboard.d": 68
    "key.keyboard.e": 69
    "key.keyboard.f": 70
    "key.keyboard.g": 71
    "key.keyboard.h": 72
    "key.keyboard.i": 73
    "key.keyboard.j": 74
    "key.keyboard.k": 75
    "key.keyboard.l": 76
    "key.keyboard.m": 77
    "key.keyboard.n": 78
    "key.keyboard.o": 79
    "key.keyboard.p": 80
    "key.keyboard.q": 81
    "key.keyboard.r": 82
    "key.keyboard.s": 83
    "key.keyboard.t": 84
    "key.keyboard.u": 85
    "key.keyboard.v": 86
    "key.keyboard.w": 87
    "key.keyboard.x": 88
    "key.keyboard.y": 89
    "key.keyboard.z": 90
    "key.keyboard.f1": 290
    "key.keyboard.f2": 291
    "key.keyboard.f3": 292
    "key.keyboard.f4": 293
    "key.keyboard.f5": 294
    "key.keyboard.f6": 295
    "key.keyboard.f7": 296
    "key.keyboard.f8": 297
    "key.keyboard.f9": 298
    "key.keyboard.f10": 299
    "key.keyboard.f11": 300
    "key.keyboard.f12": 301
    "key.keyboard.f13": 302
    "key.keyboard.f14": 303
    "key.keyboard.f15": 304
    "key.keyboard.f16": 305
    "key.keyboard.f17": 306
    "key.keyboard.f18": 307
    "key.keyboard.f19": 308
    "key.keyboard.f20": 309
    "key.keyboard.f21": 310
    "key.keyboard.f22": 311
    "key.keyboard.f23": 312
    "key.keyboard.f24": 313
    "key.keyboard.f25": 314
    "key.keyboard.num.lock": 282
    "key.keyboard.keypad.0": 320
    "key.keyboard.keypad.1": 321
    "key.keyboard.keypad.2": 322
    "key.keyboard.keypad.3": 323
    "key.keyboard.keypad.4": 324
    "key.keyboard.keypad.5": 325
    "key.keyboard.keypad.6": 326
    "key.keyboard.keypad.7": 327
    "key.keyboard.keypad.8": 328
    "key.keyboard.keypad.9": 329
    "key.keyboard.keypad.add": 334
    "key.keyboard.keypad.decimal": 330
    "key.keyboard.keypad.enter": 335
    "key.keyboard.keypad.equal": 336
    "key.keyboard.keypad.multiply": 332
    "key.keyboard.keypad.divide": 331
    "key.keyboard.keypad.subtract": 333
    "key.keyboard.down": 264
    "key.keyboard.left": 263
    "key.keyboard.right": 262
    "key.keyboard.up": 265
    "key.keyboard.apostrophe": 39
    "key.keyboard.backslash": 92
    "key.keyboard.comma": 44
    "key.keyboard.equal": 61
    "key.keyboard.grave.accent": 96
    "key.keyboard.left.bracket": 91
    "key.keyboard.minus": 45
    "key.keyboard.period": 46
    "key.keyboard.right.bracket": 93
    "key.keyboard.semicolon": 59
    "key.keyboard.slash": 47
    "key.keyboard.space": 32
    "key.keyboard.tab": 258
    "key.keyboard.left.alt": 342
    "key.keyboard.left.control": 341
    "key.keyboard.left.shift": 340
    "key.keyboard.left.win": 343
    "key.keyboard.right.alt": 346
    "key.keyboard.right.control": 345
    "key.keyboard.right.shift": 344
    "key.keyboard.right.win": 347
    "key.keyboard.enter": 257
    "key.keyboard.escape": 256
    "key.keyboard.backspace": 259
    "key.keyboard.delete": 261
    "key.keyboard.end": 269
    "key.keyboard.home": 268
    "key.keyboard.insert": 260
    "key.keyboard.page.down": 267
    "key.keyboard.page.up": 266
    "key.keyboard.caps.lock": 280
    "key.keyboard.pause": 284
    "key.keyboard.scroll.lock": 281
    "key.keyboard.menu": 348
    "key.keyboard.print.screen": 283
    "key.keyboard.world.1": 161
    "key.keyboard.world.2": 162
    */
}
