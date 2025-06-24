
# 📚 Proyecto: Gestión de Préstamos Vencidos - Biblioteca INET

## 📌 Descripción

Este proyecto implementa un sistema de visualización de préstamos vencidos en una biblioteca, a partir de un archivo Excel exportado por el sistema PMB. Permite importar datos en distintos formatos de Excel, procesarlos y visualizarlos en una interfaz gráfica basada en Swing.

---

## 🛠️ Requisitos

- Java 8 o superior
- IDE compatible con Java (se recomienda Eclipse)
- Librerías externas incluidas en el proyecto (`/libs`)

---

## 📦 Dependencias externas

El proyecto utiliza dos bibliotecas para el manejo de archivos Excel:

### 1. **Apache POI** (para archivos `.xlsx`)
- `poi-*.jar`
- `poi-ooxml-*.jar`
- `poi-ooxml-schemas-*.jar`
- `xmlbeans-*.jar`
- `commons-math3-*.jar`

Apache POI permite trabajar con el formato Office Open XML (`.xlsx`, desde Excel 2007 en adelante) usando la API **XSSF**.

### 2. **JXL (JExcelAPI)** (para archivos `.xls` antiguos)
- `jxl-2.6.12.jar`

JXL permite leer archivos `.xls` en formato BIFF5 (Excel 5.0/7.0, versiones muy antiguas). Apache POI no soporta este formato, por eso se utiliza JXL como complemento.

---

## 🧩 Diferencias entre formatos Excel soportados

| Formato | Extensión | Versión de Excel        | Biblioteca usada | API       |
|---------|-----------|--------------------------|------------------|-----------|
| `.xlsx` | Moderno   | Excel 2007 o superior    | Apache POI       | XSSF      |
| `.xls`  | Estándar  | Excel 97–2003            | Apache POI       | HSSF      |
| `.xls`  | Antiguo   | Excel 5.0/7.0 (BIFF5)     | **JXL**          | JXL API   |

> **Nota**: Apache POI no soporta archivos `.xls` en formato BIFF5, por lo que se utiliza JXL como alternativa para estos casos.

---

## 🚀 Cómo levantar el proyecto en Eclipse

1. **Importar el proyecto**
   - Abrí Eclipse.
   - Seleccioná: `File > Open Projects from File System...`
   - Navegá hasta la carpeta del proyecto.

2. **Configurar las rutas de las librerías**
   > Si el proyecto no compila, es probable que las rutas a los JARs estén rotas. Para corregirlo:

   - Click derecho sobre el proyecto > `Build Path > Configure Build Path`.
   - En la pestaña **Libraries**, seleccioná las librerías que figuran como "missing" y hacé clic en **Remove**.
   - Luego hacé clic en **Add External JARs...**.
   - Navegá hasta la carpeta `/libs` dentro del proyecto y seleccioná **todos los `.jar`**.
   - Aceptá para guardar los cambios.

3. **Ejecutar**
   - Abrí la clase `Principal.java`.
   - Hacé clic derecho sobre ella > `Run As > Java Application`.

---

## 📂 Estructura del proyecto

```
/src
  /modelo
  /vista
  /controlador
  /persistencia
/libs
  (archivos .jar de Apache POI y JXL)
datos.xls (archivo de ejemplo)
/README.md
```

---

## 📞 Soporte

Si el archivo `.xls` que estás intentando abrir no se carga correctamente, verificá que no sea un archivo en formato **BIFF4 o anterior**, ya que **JXL solo soporta BIFF5**.
