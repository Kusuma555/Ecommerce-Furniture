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
  Box,
} from "@mui/material";
import { getCategories } from "../../../connections/category";
import {
  addProduct,
  getProduct,
  updateProduct,
} from "../../../connections/product";
import { getImages, uploadImage } from "../../../connections/upload-image";
import Carousel from "react-material-ui-carousel";

const MAX_COUNT = 5;
const MIN_COUNT = 2;

export default function ProductUpdateForm({ productDetails, authToken }) {
  const [loading, setLoading] = useState(false);
  const [categories, setCategories] = useState([]);
  const [uploadedFiles, setUploadedFiles] = useState([]);
  const [filesSelected, setFilesSelected] = useState(false);
  const [alert, setAlert] = useState({});
  const [searchProductId, setSearchProductId] = useState(
    productDetails?.productId
  );
  const [images, setImages] = useState([]);

  const [product, setProduct] = useState(
    productDetails
      ? productDetails
      : {
          title: "",
          description: "",
          price: "",
          stock: "",
          categoryId: "",
        }
  );

  const handleSearchProductIdChange = (e) => setSearchProductId(e.target.value);

  const handleProductFindSubmit = (e) => {
    e?.preventDefault();

    getProduct(
      { productId: searchProductId },
      {
        success: (res) => {
          setLoading(false);
          const data = res.data.data;
          setProduct({ ...data });
          getImages(
            { authToken, ownerId: `prod${data.productId}` },
            {
              success: (res) => {
                setLoading(false);
                const data = res.data.data;
                setImages(data.urls);
                setFilesSelected(true);
                // console.log(data.urls);
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
        },
        error: (err) => {
          setLoading(false);
          const msg = err?.response?.data?.message;
          setAlert({
            severity: "error",
            message: msg ? msg : err.message,
          });

          setProduct({
            title: "",
            description: "",
            price: "",
            stock: "",
            categoryId: "",
          });
        },
      }
    );
  };

  const handleFileChange = (e) => {
    const chosenFiles = Array.prototype.slice.call(e.target.files);

    if (chosenFiles.length > MAX_COUNT || chosenFiles.length < MIN_COUNT) {
      setAlert({
        severity: "error",
        message: `You can only add a maximum of ${MAX_COUNT} images and minimum of ${MIN_COUNT}`,
      });
      return true;
    }

    setFilesSelected(true);
    setUploadedFiles(chosenFiles);

    let urls = [];
    chosenFiles.forEach((img) => {
      urls = [...urls, URL.createObjectURL(img)];
    });
    setImages(urls);
    // console.log(images);
  };

  const handleOnSubmit = (e) => {
    e.preventDefault();

    setAlert({});

    if (images.length < MIN_COUNT) {
      if (
        uploadedFiles.length > MAX_COUNT ||
        uploadedFiles.length < MIN_COUNT
      ) {
        setAlert({
          severity: "error",
          message: "Select atleast 2 images (max 5)",
        });
        return;
      }
    }
    setLoading(true);

    //files to be send
    const files = new FormData();
    // files.append("files", uploadedFiles);
    for (const key of Object.keys(uploadedFiles)) {
      files.append("files", uploadedFiles[key]);
    }

    const afterSuccess = (res) => {
      setLoading(false);
      setAlert({
        severity: "success",
        message: "Uodated Successfully",
      });

      setProduct({
        title: "",
        description: "",
        price: "",
        stock: "",
        categoryId: "",
      });
      setUploadedFiles([]);
    };

    const afterError = (err) => {
      const msg = err?.response?.data?.message;
      setAlert({
        severity: "error",
        message: msg ? msg : err.message,
      });
      setLoading(false);
    };
    updateProduct(
      { authToken, product },
      {
        success: (res) => {
          const data = res.data.data;
          //upload images
          if (!res.data.error) {
            const ownerId = `prod${data.productId}`;
            if (uploadedFiles.length === 0) {
              afterSuccess();
              return;
            }

            uploadImage(
              { authToken, ownerId, files },
              {
                success: (res) => {
                  afterSuccess(res);
                },
                error: (err) => {
                  afterError(err);
                },
              }
            );
          }
        },

        error: (err) => {
          afterError(err);
        },
      }
    );
  };

  useEffect(() => {
    setLoading(true);

    if (searchProductId) handleProductFindSubmit(null);

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
      {alert.message ? (
        <>
          <Alert severity={alert.severity}>{alert.message}</Alert>
          <br />
        </>
      ) : null}

      <form onSubmit={handleProductFindSubmit}>
        <Box style={{ display: "flex", padding: "5px" }}>
          <TextField
            fullWidth
            label="Product Id"
            variant="outlined"
            type="text"
            value={searchProductId}
            onChange={handleSearchProductIdChange}
            required
          />
          <Button
            variant="contained"
            type="submit"
            disabled={loading}
            sx={{
              width: "20%",
              marginLeft: "3px",
            }}
          >
            {loading ? "Please wait" : "Get Details"}
          </Button>
        </Box>
      </form>

      {product?.productId ? (
        <Box component="div">
          <hr />
          <Typography align="left" variant="h5" sx={{ paddingBottom: "10px" }}>
            Product Details
          </Typography>
          <hr />

          <Carousel autoPlay={false}>
            {images.map((img, i) => (
              <img
                style={{ width: 480, height: 320, padding: 5 }}
                key={i}
                src={img}
                alt="Thumb"
              />
            ))}
          </Carousel>

          <form onSubmit={handleOnSubmit}>
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
              onChange={(e) =>
                setProduct({ ...product, title: e.target.value })
              }
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
              onChange={(e) =>
                setProduct({ ...product, price: e.target.value })
              }
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
              onChange={(e) =>
                setProduct({ ...product, stock: e.target.value })
              }
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
              {loading ? "Please wait" : "Update"}
            </Button>
          </form>
        </Box>
      ) : null}
    </>
  );
}
