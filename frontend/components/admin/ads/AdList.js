import {
  Button,
  Grid,
  Paper,
  Table,
  TableContainer,
  TextField,
  TableCell,
  TableHead,
  TableRow,
  TableBody,
  Alert,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { getAds } from "../../../connections/ads";
import { getCategories } from "../../../connections/category";
import { deleteProduct } from "../../../connections/product";

export default function AdList({ callback, authToken }) {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [alert, setAlert] = useState({});

  const handleUpdateClick = (productDetails) =>
    callback("update", productDetails);

  const handleDeleteProduct = (productId) => {
    deleteProduct(
      { authToken, productId },
      {
        success: (res) => {
          setLoading(false);
          // const data = res.data.data;
          setProducts([...products.filter((p) => p.productId !== productId)]);
          // console.log(categories[0]);
        },
        error: (err) => {
          setLoading(false);
          const msg = err?.response?.data?.message;
          setAlert({
            severity: "error",
            message: msg ? msg : err.message,
          });
        },
      }
    );
  };

  useEffect(() => {
    setLoading(true);
    getCategories(
      { authToken },
      {
        success: (res) => {
          setLoading(false);
          const data = res.data.data;
          setCategories(data);
        },
        error: (err) => {
          setLoading(false);
          const msg = err?.response?.data?.message;
          setAlert({
            severity: "error",
            message: msg ? msg : err.message,
          });
        },
      }
    );

    getAds(
      { authToken },
      {
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
      }
    );
  }, []);
  return (
    <>
      {alert.message ? (
        <>
          <Alert severity={alert.severity}>{alert.message}</Alert> <br />
        </>
      ) : null}
      <Grid container alignContent={"center"} justifyContent={"center"}>
        <Grid
          item
          xs={12}
          sm={12}
          md={12}
          lg={12}
          style={{
            display: "flex",
            padding: "5px",
            marginBottom: "10px",
            width: "100%",
          }}
        >
          <TextField
            fullWidth
            label="Search"
            variant="outlined"
            placeholder="Search product"
            type="text"
            sx={{ margin: "1px" }}
          />

          <Button variant="outlined" sx={{ margin: "1px" }} disabled={loading}>
            {loading ? "Please wait" : "Search"}
          </Button>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12}>
          <TableContainer container={Paper}>
            <Table sx={{ minWidth: "100%" }} aria-label="Products table">
              <TableHead>
                <TableRow sx={{ background: "#cacaca" }}>
                  <TableCell>
                    <b>SL No.</b>
                  </TableCell>
                  <TableCell>
                    <b>Ad Id</b>
                  </TableCell>
                  <TableCell align="left">
                    <b>Title</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Price(&#8377;)</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Quantity</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Category</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Updated At</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Total Price (&#8377;)</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Action</b>
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {products.map((row, i) => (
                  <TableRow
                    key={row.productId}
                    sx={{
                      "&:last-child td, &:last-child th": { border: 0 },
                    }}
                  >
                    <TableCell component="th" scope="row">
                      {i + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      {row.adId}
                    </TableCell>
                    <TableCell align="left">{row.title}</TableCell>
                    <TableCell align="center">{row.price}</TableCell>
                    <TableCell align="center">{row.quantity}</TableCell>
                    <TableCell align="center">
                      {
                        categories.find(
                          (cat) => cat.categoryId === row.categoryId
                        )?.name
                      }
                    </TableCell>
                    <TableCell align="center">
                      {row.updatedAt?.split("T")[0]}
                    </TableCell>
                    <TableCell align="center">
                      {row.quantity * row.price}
                    </TableCell>
                    <TableCell align="right">
                      <Button
                        variant="outlined"
                        color="error"
                        onClick={(e) => handleDeleteProduct(row.productId)}
                      >
                        Delete
                      </Button>
                      &nbsp;
                      <Button variant="outlined" color="success" component="a">
                        <a
                          style={{ textDecoration: "none" }}
                          target="_blank"
                          href={`http://localhost:3000/view/ad/${row.adId}`}
                        >
                          View
                        </a>
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </Grid>
    </>
  );
}
