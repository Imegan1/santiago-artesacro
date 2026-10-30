# Santiago Artesacro

Plataforma web de catálogo y encargos de **arte sacro hecho a mano**, desarrollada para el artista Santiago (Instagram: *santiago.artesacro*).

| | |
|---|---|
| **Evidencia** | GA7-220501096-AA3-EV01 · Codificación de módulos del software |
| **Aprendiz** | Alexander Gaviria Cossío |
| **Programa** | Análisis y Desarrollo de Software (ADSO) · Ficha 3336098 |
| **Instructor** | Camilo Alfonso Salamanca |
| **Institución** | SENA |
| **Repositorio** | https://github.com/Imegan1/santiago-artesacro |
| **Fecha** | Octubre de 2026 |

---

## 1. Qué se entrega en esta evidencia

Se codificaron los módulos principales del sitio web:

| Módulo | Qué hace | Servlet / vista |
|---|---|---|
| **Catálogo** | Muestra las obras con categoría, medidas, material y precio; permite filtrar por categoría | `CatalogoServlet` · `catalogo.jsp` |
| **Usuarios** | Registro con validaciones, inicio y cierre de sesión | `RegistroServlet`, `LoginServlet`, `LogoutServlet` |
| **Encargos** | Un cliente con sesión pide una pieza del catálogo o describe una personalizada, y consulta el estado de sus pedidos | `EncargoServlet`, `MisPedidosServlet` |
| **Inicio** | Portada con presentación y contacto por WhatsApp | `index.jsp` |

---

## 2. Tecnologías y marco de trabajo

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework / plataforma web | **Jakarta (Java) EE 8: Servlet 4.0 + JSP 2.3** (API `javax.servlet`) |
| Servidor de aplicaciones | Apache Tomcat 9 |
| Acceso a datos | JDBC con `PreparedStatement` (driver MySQL Connector/J 8.3.0) |
| Base de datos | MySQL / MariaDB (XAMPP) |
| Front-end | HTML5 + CSS3 (vistas JSP) |
| IDE | Eclipse IDE for Enterprise Java and Web Developers |
| Control de versiones | Git + GitHub (GitHub Desktop) |

**Patrón de arquitectura: MVC por capas**

```
Navegador → controlador (Servlet) → dao (acceso a datos) → MySQL
                  │                      │
                  └─────── modelo ───────┘
                  │
                  └→ vista (JSP) → HTML al navegador
```

- **modelo**: clases del dominio (`Usuario`, `Producto`, `Categoria`, `Pedido`).
- **dao**: consultas SQL (`UsuarioDao`, `ProductoDao`, `PedidoDao`).
- **controlador**: servlets que reciben la petición, validan y eligen la vista.
- **util**: conexión (`Conexion`), cifrado de contraseñas (`Clave`) y escape de HTML (`Texto`).
- **vistas**: páginas JSP; las de uso privado están en `WEB-INF/vistas` para que solo se abran a través de su servlet.

---

## 3. Cambios frente al informe técnico «Plan de trabajo y versionamiento»

El plan de trabajo se elaboró en septiembre de 2026. Durante la construcción hubo ajustes que se documentan aquí:

| Plan de trabajo | Implementación final | Observación |
|---|---|---|
| Fase 1: front-end estático (`login.html`, `registro.html`, `css/styles.css`, `js/main.js`) | Vistas JSP generadas por el servidor y `css/estilos.css` | La validación se hace en el servidor, no solo en el navegador |
| Fase 3: backend en **PHP** / API | Backend en **Java (Servlets + JSP + JDBC)** | Cambio de tecnología documentado en este README |
| Fase 2: esquema MySQL para clientes, credenciales y catálogo | Se mantiene MySQL en XAMPP; el esquema se amplió con `categorias`, `productos` y `pedidos` | Ver `database/init.sql` |
| IDE: Visual Studio Code | Eclipse para el código; GitHub Desktop para Git | El flujo de Git y la convención de commits no cambian |
| Rama `feature/backend-php` | Rama `feature/modulo-web-java` | Mismo flujo (GitHub Flow) |
| Git + GitHub, GitHub Flow, Conventional Commits | Igual | Ver sección 7 |

---

## 4. Trazabilidad con los artefactos anteriores

### 4.1 Historias de usuario

| ID | Historia | Estado | Dónde se implementa |
|---|---|---|---|
| HU-001 | Información del artista | **Pendiente** | Hoy solo hay una presentación breve en `index.jsp`; falta la sección «Sobre el artista» |
| HU-002 | Galería de obras | **Implementada** (falta vista ampliada) | `CatalogoServlet`, `ProductoDao`, `catalogo.jsp`. Las fotos se cargan desde la columna `imagen` |
| HU-003 | Formulario de contacto / solicitudes | **Implementada con cambios** | `EncargoServlet`, `PedidoDao`, `encargo.jsp`. La solicitud se **guarda en la base de datos** (con validación y confirmación) en vez de enviarse por correo; además hay botón de WhatsApp |
| HU-004 | Administración de contenido | **Pendiente** | El rol `administrador` ya existe; falta el panel. Por ahora el contenido se gestiona desde la base de datos |
| HU-005 | Novedades y eventos | **Pendiente** | — |

### 4.2 Casos de uso

| Caso de uso | Actor | Estado |
|---|---|---|
| Visualizar catálogo de obras | Visitante | Implementado |
| Ver detalle de una obra | Visitante | Parcial (los datos se ven en la tarjeta de cada obra) |
| Gestionar contenido del sitio | Administrador | Pendiente |

### 4.3 Requisitos no funcionales

| Requisito | Cómo se cumple |
|---|---|
| Accesible desde distintos navegadores | HTML5/CSS3 estándar, diseño adaptable a celular |
| Interfaz intuitiva | Menú fijo, filtros por categoría, mensajes de error claros |
| Información segura | Contraseñas cifradas (PBKDF2), consultas con `PreparedStatement`, escape de HTML contra XSS, sesión con caducidad de 30 minutos, validación en el servidor |

---

## 5. Instalación y ejecución

**Requisitos:** JDK 17 · Apache Tomcat 9 · XAMPP (MySQL) · Eclipse IDE for Enterprise Java and Web Developers.

1. **Base de datos.** Encender MySQL en XAMPP, abrir phpMyAdmin, pestaña *Importar* y cargar `database/init.sql`. Se crea la base `santiago_artesacro` con sus tablas y datos de ejemplo.
2. **Proyecto.** En Eclipse: *File > Import > General > Existing Projects into Workspace* y elegir la carpeta del proyecto. Si Eclipse no reconoce el servidor, configurar *Window > Preferences > Server > Runtime Environments* con Apache Tomcat 9.
3. **Despliegue.** Clic derecho en el proyecto > *Export > WAR file* hacia la carpeta `webapps` de Tomcat y arrancar Tomcat.
4. **Abrir:** `http://localhost:8080/santiago-artesacro/`

**Conexión a la base de datos.** Por defecto usa `root` sin contraseña en `localhost:3306` (XAMPP). Para otro entorno se definen las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD`; no hay que cambiar el código.

**Crear un administrador.** Registrarse en la página y ejecutar en phpMyAdmin:
```sql
UPDATE usuarios SET rol = 'administrador' WHERE correo = 'tu_correo@ejemplo.com';
```

---

## 6. Estructura del repositorio

```
santiago-artesacro/
├── database/
│   └── init.sql                 Script de la base de datos
├── src/main/java/
│   ├── controlador/             Servlets (CatalogoServlet, RegistroServlet, ...)
│   ├── dao/                     Acceso a datos (UsuarioDao, ProductoDao, PedidoDao)
│   ├── modelo/                  Clases del dominio (Usuario, Producto, Categoria, Pedido)
│   └── util/                    Conexion, Clave (cifrado), Texto (escape HTML)
├── src/main/webapp/
│   ├── css/estilos.css          Estilos
│   ├── WEB-INF/
│   │   ├── web.xml              Configuración de la aplicación
│   │   ├── cabecera.jspf        Menú compartido
│   │   ├── vistas/              Páginas privadas (encargo, mis pedidos)
│   │   └── lib/                 Driver de MySQL
│   ├── index.jsp, catalogo.jsp, login.jsp, registro.jsp
│   └── META-INF/
├── .gitignore
└── README.md
```

---

## 7. Estándares de codificación y versionamiento

**Codificación**
- Paquetes en minúscula; clases en `PascalCase`; métodos y variables en `camelCase`; constantes en `MAYUSCULAS_CON_GUION_BAJO`.
- Comentarios Javadoc en todas las clases y métodos públicos; comentarios en línea donde la lógica no es obvia; comentarios `<%-- --%>` en las vistas.
- Sangría de 4 espacios, codificación UTF-8, una clase por archivo.
- Seguridad: `PreparedStatement` en todas las consultas, escape de HTML en la salida, contraseñas nunca en texto plano, mensajes de error genéricos hacia el usuario y detalle técnico solo en el registro del servidor.

**Versionamiento**
- Git + GitHub con *GitHub Flow*: rama estable `main` y ramas por funcionalidad (`feature/...`).
- Mensajes de commit con *Conventional Commits* (`feat`, `fix`, `docs`, `style`, `chore`), por ejemplo: `feat(encargos): agrega módulo de encargos y consulta de pedidos`.

---

## 8. Casos de prueba manuales

| # | Prueba | Resultado esperado | Resultado |
|---|---|---|---|
| 1 | Registrarse con datos válidos | Redirige a *Ingresar* con aviso verde | ☐ |
| 2 | Registrarse con contraseña de menos de 8 caracteres | Aviso rojo; el formulario conserva los datos | ☐ |
| 3 | Registrarse con un correo ya existente | Aviso «Ese correo ya está registrado» | ☐ |
| 4 | Ingresar con credenciales correctas | Menú muestra «Hola, nombre» y «Salir» | ☐ |
| 5 | Ingresar con contraseña incorrecta | Aviso «Correo o contraseña incorrectos» | ☐ |
| 6 | Verificar la contraseña en la base de datos | Aparece cifrada (`120000:...:...`), no en texto | ☐ |
| 7 | Filtrar el catálogo por categoría | Solo se muestran las obras de esa categoría | ☐ |
| 8 | Pulsar «Encargar» sin sesión | Redirige a *Ingresar* | ☐ |
| 9 | Enviar un encargo desde una pieza | Redirige a *Mis pedidos* con aviso verde | ☐ |
| 10 | Encargo personalizado con menos de 10 caracteres | Aviso rojo de descripción mínima | ☐ |
| 11 | Cerrar sesión | El menú vuelve a mostrar «Ingresar» y «Registrarse» | ☐ |

---

## 9. Trabajo pendiente

- Sección «Sobre el artista» (HU-001) y vista ampliada de cada obra.
- Panel de administración para gestionar obras, fotos y el estado de los pedidos (HU-004).
- Novedades y eventos (HU-005).
- Cargar las obras reales y el logo del artista.
- Publicación del sitio en un servidor con soporte para Java.
