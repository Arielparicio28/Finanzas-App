import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import User from "../interfaces/userInterface";
import { getOneUser } from "../services/userConnection";


const UserDetail = () => {
    const { id } = useParams<{ id: string }>();
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
    if(id) 
    {
        getOneUser(id).then((response) => {
            setUser(response.data.data);
            console.log("respuesta de la api: " + response.data);
        });
    }
    }, [id]);

    if (!user) return <p>Cargando...</p>;

    return (
        <div  className="p-4 max-w-2xl mx-auto">
            <h2 className="text-2xl font-bold mb-4">Detalles del Usuario</h2>
        <p className="text-gray-600 text-sm">Nombre: {user.profile.firstName}</p>
        <p className="text-gray-600 text-sm">Apellido: {user.profile.lastName}</p>
        <p className="text-gray-600 text-sm">Edad: {user.profile.age}</p>
        <p className="text-gray-600 text-sm">Teléfono: {user.profile.phone}</p>
        <p className="text-gray-600 text-sm">Email: {user.email}</p>
        </div>
    );
};

export default UserDetail;
