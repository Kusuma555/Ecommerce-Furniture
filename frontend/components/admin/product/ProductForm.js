import React, { useContext, useState, useEffect } from "react";
import {
  Alert,
  Button,
  Typography,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { getCategories } from "../../../connections/category";
import { addProduct } from "../../../connections/product";
import { uploadImage } from "../../../connections/upload-image";
import Carousel from "react-material-ui-carousel";

const MAX_COUNT = 5;
const MIN_COUNT = 2;

export default function ProductForm({ authToken }) {
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [filesSelected, setFilesSelected] = useState(false);
  const [alert, setAlert] = useState({});

  const [product, setProduct] = useState({
    title: "",
    description: "",
    price: "",
    stock: "",
    categoryId: "",
  });

  const handleFileChange = (e) => {
    const chosenFiles = Array.prototype.slice.call(e.target.files);

    if (chosenFiles.length > MAX_COUNT || chosenFiles.length < MIN_COUNT) {
      setAlert({
        severity: "error",
        message: `You can only add a maximum of ${MAX_COUNT} images and minimum of ${MIN_COUNT}`,
      });
      // setFileLimit(true);
      return true;
    }

    setFilesSelected(true);
    setUploadedFiles(chosenFiles);
    // console.log(uploadedFiles);
  };

  const handleOnSubmit = (e) => {
    e.preventDefault();

    setAlert({});

    if (uploadedFiles.length > MAX_COUNT || uploadedFiles.length < MIN_COUNT) {
      setAlert({
        severity: "error",
        message: "Select atleast 2 images (max 5)",
      });
      return;
    }
    setLoading(true);

    const files = new FormData();
    for (const key of Object.keys(uploadedFiles)) {
      files.append("files", uploadedFiles[key]);
    }

    addProduct(
      { authToken, product },
      {
        success: (res) => {
          const data = res.data.data;
          //upload images
          if (!res.data.error) {
            const ownerId = `prod${data.productId}`;
            uploadImage(
              { authToken, ownerId, files },
              {
                success: (res) => {
                  setLoading(false);
                  setAlert({
                    severity: "success",
                    message: "Added Successfully",
                  });

                  setProduct({
                    title: "",
                    description: "",
                    price: "",
                    stock: "",
                    categoryId: "",
                  });
                  setUploadedFiles([]);
                },
                error: (err) => {
                  const msg = err?.response?.data?.message;
                  setAlert({
                    severity: "error",
                    message: msg ? msg : err.message,
                  });
                  setLoading(false);
                },
              }
            );
          }
        },

        error: (err) => {
          const msg = err?.response?.data?.message;
          setAlert({
            severity: "error",
            message: msg ? msg : err.message,
          });
          setLoading(false);
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
          setCategories([...data]);
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
  }, []);

  return (
    <>
      <hr />
      <Typography align="center" variant="h5" sx={{ paddingBottom: "10px" }}>
        Add Product
      </Typography>
      <hr />
      <Carousel>
        {uploadedFiles.map((file, i) => (
          <img
            style={{ width: 480, height: 320, padding: 5 }}
            key={i}
            src={URL.createObjectURL(file)}
            alt="Thumb"
          />
        ))}
      </Carousel>
      <br />
      <br />
      <form onSubmit={handleOnSubmit}>
        {alert.message ? (
          <>
            <Alert severity={alert.severity}>{alert.message}</Alert>
            <br />
          </>
        ) : null}

        <Button
          variant={!filesSelected ? "outlined" : "contained"}
          color={!filesSelected ? "error" : "success"}
          component="label"
          fullWidth
        >
          Select Images to Upload
          <input
            type="file"
            accept=".jpg,.jpeg,.png"
            onChange={handleFileChange}
            multiple
            hidden
          />
        </Button>

        <br />
        <br />

        <TextField
          fullWidth
          label="Product Name"
          variant="outlined"
          type="text"
          value={product?.title}
          onChange={(e) => setProduct({ ...product, title: e.target.value })}
          required
        />
        <br />
        <br />
        <TextField
          fullWidth
          label="Product Price (Rs.)"
          variant="outlined"
          type="number"
          value={product?.price}
          onChange={(e) => setProduct({ ...product, price: e.target.value })}
          required
        />
        <br />
        <br />

        <TextField
          fullWidth
          label="Product Stock (Available quantity)"
          variant="outlined"
          type="number"
          value={product?.stock}
          onChange={(e) => setProduct({ ...product, stock: e.target.value })}
          required
        />
        <br />
        <br />

        <TextField
          fullWidth
          label="Product Description"
          variant="outlined"
          type="text"
          multiline
          rows={4}
          value={product?.description}
          onChange={(e) =>
            setProduct({ ...product, description: e.target.value })
          }
          required
        />
        <br />
        <br />
        <FormControl fullWidth required>
          <InputLabel id="product-category">Product Category</InputLabel>
          <Select
            labelId="product-category"
            // value={age}
            // onChange={handleChange}
            // defaultValue="chair"
            value={product?.categoryId}
            onChange={(e) =>
              setProduct({ ...product, categoryId: e.target.value })
            }
          >
            {categories.map((row) => {
              return (
                <MenuItem key={row.categoryId} value={row.categoryId}>
                  {row.name}
                </MenuItem>
              );
            })}
          </Select>
        </FormControl>
        <br />
        <br />
        <Button
          variant="contained"
          type="submit"
          disabled={loading ? true : !filesSelected}
          fullWidth
        >
          {loading ? "Please wait" : "Add Product"}
        </Button>
      </form>
    </>
  );
}
