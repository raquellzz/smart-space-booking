import { createContext, useState } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem('@SSB:user');
    
    if (storedUser) {
      return JSON.parse(storedUser);
    }
    return null;
  });

  const login = (userData) => {
    setUser(userData);

    localStorage.setItem('@SSB:user', JSON.stringify(userData));
  };

  const logout = () => {
    setUser(null);

    localStorage.removeItem('@SSB:user');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};