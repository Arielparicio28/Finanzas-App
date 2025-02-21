import User from "../interfaces/userInterface";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAllUsers } from "../services/userConnection";


const UserList = () => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
   getAllUsers()
      .then((response) => {
        setUsers(response.data)
      })
      .catch((error) => {
        console.error('Error obteniendo los datos:', error)
      })
  }, []);
  if (!users) return null
  console.log(users)
  return (
    <div className="p-4 max-w-2xl mx-auto">
      <h2 className="text-2xl font-bold mb-4">Lista de Usuarios</h2>
      {users.length === 0 ? (
        <p>No se encontraron usuarios.</p>
      ) : (
        <ul className="space-y-3">
          {users.map((user: User) => (
  <li key={user.id} className="p-3 border rounded shadow hover:bg-gray-50 transition-colors">
    {user.profile && (
      <>
        <p className="text-gray-600 text-sm">Nombre: {user.profile.firstName}</p>
        <p className="text-gray-600 text-sm">Apellido: {user.profile.lastName}</p>
      </>
    )}
    <Link to={`/users/${user.id}`} className="text-blue-500 underline">
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
