import { Container } from "@mui/system";
import { useRouter } from "next/router";
import React, { useContext, useEffect } from "react";
import ProductPanel from "../../../../components/admin/product/ProductPanel";
import BackButton from "../../../../components/BackButton";
import { AuthContext } from "../../../../contexts/AuthContext";
import { NavContext } from "../../../../contexts/NavContext";

export default function index() {
  const { setLoading, setAlert } = useContext(NavContext);
  const { authToken } = useContext(AuthContext);
  const router = useRouter();

  useEffect(() => {
    document.title = "Adim: Products";
    if (!authToken) router.push(`${router.basePath}/admin`);
  }, [authToken]);

  return (
    <Container>
      <BackButton />
      {authToken ? <ProductPanel /> : null}
    </Container>
  );
}
