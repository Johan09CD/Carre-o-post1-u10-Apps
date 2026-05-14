# Reglas de ProGuard para el proyecto CICDApp
# Documentación: http://developer.android.com/guide/developing/tools/proguard.html

# Mantener atributos de depuración para stack traces legibles
-keepattributes SourceFile,LineNumberTable

# Renombrar el atributo de archivo fuente para reducir tamaño
-renamesourcefileattribute SourceFile
