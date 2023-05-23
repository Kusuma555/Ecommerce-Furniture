import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";
import { getImages } from "../connections/upload-image";
import { getCategory } from "../connections/category";
import { useRouter } from "next/router";

export default function ItemCard({ product, callback }) {
  const [image, setImage] = useState("noimage");
  const router = useRouter();

  useEffect(() => {
    getCategory(
      { categoryId: product.categoryId },
      {
        success: (res) => {
          const data = res.data.data;
          product.category = data.name;

          getImages(
            { ownerId: `prod${product.productId}` },
            {
              success: (res) => {
                const data = res.data.data;
                product.images = data.urls;

                if (!data.error && data.urls.lenght !== 0)
                  setImage(data.urls[0]);
              },
              error: (err) => {
                //console.log(err);
              },
            }
          );
        },
        error: (err) => {
          //console.log(err);
        },
      }
    );
  }, []);

  return (
    <Card
      raised
      sx={{
        maxWidth: 250,
        "&:hover": {
          cursor: "pointer",
          border: "solid",
          borderColor: "gold",
          borderWidth: "thin",
        },
      }}
      onClick={() => {
        router.push(`/view/product/${product.productId}`);
      }}
    >
      <CardMedia
        sx={{
          height: "128px",
          padding: "1em 1em 0 1em",
          objectFit: "contain",
        }}
        component="img"
        image={image}
        title={`${product.title}`}
      />
      <hr />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          &#8377;{product.price}
        </Typography>
        <Typography variant="h6" color="text.secondary">
          {product.title}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {product?.category}
        </Typography>
      </CardContent>
      {/* <CardActions>
        <Button size="small">Add to cart</Button>
        <Button size="small">Buy Now</Button>
      </CardActions> */}
    </Card>
  );
}
