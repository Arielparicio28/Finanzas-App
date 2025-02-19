import { useQuery } from "react-query";
import { useParams } from "react-router-dom"; // Para obtener el ID de la URL
import { getOneUser } from "../services/userConnection";
import type User from "../interfaces/userInterface";

const UserDetail = () => {
  const { id } = useParams(); // Obtener el ID desde la URL
  console.log("ID desde useParams:", id);
  
  // Llamar a React Query con el ID
  const { data: user, isLoading, error } = useQuery<User, Error>(
    ["user", id], // La clave de cache incluye el ID
    () => getOneUser(id as string), // La función para obtener el usuario
    { enabled: !!id } // Evitar que se ejecute si no hay ID
  );

  if (isLoading) return <p className="text-xl">Cargando...</p>;
  if (error) return <p className="text-red-500">Error: {error.message}</p>;

  return (
    <div className="p-4 max-w-lg mx-auto border rounded shadow">
      <h2 className="text-2xl font-bold mb-2">{user?.username}</h2>
      <p className="text-gray-600">{user?.email}</p>
      {user?.profile && (
        <>
          <p>Nombre: {user.profile.firstName} {user.profile.lastName}</p>
          <p>Edad: {user.profile.age}</p>
          <p>Teléfono: {user.profile.phone}</p>
        </>
      )}
    </div>
  );
};

export default UserDetail;
