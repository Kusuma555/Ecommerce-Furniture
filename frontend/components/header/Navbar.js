import React, { useContext } from "react";
import { Alert, AppBar, Box, Container, LinearProgress } from "@mui/material";
import { AuthContext } from "../../contexts/AuthContext";
import { NavContext } from "../../contexts/NavContext";
import AdminToolbar from "../admin/AdminToolbar";
import UserToolbar from "./UserToolbar";

export default function Navbar() {
  const { authToken } = useContext(AuthContext);
  const { navAction, setNavAction, isAdmin, loading, alert } =
    useContext(NavContext);

  return (
    <Box sx={{ marginBottom: "2%" }}>
      <AppBar position="static">
        {isAdmin ? (
          <AdminToolbar />
        ) : (
          <UserToolbar
            authToken={authToken}
            navAction={navAction}
            setNavAction={setNavAction}
          />
        )}
      </AppBar>
      {loading ? <LinearProgress color="warning" /> : null}
      {alert.message ? (
        <Container>
          <Alert
            severity={alert.severity}
            sx={{ padding: "10px", margin: "10px" }}
          >
            {alert.message}
          </Alert>{" "}
          <br />
        </Container>
      ) : null}
    </Box>
  );
}
