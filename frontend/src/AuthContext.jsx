import { jwtDecode } from 'jwt-decode';
import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [roles, setRoles] = useState(() => {
    const storedRoles = localStorage.getItem('roles');
    return storedRoles || [];
  });

  const [isAuthenticated, setIsAuthenticated] = useState(
    localStorage.getItem('accessToken') !== null);

  const login = (token) => {
    localStorage.setItem('accessToken', token);
    const decodedToken = jwtDecode(token);
    const roles = decodedToken.roles;
    console.log("token: ", decodedToken);
    localStorage.setItem('roles', roles);
    localStorage.setItem('user_id', decodedToken.sub);
    setRoles(roles);
    setIsAuthenticated(true);
  };

  const logout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('roles');
    localStorage.removeItem('user_id');
    setRoles([]);
    setIsAuthenticated(false);
  };

  useEffect(() => {
    const handleStorageChange = () => {
      const storedRoles = localStorage.getItem('roles');
      setRoles(storedRoles || []);
      setIsAuthenticated(localStorage.getItem('accessToken') !== null);
    };

    window.addEventListener('storage', handleStorageChange);
    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  return (
    <AuthContext.Provider value={{ roles, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};