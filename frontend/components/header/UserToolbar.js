import React from "react";
import { Button, Toolbar, Typography } from "@mui/material";
import { NAV_CLICK_ACTION } from "../../nav-actions";

export default function UserToolbar({ authToken, navAction, setNavAction }) {
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

      <Button
        color="inherit"
        onClick={() => setNavAction(NAV_CLICK_ACTION.HOME)}
      >
        Home
      </Button>

      {authToken ? (
        <Button
          color="inherit"
          onClick={() => setNavAction(NAV_CLICK_ACTION.SELL)}
        >
          Sell
        </Button>
      ) : null}

      <Button
        color="inherit"
        onClick={() =>
          setNavAction(
            navAction === NAV_CLICK_ACTION.SEARCH_ON
              ? NAV_CLICK_ACTION.SEARCH_OFF
              : NAV_CLICK_ACTION.SEARCH_ON
          )
        }
      >
        Search
      </Button>

      {authToken ? (
        <Button
          color="inherit"
          onClick={() => setNavAction(NAV_CLICK_ACTION.PROFILE)}
        >
          Profile
        </Button>
      ) : null}

      <Button
        color="inherit"
        onClick={() => {
          if (authToken) {
            setNavAction(NAV_CLICK_ACTION.LOGOUT);
            return;
          }
          setNavAction(NAV_CLICK_ACTION.LOGIN);
        }}
      >
        {authToken ? "Logout" : "Login"}
      </Button>
    </Toolbar>
  );
}
