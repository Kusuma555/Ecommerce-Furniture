import React, { useContext, useState } from "react";
import { Button, Grid, TextField, Typography } from "@mui/material";
import { adminLogin } from "../../connections/login-register";
import { AuthContext } from "../../contexts/AuthContext";
import { NavContext } from "../../contexts/NavContext";

export default function Login() {
  const [credentials, setCredentials] = useState({
    emailId: "",
    password: "",
  });

  const { setLogin } = useContext(AuthContext);
  const { loading, setLoading, setAlert } = useContext(NavContext);

  const handleEmailChange = (e) =>
    setCredentials({ ...credentials, emailId: e.target.value });
  const handlePasswordChange = (e) =>
    setCredentials({ ...credentials, password: e.target.value });

  const handleOnSubmit = (e) => {
    e.preventDefault();

    setAlert({});

    const handleCallback = {
      success: (res) => {
        setLoading(false);
        setLogin(res.data.data);
      },
      error: (err) => {
        setLoading(false);

        const msg = err?.response?.data?.message;
        setAlert({
          severity: "error",
          message: msg ? msg : err.message,
        });
      },
    };

    adminLogin(credentials, handleCallback);
    setLoading(true);
  };

  return (
    <form onSubmit={handleOnSubmit}>
      <Grid container alignItems="center" justifyContent="center">
        <Grid
          style={{
            backgroundColor: "#ecedee",
            padding: "30px",
            borderRadius: "10px",
            textAlign: "center",
          }}
          item
          xs={10}
          sm={6}
          md={4}
          lg={4}
        >
          <Typography
            align="center"
            variant="h5"
            sx={{ paddingBottom: "10px" }}
          >
            Admin Login
          </Typography>

          <TextField
            fullWidth
            value={credentials?.emailId}
            onChange={handleEmailChange}
            label="Email"
            variant="outlined"
            type="email"
            required
          />

          <br />
          <br />

          <TextField
            fullWidth
            value={credentials?.password}
            onChange={handlePasswordChange}
            label="Password"
            variant="outlined"
            type="password"
            required
          />

          <br />
          <br />

          <Button
            fullWidth
            variant="contained"
            type="submit"
            disabled={loading}
          >
            {loading ? "Please wait" : "Login"}
          </Button>

          <br />
        </Grid>
      </Grid>
    </form>
  );
}
