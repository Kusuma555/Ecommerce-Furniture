import { Card, CardContent, CardMedia, Grid, Typography } from "@mui/material";
import { Container, textAlign } from "@mui/system";
import { useRouter } from "next/router";
import React, { useContext, useEffect } from "react";
import { AuthContext } from "../../../contexts/AuthContext";
import { NavContext } from "../../../contexts/NavContext";

const navigations = [
  {
    name: "Products",
    color: "#C3A21A",
    image: "/Products.png",
    message: "Manage products",
  },
  {
    name: "Orders",
    color: "#8FC31A",
    image: "/order.png",
    message: "Manage Orders",
  },
  {
    name: "Ads",
    color: "#1AC3A2",
    image: "/ads.png",
    message: "Manage Ads",
  },
  {
    name: "Categories",
    color: "#1A90C3",
    image: "/categories.png",
    message: "Manage Categories",
  },
];

export default function index() {
  const router = useRouter();
  const { authToken } = useContext(AuthContext);
  const { setIsAdmin } = useContext(NavContext);

  useEffect(() => {
    setIsAdmin(true);
    document.title = "Admin Dashboard";

    if (!authToken) router.push(`${router.basePath}/admin`);

    // console.log("Dashboard", authToken);
  }, [authToken]);

  return (
    <>
      {authToken ? (
        <Container>
          <Grid
            container
            alignContent={"center"}
            alignItems={"center"}
            spacing={2}
          >
            {navigations.map((nav, i) => (
              <Grid key={i} item xs={12} sm={12} md={3} lg={3}>
                <Card
                  raised
                  sx={{
                    background: nav.color,
                    "&:hover": {
                      cursor: "pointer",
                      border: "solid",
                      borderColor: "gold",
                      borderWidth: "thin",
                    },
                  }}
                  onClick={() => {
                    router.push(`${router.pathname}/${nav.name.toLowerCase()}`);
                  }}
                >
                  <CardMedia
                    sx={{
                      height: "128px",
                      padding: "1em 1em 0 1em",
                      objectFit: "contain",
                    }}
                    component="img"
                    image={nav.image}
                    title={nav.name}
                  />
                  {/* <hr /> */}
                  <CardContent sx={{ background: "rgba(240, 240, 240, 0.8)" }}>
                    <Typography gutterBottom variant="h5" component="div">
                      {nav.name}
                    </Typography>
                    <Typography variant="overline" color="text.secondary">
                      {nav.message}
                    </Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Container>
      ) : (
        <div>You are not authorized {authToken}</div>
      )}
      ;
    </>
  );
}
