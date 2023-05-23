import React, { useContext, useState } from "react";
import { Alert, Button, Grid, TextField, Typography } from "@mui/material";
import { NAV_CLICK_ACTION } from "../../nav-actions";
import { login } from "../../connections/login-register";
import { AuthContext } from "../../contexts/AuthContext";

export default function Login({ callback }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [alert, setAlert] = useState({});
  const [loading, setLoading] = useState(false);

  const { setLogin } = useContext(AuthContext);

  const handleEmailChange = (e) => setEmail(e.target.value);
  const handlePasswordChange = (e) => setPassword(e.target.value);
  const handleRegisterClick = () =>
    callback ? callback(NAV_CLICK_ACTION.REGISTER) : null;

  const handleOnSubmit = (e) => {
    e.preventDefault();

    setAlert({});

    const handleCallback = {
      success: (res) => {
        setAlert({
          severity: "success",
          message: res.data.message,
        });

        setLogin(res.data.data);

        if (callback) callback(NAV_CLICK_ACTION.HOME);
      },
      error: (err) => {
        setLoading(false);
        console.log(err);
        const msg = err?.response?.data?.message;
        setAlert({
          severity: "error",
          message: msg ? msg : err.message,
        });
      },
    };

    login({ emailId: email, password }, handleCallback);

    setLoading(true);
  };

  return (
    <form onSubmit={handleOnSubmit}>
      <Grid
        container
        alignItems="center"
        justifyContent="center"
        style={{
          marginTop: "5%",
          marginBottom: "5%",
        }}
      >
        <Grid
          style={{
            backgroundColor: "#ecedee",
            padding: "30px",
            borderRadius: "10px",
            textAlign: "center",
          }}
          item
          xs={12}
          sm={10}
          md={4}
          lg={4}
        >
          <Typography
            align="center"
            variant="h5"
            sx={{ paddingBottom: "10px" }}
          >
            Login
          </Typography>

          {alert.message ? (
            <>
              <Alert severity={alert.severity}>{alert.message}</Alert> <br />
            </>
          ) : null}

          <TextField
            fullWidth
            value={email}
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
            value={password}
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
          <br />

          <Button fullWidth variant="text" onClick={handleRegisterClick}>
            Don't have an account? Register here
          </Button>
        </Grid>
      </Grid>
    </form>
  );
}
