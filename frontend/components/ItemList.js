import { Grid, Typography } from "@mui/material";
import React, { useEffect } from "react";
import { useState } from "react";
import ItemCard from "./ItemCard";
import { getProducts } from "../connections/product";

export default function ItemList({ callback }) {
  const [loading, setLoading] = useState(true);
  const [products, setProducts] = useState([]);

  useEffect(() => {
    setLoading(true);
    getProducts({
      success: (res) => {
        const data = res.data.data;
        // console.log(data);
        setLoading(false);
        setProducts(data);
      },
      error: (err) => {
        setLoading(false);
        const msg = err?.response?.data?.message;
        setAlert({
          severity: "error",
          message: msg ? msg : err.message,
        });

        console.log(msg ? msg : err.message);
      },
    });
  }, []);

  return (
    <Grid
      container
      spacing={3}
      alignItems="center"
      justifyContent="center"
      style={{
        padding: "20px",
      }}
    >
      {loading ? (
        <Grid item xs={12} sm={12} md={12} lg={12}>
          <Typography variant="h5" align="center">
            Loading please wait
          </Typography>
        </Grid>
      ) : (
        products.map((product) => (
          <Grid key={product.productId} item xs={6} sm={4} md={3} lg={3}>
            <ItemCard product={product} callback={callback} />
          </Grid>
        ))
      )}
    </Grid>
  );
}
