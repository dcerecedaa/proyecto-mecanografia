# ✨ Juego de mecanografia

**Aplicación completa de mecanografía desarrollada en Java, pensada para mejorar velocidad, precisión y técnica mediante ejercicios interactivos y estadísticas en tiempo real.**

---

## 💻 Descripción General

Esta aplicación ayuda a los usuarios a mejorar sus habilidades de mecanografía mediante:

- **Entrenamientos interactivos** en tiempo real
- **Estadísticas detalladas** de rendimiento
- **Interfaz visual** con teclado virtual
- **Retos temporizados** con límite de errores
- **Roles de usuario y administrador** con progreso guardado

**Ideal para principiantes que aprenden mecanografía y para usuarios avanzados que buscan mejorar su velocidad.**

---

## 🚀 Características Principales

### 🎯 Funcionalidades Centrales
- ✅ **Dos niveles de dificultad:** Fácil y Difícil
- ✅ **Estadísticas en tiempo real:** PPM, errores y puntuación
- ✅ **Perfiles de usuario** con datos guardados
- ✅ **Panel de administrador** para gestionar usuarios y textos

### ✨ Experiencia de Usuario
- 🎹 **Teclado virtual** con feedback en tiempo real
- ⏱️ **Retos temporizados** con límite de errores
- 💡 **Interfaz visual clara** y con animaciones
- 📈 **Seguimiento y guardado** de estadísticas por sesión

### ⚙️ Capacidades Técnicas
- 🧾 **Almacenamiento basado en archivos** (.txt)
- 🔐 **Login y autenticación** de usuario seguro
- 🧼 **Validación de entradas** en múltiples niveles
- 💻 **Compatible** con Windows, macOS y Linux

---

## 📋 Estructura de la Aplicación

| Pantalla | Descripción |
|----------|-------------|
| **Pantalla de carga** | Verifica archivos y configuración |
| **Pantalla de bienvenida** | Entrada inicial con Play y Exit |
| **Login** | Acceso de usuario/admin |
| **Panel de usuario** | Acceso a niveles, estadísticas y tutoriales |
| **Panel de administrador** | Gestión de usuarios y edición de textos |
| **Panel de práctica** | Interfaz de escritura con métricas en tiempo real |

---

## 🎮 Niveles de Dificultad

| Característica | 🅰️ **Fácil** | 🅱️ **Difícil** |
|----------------|--------------|----------------|
| **Longitud texto** | 200 caracteres | 1000 caracteres |
| **Tiempo límite** | 4 minutos | 3 minutos |
| **Máx. errores** | 5 | 3 |
| **Público objetivo** | Principiantes | Usuarios avanzados |

---

## 🛠️ Instalación y Uso

1. **Extrae el ZIP del proyecto.**

2. **En Eclipse:**
   - `File → Import → Existing Projects into Workspace`
   - Selecciona la carpeta del proyecto
   - Haz clic en **Finish**

3. **Ejecuta `PantallaCarga.java`** como Java Application.

> ⚠️ **Importante:** No modifiques los archivos `.java` a menos que quieras editar la funcionalidad.
> Puedes editar los archivos `.txt` de manera segura para actualizar usuarios o textos.

---

## 📁 Estructura del Proyecto

```bash
src/
├── 🚀 PantallaCarga.java        # Punto de entrada principal
├── 🏠 PanelBienvenida.java      # Pantalla de bienvenida
├── 🔐 PanelLogin.java           # Login usuario/admin
├── 👤 PanelUsuario.java         # Panel de usuario
├── 👑 PanelAdministrador.java   # Panel administrador
├── 🅰️ PanelNivelFacil.java      # Nivel de mecanografía fácil
├── 🅱️ PanelNivelDificil.java    # Nivel de mecanografía difícil
├── ✏️ Lecciones.java            # Editor de textos para admin
├── 👥 usuarios.txt              # Credenciales de usuario
├── 📝 textos.txt                # Textos de práctica
└── 📊 estadisticas.txt          # Estadísticas de sesiones
```

---

## 💻 Stack Tecnológico

| Categoría | Detalles |
|-----------|----------|
| **Lenguaje** | Java 22 |
| **Librería UI** | Java Swing |
| **Almacenamiento** | Archivos de texto (.txt) |
| **Diseño** | Interfaz visual clara con animaciones |
| **Compatibilidad** | Windows, macOS, Linux |

---

## ⭐ Soporte y Contribuciones

**Si te resulta útil este proyecto:**

- 🌟 **Dale una estrella** en GitHub
- 🛠️ **Haz un fork** y crea tu propia versión
- 🐛 **Abre un issue** si encuentras bugs o quieres

---

## 👨‍💻 Autor

**David Cereceda Pérez**  
[GitHub](https://github.com/davidcereceda) | [LinkedIn](https://linkedin.com/in/davidcereceda)

---
