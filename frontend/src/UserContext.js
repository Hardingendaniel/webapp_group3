import React, {createContext, useContext, useState} from "react";
import {deleteAuthorizationCookies} from "./tools/authentication";

const UserContext = createContext();

export function UserProvider({ children }) {
    const [user, setUser] = useState(null);
    const [loggedIn, setLoggedIn] = useState(false);

    const login = (userData) => {
        setUser(userData);
        setLoggedIn(true);
    }

    const logout = () => {
        deleteAuthorizationCookies()
        setUser(null);
        setLoggedIn(false);
    }

    return (
        <UserContext.Provider value={{user, login, logout}}>
            {children}
        </UserContext.Provider>
    );
}

export function useUser() {
    return useContext(UserContext)
}