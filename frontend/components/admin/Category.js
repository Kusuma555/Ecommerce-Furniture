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
import React, { useContext, useEffect, useState } from "react";
import {
  addCategory,
  deleteCategory,
  getCategories,
  updateCategory,
} from "../../connections/category";
import { AuthContext } from "../../contexts/AuthContext";

export default function Category() {
  const [category, setCategory] = useState("");
  const [alert, setAlert] = useState({});
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [updateCatId, setUpdateCatId] = useState("");

  const { authToken } = useContext(AuthContext);

  const handleCategoryChange = (e) => setCategory(e.target.value);

  const handleDeleteCategory = (categoryId) => {
    deleteCategory(
      { categoryId, authToken },
      {
        success: (res) => {
          setAlert({
            severity: "success",
            message: "Category deleted successfully",
          });
          setLoading(false);
          // const data = res.data.data;
          setCategories([
            ...categories.filter((c) => c.categoryId !== categoryId),
          ]);
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

  const handleAddCategory = () => {
    if (!category || category === "") {
      setAlert({
        severity: "error",
        message: "Enter a valid category name",
      });
      return;
    }

    setAlert({});
    setLoading(true);

    const resCallback = {
      success: (res) => {
        setAlert({
          severity: "success",
          message: "Category added successfully",
        });
        setLoading(false);
        const data = res.data.data;

        if (updateCatId)
          setCategories(
            categories.map((cat) =>
              cat.categoryId == updateCatId ? data : cat
            )
          );
        else setCategories([...categories, data]);

        setCategory("");
        setUpdateCatId(null);
      },
      error: (err) => {
        setLoading(false);
        const msg = err?.response?.data?.message;
        setAlert({
          severity: "error",
          message: msg ? msg : err.message,
        });

        setUpdateCatId(null);
      },
    };

    if (updateCatId)
      updateCategory(
        { authToken, category, categoryId: updateCatId },
        resCallback
      );
    else addCategory({ category, authToken }, resCallback);
  };

  useEffect(() => {
    getCategories(
      { authToken },
      {
        success: (res) => {
          setLoading(false);
          const data = res.data.data;
          setCategories([...data]);
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
          }}
        >
          <TextField
            fullWidth
            value={category}
            onChange={handleCategoryChange}
            label="Category Name"
            variant="outlined"
            placeholder="Add a Category"
            type="text"
            required
            // disabled={!editAddress}
            sx={{ margin: "1px" }}
          />

          <Button
            variant="outlined"
            sx={{ margin: "1px" }}
            onClick={handleAddCategory}
            disabled={loading}
          >
            {loading ? "Please wait" : updateCatId ? "Update" : "Add"}
          </Button>
        </Grid>
        <Grid item xs={12} sm={12} md={12} lg={12}>
          <TableContainer container={Paper}>
            <Table sx={{ minWidth: 650 }} aria-label="Category table">
              <TableHead>
                <TableRow sx={{ background: "#cacaca" }}>
                  <TableCell>
                    <b>SL No.</b>
                  </TableCell>{" "}
                  <TableCell>
                    <b>Category Id</b>
                  </TableCell>
                  <TableCell align="center">
                    <b>Category Name</b>
                  </TableCell>
                  <TableCell align="right">
                    <b>Action</b>
                  </TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {categories.map((row, i) => (
                  <TableRow
                    key={row.categoryId}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      {i + 1}
                    </TableCell>
                    <TableCell component="th" scope="row">
                      {row.categoryId}
                    </TableCell>
                    <TableCell align="center">{row.name}</TableCell>
                    <TableCell align="right">
                      <Button
                        variant="outlined"
                        color="error"
                        sx={{ marginRight: "1px" }}
                        onClick={(e) => handleDeleteCategory(row.categoryId)}
                      >
                        Delete
                      </Button>

                      <Button
                        variant="outlined"
                        color="warning"
                        sx={{ marginLeft: "1px" }}
                        onClick={(e) => {
                          setUpdateCatId(row.categoryId);
                          setCategory(row.name);
                        }}
                      >
                        Update
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
