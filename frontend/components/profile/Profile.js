import { Button, Grid, Container } from "@mui/material";
import React, { useContext, useState } from "react";
import { NAV_CLICK_ACTION } from "../../nav-actions";
import { AuthContext } from "../../contexts/AuthContext";
import AddressForm from "./AddressForm";
import BasicDetailsForm from "./BasicDetailsForm";

export default function Profile({ callback }) {
  const MENU_ACTIONS = {
    BASIC_DETAILS: "basic",
    ADDRESS: "address",
    ORDERS: "order",
    ADS: "ads",
  };

  const { authToken } = useContext(AuthContext);
  const userId = localStorage.getItem("userId");

  if (!userId || !authToken) callback(NAV_CLICK_ACTION.LOGIN);

  const [selected, setSelected] = useState(MENU_ACTIONS.BASIC_DETAILS);
  const [component, setComponent] = useState(
    <BasicDetailsForm authToken={authToken} userId={userId} />
  );

  const handleMenuSelect = (action) => {
    setSelected(action);
    switch (action) {
      case MENU_ACTIONS.BASIC_DETAILS:
        setComponent(
          <BasicDetailsForm authToken={authToken} userId={userId} />
        );
        break;

      case MENU_ACTIONS.ADDRESS:
        setComponent(<AddressForm authToken={authToken} userId={userId} />);
        break;

      case MENU_ACTIONS.ORDERS:
        setComponent(
          <>
            <h3 authToken={authToken} userId={userId}>
              Orders
            </h3>
          </>
        );
        break;

      case MENU_ACTIONS.ADS:
        setComponent(
          <>
            <h3 authToken={authToken} userId={userId}>
              Ads
            </h3>
          </>
        );
        break;
    }
  };

  const gridStyle = {
    backgroundColor: "#ecedee",
    padding: "30px",
    margin: "5px",
    borderRadius: "10px",
    textAlign: "center",
  };

  return (
    <Grid container alignContent={"center"} justifyContent={"center"}>
      <Grid item xs={12} sm={12} md={12} lg={12}>
        <Container
          align="center"
          sx={{
            // backgroundColor: "#cacaca",
            width: "100%",
            height: "100px",
          }}
        >
          <Button
            sx={{ margin: "2px" }}
            variant={
              selected === MENU_ACTIONS.BASIC_DETAILS ? "contained" : "outlined"
            }
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.BASIC_DETAILS);
            }}
          >
            Basic Details
          </Button>
          <Button
            sx={{ margin: "2px" }}
            variant={
              selected === MENU_ACTIONS.ADDRESS ? "contained" : "outlined"
            }
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.ADDRESS);
            }}
          >
            Address
          </Button>
          <Button
            sx={{ margin: "2px" }}
            variant={
              selected === MENU_ACTIONS.ORDERS ? "contained" : "outlined"
            }
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.ORDERS);
            }}
          >
            Orders
          </Button>
          <Button
            sx={{ margin: "2px" }}
            variant={selected === MENU_ACTIONS.ADS ? "contained" : "outlined"}
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.ADS);
            }}
          >
            Ads
          </Button>
        </Container>
      </Grid>
      <Grid style={gridStyle} item xs={12} sm={12} md={5} lg={5}>
        {component}
      </Grid>
    </Grid>
  );
}
