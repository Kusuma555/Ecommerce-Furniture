import { Button } from "@mui/material";
import { Container } from "@mui/system";
import { useRouter } from "next/router";
import React, { useContext, useEffect, useState } from "react";
import BackButton from "../../../components/BackButton";
import ItemDetails from "../../../components/ItemDetails";
import { getCategory } from "../../../connections/category";
import { getProduct } from "../../../connections/product";
import { getImages } from "../../../connections/upload-image";
import { AuthContext } from "../../../contexts/AuthContext";

export default function ViewProduct() {
  const { authToken } = useContext(AuthContext);

  const router = useRouter();
  const [product, setProduct] = useState({});
  const [images, setImages] = useState([]);
  const [category, setCategory] = useState("");
  const pid = router.query.pid;

  useEffect(() => {
    if (!pid) return;

    getProduct(
      { productId: pid },
      {
        success: (res) => {
          const data = res.data.data;
          if (!data.error) {
            document.title = data.title;

            setProduct(data);

            getCategory(
              { categoryId: data.categoryId },
              {
                success: (res) => {
                  const data = res.data.data;
                  setCategory(data.name);
                },
                error: (err) => {},
              }
            );
          }

          console.log(temp);
        },
        error: (err) => {},
      }
    );

    getImages(
      { ownerId: `prod${pid}` },
      {
        success: (res) => {
          const data = res.data.data;
          setImages(data.urls);
        },
        error: (err) => {},
      }
    );
  }, [pid]);

  return (
    <Container>
      <BackButton />

      <br />
      <br />
      <ItemDetails
        authToken={authToken}
        product={product}
        images={images}
        category={category}
      />
    </Container>
  );
}
