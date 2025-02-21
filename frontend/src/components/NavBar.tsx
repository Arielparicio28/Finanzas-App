import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';


const Navbar: React.FC = () => {
  const [mousePosition, setMousePosition] = useState<{ x: number; y: number }>({ x: 0, y: 0 });
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    const handleMouseMove = (event: MouseEvent) => {
      const { clientX, clientY } = event;
      setMousePosition({ x: clientX, y: clientY });
    };

    window.addEventListener('mousemove', handleMouseMove);

    return () => {
      window.removeEventListener('mousemove', handleMouseMove);
    };
  }, []);

  return (
    <nav
      className="relative flex items-center justify-between w-full bg-background text-text p-4"
      style={{
        '--x': `${mousePosition.x}px`,
        '--y': `${mousePosition.y}px`,
      } as React.CSSProperties}
    >

      {/* Botón Hamburguesa */}
      <div className="lg:hidden z-30">
        <button
          className="text-primary focus:outline-none"
          onClick={() => setMenuOpen(!menuOpen)}
        >
          <svg
            className="w-6 h-6"
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              /* Para definir las rutas de un SVG en términos de coordenadas. */
              d={menuOpen ? 'M6 18L18 6M6 6l12 12' : 'M4 6h16M4 12h16m-7 6h7'}
            />
          </svg>
        </button>
      </div>

      {/* Opciones del Navbar */}
      <div
        className={`absolute top-0 left-0 z-20 w-full bg-background p-4 space-y-4 pt-10 lg:static lg:flex lg:items-center lg:space-x-8 lg:space-y-0 lg:bg-transparent lg:w-auto  ${
          menuOpen ? 'block' : 'hidden'
        }`}
      >
          <Link to="/" className="hover:text-primary block lg:inline">
            Home
        </Link>
        <Link to="users" className="hover:text-primary block lg:inline">
          Usuarios
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;