import ApiResponse from "../interfaces/apiResponse";
import User from "../interfaces/userInterface";
import { axiosPrivate } from "./axiosPrivate"



export const getAllUsers = async (): Promise<ApiResponse<User[]>> => {
    const response = await axiosPrivate.get<ApiResponse<User[]>>('/users');
    return response.data;
  };

 export const getOneUser = async (id: string): Promise<User> => {
    const response = await axiosPrivate.get<ApiResponse<User>>(`/users/${id}`);
    return response.data.data; // Extraer el usuario de la respuesta
    //response.data es el ApiResponse, y response.data.data es el usuario en sí
  };



/*   

  export const getOneGestion = (id:string) => {
    return axiosPrivate.get(`gestion/${id}`)
  }
  
  export const postGestion = (data:GestionConvocatoria) => {
    return axiosPrivate.post('/gestion', data, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
  export const updateGestion = (id: string, data: GestionConvocatoria) => {
    return axiosPrivate.patch(`gestion/${id}`, data)
  } */
  