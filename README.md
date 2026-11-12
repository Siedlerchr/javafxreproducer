# javafxreproducer

To reproduce simply execute `./gradlew run` or to create a jlink application use `./gradlew jlinzip`

Alternatively, use the precompiled binaries from the [Java CI with Gradle check](https://github.com/Siedlerchr/javafxreproducer/actions/workflows/gradle.yml) workflow artifacts

## Screenshot

![screenshot](screenshot.png)

## Related Work

- [MyApp - JavaFX Demo Project](https://github.com/JulienTroudet/easyJavaFXSetup) - Demo project designed to provide a solid foundation for modern JavaFX applications. It includes all the essential configurations to simplify development, theme management, and multi-platform distribution.

## Ethiopic font fallback reproducer

Install Noto Sans Ethiopic, restart the application, then run `./gradlew ethiopicFontReproducer` (Windows: `gradlew.bat ethiopicFontReproducer`). Compare the Ethiopic text in the default label, explicitly selected font field, and monospaced source area. All should display readable characters rather than boxes. The console reports the OS, Java and JavaFX versions, whether JavaFX lists Noto Sans Ethiopic, and the resolved requested fonts; those font names do not identify the individual fallback glyph fonts. Repeat without the font installed if useful. This example uses the JavaFX version configured in this project, which may differ from the affected JabRef release.
