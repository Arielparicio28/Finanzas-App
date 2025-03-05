# Practica-Hakaton-2025

🔒 Cómo Funciona la Lista Negra

1️⃣ Login

1.Cuando un usuario inicia sesión, recibe un token JWT válido.
2.Puede usar este token para acceder a recursos protegidos.

2️⃣ Logout

1.El usuario decide cerrar sesión.
2.El token se agrega a la lista negra, evitando su reutilización.

3️⃣ Filtro de Seguridad

En cada solicitud protegida, el backend verifica:
1.Si el token está en la lista negra → Rechazarlo (401 Unauthorized).
2.Si no está en la lista negra → Permitir la solicitud.

📝 Sin Lista Negra
1.Un usuario inicia sesión y obtiene un token válido.
2.Hace logout, pero su token sigue siendo válido hasta que expire.
Si un atacante obtiene ese token, puede seguir usándolo.

🛠 Con Lista Negra
1.Un usuario inicia sesión y obtiene un token.
2.Al hacer logout, el backend agrega su token a la lista negra.
3.Si el usuario o un atacante intenta usar ese token, el backend lo rechaza automáticamente.

¿Qué es AOP?
Es un patrón que permite ejecutar lógica automáticamente antes o después de los métodos sin necesidad de repetir código.