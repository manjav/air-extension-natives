echo f | xcopy /f /y ..\native_abilities_android\app\build\intermediates\packaged-classes\release\classes.jar android\

REM echo f | xcopy /f /y  C:\_projects\contributions\air-extension-android\package\android\classes.jar
"C:\_projects\4.6.0+31.0.0\bin\adt" -package -storetype pkcs12 -keystore cert.p12 -storepass 111111 -target ane "com.gerantech.extensions.nativeabilities.ane" extension.xml -swc android_extension_as3_lib.swc -platform Android-ARM -C android . -platform default -C default . && echo f | xcopy /f /y com.gerantech.extensions.nativeabilities.ane ..\native_abilities_test\exts\
