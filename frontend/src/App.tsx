import "./App.css";
import { RouterProvider } from "react-router-dom";
import router from "./Router/Router.tsx";

function App() {
  return (
    <div className="min-h-screen bg-gray-100">
      <RouterProvider router={router} />
    </div>
  );
}

export default App;