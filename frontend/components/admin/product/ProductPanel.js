import React, { useContext, useEffect, useState } from "react";
import ProductForm from "./ProductForm";
import { Grid, Container, Button } from "@mui/material";
import ProductList from "./ProductList";
import ProductUpdateForm from "./ProductUpdateForm";
import { AuthContext } from "../../../contexts/AuthContext";

export default function ProductPanel() {
  const MENU_ACTIONS = {
    VIEW_PRODUCTS: "view",
    ADD_PRODUCTS: "add",
    UPDATE_PRODUCTS: "update",
  };

  const { authToken } = useContext(AuthContext);
  const [selected, setSelected] = useState(MENU_ACTIONS.VIEW_PRODUCTS);
  const [component, setComponent] = useState(
    <ProductList authToken={authToken} />
  );

  const handleMenuSelect = (action, productDetails = null) => {
    setSelected(action);
    switch (action) {
      case MENU_ACTIONS.VIEW_PRODUCTS:
        setComponent(
          <ProductList authToken={authToken} callback={handleMenuSelect} />
        );
        break;

      case MENU_ACTIONS.ADD_PRODUCTS:
        setComponent(<ProductForm authToken={authToken} />);
        break;

      case MENU_ACTIONS.UPDATE_PRODUCTS:
        setComponent(
          <ProductUpdateForm
            authToken={authToken}
            productDetails={productDetails}
          />
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

  useEffect(() => {
    handleMenuSelect(MENU_ACTIONS.VIEW_PRODUCTS);
  }, []);

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
              selected === MENU_ACTIONS.VIEW_PRODUCTS ? "contained" : "outlined"
            }
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.VIEW_PRODUCTS);
            }}
          >
            View Products
          </Button>
          <Button
            sx={{ margin: "2px" }}
            variant={
              selected === MENU_ACTIONS.ADD_PRODUCTS ? "contained" : "outlined"
            }
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.ADD_PRODUCTS);
            }}
          >
            ADD Products
          </Button>

          <Button
            sx={{ margin: "2px" }}
            variant={
              selected === MENU_ACTIONS.UPDATE_PRODUCTS
                ? "contained"
                : "outlined"
            }
            onClick={(e) => {
              handleMenuSelect(MENU_ACTIONS.UPDATE_PRODUCTS);
            }}
          >
            Update Products
          </Button>
        </Container>
      </Grid>
      <Grid style={gridStyle} item xs={12} sm={12} md={12} lg={12}>
        {component}
      </Grid>
    </Grid>
  );
}
