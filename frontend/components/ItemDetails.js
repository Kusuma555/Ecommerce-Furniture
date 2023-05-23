import { Grid, Box, Typography, Button } from "@mui/material";
import React from "react";
import Carousel from "react-material-ui-carousel";

export default function ItemDetails({ authToken, product, images, category }) {
  const typoStyle = {
    padding: "10px",
    border: "solid",
    borderWidth: "thin",
    borderColor: "black",
  };
  return (
    <>
      <Grid container>
        <Grid item xs={12} sm={12} md={6} lg={6}>
          <Carousel>
            {images.map((url, i) => (
              <Box
                key={i}
                component="img"
                sx={{
                  width: "100%",
                  height: "inherit",
                }}
                alt={product?.title}
                src={url}
              />
            ))}
          </Carousel>
        </Grid>

        <Grid item xs={12} sm={12} md={6} lg={6}>
          <Box sx={{ padding: "10px", marginLeft: "10px" }}>
            <Typography gutterBottom align="left" variant="h3">
              {product.title}
            </Typography>
            <br />
            <Typography align="left" variant="p" sx={typoStyle}>
              <strong>{category} </strong>
            </Typography>
            <br />
            <br />
            <Typography gutterBottom align="left" variant="p" sx={typoStyle}>
              {product.description}
            </Typography>
            <br />
            <br />
            <Typography
              gutterBottom
              variant="h3"
              component="div"
              sx={typoStyle}
            >
              &#8377;{product.price}
            </Typography>
            {!authToken ? (
              <Typography
                gutterBottom
                variant="overline"
                component="div"
                // sx={typoStyle}
              >
                You need to <strong>Login</strong> to Buy or Add to cart
              </Typography>
            ) : null}
            <Button variant="contained" disabled={!authToken}>
              Add to Cart
            </Button>
            &nbsp;
            <Button variant="contained" disabled={!authToken}>
              Buy Now
            </Button>
          </Box>
        </Grid>
      </Grid>
    </>
  );
}
