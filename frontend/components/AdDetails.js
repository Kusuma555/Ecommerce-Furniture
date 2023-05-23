import { Grid, Box, Typography, Button } from "@mui/material";
import React from "react";
import Carousel from "react-material-ui-carousel";

export default function AdDetails({ authToken, ad, images, category }) {
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
                alt={ad?.title}
                src={url}
              />
            ))}
          </Carousel>
        </Grid>

        <Grid item xs={12} sm={12} md={6} lg={6}>
          <Box sx={{ padding: "10px", marginLeft: "10px" }}>
            <Typography gutterBottom align="left" variant="h3">
              {ad.title}
            </Typography>
            <br />
            <Typography align="left" variant="p" sx={typoStyle}>
              <strong>{category} </strong>
            </Typography>
            <br />
            <br />
            <Typography gutterBottom align="left" variant="p" sx={typoStyle}>
              {ad.description}
            </Typography>
            <br />
            <br />
            <Typography
              gutterBottom
              variant="h3"
              component="div"
              sx={typoStyle}
            >
              &#8377;{ad.price}
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
            <Button variant="contained" color="error" disabled={!authToken}>
              Reject
            </Button>
            &nbsp;
            <Button variant="contained" color="success" disabled={!authToken}>
              Accept
            </Button>
          </Box>
        </Grid>
      </Grid>
    </>
  );
}
