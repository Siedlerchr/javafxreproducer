# javafxreproducer

To reproduce simply execute `./gradlew run` or to create a jlink application use `./gradlew jlinzip`

Alternatively, use the precompiled binaries from the [Java CI with Gradle check](https://github.com/Siedlerchr/javafxreproducer/actions/workflows/gradle.yml) workflow artifacts

## Screenshot

![screenshot](screenshot.png)

## Related Work

- [MyApp - JavaFX Demo Project](https://github.com/JulienTroudet/easyJavaFXSetup) - Demo project designed to provide a solid foundation for modern JavaFX applications. It includes all the essential configurations to simplify development, theme management, and multi-platform distribution.

## CodeArea RTF font reproducer

Run `./gradlew rtfFontReproducer`. The window displays indented text in a monospace font; the console prints the RTF generated for the same text by `RtfFormatHandler`.

Expected: the exported RTF uses a monospace font, preserving the code area's alignment. Actual with JavaFX 28-ea+12: its font table uses `\fswiss Helvetica` and the export does not specify a monospace font. No system clipboard contents are changed. Use `./gradlew rtfFontReproducer --args=--verify` to check this behavior and exit automatically.
