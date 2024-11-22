Augusto Perrone - 142514
Link al repo: https://github.com/sssdark302/taller4.git
# 📱 **Aplicación Android - Funcionalidades y Diseño**

¡Esta aplicación es un proyecto avanzado que combina sensores, widgets y fragmentos para aprender el desarrollo en Android Studio! 🎉

---

## 🌟 **Principales Funcionalidades**

### 🏠 **Pantalla de Inicio (`MainActivity`)**
- Saludo personalizado que cambia según la hora:  
  🌞 *Buenos días*  
  🌤️ *Buenas tardes*  
  🌙 *Buenas noches*
- Botón para ir a la Pantalla Principal.

---

### 🔧 **Pantalla Principal (`PrincipalActivity`)**
- **Interacciones clave:**
    - Ingresa tu nombre y guárdalo en:
        - 🗂️ **SharedPreferences**.
        - 📊 **Base de datos SQLite** (guardar, cargar, limpiar).
    - Realiza una tarea en segundo plano con una barra de progreso.
    - Usa los botones para navegar a:
        - ⚙️ Configuración.
        - 🌟 Fragmentos.
- **Sensor de movimiento**: Cambia dinámicamente el color de fondo con el acelerómetro.

---

### 🎨 **Pantalla de Configuración (`ConfigActivity`)**
- Cambia el color de fondo: 🔴 *Rojo* | 🔵 *Azul*.
- Regresa a la pantalla de inicio.

---

### 🧩 **Pantalla de Fragmentos (`FragmentsActivity`)**
- **`FragmentLista`**:  
  📜 Muestra una lista de nombres guardados.  
  ✅ Permite seleccionar un nombre.
- **`FragmentDetalles`**:  
  🔍 Muestra los detalles del nombre seleccionado en tiempo real.

---

### 📦 **Widget**
- Muestra los nombres guardados en la base de datos.
- 🔄 Botón para actualizar manualmente los datos del widget.

---

## 🛠️ **Tecnologías Utilizadas**
- **Android Studio**: Plataforma de desarrollo.
- **SQLite**: Base de datos local.
- **ConstraintLayout**: Diseño responsivo y flexible.
- **SharedPreferences**: Almacenamiento de preferencias.
- **Acelerómetro**: Sensor para detectar movimiento.
- **Widgets**: Componentes interactivos en la pantalla de inicio.
- **Fragmentos**: Componentes reutilizables.
