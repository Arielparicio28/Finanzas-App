import { createBrowserRouter} from "react-router-dom";
import UserList from "../components/UserList";
import UserDetail from "../components/UserDetail";
import NotFound from "../components/NotFound";

const router = createBrowserRouter([
  {
    path: "/",
    element: <UserList />,
  },
  {
    path: "/users",
    element: <UserList />,
  },
  {
    path: "/users/:id",
    element: <UserDetail />,
  },
  {
    path: "*",
    element: <NotFound />,
  },
]);

export default router;
