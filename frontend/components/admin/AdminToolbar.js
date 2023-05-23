import React, { useContext } from "react";
import { Button, Toolbar, Typography } from "@mui/material";
import { AuthContext } from "../../contexts/AuthContext";

export default function AdminToolbar() {
  const { authToken, setLogin } = useContext(AuthContext);
  return (
    <Toolbar>
      <Typography
        variant="h6"
        noWrap
        component="div"
        sx={{ flexGrow: 1, display: { xs: "none", sm: "block" } }}
      >
        Buy Sell Trade
      </Typography>

      {authToken ? (
        <Button
          color="inherit"
          onClick={() => {
            setLogin({ userId: "", token: "" });
          }}
        >
          Logout
        </Button>
      ) : null}
    </Toolbar>
  );
}
