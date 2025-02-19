// UserList.tsx
import { useQuery } from "react-query";
import { getAllUsers } from "../services/userConnection";
import User from "../interfaces/userInterface";
import ApiResponse from "../interfaces/apiResponse";
import { Link } from "react-router-dom";

const UserList = () => {
  // Usamos useQuery para hacer el fetch de usuarios
  const { data, isLoading, error } = useQuery<ApiResponse<User[]>, Error>(
    "users",
    getAllUsers
  );

  console.log("Response data:", data);
  if (isLoading) {
    return (
      <div className="flex justify-center items-center h-64">
        <p className="text-xl font-semibold">Cargando...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-red-500 text-center mt-4">
        <p>Error: {error.message}</p>
      </div>
    );
  }

  return (
    <div className="p-4 max-w-2xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Lista de Usuarios</h2>
      {data && data.data.length === 0 ? (
        <p>No se encontraron usuarios.</p>
      ) : (
        <ul className="space-y-3">
          {data?.data.map((user: User) => (
            <li
              key={user.id}
              className="p-3 border rounded shadow hover:bg-gray-50 transition-colors"
            >
              <p className="text-gray-600 text-sm">Usuario: {user.username}</p>
              <p className="text-gray-600 text-sm">Email: {user.email}</p>
              {/* Si el perfil es un objeto, accede a sus propiedades directamente */}
              {user.profile && (
                <>
                  <p className="text-gray-600 text-sm">
                    Nombre: {user.profile.firstName}
                  </p>
                  <p className="text-gray-600 text-sm">
                    Apellido: {user.profile.lastName}
                  </p>
                  <p className="text-gray-600 text-sm">
                    Edad: {user.profile.age}
                  </p>
                  <p className="text-gray-600 text-sm">
                    Teléfono: {user.profile.phone}
                  </p>
                </>
              )}
            
                <Link
                to={`/users/${user.id}`}
                className="text-blue-500 underline"
              >
                Ver detalles
              </Link>
            </li>
          ))}
        </ul>
      )}
      
    </div>
  );
};

export default UserList;
