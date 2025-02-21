import ApiResponse from "../interfaces/apiResponse";
import User from "../interfaces/userInterface";
import { axiosPrivate } from "./axiosPrivate"



export const getAllUsers = async (): Promise<ApiResponse<User[]>> => {
    return await axiosPrivate.get('/users');
   
  };

 export const getOneUser =  (id: string)   => {
    return  axiosPrivate.get(`/users/${id}`);
 
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
  