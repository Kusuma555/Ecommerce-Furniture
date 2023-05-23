import { CssBaseline } from "@mui/material";
import React, { useEffect, useState } from "react";
import Footer from "../components/Footer";
import Navbar from "../components/header/Navbar";
import { logout } from "../connections/login-register";
import { AuthContext } from "../contexts/AuthContext";
import { NavContext } from "../contexts/NavContext";

export default function MyApp({ Component, pageProps }) {
  const [authToken, setAuthToken] = useState("");
  const [navAction, setNavAction] = useState(null);
  const [isAdmin, setIsAdmin] = useState(false);
  const [loading, setLoading] = useState(false);
  const [alert, setAlert] = useState({});

  const setLogin = (data) => {
    // console.log(data);
    if (data.token && data.userid) {
      localStorage.setItem("userId", data.userid);
      localStorage.setItem("token", data.token);
      setAuthToken(data.token);
      return;
    }

    logout(
      { authToken },
      {
        success: (res) => {
          localStorage.removeItem("userId");
          localStorage.removeItem("token");
          setAuthToken(null);
        },
        error: (err) => {
          console.log(err);
        },
      }
    );
  };

  const navValues = {
    navAction,
    setNavAction,
    isAdmin,
    setIsAdmin,
    loading,
    setLoading,
    alert,
    setAlert,
  };

  useEffect(() => {
    setAuthToken(localStorage.getItem("token"));
  }, []);

  return (
    <AuthContext.Provider value={{ authToken, setAuthToken, setLogin }}>
      <NavContext.Provider value={navValues}>
        <CssBaseline />
        <Navbar />
        <Component {...pageProps} />;
        <Footer />
      </NavContext.Provider>
    </AuthContext.Provider>
  );
}
