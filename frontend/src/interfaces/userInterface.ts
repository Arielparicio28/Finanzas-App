interface Profile {
  firstName: string;
  lastName: string;
  age: number;
  phone: string;
}

interface User {
  id: string;
  username: string;
  email: string;
  passwordHash: string;
  profile: Profile;
  createdAt: Date;
  updatedAt: Date;
}
export default User;
